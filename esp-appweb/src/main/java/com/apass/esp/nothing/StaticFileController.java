package com.apass.esp.nothing;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.BsdiffInfoEntity;
import com.apass.esp.domain.entity.WeexInfoEntity;
import com.apass.esp.domain.kvattr.ShipmentTimeConfigAttr;
import com.apass.esp.domain.vo.CommissionWalletVo;
import com.apass.esp.domain.vo.CommissionWalletVoOld;
import com.apass.esp.mq.listener.JDTaskAmqpAccess;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.schedule.JdAfterSaleScheduleTask;
import com.apass.esp.service.common.BsdiffinfoService;
import com.apass.esp.service.common.KvattrService;
import com.apass.esp.service.common.WeexInfoService;
import com.apass.esp.service.order.OrderService;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.MD5Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @Value("${nfs.bsdiff}")
    private String nfsBsdiffPath;

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

    @Autowired
    private BsdiffinfoService bsdiffinfoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(StaticFileController.class);
    private static final String VERPATH = "/verzip";
    private static final String PATCHPATH = "/patchzip";

    @RequestMapping(value = "v1/app_weex")
    @ResponseBody
    @Deprecated
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
    @Deprecated
    public Response getMd5ByFile2() {
        ClassLoader classLoader = StaticFileController.class.getClassLoader();
        List<CommissionWalletVo> commissionWalletVos = Lists.newArrayList();

        InputStream in  =  classLoader.getResourceAsStream("static/WebContent/js/commission/commission.weex_prd_v27.js");

        String md5 = MD5Utils.getMd5ByFile(in);
        CommissionWalletVo commissionWalletVo = new CommissionWalletVo();
        commissionWalletVo.setAndroidVer("27");
        commissionWalletVo.setIosVer("27");
        commissionWalletVo.setFlag(true);
        commissionWalletVo.setId("commission");
        commissionWalletVo.setUrl(appWebDomain+"/appweb/WebContent/js/commission/commission.weex_prd_v27.js");
        commissionWalletVo.setMd5(md5);
        commissionWalletVo.setOffLine(false);
        commissionWalletVos.add(commissionWalletVo);

        InputStream in2  =  classLoader.getResourceAsStream("static/WebContent/js/wallet/wallet.weex_prd_v6.js");
        String md52 = MD5Utils.getMd5ByFile(in2);
        CommissionWalletVo commissionWalletVo2 = new CommissionWalletVo();
        commissionWalletVo2.setAndroidVer("6");
        commissionWalletVo2.setIosVer("6");
        commissionWalletVo2.setFlag(true);
        commissionWalletVo2.setId("wallet");
        commissionWalletVo2.setUrl(appWebDomain+"/appweb/WebContent/js/wallet/wallet.weex_prd_v6.js");
        commissionWalletVo2.setMd5(md52);
        commissionWalletVo2.setOffLine(false);
        commissionWalletVos.add(commissionWalletVo2);

        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(in2);

        return Response.successResponse(commissionWalletVos);
    }

    @RequestMapping(value = "v3/app_weex")
    @ResponseBody
    @Deprecated
    public Response getMd5ByFile3() {
        LOGGER.info("weex,v3自动部署程序开始执行.....,运行环境:{}",systemEnvConfig.getEve());
        List<CommissionWalletVoOld> commissionWalletVos = Lists.newArrayList();
        File file1 = null;
        File file2 = null;
        String ver1 = null;
        String ver2 = null;
        String weexPath1 = null;
        String weexPath2 = null;

        try {
            List<WeexInfoEntity> weexInfoEntities = weexInfoService.queryWeexInfoList();

            for (WeexInfoEntity weexInfoEntity:weexInfoEntities) {
                if(StringUtils.equals(weexInfoEntity.getWeexBlong(),"ajqh")){
                    if(StringUtils.equals(weexInfoEntity.getWeexEve(),systemEnvConfig.getEve()) && StringUtils.equals(weexInfoEntity.getWeexType(),"commission")){
                        file1 = new File(rootPath+weexInfoEntity.getWeexPath());
                        weexPath1 = weexInfoEntity.getWeexPath();
                        ver1 = weexInfoEntity.getWeexVer();
                    }
                    if(StringUtils.equals(weexInfoEntity.getWeexEve(),systemEnvConfig.getEve()) && StringUtils.equals(weexInfoEntity.getWeexType(),"wallet")){
                        file2 = new File(rootPath+weexInfoEntity.getWeexPath());
                        weexPath2 = weexInfoEntity.getWeexPath();
                        ver2 = weexInfoEntity.getWeexVer();
                    }
                }
            }

            LOGGER.info("file1:{},file2:{}",file1.getPath(),file2.getPath());
            FileInputStream in1 = new FileInputStream(file1);
            String md5 = MD5Utils.getMd5ByFile(in1);
            CommissionWalletVoOld commissionWalletVo = new CommissionWalletVoOld();
            commissionWalletVo.setVer(ver1);
            commissionWalletVo.setFlag(true);
            commissionWalletVo.setId("commission");
            commissionWalletVo.setUrl(appWebDomain+"/static"+weexPath1);
            commissionWalletVo.setMd5(md5);
            commissionWalletVo.setOffLine(false);
            commissionWalletVos.add(commissionWalletVo);

            FileInputStream in2 = new FileInputStream(file2);
            String md52 = MD5Utils.getMd5ByFile(in2);
            CommissionWalletVoOld commissionWalletVo2 = new CommissionWalletVoOld();
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

        return Response.successResponse(commissionWalletVos);
    }

    @RequestMapping(value = "v4/app_weex")
    @ResponseBody
    public Response getMd5ByFile4() {
        LOGGER.info("weex,v4自动部署程序开始执行.....,运行环境:{}",systemEnvConfig.getEve());
        List<CommissionWalletVo> commissionWalletVos = Lists.newArrayList();
        File file1 = null;
        File file2 = null;
        String iosVer1 = null;
        String androidVer1 = null;
        String iosVer2 = null;
        String androidVer2 = null;
        String weexPath1 = null;
        String weexPath2 = null;

        try {
            List<WeexInfoEntity> weexInfoEntities = weexInfoService.queryWeexInfoList();

            for (WeexInfoEntity weexInfoEntity:weexInfoEntities) {
                if(StringUtils.equals(weexInfoEntity.getWeexBlong(),"ajqh")){
                    if(StringUtils.equals(weexInfoEntity.getWeexEve(),systemEnvConfig.getEve()) && StringUtils.equals(weexInfoEntity.getWeexType(),"commission")){
                        file1 = new File(rootPath+weexInfoEntity.getWeexPath());
                        iosVer1= weexInfoEntity.getIosVer();
                        androidVer1 = weexInfoEntity.getAndroidVer();
                        weexPath1 = weexInfoEntity.getWeexPath();
                    }
                    if(StringUtils.equals(weexInfoEntity.getWeexEve(),systemEnvConfig.getEve()) && StringUtils.equals(weexInfoEntity.getWeexType(),"wallet")){
                        file2 = new File(rootPath+weexInfoEntity.getWeexPath());
                        iosVer2= weexInfoEntity.getIosVer();
                        androidVer2 = weexInfoEntity.getAndroidVer();
                        weexPath2 = weexInfoEntity.getWeexPath();
                    }
                }
            }

            LOGGER.info("file1:{},file2:{}",file1.getPath(),file2.getPath());
            FileInputStream in1 = new FileInputStream(file1);
            String md5 = MD5Utils.getMd5ByFile(in1);
            CommissionWalletVo commissionWalletVo = new CommissionWalletVo();
            commissionWalletVo.setIosVer(iosVer1);
            commissionWalletVo.setAndroidVer(androidVer1);
            commissionWalletVo.setFlag(true);
            commissionWalletVo.setId("commission");
            commissionWalletVo.setUrl(appWebDomain+"/static"+weexPath1);
            commissionWalletVo.setMd5(md5);
            commissionWalletVo.setOffLine(false);
            commissionWalletVos.add(commissionWalletVo);

            FileInputStream in2 = new FileInputStream(file2);
            String md52 = MD5Utils.getMd5ByFile(in2);
            CommissionWalletVo commissionWalletVo2 = new CommissionWalletVo();
            commissionWalletVo2.setIosVer(iosVer2);
            commissionWalletVo2.setAndroidVer(androidVer2);
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

        return Response.successResponse(commissionWalletVos);
    }

    @RequestMapping(value = "ajp/v1/app_weex")
    @ResponseBody
    public Response getMd5ByFileAjp() {
        LOGGER.info("weex,v5自动部署程序开始执行.....查询安家派weex,运行环境:{}",systemEnvConfig.getEve());
        List<CommissionWalletVo> commissionWalletVos = Lists.newArrayList();
        File file = null;
        String iosVer = null;
        String androidVer = null;
        String weexPath = null;

        try {
            List<WeexInfoEntity> weexInfoEntities = weexInfoService.queryWeexInfoList();

            for (WeexInfoEntity weexInfoEntity:weexInfoEntities) {
                if(StringUtils.equals(weexInfoEntity.getWeexBlong(),"ajp")&&StringUtils.equals(weexInfoEntity.getWeexEve(),systemEnvConfig.getEve())
                        && StringUtils.equals(weexInfoEntity.getWeexType(),"wallet")){
                    file = new File(rootPath+weexInfoEntity.getWeexPath());
                    iosVer = weexInfoEntity.getIosVer();
                    androidVer = weexInfoEntity.getAndroidVer();
                    weexPath = weexInfoEntity.getWeexPath();
                }
            }

            LOGGER.info("安家派wallet.js的绝对路径{}",file.getPath());
            FileInputStream in = new FileInputStream(file);
            String md5 = MD5Utils.getMd5ByFile(in);

            CommissionWalletVo commissionWalletVo = new CommissionWalletVo();
            commissionWalletVo.setIosVer(iosVer);
            commissionWalletVo.setAndroidVer(androidVer);
            commissionWalletVo.setFlag(true);
            commissionWalletVo.setId("wallet");
            commissionWalletVo.setUrl(appWebDomain+"/static"+weexPath);
            commissionWalletVo.setMd5(md5);
            commissionWalletVo.setOffLine(false);
            commissionWalletVos.add(commissionWalletVo);

            IOUtils.closeQuietly(in);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return Response.successResponse(commissionWalletVos);
    }

    @RequestMapping(value = "bsdiff/download")
    @ResponseBody
    public Response downLoad(@RequestBody(required=true) Map<String,Object> paramMap){
        LOGGER.info("bsdiff下载开始执行了,参数:{}", GsonUtils.toJson(paramMap));
        Map<String,String> resultMap = Maps.newHashMap();
        String path = null;
        String md5 = null;
        try{
            String ver = (String)paramMap.get("ver");
            List<BsdiffInfoEntity> bsdiffInfoEntities = bsdiffinfoService.listAll();
            if(CollectionUtils.isEmpty(bsdiffInfoEntities)){
                return Response.fail("数据有误！");
            }

            BsdiffInfoEntity bsdiffInfoEntity = bsdiffInfoEntities.get(0);//选择已上传最大版本号vermax_ver.返回的文件路径为patchpath目录下的vermax_ver.zip文件

            if(StringUtils.isBlank(ver)){//如果版本号为空，说明是第一次打开。返回最新zip包
                path = appWebDomain+"/static"+ nfsBsdiffPath + VERPATH + "/"+ bsdiffInfoEntity.getBsdiffVer()+".zip";
                md5 = MD5Utils.getMD5(new File(rootPath + nfsBsdiffPath + VERPATH + "/"+ bsdiffInfoEntity.getBsdiffVer()+".zip"));
            }else{//如果不是空，判断版本号是否是最新版本号，如果是 url返回空，否则 返回对应patch包
                if(StringUtils.equals(ver,bsdiffInfoEntity.getBsdiffVer())){
                    path = "";
                    md5 = "";
                }else {
                    path = appWebDomain+"/static" + nfsBsdiffPath + PATCHPATH +"/" + bsdiffInfoEntity.getBsdiffVer()+"_"+ver+".zip";
                    md5 = MD5Utils.getMD5(new File(rootPath + nfsBsdiffPath + PATCHPATH +"/" + bsdiffInfoEntity.getBsdiffVer()+"_"+ver+".zip"));
                }
            }
            resultMap.put("ver",bsdiffInfoEntity.getBsdiffVer());
            resultMap.put("url",path);
            resultMap.put("md5",md5);
            LOGGER.info("bsdiff下载执行结束了,返回值:{}", GsonUtils.toJson(resultMap));
        }catch (Exception e){
            LOGGER.error("下载失败",e);
            return Response.fail("下载失败");
        }
        return Response.success("下载成功",resultMap);
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
