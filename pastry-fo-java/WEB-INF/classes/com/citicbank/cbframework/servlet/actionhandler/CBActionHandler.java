package com.citicbank.cbframework.servlet.actionhandler;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.citicbank.cbframework.usercontext.CBUserContext;

public interface CBActionHandler {
	JSONObject handleAction(HttpServletRequest request, CBUserContext userContext) throws Exception;
}
