package com.apass.esp.web.goods;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.GoodsAttr;
import com.apass.esp.domain.vo.GoodsAttrVo;
import com.apass.esp.service.goods.GoodsAttrService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.log.LogAnnotion;
import com.apass.gfb.framework.log.LogValueTypeEnum;
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
    public ResponsePageBody<GoodsAttr> getGoodsAttrList(GoodsAttr entity) {
        ResponsePageBody<GoodsAttr> respBody = new ResponsePageBody<GoodsAttr>();
        try {
            // 获取页面查询条件
            ResponsePageBody<GoodsAttr> pagination=goodsAttrService.getGoodsAttrPage(entity);
            respBody.setTotal(pagination.getTotal());
            respBody.setRows(pagination.getRows());
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOG.error("商品属性列表查询失败", e);
            respBody.setMsg("商品属性列表查询失败");
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
            } else if(result == 2){
                return Response.fail("商品属性名称重复,新增失败！");
            }else{
                return Response.fail("商品属性新增失败！");
            }
        }catch(Exception e) {
            LOG.error("ADD GOODSATTR EXCEPTION!", e);
            return Response.fail("商品属性新增异常！");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/editGoodsAttr",method = RequestMethod.POST)
    @LogAnnotion(operationType = "商品属性维护", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public Response editGoodsAttr(HttpServletRequest request) {
        try{
            String idedit = HttpWebUtils.getValue(request, "id");
            Long id = Long.parseLong(idedit);
            String name = HttpWebUtils.getValue(request, "name");
            String user = SpringSecurityUtils.getLoginUserDetails().getUsername();
            int result = goodsAttrService.editGoodsAttr(id,name,user);
            if (result == 1) {
                return Response.success("商品属性新增成功！");
            } else if(result == 2){
                return Response.fail("商品属性名称重复,修改失败！");
            }else{
                return Response.fail("商品属性新增失败！");
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
            }else if(result == 2){
                return Response.fail("商品属性关联类目,删除失败！");
            } else {
                return Response.fail("商品属性删除失败！");
            }
        }catch(Exception e) {
            LOG.error("ADD GOODSATTR EXCEPTION!", e);
            return Response.fail("商品属性删除异常！");
        }
    }
    @ResponseBody
    @RequestMapping("/allGoodsAttr")
    public Response listGoodsAttrs(String categoryId) {
        List<GoodsAttrVo> goodsAttrs = null;
        try {
            goodsAttrs = goodsAttrService.listAll(categoryId);
        }catch (Exception e){
            LOG.error("查询全部商品属性失败",e);
            return Response.fail("查询全部商品属性失败");
        }
        return Response.success("查询全部商品属性成功",goodsAttrs);
    }
}