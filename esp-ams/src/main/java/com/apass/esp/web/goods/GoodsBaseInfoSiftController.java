package com.apass.esp.web.goods;

import java.util.Random;

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
import com.apass.esp.domain.entity.Category;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.enums.GoodsType;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.utils.FileUtilsCommons;
import com.apass.esp.utils.ImageTools;
import com.apass.esp.utils.PaginationManage;
import com.apass.esp.utils.ResponsePageBody;
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
	/**
	 * 日志
	 */
	private static final Logger LOG = LoggerFactory.getLogger(GoodsBaseInfoSiftController.class);

	private static final String GOODS_SIFT_PAGE = "goods/sift-page";
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private CategoryInfoService categoryInfoService;
	
	@Value("${nfs.rootPath}")
	private String rootPath;

	/**
	 * 图片服务器地址
	 */
	@Value("${nfs.goods}")
	private String nfsGoods;

	/**
	 * 商品精选管理页面
	 */
	@RequestMapping("/page")
	public String handlePage() {
		return GOODS_SIFT_PAGE;
	}

	/**
	 * 说明：商品分页查询
	 * 
	 * @param request
	 * @return
	 * @author xiaohai
	 * @time：2016年12月23日 下午2:24:43
	 */
	@ResponseBody
	@RequestMapping("/pagelist")
	public ResponsePageBody<GoodsInfoEntity> handlePageList(HttpServletRequest request) {
		ResponsePageBody<GoodsInfoEntity> respBody = new ResponsePageBody<GoodsInfoEntity>();
		try {
			// 获取分页数据
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
			// 获取页面查询条件
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
			PaginationManage<GoodsInfoEntity> pagination = goodsService.page(goodsInfoEntity, page);
			if (pagination == null) {
				respBody.setTotal(0);
				respBody.setStatus(CommonCode.SUCCESS_CODE);
				return respBody;
			}
		    for(int i=0;i<pagination.getDataList().size();i++){
         	   Long categoryId=pagination.getDataList().get(i).getCategoryId3();
                Category category=categoryInfoService.selectNameById(categoryId);
                if(null !=category){
                	pagination.getDataList().get(i).setCategoryName3(category.getCategoryName());
                }
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
	 * 说明：修改商品状态
	 * 
	 * @param request
	 * @return
	 * @author xiaohai
	 * @time：2016年12月21日 下午6:53:42
	 */
	@ResponseBody
	@RequestMapping("/update")
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
			
			// 根据id查询商品
			GoodsInfoEntity goodsInfoEntity = goodsService.selectByGoodsId(Long.valueOf(goodsId));
			
			// 设为精选
			if (goodsType.equals(GoodsType.GOOD_SIFT.getCode())) {
				if (countSift >= 50) {
					return Response.fail("精选商品的数量不能超过50件");
				}
				String goodsSiftUrl = goodsInfoEntity.getGoodsSiftUrl();// 精选图片的url
				if (StringUtils.isBlank(goodsSiftUrl)) {
					return Response.fail("请先请先上传文件！");
				}
			}
			// 设为正常
			if (goodsType.equals(GoodsType.GOOD_NORMAL.getCode()) && countSift <= 3) {
				return Response.fail("精选商品的数量不能少于3件");
			}

			GoodsInfoEntity entity = new GoodsInfoEntity();
			entity.setId(Long.valueOf(goodsId));
			entity.setGoodsType(goodsType);
			//当商品由精选商品变为普通商品时 更新时间变为商品创建时间
			if(goodsType.equals(GoodsType.GOOD_NORMAL.getCode())){
				entity.setUpdateDate(goodsInfoEntity.getCreateDate());
			}
			Integer count = goodsService.updateService(entity);
			if (count != 1) {
				return Response.fail("商品状态修改失败");
			}
			return Response.success("商品状态修改成功！");
		} catch (Exception e) {
			LOG.error("商品状态修改失败！", e);
			return Response.fail("商品状态修改失败！");
		}
	}

	/**
	 * 上传精选图片
	 * 
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

			/**
			 * 图片校验
			 */
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

			/**
			 * 上传文件
			 */
			FileUtilsCommons.uploadFilesUtil(rootPath, url, siftGoodFileModel.getSiftGoodsFile());

			/**
			 * 保存信息至商品表
			 */
			GoodsInfoEntity entity = new GoodsInfoEntity();
			entity.setId(Long.valueOf(siftGoodFileModel.getSiftGoodsId()));
			entity.setGoodsSiftUrl(url);
			entity.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
			int result = goodsService.updateService(entity);
			if (result == 1) {
				return Response.success("上传精选图片成功！");
			} else {
				return Response.fail("上传精选图片失败！");
			}
		} catch (Exception e) {
			LOG.error("上传精选图片失败!", e);
			return Response.fail("上传精选图片失败！");
		}
	}
}
