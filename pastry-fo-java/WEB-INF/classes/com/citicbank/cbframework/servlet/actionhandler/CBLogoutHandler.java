package com.citicbank.cbframework.servlet.actionhandler;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.citicbank.cbframework.log.CBLogger;
import com.citicbank.cbframework.servlet.customhandler.CBCustomBusinessHandler;
import com.citicbank.cbframework.servlet.customhandler.CBBusinessHandlerResult;
import com.citicbank.cbframework.usercontext.CBUserContext;

public class CBLogoutHandler implements CBActionHandler{
	private CBCustomBusinessHandler customHandler;

	public CBLogoutHandler(CBCustomBusinessHandler customHandler){
		this.customHandler=customHandler;
	}
	
	public JSONObject handleAction(HttpServletRequest request, CBUserContext userContext) throws Exception{
		CBLogger.d("----- handle logout -----");
		
		JSONObject jsonBusiness = new JSONObject();
		
		@SuppressWarnings("unused")
		int result = CBBusinessHandlerResult.RESULT_DONE;
		@SuppressWarnings("unused")
		JSONObject data = null;
		
		// 登出后处理
		if (customHandler != null){
			CBBusinessHandlerResult r = customHandler.handleRequest(request, jsonBusiness);
			result = r.getResult();
			data = r.getData();
		}
		
		userContext.setSessionState(CBUserContext.SESSION_STATE_UNLOGIN);
		jsonBusiness = new JSONObject();
		try {
			jsonBusiness.put("RETCODE", "AAAAAAA");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonBusiness;
	}
}
