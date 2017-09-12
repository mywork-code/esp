package com.apass.esp.nothing;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.WeexInfoEntity;
import com.apass.esp.domain.kvattr.ShipmentTimeConfigAttr;
import com.apass.esp.domain.vo.CommissionWalletVo;
import com.apass.esp.mq.listener.JDTaskAmqpAccess;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.schedule.JdAfterSaleScheduleTask;
import com.apass.esp.service.common.KvattrService;
import com.apass.esp.service.common.WeexInfoService;
import com.apass.esp.service.order.OrderService;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import com.apass.gfb.framework.utils.MD5Utils;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Controller
public class StaticFileController {

    @Autowired
    private KvattrService kvattrService;

    @Value("${esp.image.uri}")
    private String appWebDomain;
    @Value("${nfs.rootPath}")
    private String rootPath;

    @Autowired
    private  OrderInfoRepository orderInfoRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JDTaskAmqpAccess jdTaskAmqpAccess;

    @Autowired
    private SystemEnvConfig systemEnvConfig;

    @Autowired
    private WeexInfoService weexInfoService;

    @Autowired
    private JdAfterSaleScheduleTask jdAfterSaleScheduleTask;

    @RequestMapping(value = "v1/app_weex")
    @ResponseBody
    public Response getMd5ByFile() {
        InputStream in  =  StaticFileController.class.getClassLoader().getResourceAsStream("static/WebContent/js/commission/commission.weex_v17.js");
        String md5 = MD5Utils.getMd5ByFile(in);
        Map<String, Object> map = new HashMap<>();
        map.put("flag",true);
        map.put("id","app_weex");
        map.put("url",appWebDomain + "/appweb/WebContent/js/commission/commission.weex_v17.js");
        map.put("ver","17");
        map.put("md5",md5);

        IOUtils.closeQuietly(in);

        return Response.successResponse(map);
    }

    @RequestMapping(value = "v2/app_weex")
    @ResponseBody
    public Response getMd5ByFile2() {
        ClassLoader classLoader = StaticFileController.class.getClassLoader();
        List<CommissionWalletVo> commissionWalletVos = Lists.newArrayList();


        InputStream in  =  classLoader.getResourceAsStream("static/WebContent/js/commission/commission.weex_sit_v25.js");

        String md5 = MD5Utils.getMd5ByFile(in);
        CommissionWalletVo commissionWalletVo = new CommissionWalletVo();
        commissionWalletVo.setVer("25");
        commissionWalletVo.setFlag(true);
        commissionWalletVo.setId("commission");
        commissionWalletVo.setUrl(appWebDomain+"/appweb/WebContent/js/commission/commission.weex_sit_v25.js");
        commissionWalletVo.setMd5(md5);
        commissionWalletVo.setOffLine(false);
        commissionWalletVos.add(commissionWalletVo);

        InputStream in2  =  classLoader.getResourceAsStream("static/WebContent/js/wallet/wallet.weex_sit_v5.js");
        String md52 = MD5Utils.getMd5ByFile(in2);
        CommissionWalletVo commissionWalletVo2 = new CommissionWalletVo();
        commissionWalletVo2.setVer("5");
        commissionWalletVo2.setFlag(true);
        commissionWalletVo2.setId("wallet");
        commissionWalletVo2.setUrl(appWebDomain+"/appweb/WebContent/js/wallet/wallet.weex_sit_v5.js");
        commissionWalletVo2.setMd5(md52);
        commissionWalletVo2.setOffLine(false);
        commissionWalletVos.add(commissionWalletVo2);

        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(in2);

        return Response.successResponse(commissionWalletVos);
    }

    @RequestMapping(value = "v3/app_weex")
    @ResponseBody
    public Response getMd5ByFile3() {
        List<CommissionWalletVo> commissionWalletVos = Lists.newArrayList();
        File file1 = null;
        File file2 = null;
        String ver1 = null;
        String ver2 = null;
        String weexPath1 = null;
        String weexPath2 = null;

        try {
            List<WeexInfoEntity> weexInfoEntities = weexInfoService.queryWeexInfoList();
            if (systemEnvConfig.isDEV()){

                for (WeexInfoEntity weexInfoEntity:weexInfoEntities) {
                    if(StringUtils.equals(weexInfoEntity.getWeexEve(),"sit") && StringUtils.equals(weexInfoEntity.getWeexType(),"commision")){
                        file1 = new File(weexInfoEntity.getWeexPath());
                        ver1 = weexInfoEntity.getWeexVer();
                        weexPath1 = weexInfoEntity.getWeexPath();
                    }
                    if(StringUtils.equals(weexInfoEntity.getWeexEve(),"sit") && StringUtils.equals(weexInfoEntity.getWeexType(),"wallet")){
                        file2 = new File(weexInfoEntity.getWeexPath());
                        ver2 = weexInfoEntity.getWeexVer();
                        weexPath2 = weexInfoEntity.getWeexPath();
                    }
                }

            } else if(systemEnvConfig.isUAT()){
                for (WeexInfoEntity weexInfoEntity:weexInfoEntities) {
                    if(StringUtils.equals(weexInfoEntity.getWeexEve(),"uat") && StringUtils.equals(weexInfoEntity.getWeexType(),"commision")){
                        file1 = new File(weexInfoEntity.getWeexPath());
                        ver1 = weexInfoEntity.getWeexVer();
                        weexPath1 = weexInfoEntity.getWeexPath();
                    }
                    if(StringUtils.equals(weexInfoEntity.getWeexEve(),"uat") && StringUtils.equals(weexInfoEntity.getWeexType(),"wallet")){
                        file2 = new File(weexInfoEntity.getWeexPath());
                        ver2 = weexInfoEntity.getWeexVer();
                        weexPath2 = weexInfoEntity.getWeexPath();
                    }
                }
            }else if(systemEnvConfig.isPROD()){
                for (WeexInfoEntity weexInfoEntity:weexInfoEntities) {
                    if(StringUtils.equals(weexInfoEntity.getWeexEve(),"prod") && StringUtils.equals(weexInfoEntity.getWeexType(),"commision")){
                        file1 = new File(weexInfoEntity.getWeexPath());
                        ver1 = weexInfoEntity.getWeexVer();
                        weexPath1 = weexInfoEntity.getWeexPath();
                    }
                    if(StringUtils.equals(weexInfoEntity.getWeexEve(),"prod") && StringUtils.equals(weexInfoEntity.getWeexType(),"wallet")){
                        file2 = new File(weexInfoEntity.getWeexPath());
                        ver2 = weexInfoEntity.getWeexVer();
                        weexPath2 = weexInfoEntity.getWeexPath();
                    }
                }
            }else{
                return Response.fail("发布有误，无法区分是什么环境");
            }

            FileInputStream in1 = new FileInputStream(file1);
            String md5 = MD5Utils.getMd5ByFile(in1);
            CommissionWalletVo commissionWalletVo = new CommissionWalletVo();
            commissionWalletVo.setVer(ver1);
            commissionWalletVo.setFlag(true);
            commissionWalletVo.setId("commission");
            commissionWalletVo.setUrl(appWebDomain+"/static"+weexPath1);
            commissionWalletVo.setMd5(md5);
            commissionWalletVo.setOffLine(false);
            commissionWalletVos.add(commissionWalletVo);

            FileInputStream in2 = new FileInputStream(file2);
            String md52 = MD5Utils.getMd5ByFile(in2);
            CommissionWalletVo commissionWalletVo2 = new CommissionWalletVo();
            commissionWalletVo2.setVer(ver2);
            commissionWalletVo2.setFlag(true);
            commissionWalletVo2.setId("wallet");
            commissionWalletVo2.setUrl(appWebDomain+"/static"+weexPath2);
            commissionWalletVo2.setMd5(md52);
            commissionWalletVo2.setOffLine(false);
            commissionWalletVos.add(commissionWalletVo2);

            IOUtils.closeQuietly(in1);
            IOUtils.closeQuietly(in2);

            return Response.successResponse(commissionWalletVos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return  null;
    }

    @RequestMapping(value = "jsUtils/test1", method = RequestMethod.POST)
    @ResponseBody
    public Response test(@RequestBody Map<String, Object> paramMap) {
        kvattrService.add(new ShipmentTimeConfigAttr());
        return Response.successResponse();
    }

    @RequestMapping(value = "jsUtils/test2", method = RequestMethod.POST)
    @ResponseBody
    public Response test2(@RequestBody Map<String, Object> paramMap) {
        ShipmentTimeConfigAttr t = new ShipmentTimeConfigAttr();
        try {
             t =  kvattrService.get(new ShipmentTimeConfigAttr() );
        }catch (Exception e ){

        }

        return Response.successResponse(t);
    }

    @RequestMapping(value = "jsUtils/initGoodsSaleVolume", method = RequestMethod.POST)
    @ResponseBody
    public Response initGoodsSaleVolume(@RequestBody Map<String, Object> paramMap){
        List<String> orderIdList = orderInfoRepository.initGoodsSaleVolume();
        orderService.updateJdGoodsSaleVolume(orderIdList);
        return Response.successResponse();
    }

    @RequestMapping("/test/mq/sendmsg")
    @ResponseBody
    public String testSendMsgToMq(){
        jdTaskAmqpAccess.directSend("test");
        return "success";
    }


    @RequestMapping("/test/jdAftersale")
    @ResponseBody
    public String jdAfterSaleScheduleTask(){
        jdAfterSaleScheduleTask.handleJdConfirmPreInventoryTask();
        return "success";
    }
}
