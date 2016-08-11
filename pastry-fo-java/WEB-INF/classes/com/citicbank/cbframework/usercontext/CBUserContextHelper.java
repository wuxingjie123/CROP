package com.citicbank.cbframework.usercontext;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.citicbank.cbframework.common.util.CBJSONBusinessUtil;

public class CBUserContextHelper{
	private static CBUserContext timeoutUserContext = new CBDefaultTimeoutUserContext(){
		protected JSONObject getTimeoutBusiness() {
			return CBJSONBusinessUtil.getErrorBusiness("CPFO010", "Session Timeout!");
		}
	};

	public final static CBUserContext getUserContext(HttpSession session) {
		Object obj = session.getAttribute(CBUserContext.USERCONTEXT);
		if (obj instanceof CBUserContext) {
			return (CBUserContext) obj;
		}else {
			return timeoutUserContext ;	
		}
	}
}
