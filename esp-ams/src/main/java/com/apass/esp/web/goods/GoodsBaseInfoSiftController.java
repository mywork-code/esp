package com.apass.esp.web.goods;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.goods.SiftGoodFileModel;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.enums.GoodsType;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.utils.FileUtilsCommons;
import com.apass.esp.utils.ImageTools;
import com.apass.esp.utils.PaginationManage;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.log.LogAnnotion;
import com.apass.gfb.framework.log.LogValueTypeEnum;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.apass.gfb.framework.utils.RandomUtils;
/**
 * 说明：用来实现商品精选推荐管理的功能
 *
 * @author xiaohai
 * @version 1.0
 * @date 2016年12月20日
 */
@Controller
@RequestMapping("/application/goods/sift")
public class GoodsBaseInfoSiftController {
    private static final Logger LOG = LoggerFactory.getLogger(GoodsBaseInfoSiftController.class);
    @Autowired
    private GoodsService goodsService;
    @Value("${nfs.rootPath}")
    private String rootPath;
    @Value("${nfs.goods}")
    private String nfsGoods;
    /**
     * INIT
     * @return
     */
    @RequestMapping("/page")
    public String handlePage() {
        return "goods/sift-page";
    }
    /**
     * 精选商品分页查询
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/pagelist")
    public ResponsePageBody<GoodsInfoEntity> handlePageList(HttpServletRequest request) {
        ResponsePageBody<GoodsInfoEntity> respBody = new ResponsePageBody<GoodsInfoEntity>();
        try {
            Page page = new Page();
            String pageNo = HttpWebUtils.getValue(request, "page");
            String pageSize = HttpWebUtils.getValue(request, "rows");
            if(!StringUtils.isAnyBlank(pageNo,pageSize)){
                Integer pageNoNum = Integer.parseInt(pageNo);
                Integer pageSizeNum = Integer.parseInt(pageSize);
                page.setPage(pageNoNum <= 0 ? 1 : pageNoNum);
                page.setLimit(pageSizeNum <= 0 ? 1 : pageSizeNum);
            }
            String merchantName = HttpWebUtils.getValue(request, "merchantName");
            String goodsType = HttpWebUtils.getValue(request, "goodsType");
            String goodsName = HttpWebUtils.getValue(request, "goodsName");
            String goodsCode = HttpWebUtils.getValue(request, "goodsCode");
            String goodsCategoryCombo = HttpWebUtils.getValue(request, "goodsCategoryCombo");
            GoodsInfoEntity goodsInfoEntity = new GoodsInfoEntity();
            goodsInfoEntity.setGoodsType(goodsType);
            goodsInfoEntity.setGoodsCode(goodsCode);
            goodsInfoEntity.setGoodsName(goodsName);
            goodsInfoEntity.setIsDelete("01");
            goodsInfoEntity.setMerchantName(merchantName);
            if (StringUtils.isNotBlank(goodsCategoryCombo)) {
                String[] aArray = goodsCategoryCombo.split("_");
                String level = aArray[0];
                String id = aArray[1];
                if ("1".equals(level)) {
                    if (!("-1".equals(id))) {
                        goodsInfoEntity.setCategoryId1(Long.valueOf(id));
                    }
                } else if ("2".equals(level)) {
                    goodsInfoEntity.setCategoryId2(Long.valueOf(id));
                } else if ("3".equals(level)) {
                    goodsInfoEntity.setCategoryId3(Long.valueOf(id));
                }
            }
            // 获取分页结果返回给页面
            PaginationManage<GoodsInfoEntity> pagination = goodsService.pageForSiftList(goodsInfoEntity, page);
            if (pagination == null) {
                respBody.setTotal(0);
                respBody.setStatus(CommonCode.SUCCESS_CODE);
                return respBody;
            }
            respBody.setTotal(pagination.getTotalCount());
            respBody.setRows(pagination.getDataList());
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOG.error("商品精选列表查询失败", e);
            respBody.setMsg("商品精选列表查询失败");
        }
        return respBody;
    }
    /**
     * 修改商品状态
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    @LogAnnotion(operationType = "设置商品是否为精选", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public Response handleDelete(HttpServletRequest request) {
        try {
            // 查询商品精选数量
            Integer countSift = goodsService.goodsPageListCount();
            // 获取商品的id和商品类型（1--正常，2--精选）
            String goodsId = HttpWebUtils.getValue(request, "goodsId");
            if (StringUtils.isBlank(goodsId)) {
                return Response.fail("要修改的商品ID不能为空");
            }
            String goodsType = HttpWebUtils.getValue(request, "goodsType");
            if (StringUtils.isBlank(goodsType)) {
                return Response.fail("要修改的商品类型不能为空");
            }
            String siftGoodsSort = HttpWebUtils.getValue(request, "siftGoodsSort");
            // 根据id查询商品
            GoodsInfoEntity entity = goodsService.selectByGoodsId(Long.valueOf(goodsId));
            // 设为精选
            if (goodsType.equals(GoodsType.GOOD_SIFT.getCode())&&countSift >= 50) {
                return Response.fail("精选商品的数量不能超过50件");
            }
            // 设为正常
            if (goodsType.equals(GoodsType.GOOD_NORMAL.getCode()) && countSift <= 3) {
                return Response.fail("精选商品的数量不能少于3件");
            }
            entity.setGoodsType(goodsType);
            //当商品由精选商品变为普通商品时  更新精选商品排序字段   并且更新其余精选排序字段
            if(goodsType.equals(GoodsType.GOOD_NORMAL.getCode())){
                entity.setSiftSort(0);
                entity.setUpdateDate(new Date());
                goodsService.updateService(entity);
                GoodsInfoEntity entityTwo = new GoodsInfoEntity();
                entityTwo.setGoodsType(GoodsType.GOOD_SIFT.getCode());
                List<GoodsInfoEntity> lsit = goodsService.goodsSiftList(entityTwo);
                Integer sort = 0;
                for(GoodsInfoEntity en : lsit){
                    en.setSiftSort(++sort);
                    en.setUpdateDate(new Date());
                    entity.setUpdateUser(SpringSecurityUtils.getCurrentUser());
                    goodsService.updateService(en);
                }
            }
            //当商品由普通商品变为精选商品   更新精选商品排序字段   并且更新其余重复精选排序字段
            if (goodsType.equals(GoodsType.GOOD_SIFT.getCode())) {
                Integer sort = Integer.parseInt(siftGoodsSort);
                Integer sort2 = sort;
                entity.setSiftSort(sort);
                goodsService.updateService(entity);
                GoodsInfoEntity entityTwo = new GoodsInfoEntity();
                entityTwo.setGoodsType(goodsType);
                List<GoodsInfoEntity> lsit = goodsService.goodsSiftList(entityTwo);
                for(GoodsInfoEntity en : lsit){
                    if(en.getSiftSort()<sort||en.getId().equals(Long.valueOf(goodsId))){
                        continue;
                    }
                    en.setSiftSort(++sort2);
                    en.setUpdateDate(new Date());
                    entity.setUpdateUser(SpringSecurityUtils.getCurrentUser());
                    goodsService.updateService(en);
                }
            }
            return Response.success("商品状态修改成功！");
        } catch (Exception e) {
            LOG.error("商品状态修改失败！", e);
            return Response.fail("商品状态修改失败！");
        }
    }
    /**
     * 上传精选图片
     * @param siftGoodFileModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/upSiftFile", method = RequestMethod.POST)
    public Response uplogoFile(@ModelAttribute("siftGoodFileModel") SiftGoodFileModel siftGoodFileModel) {
        try {
            MultipartFile file = siftGoodFileModel.getSiftGoodsFile();
            String imgType = ImageTools.getImgType(file);
            String random = RandomUtils.getRandom(10);
            String fileName = "sift_" + siftGoodFileModel.getSiftGoodsId() +random+ "." + imgType;
            String url = nfsGoods + siftGoodFileModel.getSiftGoodsId() + "/" + fileName;
            //图片校验
            boolean checkSiftGoodsImgSize = ImageTools.checkSiftGoodsImgSize(file);// 尺寸
            boolean checkImgType = ImageTools.checkImgType(file);// 类型
            int size = file.getInputStream().available();// 大小
            if (!(checkSiftGoodsImgSize && checkImgType)) {// 350*350; .png,.jpg
                file.getInputStream().close();
                return Response.fail("文件尺寸不符,上传图片尺寸必须是宽：350px,高：350px,格式：.jpg,.png");
            } else if (size > 1024 * 500) {
                file.getInputStream().close();
                return Response.fail("文件不能大于500kb!");
            }
            FileUtilsCommons.uploadFilesUtil(rootPath, url, siftGoodFileModel.getSiftGoodsFile());
            //保存信息至商品表
            GoodsInfoEntity entity = new GoodsInfoEntity();
            entity.setId(Long.valueOf(siftGoodFileModel.getSiftGoodsId()));
            entity.setGoodsSiftUrl(url);
            entity.setUpdateUser(SpringSecurityUtils.getCurrentUser());
            goodsService.updateService(entity);
            return Response.success("上传精选图片成功！");
        } catch (Exception e) {
            LOG.error("上传精选图片失败!", e);
            return Response.fail("上传精选图片失败！");
        }
    }
    /**
     * 编辑精选商品的排序和图片
     * @param siftGoodFileModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/upSiftFileEdit", method = RequestMethod.POST)
    public Response upSiftFileEdit(@ModelAttribute("siftGoodFileModel") SiftGoodFileModel siftSort) {
        MultipartFile file = null;
        InputStream is = null;
        try {
            file = siftSort.getSiftGoodsFile();
            if(file!=null){
                is = file.getInputStream();
                int size = is.available();// 大小
                if(size>0){//选择了图片   先上传图片
                    String imgType = ImageTools.getImgType(file);
                    String random = RandomUtils.getRandom(10);
                    String fileName = "sift_" + siftSort.getSiftGoodsId() +random+ "." + imgType;
                    String url = nfsGoods + siftSort.getSiftGoodsId() + "/" + fileName;
                    //图片校验
                    boolean checkSiftGoodsImgSize = ImageTools.checkSiftGoodsImgSize(file);// 尺寸
                    boolean checkImgType = ImageTools.checkImgType(file);// 类型
                    if (!(checkSiftGoodsImgSize && checkImgType)) {// 350*350; .png,.jpg
                        return Response.fail("文件尺寸不符,上传图片尺寸必须是宽：350px,高：350px,格式：.jpg,.png");
                    } else if (size > 1024 * 500) {
                        return Response.fail("文件不能大于500kb!");
                    }
                    FileUtilsCommons.uploadFilesUtil(rootPath, url, file);
                    //保存信息至商品表
                    GoodsInfoEntity goods = new GoodsInfoEntity();
                    goods.setId(Long.valueOf(siftSort.getSiftGoodsId()));
                    goods.setGoodsSiftUrl(url);
                    goods.setUpdateDate(new Date());
                    goods.setUpdateUser(SpringSecurityUtils.getCurrentUser());
                    int result = goodsService.updateService(goods);
                    if (result != 1) {
                        return Response.fail("修改精选商品失败！");
                    }
                }
            }
            //在修改精选排序字段
            GoodsInfoEntity entity = goodsService.selectByGoodsId(siftSort.getSiftGoodsId());
            Integer sort = Integer.parseInt(siftSort.getSiftSort());
            Integer sort2 = sort;
            entity.setSiftSort(sort);
            entity.setUpdateDate(new Date());
            entity.setUpdateUser(SpringSecurityUtils.getCurrentUser());
            goodsService.updateService(entity);
            GoodsInfoEntity entityTwo = new GoodsInfoEntity();
            entityTwo.setGoodsType(GoodsType.GOOD_SIFT.getCode());
            List<GoodsInfoEntity> lsit = goodsService.goodsSiftList(entityTwo);
            for(GoodsInfoEntity en : lsit){
                if(en.getSiftSort()<sort||en.getId().equals(Long.valueOf(siftSort.getSiftGoodsId()))){
                    continue;
                }
                en.setSiftSort(++sort2);
                en.setUpdateDate(new Date());
                en.setUpdateDate(new Date());
                entity.setUpdateUser(SpringSecurityUtils.getCurrentUser());
                goodsService.updateService(en);
            }
            return Response.success("修改精选商品成功！");
        } catch (Exception e) {
            LOG.error("修改精选商品失败!", e);
            return Response.fail("修改精选商品失败！");
        }finally{
            try {
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                LOG.error("IOException CLOSE EXCEPTION", e);
            }
        }
    }
}