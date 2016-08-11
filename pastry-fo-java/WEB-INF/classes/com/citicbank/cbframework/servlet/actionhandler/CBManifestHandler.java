package com.citicbank.cbframework.servlet.actionhandler;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.citicbank.cbframework.common.util.CBStreamOperator;
import com.citicbank.cbframework.log.CBLogger;
import com.citicbank.cbframework.utils.CBFileCache;
import com.citicbank.cbframework.usercontext.CBUserContext;

public class CBManifestHandler implements CBActionHandler{
	
	public JSONObject handleAction(HttpServletRequest request, CBUserContext userContext) throws Exception{
		CBLogger.d("----- handle manifest update -----");

		JSONObject jsonBusiness;
		try {
			// 取得客户端的数据包
			String strComPkg = new String(
					CBStreamOperator.getInputStreamBytes(request
							.getInputStream()), "UTF-8");
			jsonBusiness = userContext.getRequestBusiness(strComPkg);

			String url = jsonBusiness.getString("url");
			String type = jsonBusiness.getString("type");
			String hash = jsonBusiness.getString("hash");

			CBLogger.d(String.format("update %s %s:%s",url,type,hash));
			
			String newHash = CBFileCache.getFileHash(userContext, url, type);

			jsonBusiness = new JSONObject();
			jsonBusiness.put("result", hash.equals(newHash) ? "0" : "1");
			jsonBusiness.put("hash", newHash);

		} catch (Exception e) {
			jsonBusiness = new JSONObject();
			e.printStackTrace();
		}
		return jsonBusiness;
	}
}
