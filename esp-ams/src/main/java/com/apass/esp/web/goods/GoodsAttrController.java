package com.apass.esp.web.goods;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.GoodsAttr;
import com.apass.esp.service.goods.GoodsAttrService;
import com.apass.esp.utils.PaginationManage;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.log.LogAnnotion;
import com.apass.gfb.framework.log.LogValueTypeEnum;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.HttpWebUtils;
/**
 * 商品属性管理
 * @author ht
 * 20171027  sprint11  新增商品属性维护
 */
@Controller
@RequestMapping("/application/goods/goodsAttr")
public class GoodsAttrController {
    private static final Logger LOG = LoggerFactory.getLogger(GoodsAttrController.class);
    @Autowired
    private GoodsAttrService goodsAttrService;
    /**
     * 商品精选管理页面
     */
    @RequestMapping("/goodsAttribute")
    public String handlePage() {
        return "goods/goodsAttribute";
    }
    /**
     * 商品属性列表查询
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getGoodsAttrList")
    public ResponsePageBody<GoodsAttr> getGoodsAttrList(HttpServletRequest request) {
        ResponsePageBody<GoodsAttr> respBody = new ResponsePageBody<GoodsAttr>();
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
            // 获取页面查询条件
            String attrName = HttpWebUtils.getValue(request, "attrName");
            if (StringUtils.isNoneBlank(attrName)) {
                attrName = URLDecoder.decode(attrName, "utf-8");
            }
            GoodsAttr entity = new GoodsAttr();
            entity.setName(attrName);
            // 获取分页结果返回给页面
            PaginationManage<GoodsAttr> pagination = goodsAttrService.getGoodsAttrList(entity, page);
            if (pagination == null) {
                respBody.setTotal(0);
                respBody.setStatus(CommonCode.SUCCESS_CODE);
            }else{
                respBody.setTotal(pagination.getTotalCount());
                respBody.setRows(pagination.getDataList());
                respBody.setStatus(CommonCode.SUCCESS_CODE);
            }
        } catch (Exception e) {
            LOG.error("商品精选列表查询失败", e);
            respBody.setMsg("商品精选列表查询失败");
        }
        return respBody;
    }
    @ResponseBody
    @RequestMapping(value = "/addGoodsAttr")
    @LogAnnotion(operationType = "商品属性新增", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public Response addGoodsAttr(HttpServletRequest request) {
        try{
            String name = HttpWebUtils.getValue(request, "name");
            String user = SpringSecurityUtils.getLoginUserDetails().getUsername();
            int result = goodsAttrService.addGoodsAttr(name,user);
            if (result == 1) {
                return Response.success("商品属性新增成功！");
              } else {
                return Response.fail("商品属性新增失败！");
              }
        }catch(Exception e) {
            LOG.error("ADD GOODSATTR EXCEPTION!", e);
            return Response.fail("商品属性新增异常！");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/editGoodsAttr",method = RequestMethod.GET)
    @LogAnnotion(operationType = "商品属性维护", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public Response editGoodsAttr(HttpServletRequest request) {
        try{
            String idedit = HttpWebUtils.getValue(request, "id");
            Long id = Long.parseLong(idedit);
            String name = HttpWebUtils.getValue(request, "name");
            String user = SpringSecurityUtils.getLoginUserDetails().getUsername();
            int result = goodsAttrService.editGoodsAttr(id,name,user);
            if (result == 1) {
                return Response.success("商品属性维护成功！");
              } else {
                return Response.fail("商品属性维护失败！");
              }
        }catch(Exception e) {
            LOG.error("ADD GOODSATTR EXCEPTION!", e);
            return Response.fail("商品属性维护异常！");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/deleteGoodsAttr",method = RequestMethod.GET)
    @LogAnnotion(operationType = "商品属性删除", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public Response deleteGoodsAttr(HttpServletRequest request) {
        try{
            String idedit = HttpWebUtils.getValue(request, "id");
            Long id = Long.parseLong(idedit);
            int result = goodsAttrService.deleteGoodsAttr(id);
            if (result == 1) {
                return Response.success("商品属性删除成功！");
              } else {
                return Response.fail("商品属性删除失败！");
              }
        }catch(Exception e) {
            LOG.error("ADD GOODSATTR EXCEPTION!", e);
            return Response.fail("商品属性删除异常！");
        }
    }
}