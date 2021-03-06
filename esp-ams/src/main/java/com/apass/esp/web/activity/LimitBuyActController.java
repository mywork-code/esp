package com.apass.esp.web.activity;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.aisino.UpLoadUtil;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.Kvattr;
import com.apass.esp.domain.entity.LimitBuyAct;
import com.apass.esp.domain.entity.LimitGoodsSku;
import com.apass.esp.domain.entity.activity.LimitBuyActVo;
import com.apass.esp.domain.entity.activity.LimitGoodsSkuVo;
import com.apass.esp.domain.enums.LimitBuyStatus;
import com.apass.esp.domain.enums.StartTime;
import com.apass.esp.service.activity.LimitBuyActService;
import com.apass.esp.service.activity.LimitGoodsSkuService;
import com.apass.esp.utils.FileUtilsCommons;
import com.apass.esp.utils.ImageTools;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.log.LogAnnotion;
import com.apass.gfb.framework.log.LogValueTypeEnum;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.RandomUtils;
/**
 * 限时购后台交互
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/activity/limitBuyActContro")
public class LimitBuyActController {
    private static final Logger LOGGER  = LoggerFactory.getLogger(LimitBuyActController.class);
    @Autowired
    private LimitBuyActService limitBuyActService;
    @Autowired
    private LimitGoodsSkuService limitGoodsSkuService;
    @Value("${nfs.rootPath}")
    private String rootPath;
    @Value("${nfs.reportfile}")
    private String reportfile;
    @Value("${nfs.goods}")
    private String nfsGoods;
    /**
     * 限时购管理页面
     */
    @RequestMapping("/limitBuyPage")
    public String limitBuyPage() {
        return "activity/limitBuy";
    }
    /**
     * 限时购活动列表查询
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getLimitBuyActPage")
    public ResponsePageBody<LimitBuyAct> getLimitBuyActPage(LimitBuyActVo entity) {
        ResponsePageBody<LimitBuyAct> respBody = new ResponsePageBody<LimitBuyAct>();
        try {
            ResponsePageBody<LimitBuyAct> pagination=limitBuyActService.getLimitBuyActPage(entity);
            respBody.setTotal(pagination.getTotal());
            respBody.setRows(pagination.getRows());
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOGGER.error("限时购活动列表查询失败", e);
            respBody.setMsg("限时购活动列表查询失败");
        }
        return respBody;
    }
    /**
     * 活动开始时间下拉框
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getStartTimeDorp", method = RequestMethod.GET)
    public List<Kvattr> getStartTimeDorp() {
        try{
            List<Kvattr> list = new ArrayList<Kvattr>();
            StartTime [] arrtime = StartTime.values();
            for(int i = 0;i<arrtime.length;i++){
                StartTime time = arrtime[i];
                Kvattr kv = new Kvattr();
                kv.setKey(time.getKey());
                kv.setValue(time.getValue());
                list.add(kv);
            }
            return list;
        }catch (Exception e) {
            LOGGER.error("活动开始时间下拉框载入失败!", e);
            return null;
        }
    }
    /**
     * 活动状态下拉框
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getStatusDorp", method = RequestMethod.GET)
    public List<Kvattr> getStatusDorp() {
        try{
            List<Kvattr> list = new ArrayList<Kvattr>();
            LimitBuyStatus [] arrstatus = LimitBuyStatus.values();
            for(int i = 0;i<arrstatus.length;i++){
                LimitBuyStatus status = arrstatus[i];
                Kvattr kv = new Kvattr();
                kv.setKey(status.getKey());
                kv.setValue(status.getValue());
                list.add(kv);
            }
            return list;
        }catch (Exception e) {
            LOGGER.error("活动状态下拉框载入失败!", e);
            return null;
        }
    }
    /**
     * 限时购活动商品列表查询
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getLimitGoodsList")
    public ResponsePageBody<LimitGoodsSkuVo> getLimitGoodsList(LimitGoodsSku entity) {
        ResponsePageBody<LimitGoodsSkuVo> respBody = new ResponsePageBody<LimitGoodsSkuVo>();
        try {
            ResponsePageBody<LimitGoodsSkuVo> pagination=limitGoodsSkuService.getLimitGoodsList(entity);
            respBody.setTotal(pagination.getTotal());
            respBody.setRows(pagination.getRows());
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOGGER.error("限时购活动列表查询失败", e);
            respBody.setMsg("限时购活动列表查询失败");
        }
        return respBody;
    }
    /**
     * 限时购活动新增   上传限时购商品列表
     * @param file
     * @return
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/upLoadLimitGoodsSku")
    @LogAnnotion(operationType = "限时购活动商品上传", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public ResponsePageBody<LimitGoodsSkuVo> upLoadLimitGoodsSku(@ModelAttribute("upFile") LimitBuyActVo actvo) {
        ResponsePageBody<LimitGoodsSkuVo> respBody = new ResponsePageBody<LimitGoodsSkuVo>();
        try{
            List<LimitGoodsSku> listsku = UpLoadUtil.readImportExcelByMultipartFile(actvo.getUpLoadGoodsFile(), LimitGoodsSku.class);
            Map<String ,Object> map = limitGoodsSkuService.findGoodsInfoListBySkuId(listsku);
            List<LimitGoodsSkuVo> listgoods = (List<LimitGoodsSkuVo>) map.get("date");
            respBody.setTotal(listgoods.size());
            respBody.setRows(listgoods);
            respBody.setStatus(CommonCode.SUCCESS_CODE);
            respBody.setMsg("限时购活动商品上传成功!"+map.get("msg").toString());
        }catch(InvalidFormatException e) {
            LOGGER.error("upLoadLimitGoodsSku EXCEPTION!", e);
            respBody.setMsg("限时购活动商品上传异常,文件内容转换错误!");
        }catch(IOException e) {
            LOGGER.error("upLoadLimitGoodsSku EXCEPTION!", e);
            respBody.setMsg("限时购活动商品上传异常,载入文件内容错误!");
        }catch(InstantiationException e) {
            LOGGER.error("upLoadLimitGoodsSku EXCEPTION!", e);
            respBody.setMsg("限时购活动商品上传异常,实例化Document文件异常!");
        }catch(IllegalAccessException e) {
            LOGGER.error("upLoadLimitGoodsSku EXCEPTION!", e);
            respBody.setMsg("限时购活动商品上传异常,非法内存占用错误!");
        }catch(BusinessException e) {
            LOGGER.error("upLoadLimitGoodsSku EXCEPTION!", e);
            respBody.setMsg("限时购活动商品上传异常,"+e.getErrorDesc());
        }catch(Exception e) {
            LOGGER.error("upLoadLimitGoodsSku EXCEPTION!", e);
            respBody.setMsg("限时购活动商品上传异常！商品列表查询失败!");
        }
        return respBody;
    }
    /**
     * 限时购活动新增 
     * 限时购活动开始日期验证  有没有相同时间活动
     * 验证通过    
     * 1。 限时购活动 保存    2。 限时购商品列表  保存
     * @param buyActView
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addLimitBuyAct", method = RequestMethod.POST)
    @LogAnnotion(operationType = "限时购活动新增", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public Response addLimitBuyAct(@RequestBody LimitBuyActVo buyActView) {
        try{
            String username = SpringSecurityUtils.getCurrentUser();
            return limitBuyActService.addLimitBuyAct(buyActView,username);
        }catch(BusinessException e) {
            LOGGER.error("addLimitBuyAct EXCEPTION!", e);
            return Response.fail(e.getErrorDesc());
        }catch(Exception e) {
            LOGGER.error("ADD addLimitBuyAct EXCEPTION!", e);
            return Response.fail("限时购活动新增异常！");
        }
    }
    /**
     * 限时购活动修改
     * 限时购活动开始日期验证  有没有相同时间活动
     * 验证通过    
     * 1。 限时购活动 保存    2。 限时购商品列表  保存
     * @param buyActView
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editLimitBuyAct", method = RequestMethod.POST)
    @LogAnnotion(operationType = "限时购活动修改", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public Response editLimitBuyAct(@RequestBody LimitBuyActVo buyActView) {
        try{
            String username = SpringSecurityUtils.getCurrentUser();
            return limitBuyActService.editLimitBuyAct(buyActView,username);
        }catch(BusinessException e) {
            LOGGER.error("addLimitBuyAct EXCEPTION!", e);
            return Response.fail(e.getErrorDesc());
        }catch(Exception e) {
            LOGGER.error("ADD addLimitBuyAct EXCEPTION!", e);
            return Response.fail("限时购活动修改异常！");
        }
    }
    /**
     * 查询活动状态
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping("/getLimitBuyActStatus")
    public Response getLimitBuyActStatus(LimitGoodsSku entity) {
        try {
            Byte status = limitBuyActService.readEntity(limitGoodsSkuService.readEntity(entity.getId()).getLimitBuyActId()).getStatus();
            return Response.success("限时购活动状态查询成功！",status);
        } catch (Exception e) {
            LOGGER.error("限时购活动状态查询失败", e);
            return Response.fail("限时购活动状态查询失败,");
        }
    }
    /**
     * 限时购活动修改
     * 限时购活动商品删除
     * @param goodsSkuId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editLimitBuyActDeleteGoods")
    @LogAnnotion(operationType = "限时购活动修改", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public Response editLimitBuyActDeleteGoods(Long goodsSkuId) {
        try{
            return limitBuyActService.editLimitBuyActDeleteGoods(goodsSkuId);
        }catch(BusinessException e) {
            LOGGER.error("addLimitBuyAct EXCEPTION!", e);
            return Response.fail("限时购活动修改异常,"+e.getErrorDesc());
        }catch(Exception e) {
            LOGGER.error("ADD addLimitBuyAct EXCEPTION!", e);
            return Response.fail("限时购活动修改异常！");
        }
    }
    /**
     * 限时购URL  上传缩略图  返回该缩略图URL给前端。
     * 
     * @param stockInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/upLoadSkuPic", method = RequestMethod.POST)
    @LogAnnotion(operationType = "新增库存", valueType = LogValueTypeEnum.VALUE_DTO)
    public Response upLoadSkuPic(@ModelAttribute("upFile") LimitBuyActVo skuvo) {
        InputStream is = null;
        try {
            MultipartFile file = skuvo.getUpLoadGoodsFile();
            String imgType = ImageTools.getImgType(file);
            String fileDiName = RandomUtils.getRandom(10);
            String fileName = "skuUrl_"+ fileDiName + "." + imgType;
            Long skuId = skuvo.getLimitBuyActId();
            String url = nfsGoods + skuId + "/" + fileName;
            //缩略图校验
            boolean checkSiftGoodsImgSize = ImageTools.checkGoodsLogoImgSize(file);// 尺寸
            boolean checkImgType = ImageTools.checkImgType(file);// 类型
            int size = file.getInputStream().available();// 大小
            is = file.getInputStream();
            if (!(checkSiftGoodsImgSize && checkImgType)) {// 130*130px;// .png,.jpg
                return Response.fail("上传缩略图尺寸不符合 宽:130px,高:130px,格式:.jpg,.png");
            } else if (size > 1024 * 300) {
                return Response.fail("文件不能大于300kb!");
            }
            //上传文件
            FileUtilsCommons.uploadFilesUtil(rootPath, url,file);
            return Response.success("success", url);
        } catch (Exception e) {
            LOGGER.error("上传缩略图失败!", e);
            return Response.fail("上传缩略图失败!");
        }finally{
            try {
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                LOGGER.error("IOException COLSE !", e);
            }
        }
    }
    /**
     * 膜版下载
     * 
     * @param response
     * @return
     */
    @RequestMapping(value = "/downloadTemplate", method = RequestMethod.POST)
    @LogAnnotion(operationType = "", valueType = LogValueTypeEnum.VALUE_EXPORT)
    public void downloadTemplate(HttpServletResponse response) {
        OutputStream os = null;
        InputStream is = null;
        try {
            String fileName = "upLoadTemplate";
            String filePath = reportfile + fileName + ".xls";
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.addHeader("Content-Disposition","attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));// 设置文件名
            Long cost = limitBuyActService.downloadTemplate(filePath);
            if(cost!=-1L){
                os = response.getOutputStream();;
                is = new FileInputStream(new File(filePath));
                byte[] b = new byte[1024];
                int i = 0;
                while ((i = is.read(b)) > 0) {
                    os.write(b, 0, i);
                }
                os.flush();
            }
        } catch (Exception e) {
            LOGGER.error("导出文件失败", e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                LOGGER.error("关闭资源失败。", e);
            }finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    LOGGER.error("关闭资源失败。", e);
                }
            }
        }
    }
}