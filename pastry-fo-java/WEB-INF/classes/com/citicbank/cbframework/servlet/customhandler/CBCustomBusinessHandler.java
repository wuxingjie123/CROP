package com.citicbank.cbframework.servlet.customhandler;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

public interface CBCustomBusinessHandler {
	CBBusinessHandlerResult handleRequest(HttpServletRequest request,JSONObject jsonBusiness);
}
