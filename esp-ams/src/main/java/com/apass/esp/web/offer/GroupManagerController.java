package com.apass.esp.web.offer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;

/**
 * 分组管理
 */
@Controller
@RequestMapping(value = "/group/cfg")
public class GroupManagerController {
	
	@ResponseBody
    @RequestMapping(value ="/add/save",method = RequestMethod.POST)
 	public Response groupAddSave(){
		
		
		
		return Response.success("添加成功!");
	}
}
