package com.citicbank.cbframework.usercontext;

import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.citicbank.cbframework.common.exception.CBHandshakeException;
import com.citicbank.cbframework.security.CBComPackage;
import com.citicbank.cbframework.security.CBDataPackage;
import com.citicbank.cbframework.security.CBServerKeyStore;

public class CBDefaultUserContext extends HashMap<String, String> implements CBUserContext{
	private static final long serialVersionUID = 1L;
	
	protected String clientRandom;
	protected String serverRandom;
	protected String clientType;
	protected String clientVersion;
	protected String deviceId;
	protected String sessionId;
	
	private int sessionState=SESSION_STATE_NULLSESSION;

	private JSONObject handshakeData;

	private JSONObject sessionData = new JSONObject();

	private JSONObject customSessionData = new JSONObject();
	
	public CBDefaultUserContext(){
	
	}
	
	public CBDefaultUserContext(HttpSession session,String strComPkg) throws CBHandshakeException {
				//会话ID
		sessionId=session.getId();
		
		handshakeData=getRequestBusiness(strComPkg);
		try{
			//客户端版本号
			clientVersion=handshakeData.getString("clientVersion");
			//客户端版本号
	        clientType=handshakeData.getString("clientType");
	        //设备标识
	        deviceId=handshakeData.getString("deviceId");
	        //客户端随机数
	        clientRandom=handshakeData.getString("cr");
	        //服务器随机数   
	        serverRandom=UUID.randomUUID().toString().replace("-", "");
	        //登录状态
	        sessionState=SESSION_STATE_UNLOGIN;
	        
	        //将上下文对象放入Session
	        session.setAttribute(USERCONTEXT, this);
		}catch(Exception e){
			throw new CBHandshakeException(e,"握手失败");
		}
	}
	
	public JSONObject getRequestBusiness(String strComPkg){
		CBComPackage comPkg = new CBComPackage(this,CBServerKeyStore.INSTANCE);
    	try {
			comPkg.parseJSONString(strComPkg);
			JSONObject jsonBusiness = new JSONObject(new String(comPkg.getDataPackage().getBusiness(),"UTF-8"));
			System.out.println("============================================="+jsonBusiness.toString());
			return jsonBusiness;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
	}
	
	public String getResponseComPackage(JSONObject jsonBusiness){
		try {
			CBComPackage comPkg = new CBComPackage(this,CBServerKeyStore.INSTANCE);
			CBDataPackage dataPkg = comPkg.getDataPackage();
			dataPkg.setBusiness(jsonBusiness.toString().getBytes("UTF-8"));
			
			//附加会话数据
			//会话状态
			sessionData.put("state", sessionState);
			sessionData.put("data", customSessionData);
			dataPkg.setSessionData(sessionData);
			//dataPkg.setEncryptFlag(0);
			
			//移除权限数据
			sessionData.remove("privilege");
			//移除自定义数据中的事件类数据
			customSessionData.remove("event");
			
			return comPkg.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addSessionData(String type, String key,Object value){
		JSONObject data=customSessionData.optJSONObject(type);
		try {
			if (data==null) {
				data = new JSONObject();
				customSessionData.put(type, data);
			}
			data.put(key, value);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getClientRandom() {
		return clientRandom;
	}

	public String getServerRandom() {
		return serverRandom;
	}
	
	public String getClientType() {
		return clientType;
	}
	
	public int getSessionState() {
		return sessionState;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	public String getClientVersion() {
		return clientVersion;
	}
	
	public void setSessionState(int sessionState) {
		this.sessionState = sessionState;
	}

	public static void markHeader(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
        CBUserContext userContext =(CBUserContext) request.getSession().getAttribute(USERCONTEXT);
		if(userContext.getSessionState()==CBUserContext.SESSION_STATE_TIMEOUT){
			response.setHeader("sessionState",SESSION_STATE_TIMEOUT+"");
		}else{
			response.setHeader("Service-Number",userContext.getServerRandom());
			response.setHeader("sessionState",userContext.getSessionState()+"");
		}
	}

	public String getErrorComPackage(Exception e) {
		CBComPackage errComPackage = new CBComPackage(this,CBServerKeyStore.INSTANCE);
		return errComPackage.toJSONString();
	}
	
	public void setRoles(String roles) {
		
	}
		
	public JSONObject getSessionData() {
		return customSessionData;
	}

	public JSONObject getHandshakeData() {
		return handshakeData;
	}

	public boolean isTimeout() {
		return false;
	}

	public int getContextType() {
		return CONTEXT_TYPE_SERVER;
	}
	/*
	 * 取出3DES密钥存入内存
	 */
	public String getSRKey1() {
		return this.getServerRandom().substring(4, 12);
	}

	public String getCRKey2() {
		return this.getClientRandom().substring(4, 12);
	}

	public String getSessionKey3() {
		String sessionId = this.getSessionId();
		sessionId = sessionId.substring(sessionId.length() - 9, sessionId.length() - 1);
		return sessionId;
	}

}
