package com.xunda.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/test")
public class TestController{
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public JSONObject mobileCode(ModelMap model,HttpServletRequest request,HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("RETAAAAA", "XXXX");
		
		System.out.println("mobileCode>>>>>>");
		return jsonObject;
	}
	
}