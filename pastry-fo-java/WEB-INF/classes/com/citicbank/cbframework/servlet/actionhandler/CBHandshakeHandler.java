package com.citicbank.cbframework.servlet.actionhandler;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.citicbank.cbframework.common.util.CBStreamOperator;
import com.citicbank.cbframework.log.CBLogger;
import com.citicbank.cbframework.servlet.customhandler.CBCustomBusinessHandler;
import com.citicbank.cbframework.servlet.customhandler.CBBusinessHandlerResult;
import com.citicbank.cbframework.usercontext.CBDefaultUserContext;
import com.citicbank.cbframework.usercontext.CBUserContext;

public class CBHandshakeHandler implements CBActionHandler{
	
	private CBCustomBusinessHandler customHandler;

	public CBHandshakeHandler(CBCustomBusinessHandler customHandler){
		this.customHandler = customHandler;
	}
	
	public JSONObject handleAction(HttpServletRequest request, CBUserContext userContext) throws Exception{
		CBLogger.d("----- handle handshake -----");
		
		// 取得客户端的数据包
		String strComPkg = new String(
				CBStreamOperator.getInputStreamBytes(request
						.getInputStream()), "UTF-8");
                CBLogger.d("handshake encrypt data="+strComPkg);
		userContext = new CBDefaultUserContext(request.getSession(), strComPkg);
		JSONObject handshakeData = userContext.getHandshakeData();

		CBLogger.d("handshake data = " + handshakeData.toString());
		
		int result = CBBusinessHandlerResult.RESULT_DONE;
		JSONObject handshakeResponse = null;
		
		// 握手后处理
		if (customHandler != null) {
			CBBusinessHandlerResult r = customHandler.handleRequest(request, handshakeData);
			result = r.getResult();
			handshakeResponse = r.getData();
		}
		
		if (handshakeResponse==null) {
			handshakeResponse=new JSONObject();
		}
	
		switch (result) {
		case CBBusinessHandlerResult.RESULT_DONE:
			handshakeResponse.put("ret", "OK");
			break;
		default:
			handshakeResponse.put("ret", "Failed");
			break;
		}
		
		return handshakeResponse;
	}
}
