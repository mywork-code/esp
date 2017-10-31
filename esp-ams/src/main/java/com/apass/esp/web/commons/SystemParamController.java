package com.apass.esp.web.commons;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.Kvattr;
import com.apass.esp.domain.entity.WeexInfoEntity;
import com.apass.esp.domain.entity.common.SystemParamEntity;
import com.apass.esp.domain.enums.PaymentStatusEnum;
import com.apass.esp.domain.kvattr.JdSystemParamVo;
import com.apass.esp.domain.kvattr.PaymentVo;
import com.apass.esp.service.common.KvattrService;
import com.apass.esp.service.common.SystemParamService;
import com.apass.esp.service.common.WeexInfoService;
import com.apass.esp.utils.FileUtilsCommons;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.log.LogAnnotion;
import com.apass.gfb.framework.log.LogValueTypeEnum;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.security.userdetails.ListeningCustomSecurityUserDetails;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpWebUtils;

/**
 * 
 * @description 系统参数管理
 *
 * @author chenbo
 * @version $Id: SystemParamController.java, v 0.1 2017年1月11日 上午11:15:57 chenbo
 *          Exp $
 */
@Controller
@RequestMapping("/application/system/param")
public class SystemParamController {
    @Value("${nfs.rootPath}")
    private String rootPath;
    @Value("${nfs.weex}")
    private String nfsWeexRoot;
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(SystemParamController.class);

    @Autowired
    private SystemParamService  systemParamService;

    @Autowired
    private KvattrService kvattrService;

    @Autowired
    private WeexInfoService weexInfoService;

    @Autowired
    private SystemEnvConfig systemEnvConfig;

    /**
     * 系统参数信息页面
     */
    @RequestMapping("/page")
    public String handlePage() {
        return "common/param/systemParam";
    }

    @RequestMapping("/jdpage")
    public String jdPage(){
        return "common/param/jdSystemPage";
    }
    
    @RequestMapping("/paymentPage")
    public String paymentPage(){
    	return "common/param/paymentPage";
    }
    
    /**
     * 配置支付方式选项是否显示
     * @param request
     * @return
     */
    @RequestMapping("/payment/query")
    @ResponseBody
    public ResponsePageBody<PaymentVo> queryPaymentParams(HttpServletRequest request){ 
    	ResponsePageBody<PaymentVo> respBody = new ResponsePageBody<PaymentVo>();
        try{
        	PaymentVo paymentVo = kvattrService.get(new PaymentVo());
            LOG.info("respBody：{}", GsonUtils.toJson(paymentVo));
            List<PaymentVo> list = new ArrayList<PaymentVo>();
            list.add(paymentVo);
            for (PaymentVo payment : list) {
				payment.setAlipay(PaymentStatusEnum.getCode(payment.getAlipay()));
				payment.setBackUnion(PaymentStatusEnum.getCode(payment.getBackUnion()));
			}
            respBody.setMsg("支付方式参数查询成功");
            respBody.setStatus("1");
            respBody.setRows(list);
        }catch (Exception e){
            LOG.error("支付方式管理，系统参数查询失败:{}",e);
        }
        return respBody;
    }
    
    @RequestMapping("/payment/update")
    @ResponseBody
    @LogAnnotion(operationType = "支付方式编辑", valueType = LogValueTypeEnum.VALUE_DTO)
    public Response updatePaymentParam(@RequestBody PaymentVo paymentVo){
        try{
        	PaymentVo paymentVo1 = kvattrService.get(new PaymentVo());
            if (paymentVo1 == null) {
                kvattrService.add(paymentVo1);
            } else {
                List<Kvattr> list = kvattrService.getTypeName(paymentVo1);
                List<Kvattr> list2= new ArrayList<>();
                for (Kvattr kvattr:list) {
                    if(kvattr.getKey().equalsIgnoreCase("alipay")){
                        String alipay = paymentVo.getAlipay();
                        kvattr.setValue(alipay);
                    }
                    if(kvattr.getKey().equalsIgnoreCase("backunion")){
                    	String backUnion = paymentVo.getBackUnion();
                        kvattr.setValue(backUnion);
                    }
                    list2.add(kvattr);
                }
                kvattrService.update(list2);
            }
            return Response.successResponse();
        }catch (Exception e){
            LOG.error("修改支付方式系统参数失败:{}",e);
            return Response.fail("修改支付方式系统参数失败");
        }
    }

    @RequestMapping("/jd/query")
    @ResponseBody
    public ResponsePageBody<JdSystemParamVo> queryJdSystemParam(HttpServletRequest request){
        ResponsePageBody<JdSystemParamVo> respBody = new ResponsePageBody<>();
        try{
            JdSystemParamVo jdSystemParamVo = kvattrService.get(new JdSystemParamVo());
            LOG.info("respBody：{}", GsonUtils.toJson(jdSystemParamVo));
            List<JdSystemParamVo> list = new ArrayList();
            list.add(jdSystemParamVo);
            respBody.setMsg("京东商品售价系统参数查询成功");
            respBody.setStatus("1");
            respBody.setRows(list);
        }catch (Exception e){
            LOG.error("京东商品售价管理，系统参数查询失败:{}",e);
        }
        return respBody;
    }



    @RequestMapping("/jd/update")
    @ResponseBody
    @LogAnnotion(operationType = "京东售价系数编辑", valueType = LogValueTypeEnum.VALUE_DTO)
    public Response updateJdSystemParam(@RequestBody JdSystemParamVo jdSystemParamVo){
        try{
            JdSystemParamVo jdSystemParamVo1 = kvattrService.get(new JdSystemParamVo());
            if (jdSystemParamVo1 == null) {
                kvattrService.add(jdSystemParamVo);
            } else {
                List<Kvattr> list = kvattrService.getTypeName(jdSystemParamVo1);
                List<Kvattr> list2= new ArrayList<>();
                for (Kvattr kvattr:list) {
                    if(kvattr.getKey().equalsIgnoreCase("protocolPrice1")){
                        String protocolPrice1 = jdSystemParamVo.getProtocolPrice1();
                        BigDecimal p1 = new BigDecimal(protocolPrice1).setScale(2,BigDecimal.ROUND_HALF_UP);
                        kvattr.setValue(p1.toString());
                    }
                    if(kvattr.getKey().equalsIgnoreCase("protocolPrice2")){
                        String protocolPrice2 = jdSystemParamVo.getProtocolPrice2();
                        BigDecimal p2 = new BigDecimal(protocolPrice2).setScale(2,BigDecimal.ROUND_HALF_UP);
                        kvattr.setValue(p2.toString());
                    }
                    if(kvattr.getKey().equalsIgnoreCase("protocolPrice3")){
                        String protocolPrice3 = jdSystemParamVo.getProtocolPrice3();
                        BigDecimal p3 = new BigDecimal(protocolPrice3).setScale(2,BigDecimal.ROUND_HALF_UP);
                        kvattr.setValue(p3.toString());
                    }
                    list2.add(kvattr);
                }
                kvattrService.update(list2);
            }
            return Response.successResponse();
        }catch (Exception e){
            LOG.error("修改京东价格系统参数失败:{}",e);
            return Response.fail("修改京东价格系统参数失败");
        }
    }

    /**
     * 系统参数信息查询
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/pagelist")
    public ResponsePageBody<SystemParamEntity> querySystemParam(HttpServletRequest request) {
        ResponsePageBody<SystemParamEntity> respBody = new ResponsePageBody<SystemParamEntity>();
        try {
            List<SystemParamEntity> systemParamList = systemParamService.querySystemParamInfo();
            respBody.setRows(systemParamList);
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOG.error("系统参数信息查询失败", e);
            respBody.setStatus(CommonCode.FAILED_CODE);
        }
        return respBody;
    }

    /**
     * 系统参数信息修改
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public Response updateSystemParam(HttpServletRequest request) {
        try {
            // 获取请求的参数
            String id = HttpWebUtils.getValue(request, "id");
            String merchantSettleRate = HttpWebUtils.getValue(request, "merchantSettleRate");
//            String goodsPriceRate = HttpWebUtils.getValue(request, "goodsPriceRate");
            String priceCostRate = HttpWebUtils.getValue(request, "priceCostRate");
            if(StringUtils.isNotBlank(priceCostRate)){
            	priceCostRate = new BigDecimal(priceCostRate).divide(new BigDecimal(100)).setScale(4, BigDecimal.ROUND_HALF_UP).toString();
            }

            // 获取商户号
            ListeningCustomSecurityUserDetails listeningCustomSecurityUserDetails = SpringSecurityUtils
                .getLoginUserDetails();
            String userName = listeningCustomSecurityUserDetails.getUsername();

            // 拼接参数
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", id);
            map.put("merchantSettleRate", merchantSettleRate);
//            map.put("goodsPriceRate", goodsPriceRate);
            map.put("priceCostRate", priceCostRate);
            map.put("userName", userName);
            map.put("systemtime", DateFormatUtil.getCurrentDate());

            // 验证参数
            paramCheck(map);

            systemParamService.updateSystemParamInfo(map);
        } catch (BusinessException e) {
            LOG.error("系统参数信息查询失败", e);
            return Response.fail(e.getErrorDesc());
        }
        return Response.success("修改系统参数成功！");
    }

    /**
     * 热部署安卓weex相关js
     */
    @RequestMapping("/weexPage")
    public String weexPage() {
        return "common/weex/weexPage";
    }

    /**
     * 查询js列表
     * @return
     */
    @ResponseBody
    @RequestMapping("/listAndriodJs")
    public ResponsePageBody queryCommisionAndWallet(){
        ResponsePageBody<WeexInfoEntity> respBody = new ResponsePageBody<WeexInfoEntity>();
        try {
            List<WeexInfoEntity> weexInfoEnties = weexInfoService.queryWeexInfoList();
            respBody.setRows(weexInfoEnties);
            respBody.setTotal(weexInfoEnties.size());
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOG.error("weex信息列表查询失败", e);
            respBody.setStatus(CommonCode.FAILED_CODE);
        }
        return respBody;
    }

    @Deprecated
    @ResponseBody
    @RequestMapping("/updateWeex")
    public Response updateWeexJs(@ModelAttribute("weexInfoEntity")WeexInfoEntity weexInfoEntity) {
        try{
            //上传js到服务端，覆盖之前js,名称不变
            MultipartFile weexFile = weexInfoEntity.getWeexFile();
            String url = null;
            if(systemEnvConfig.isDEV()){
                if(StringUtils.equals("commission",weexInfoEntity.getWeexType())){
                    url = nfsWeexRoot + "/sit/commission.weex_sit.js";
                }else {
                    url = nfsWeexRoot + "/sit/wallet.weex_sit.js";
                }
            }else if(systemEnvConfig.isUAT()){
                if(StringUtils.equals("commission",weexInfoEntity.getWeexType())){
                    url = nfsWeexRoot + "/uat/commission.weex_uat.js";
                }else {
                    url = nfsWeexRoot + "/uat/wallet.weex_uat.js";
                }
            }else if(systemEnvConfig.isPROD()){
                if(StringUtils.equals("commission",weexInfoEntity.getWeexType())){
                    url = nfsWeexRoot + "/prod/commission.weex_prod.js";
                }else {
                    url = nfsWeexRoot + "/prod/wallet.weex_prod.js";
                }
            }else {
                return Response.fail("发布有误，无法区分是什么环境");
            }

            //修改数据库内容
            weexInfoEntity.setWeexPath(url);
            weexInfoEntity.setUpdateUser(SpringSecurityUtils.getCurrentUser());
            Integer count = weexInfoService.updateWeexJs(weexInfoEntity);
            if(count != 1){
                return Response.fail("更新weex失败");
            }

            /**
             * 上传文件
             */
            FileUtilsCommons.uploadFilesUtil(rootPath, url, weexFile);

        }catch (Exception e){
            LOG.error("--------Exception--------",e);
            return Response.fail("更新weex失败");
        }

        //修改数据库存版本号
        return Response.success("更新weex成功");
    }

    @ResponseBody
    @RequestMapping("/updateWeex2")
    public Response updateWeexJs2(@ModelAttribute("weexInfoEntity")WeexInfoEntity weexInfoEntity) {
        try{
            if(systemEnvConfig.isDEV() && StringUtils.equals("uat",weexInfoEntity.getWeexEve())){
                return  Response.fail("sit环境不能操作uat文件");
            }
            if(systemEnvConfig.isUAT() && StringUtils.equals("sit",weexInfoEntity.getWeexEve())){
                return  Response.fail("uat环境不能操作sit文件");
            }

            //上传js到服务端，覆盖之前js,名称不变
            MultipartFile weexFile = weexInfoEntity.getWeexFile();
            String url = null;
            if(StringUtils.equals("ajqh",weexInfoEntity.getWeexBlong())){
                if(StringUtils.equals("commission",weexInfoEntity.getWeexType())){
                    url = nfsWeexRoot + "/prod/commission.weex_prod.js";
                }else {
                    url = nfsWeexRoot + "/prod/wallet.weex_prod.js";
                }
            }else{
                if(!StringUtils.equals(weexInfoEntity.getWeexType(),"wallet")){
                    return  Response.fail("安家派js无commision.js文件");
                }
                url = nfsWeexRoot + "/prodajp/wallet.weex_prod.js";
            }

            //修改数据库内容
            weexInfoEntity.setWeexPath(url);
            weexInfoEntity.setUpdateUser(SpringSecurityUtils.getCurrentUser());
            Integer count = weexInfoService.updateWeexJs(weexInfoEntity);
            if(count != 1){
                return Response.fail("更新weex失败");
            }

            /**
             * 上传文件
             */
            FileUtilsCommons.uploadFilesUtil(rootPath, url, weexFile);

        }catch (Exception e){
            LOG.error("--------Exception--------",e);
            return Response.fail("更新weex失败");
        }

        //修改数据库存版本号
        return Response.success("更新weex成功");
    }


    public void paramCheck(Map<String, String> map) throws BusinessException {

        if (StringUtils.isAnyBlank(map.get("id"))) {
            throw new BusinessException("主键id不能为空!");
        }

        if (StringUtils.isAnyBlank(map.get("merchantSettleRate"))) {
            throw new BusinessException("商户结算折扣率不能为空!");
        }

        if (Double.parseDouble(map.get("merchantSettleRate")) < 0
            || Double.parseDouble(map.get("merchantSettleRate").toString()) > 1) {
            throw new BusinessException("商户结算折扣率字段不合法，必须在0到1之间!");
        }

//        if (StringUtils.isAnyBlank(map.get("goodsPriceRate"))) {
//            throw new BusinessException("商品价格折扣率不能为空!");
//        }
        if (StringUtils.isAnyBlank(map.get("priceCostRate"))) {
        	throw new BusinessException("保本率不能为空!");
        }

//        if (Double.parseDouble(map.get("goodsPriceRate").toString()) < 0
//            || Double.parseDouble(map.get("goodsPriceRate").toString()) > 1) {
//            throw new BusinessException("商品价格折扣率字段不合法，必须在0到1之间!");
//        }
        if (Double.parseDouble(map.get("priceCostRate").toString()) < 0) {
        	throw new BusinessException("保本率字段不合法，必须大于0");
        }
    }
}
