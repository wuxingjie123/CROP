package com.xunda.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.citicbank.cbframework.log.CBLogger;
import com.citicbank.cbframework.servlet.actionhandler.CBActionHandler;

@Controller
@RequestMapping("/cbframework.do")
public class FrameworkController {
	private CBActionHandler customServletHandler;
	private static Map<String, CBActionHandler> actionHandlerMap = new HashMap<String, CBActionHandler>();

	/**
	 * 演示用的 就不管验证码了
	 * 
	 * @throws IOException
	 */
	// @RequestMapping()
	@ResponseBody()
	@RequestMapping(params = "act=handshake")
	public void mobileCode(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		CBLogger.d("act=handshake============================================");
		String act = request.getParameter("act");
		if (act.equals(Constants.TRADECODEFLAG_SHAKEHANG)) {
			String jsonString = "{'RET_CODE':'ok','UUIDKEY':'abcdefghijk1234567890'}";

			CBLogger.d("jsonString=" + jsonString);
			request.getSession().setAttribute("jsonBusiness", jsonString);
		}
	}

}