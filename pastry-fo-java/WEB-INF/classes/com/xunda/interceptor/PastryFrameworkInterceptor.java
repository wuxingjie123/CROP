package com.xunda.interceptor;

import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.citicbank.cbframework.common.util.CBStreamOperator;
import com.citicbank.cbframework.log.CBLogger;
import com.citicbank.cbframework.usercontext.CBDefaultUserContext;
import com.citicbank.cbframework.usercontext.CBUserContext;
import com.xunda.controller.Constants;

public class PastryFrameworkInterceptor extends HandlerInterceptorAdapter implements InitializingBean {
	private boolean APP_MODE = true;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		CBLogger.d("----- handle handshake -----");
		HttpSession session = request.getSession();
		String id = session.getId();
		CBLogger.d("session id============================"+id);
		String strComPkg = new String(CBStreamOperator.getInputStreamBytes(request.getInputStream()), "UTF-8");
		CBLogger.d("客户端的请求数据 strComPkg=" + strComPkg);
		
		
		if (APP_MODE) {
			String act = request.getParameter("act");
			CBLogger.d("act=" + act);
			// 请求握手接口
			if (act.equals(Constants.TRADECODEFLAG_SHAKEHANG)) {
				CBUserContext userContext = new CBDefaultUserContext(request.getSession(), strComPkg);
				JSONObject handshakeData = userContext.getHandshakeData();
				CBLogger.d(userContext.getClientRandom());
				CBLogger.d(userContext.getDeviceId());
				CBLogger.d("handshake data = " + handshakeData.toString());

			} else {
				CBDefaultUserContext userContext = (CBDefaultUserContext) session.getAttribute(CBUserContext.USERCONTEXT);
				JSONObject paramObject = userContext.getRequestBusiness(strComPkg);				
				//CBLogger.d("客户端的请求数据 strComPkg=request.setAttribute(‘paramObject’, paramObject)=" + paramObject);
				request.setAttribute("paramObject", paramObject);

			}
		}else{
			String act = request.getParameter("act");
			CBLogger.d("act=" + act);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		HttpSession session = request.getSession();
		// 在session中获取返回数据
		JSONObject jsonBusiness = new JSONObject((String) session.getAttribute("jsonBusiness"));
		CBLogger.d("调用服务端返回的数据 ：jsonBusiness=" + jsonBusiness.toString());

		if (APP_MODE) {
			
			ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("applicationContext.xml");
	        //根据bean节点的标识获取对象，id
			SYSParaProperty retSYSParaProperty = (SYSParaProperty) cpxac.getBean("SYSParaProperty");
	        CBLogger.d("app="+retSYSParaProperty.getAPP()+"  ip="+retSYSParaProperty.getIP());
	        Constants.CHANNEL=retSYSParaProperty.getAPP();
	        Constants.IP=retSYSParaProperty.getAPP();
	        cpxac.close();
	        
			// 对返回数据进行加密
			CBDefaultUserContext userContext = (CBDefaultUserContext) session.getAttribute(CBUserContext.USERCONTEXT);
			String returnJson = userContext.getResponseComPackage(jsonBusiness);
			// String
			// returnJson="{'RETMSG':'交易失败','RETCODE':'9999999','SUPPORT_VERSION':'SUPPORT_VERSION','SERVER_TIME':'SERVER_TIME'}";

			CBLogger.d("returnJson...." + returnJson);
			// 接返回结果返回到response
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain; charset=utf-8");

			String act = request.getParameter("act");
			CBLogger.d("act=" + act);
			if (act.equals(Constants.TRADECODEFLAG_SHAKEHANG)) {
				String number = userContext.getServerRandom();
				response.addHeader("Service-Number", number);
				response.addHeader("sessionState", "100");//100:登录成功  ； 1：握手成功！
			}
			if (act.equals(Constants.TRADECODEFLAG_5080001)) {
				String number = userContext.getServerRandom();
				response.addHeader("Service-Number", number);
				response.addHeader("sessionState", "100");//100:登录成功  ； 1：握手成功！
			}

			try {
				ServletOutputStream os = response.getOutputStream();
				os.write(returnJson.getBytes("UTF-8"));
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// 对返回数据进行加密
			CBDefaultUserContext userContext = (CBDefaultUserContext) session.getAttribute(CBUserContext.USERCONTEXT);
			// String returnJson =
			// userContext.getResponseComPackage(jsonBusiness);
			// String returnJson =
			// "{'RETMSG':'交易失败','RETCODE':'9999999','SUPPORT_VERSION':'SUPPORT_VERSION','SERVER_TIME':'SERVER_TIME','UUIDKEY':'abcdefghijk1234567890'}";
			String returnJson = jsonBusiness.toString();
			CBLogger.d("PastryFrameworkInterceptor-定义的返回数据处理=" + returnJson);
			// 接返回结果返回到response
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain; charset=utf-8");
			response.addHeader("sessionState", "100");

			try {
				ServletOutputStream os = response.getOutputStream();
				os.write(returnJson.getBytes("UTF-8"));
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}
}