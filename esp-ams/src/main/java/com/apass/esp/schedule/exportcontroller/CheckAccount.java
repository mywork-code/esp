package com.apass.esp.schedule.exportcontroller;

import com.apass.gfb.framework.utils.HttpWebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xiaohai on 2017/10/23.
 */
@Controller
@RequestMapping("/checkup/account")
public class CheckAccount {

    @RequestMapping("/orderdetail")
    public void exportOrderDetail(HttpServletRequest request){
        String start = HttpWebUtils.getValue(request, "start");
        //获取数据



        //导出数据

    }
}
