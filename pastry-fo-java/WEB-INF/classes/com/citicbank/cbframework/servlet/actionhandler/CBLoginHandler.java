package com.citicbank.cbframework.servlet.actionhandler;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.citicbank.cbframework.common.util.CBStreamOperator;
import com.citicbank.cbframework.log.CBLogger;
import com.citicbank.cbframework.servlet.customhandler.CBBusinessHandlerResult;
import com.citicbank.cbframework.servlet.customhandler.CBCustomBusinessHandler;
import com.citicbank.cbframework.usercontext.CBUserContext;

public class CBLoginHandler implements CBActionHandler{
	private CBCustomBusinessHandler customHandler;

	public CBLoginHandler(CBCustomBusinessHandler customHandler){
		this.customHandler=customHandler;
		
	}
	
	public JSONObject handleAction(HttpServletRequest request, CBUserContext userContext) throws Exception{
		CBLogger.d("----- handle login -----");
		JSONObject loginResponse = null;
		try {
			// 取得客户端的数据包
			String strComPkg = new String(
					CBStreamOperator.getInputStreamBytes(request
							.getInputStream()), "UTF-8");
			JSONObject jsonBusiness = userContext.getRequestBusiness(strComPkg);
			
			int result = CBBusinessHandlerResult.RESULT_DONE;
			if (customHandler!=null) {
				CBBusinessHandlerResult r = customHandler.handleRequest(request, jsonBusiness);
				result = r.getResult();
				loginResponse = r.getData();
			}
			
			if (loginResponse==null) {
				loginResponse = new JSONObject();
			}
			
			switch (result) {
			case CBBusinessHandlerResult.RESULT_DONE:
				userContext.setSessionState(CBUserContext.SESSION_STATE_LOGIN);
				break;
			default:
				userContext.setSessionState(CBUserContext.SESSION_STATE_UNLOGIN);
				break;
			}
		} catch (Exception e) {
			loginResponse = new JSONObject();
			e.printStackTrace();
		}
		return loginResponse;
	}
}
