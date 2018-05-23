package com.apass.esp.web.commons;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.BsdiffInfoEntity;
import com.apass.esp.domain.entity.BsdiffVo;
import com.apass.esp.service.common.BsdiffinfoService;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by xiaohai on 2018/5/23.
 */
@Controller
@RequestMapping("/noauth/application/system/param")
public class BsdiffColler {
    private static final Logger LOGGER = LoggerFactory.getLogger(BsdiffColler.class);

    @Autowired
    private BsdiffinfoService bsdiffinfoService;
    /**
     * 增量更新升级版
     * @param bsdiffEntity
     * @return
     */
    @ResponseBody
    @RequestMapping("/bsdiffUpload")
    public Response bsdiffUpload2(@ModelAttribute("bsdiffEntiry")BsdiffVo bsdiffVo) {
        try{
            LOGGER.info("bsdiff增量更新开始上传,参数 版本号:{},文件名:{}",bsdiffVo.getBsdiffVer(),bsdiffVo.getBsdiffFile().getOriginalFilename());
            BsdiffInfoEntity bsdiffInfoEntity = new BsdiffInfoEntity();
            bsdiffInfoEntity.setCreatedTime(new Date());
            bsdiffInfoEntity.setCreateUser(SpringSecurityUtils.getCurrentUser());
            bsdiffInfoEntity.setUpdatedTime(new Date());
            bsdiffInfoEntity.setUpdateUser(SpringSecurityUtils.getCurrentUser());
            bsdiffInfoEntity.setIfCompelUpdate(bsdiffVo.getIfCompelUpdate());
            bsdiffinfoService.bsdiffUpload(bsdiffVo,bsdiffInfoEntity);
        }catch (Exception e){
            LOGGER.error("增量添加上传失败",e);
            return Response.fail(e.getMessage());
        }
        return Response.success("增量添加上传成功");
    }
}
