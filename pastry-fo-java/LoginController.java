package com.xunda.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.icitic.netpay.api.LoginService;
import com.icitic.netpay.api.RegisterService;
import com.xunda.common.model.ResultObject;

@Controller
@RequestMapping("/login.do")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private RegisterService registerService;
    

    /**
     *5080001-登录
     */
    @RequestMapping()
    @ResponseBody
    public void login_5080001(ModelMap model, HttpServletRequest request,
	    HttpServletResponse response) {
	ResultObject<String> retObj = null;
	if (Constants.MOSHIFLAG) {

	    // 解析从客户端，接收的json对象
	    String jsonString = "{'PHONE_NO':'18611888989','LOG_PASSWORD':'112233'}";
	    //String jsonString = "{'QUERYNM':'10','STARTNM':'1','STARTDATE':'20150215','CURRTYPE':'01','ACCNO':'61fadb29','ENDDATE':'20150316','BASEACC':'e26ece21'}";
	    JSONObject jb = JSONObject.parseObject(jsonString);
	    String PHONE_NO = jb.getString("PHONE_NO");
	    System.out.println("PHONE_NO=" + PHONE_NO);
	    String LOG_PASSWORD = jb.getString("LOG_PASSWORD");
	    System.out.println("LOG_PASSWORD=" + LOG_PASSWORD);

	    // 调用服务端接口，进行交易处理
	    String username = request.getParameter("username").toLowerCase();
	    username = jb.getString("PHONE_NO");
	    String password = request.getParameter("password");
	    password = jb.getString("LOG_PASSWORD");
	    String ip = request.getRemoteAddr();// 实际根据前置部署来获得，此处仅仅是演示
	    String hardcode = request.getParameter("hardcode");
	    System.out.println("loginService="+loginService);
	    //retObj = 
		    loginService.login("app", username, password, ip, hardcode);
	    
	} else {
	    
	    // 生产的数据
	    String jsonString = request.getAttribute("paramObject").toString();
	    JSONObject business_json = Constants.parse_json(jsonString);
//	    String QUERYNM1 = business_json.getString("QUERYNM");
//	    System.out.println("QUERYNM1=" + QUERYNM1);

	    // 生产
	    String username = business_json.getString("PHONE_NO");
	    String password = business_json.getString("LOG_PASSWORD");
	    String ip = business_json.getString("ip");
	    String hardcode = business_json.getString("hardcode");
	    retObj = loginService.login("app", username, password, ip, hardcode);
	    System.out.println("retObj>>>" + retObj.isSuccess());

	}

	// 返回调用方的json对象
	JSONObject jsonObject = new JSONObject();
	// 根据 retObj，进行处理
	//if (retObj.isSuccess()) {
	if (retObj!=null) {
	    jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
	    jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
	    jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
	    jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
	    jsonObject.put(Constants.SERVER_LIST, retObj.getResult());
	} else {
	    jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
	    jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_FAIL);
	    jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
	    jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
	}
	request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
    }

}