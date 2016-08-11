package com.xunda.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.citicbank.cbframework.common.exception.CBInvalidParameterException;
import com.citicbank.cbframework.common.security.CB3Des;
import com.citicbank.cbframework.common.util.CBConverter;
import com.citicbank.cbframework.usercontext.CBDefaultUserContext;
import com.citicbank.cbframework.usercontext.CBUserContext;
import com.icitic.netpay.api.AccountService;
import com.icitic.netpay.api.CardService;
import com.icitic.netpay.api.ClubService;
import com.icitic.netpay.api.LoginService;
import com.icitic.netpay.api.MemberGroupService;
import com.icitic.netpay.api.MemberService;
import com.icitic.netpay.api.MessageService;
import com.icitic.netpay.api.PayService;
import com.icitic.netpay.api.RegisterService;
import com.icitic.netpay.api.SystemService;
import com.icitic.netpay.api.VerifyCodeService;
import com.xunda.common.model.Page;
import com.xunda.common.model.ResultObject;
import com.xunda.model.Badge;
import com.xunda.model.Bank;
import com.xunda.model.CardBin;
import com.xunda.model.City;
import com.xunda.model.Coupon;
import com.xunda.model.Member;
import com.xunda.model.MemberCard;
import com.xunda.model.MemberDetail;
import com.xunda.model.MemberGroup;
import com.xunda.model.Message;
import com.xunda.model.Order;
import com.xunda.model.Province;
import com.xunda.model.Recharge;
import com.xunda.model.Shop;
import com.xunda.vo.query.CouponQuery;
import com.xunda.vo.query.MemberCardQuery;
import com.xunda.vo.query.MessageQuery;
import com.xunda.vo.query.OrderQuery;
import com.xunda.vo.query.ShopQuery;
import com.citicbank.cbframework.log.CBLogger;
@Controller
// @RequestMapping("/login.do")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private VerifyCodeService verifyCodeService;

	@Autowired
	private RegisterService registerService;

	@Autowired
	private CardService cardService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private MemberGroupService memberGroupService;

	@Autowired
	private PayService payService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private MemberService memberService;
	@Autowired
	private ClubService clubService;

	@Autowired
	private SystemService systemService;

	/**
	 * 5080001-登录 http://localhost:8080/test/ptframework.do?act=5080002
	 */
	// @RequestMapping()
	// @RequestMapping("/login.do")
	// @RequestMapping(params = "username=ying")
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080001)
	@ResponseBody
	public void loginService_5080001(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			ResultObject<Member> retObj = null;
			// // 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());

			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);

			String LOG_PASSWORD = pkgFlag_json.getString("LOG_PASSWORD");
			LOG_PASSWORD = changPassword(request, LOG_PASSWORD);

			String DEVICEID = pkgFlag_json.getString("DEVICEID");

			// 返回调用方的json对象
			JSONObject jsonObject = new JSONObject();

			boolean isMobile = Constants.isMobilePhone(PHONE_NO);
			if (!isMobile) {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, "请输入正确的手机号,你输入的数据为" + PHONE_NO);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);

				request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
				return;
			}

			String ip = Constants.IP;// ?
			String hardcode = DEVICEID;// "123456789012";//?
			String username = PHONE_NO;// "18623457896";
			String password = LOG_PASSWORD;// "112233";
			try {
				retObj = loginService.login(Constants.CHANNEL, username, password, ip, hardcode);
				// CBLogger.d("=======================================retObj>>>"
				// + retObj.getResult());

			} catch (Exception e) {
				CBLogger.d(e.toString());
			}

			// 根据 retObj，进行处理
			if (retObj.isSuccess()) {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
				Member member = retObj.getResult();
				jsonObject.put("USER_REAL_NAME", member.getRealname() == null ? "" : member.getRealname());
			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, retObj.getMessage());
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
			request.getSession().setAttribute("user", retObj.getResult());
		}
	}

	public String changPassword(HttpServletRequest request, String LOG_PASSWORD) {
		HttpSession session = request.getSession();
		CBDefaultUserContext userContext = (CBDefaultUserContext) session.getAttribute(CBUserContext.USERCONTEXT);

		StringBuffer sb = new StringBuffer();
		try {
			for (int i = 0; i < LOG_PASSWORD.length(); i += 16) {
				byte[] p = CBConverter.hexToBytes(LOG_PASSWORD.substring(i, i + 16));
				byte[] b = CB3Des.decrypt(p, userContext.getSRKey1(), userContext.getCRKey2(), userContext.getSessionKey3());
//				CBLogger.d("==" + new String(p) + "  =>" + new String(b) + "  key1=" + userContext.getSRKey1() + " key2=" + userContext.getCRKey2() + "  key3"
//						+ userContext.getSessionKey3());
				sb.append(new String(b));
			}
			 CBLogger.d("--------解密后的密码----------"+sb.toString());
		} catch (CBInvalidParameterException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 5080002-获取短信验证码 http://localhost:8080/test/login.do?act=5080002
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080002)
	@ResponseBody
	public void verifyCodeService_5080002(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			if (Constants.MOSHIFLAG) {
				testAPP(request);
			} else {
				// 生产的数据
				String jsonString = request.getAttribute("paramObject").toString();
				JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
				CBLogger.d(pkgFlag_json.toJSONString());
				String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
				CBLogger.d("PHONE_NO=" + PHONE_NO);
				// String BUSINESS_CODE =
				// pkgFlag_json.getString("BUSINESS_CODE");
				// CBLogger.d("BUSINESS_CODE=" + BUSINESS_CODE);

				Long userid = getUserIDByPhone(PHONE_NO);

				/**
				 * 查询会员详细信息
				 * 
				 * @param userId
				 * @return
				 */
				ResultObject<MemberDetail> retMemberDetail = memberService.detail(userid);

				String channel = Constants.CHANNEL;
				int codeType = Integer.parseInt("2");
				String target = PHONE_NO;
				/**
				 * 发送验证码 手机登陆，注册等发送验证请求， 帮卡发送验证码请求 快捷支付请求（一种） 修改手机号
				 * 等等需要发送验证码的场景调用此方法发送验证码
				 * 
				 * @param channel
				 * @param userId
				 * @param codeType
				 * @return
				 */
				ResultObject<String> retString = verifyCodeService.generateCode(channel, userid, codeType, target);

				JSONObject jsonObject = new JSONObject();
				// 根据 retObj，进行处理
				if (retMemberDetail.isSuccess() && retString.isSuccess()) {
					jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
					jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
					jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
					jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);

					MemberDetail memberDetail = retMemberDetail.getResult();
					String Question1 = memberDetail.getQuestion1();
					String Question2 = memberDetail.getQuestion2();
					String Question3 = memberDetail.getQuestion3();

					jsonObject.put("VALIDITY_PERIOD", "");
					jsonObject.put("SAFETY_PROBLEM_1", Question1 == null ? "" : Question1);
					jsonObject.put("SAFETY_PROBLEM_2", Question2 == null ? "" : Question2);
					jsonObject.put("SAFETY_PROBLEM_3", Question3 == null ? "" : Question3);

				} else {
					jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
					jsonObject.put(Constants.RETMSG, retMemberDetail.getMessage());
					jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
					jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
				}
				request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
			}
		}
	}

	/**
	 * 5080003-用户注册
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080003)
	@ResponseBody
	public void registerService_5080003(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			ResultObject<Long> retObj = null;
			// // 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());

			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);

			String LOG_PASSWORD = pkgFlag_json.getString("LOG_PASSWORD");
			CBLogger.d("LOG_PASSWORD=" + LOG_PASSWORD);
			LOG_PASSWORD = changPassword(request, LOG_PASSWORD);

			String PAY_PASSWORD = pkgFlag_json.getString("PAY_PASSWORD");
			CBLogger.d("PAY_PASSWORD=" + PAY_PASSWORD);
			PAY_PASSWORD = changPassword(request, PAY_PASSWORD);

			// 返回调用方的json对象
			JSONObject jsonObject = new JSONObject();

			boolean isMobile = Constants.isMobilePhone(PHONE_NO);
			if (!isMobile) {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, "请输入正确的手机号,你输入的数据为" + PHONE_NO);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);

				request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
				return;
			}

			ResultObject<Member> retMber = registerService.search(PHONE_NO);
			CBLogger.d(retMber.getRetCode() + "=retMber=" + retMber.getResult());
			retMber.getRetCode();
			//CBLogger.d("retMber.getResult().getStatus() == " + retMber.getResult().getStatus());
			if (retMber.isSuccess()&&retMber.getResult()!=null&&retMber.getResult().getStatus()==0 ) {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
				// 临时用户 发验证码
				CBLogger.d("临时用户=激活");
				// 发验证码
				String channel = Constants.CHANNEL;
				Long userid = getUserIDByPhone(PHONE_NO);
				int codeType = 0;
				ResultObject<String> ret = verifyCodeService.generateCode(channel, userid, codeType, PHONE_NO);

				if (ret.isSuccess()) {
					String retString = ret.getResult();
					CBLogger.d("发送验证码=" + retString);
				} else {
					// 可以重发 3次
				}

			} else 	if(retMber.isSuccess()&&retMber.getResult()==null){
				
				Member member = new Member();
				member.setUsername(PHONE_NO);
				member.setMobile(PHONE_NO);
				member.setRegip(Constants.IP);
				member.setComefrom("手机客户端");
				member.setPassword(LOG_PASSWORD);

				retObj = registerService.register(member);
				CBLogger.d("retObj.getResult() userid=" + retObj.getResult());
				// Constants.mobileMap.put(PHONE_NO,
				// retObj.getResult().toString());
				// * username 必输
				// * email和mobile必输入一个
				// * 注册ip必输
				// * comefrom最好输入

				CBLogger.d(retObj.getRetCode() + "=retObj>>>" + retObj.getResult());

				/**
				 * 设置支付密码
				 * 
				 * @param userId
				 * @param pass
				 * @return
				 */
				ResultObject<String> retPayPass = memberService.setPaypass(retObj.getResult(), PAY_PASSWORD);
				if (retPayPass.isSuccess()) {
					jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
					jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
					jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
					jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
				} else {
					jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
					jsonObject.put(Constants.RETMSG, retPayPass.getMessage());
					jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
					jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);

				}

				// 根据 retObj，进行处理
				if (retObj.isSuccess()) {
					jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
					jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
					jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
					jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);

					// 发验证码
					String channel = Constants.CHANNEL;
					Long userid = getUserIDByPhone(PHONE_NO);
					CBLogger.d("userid=" + userid);
					int codeType = 0;
					ResultObject<String> ret = verifyCodeService.generateCode(channel, userid, codeType, PHONE_NO);

					if (ret.isSuccess()) {
						String retString = ret.getResult();
						CBLogger.d("发送验证码=" + retString);
					} else {
						// 可以重发 3次
					}
					// 验证发送码,并激活帐号
					CBLogger.d("retObj>>>" + retObj.isSuccess());

				} else {
					jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
					jsonObject.put(Constants.RETMSG, "注册失败");
					jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
					jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
				}

			} else{
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, "注册失败");
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);				
			}

			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	/**
	 * 5080031-用户激活
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080031)
	@ResponseBody
	public void registerService_50800031(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			ResultObject<String> retObj = null;
			// 生产的数据

			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);
			String VERIFYCODE = pkgFlag_json.getString("VERIFYCODE");
			CBLogger.d("VERIFYCODE=" + VERIFYCODE);

			Long userid = getUserIDByPhone(PHONE_NO);

			String channel = Constants.CHANNEL;

			/**
			 * 
			 * 验证generateCode生成的验证码
			 * 
			 * @param channel
			 * @param userId
			 * @param code
			 * @return
			 */
			ResultObject<String> verfy = verifyCodeService.verify(channel, userid, VERIFYCODE);
			JSONObject jsonObject = new JSONObject();
			if (verfy.isSuccess()) {
				/**
				 * 激活用户，验证码输入，成功激活用户
				 * 
				 * @param userId
				 * @return
				 */
				retObj = registerService.active(userid);
				CBLogger.d(retObj.getRetCode() + "=retObj>>>" + retObj.getResult());

				// 根据 retObj，进行处理
				if (retObj.isSuccess()) {
					jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
					jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
					jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
					jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
				} else {
					jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
					jsonObject.put(Constants.RETMSG, retObj.getMessage());
					jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
					jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
				}
			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, verfy.getMessage());
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			CBLogger.d("retObj>>>" + verfy.isSuccess());

			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	public Long getUserIDByPhone(String PHONE_NO) {
		ResultObject<Member> retMber = registerService.search(PHONE_NO);
		Member member = retMber.getResult();
		Long userid = member.getUserid();
		return userid;
	}

	/**
	 * 5080004-获取卡类型 发卡行
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080004)
	@ResponseBody
	public void cardService_5080004(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			// // 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());

			String CARD_NO = pkgFlag_json.getString("CARD_NO");
			Map cardInfo = getCardInfo(CARD_NO);

			// 返回调用方的json对象
			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (!cardInfo.isEmpty()) {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);

				// CARD_TYPE 1 String 卡类型 借记卡；
				// BANK_CODE 1 String 发卡行代码
				// BANK_NAME 1 String 发卡行简称
				jsonObject.put("CARD_TYPE", cardInfo.get("CardType"));
				jsonObject.put("BANK_CODE", cardInfo.get("Bankid"));
				jsonObject.put("BANK_NAME", cardInfo.get("Bankname"));

			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_FAIL);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}

			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	/**
	 * 5080005-绑卡（实名认证）
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080005)
	@ResponseBody
	public void cardService_5080005(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			ResultObject<String> retObj = null;
			// 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);
			String NAME = pkgFlag_json.getString("NAME");
			CBLogger.d("NAME=" + NAME);
			String ID_NO = pkgFlag_json.getString("ID_NO");
			CBLogger.d("ID_NO=" + ID_NO);
			String CARD_NO = pkgFlag_json.getString("CARD_NO");
			CBLogger.d("CARD_NO=" + CARD_NO);
			String CARD_TYPE = pkgFlag_json.getString("CARD_TYPE");
			CBLogger.d("CARD_TYPE=" + CARD_TYPE);
			String BANK_CODE = pkgFlag_json.getString("BANK_CODE");
			CBLogger.d("BANK_CODE=" + BANK_CODE);
			String BANK_PHONE = pkgFlag_json.getString("BANK_PHONE");
			CBLogger.d("BANK_PHONE=" + BANK_PHONE);
			String SMS_CODE = pkgFlag_json.getString("SMS_CODE");
			CBLogger.d("SMS_CODE=" + SMS_CODE);

			// 彭绍娟 330211199302262047
			MemberCard memberCard = new MemberCard();
			// String NAME = "彭绍娟 ";
			memberCard.setRealname(NAME);
			// String ID_NO = "330211199302262047";
			memberCard.setIdcard(ID_NO);

			// String CARD_NO = "123456789012345678";
			memberCard.setCardno(CARD_NO);
			// String CARD_TYPE = "1";// 借记卡
			memberCard.setCardtype(CARD_TYPE);
			// String BANK_CODE = "12345";
			memberCard.setBankid(BANK_CODE);
			// String SMS_CODE = "123456";
			memberCard.setVerified(SMS_CODE);
			memberCard.setCardtype("0");
			Long userid = getUserIDByPhone(PHONE_NO);
			memberCard.setUserid(userid);
			memberCard.setMobile(PHONE_NO);
			/**
			 * 绑定银行卡
			 * 
			 * @param card
			 * @return
			 */
			retObj = cardService.bind(memberCard);
			CBLogger.d("retObj>>>" + retObj.isSuccess());

			// 返回调用方的json对象
			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (retObj.isSuccess()) {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
				// jsonObject.put(Constants.SERVER_LIST, retObj.getResult());
			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, retObj.getMessage());
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	/**
	 * 5080006-获取消息列表
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080006)
	@ResponseBody
	public void messageService_5080006(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			Page<Message> retObj = null;
			// // 生产的数据

			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());

			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			String PAGE_NO = pkgFlag_json.getString("PAGE_NO");
			String PAGE_NUM = pkgFlag_json.getString("PAGE_NUM");
			String MSG_TYPE = pkgFlag_json.getString("MSG_TYPE");

			if ("1".equals(MSG_TYPE)) {// 公共
				MessageQuery messageQuery = new MessageQuery();
				Long userid = getUserIDByPhone(PHONE_NO);
				messageQuery.setUserid(userid);
				messageQuery.setPageNumber(Integer.parseInt(PAGE_NO));
				messageQuery.setPageSize(Integer.parseInt(PAGE_NUM));
				messageQuery.setMessageType(Integer.parseInt("0"));

				retObj = messageService.search(messageQuery);
				CBLogger.d("retObj>>>" + retObj.getResult());

			} else {
				Long userid = getUserIDByPhone(PHONE_NO);
				MessageQuery paramMessageQuery = new MessageQuery();
				paramMessageQuery.setUserid(userid);
				paramMessageQuery.setPageNumber(Integer.parseInt(PAGE_NO));
				paramMessageQuery.setPageSize(Integer.parseInt(PAGE_NUM));
				retObj = messageService.searchBindMessage(paramMessageQuery);
				CBLogger.d("searchBindMessage retObj>>>" + retObj.getResult());
			}

			// 返回调用方的json对象
			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (retObj.getTotalCount() > 0) {

				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);

				List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
				List<Message> list = retObj.getResult();
				for (int i = 0; i < list.size(); i++) {
					Message msg = list.get(i);
					Map<String, Object> temp = new HashMap<String, Object>();
					temp.put("MSG_ID", msg.getId());
					temp.put("MSG_TITLE", msg.getTitle());
					temp.put("MSG_TIME", getDateSeconds(msg.getCreated()));
					temp.put("MSG_SUMMARY", msg.getContent());
					temp.put("MSG_TYPE", msg.getMessageType());
					ret.add(temp);
				}
				jsonObject.put("MSG_LIST", ret);
			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_FAIL);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	/**
	 * 5080007-获取消息详情
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080007)
	@ResponseBody
	public void messageService_5080007(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			Page<Message> retObj = null;
			// 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String MSG_ID = pkgFlag_json.getString("MSG_ID");
			MessageQuery messageQuery = new MessageQuery();
			messageQuery.setId(Long.parseLong(MSG_ID));

			retObj = messageService.search(messageQuery);
			CBLogger.d("retObj>>>" + retObj.getResult());

			// 返回调用方的json对象
			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (retObj.getTotalCount() > 0) {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);

				List<Message> list = retObj.getResult();
				for (int i = 0; i < list.size(); i++) {
					Message msg = list.get(i);
					if (i == 0) {
						jsonObject.put("MSG_TITLE", msg.getTitle());
						jsonObject.put("MSG_TIME", getDateSeconds(msg.getCreated()));
						jsonObject.put("MSG_DETAILS", msg.getContent());
					}
				}
			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_FAIL);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	/**
	 * 5080008-权益俱乐部主界面信息
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080008)
	@ResponseBody
	public void memberGroupService_5080008(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {

			// 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);
			// String PHONE_NO="18623457896";
			Long userid = getUserIDByPhone(PHONE_NO);
			/**
			 * 
			 * 查询会员基本信息
			 * 
			 * @param userId
			 * @return
			 */
			ResultObject<Member> retmember = memberService.info(userid);
			Member member = retmember.getResult();
			Integer groupid = member.getGroupid();
			/**
			 * 查询单个会员组详情
			 * 
			 * @param groupId
			 * @return
			 */
			ResultObject<MemberGroup> retMemberGroup = memberGroupService.info(groupid);
			MemberGroup memberGroup = retMemberGroup.getResult();

			OrderQuery query = new OrderQuery();
			query.setStatus("1");
			// String userid1=(String) Constants.mobileMap.get(PHONE_NO);
			query.setUserid(userid);
			query.setPageNumber(1);

			query.setPageSize(5);
			/**
			 * 订单查询 分页
			 * 
			 * @param query
			 * @return
			 */
			Page<Order> orders = this.payService.orderQuery(query);
			List<Order> listOrder = orders.getResult();

			/**
			 * 获取商店优惠券信息
			 * 
			 */
			CouponQuery couponQuery = new CouponQuery();
			Page<Coupon> pageCoupon = clubService.search(couponQuery);
			List<Coupon> listCoupon = pageCoupon.getResult();

			// 返回调用方的json对象
			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (retmember.isSuccess()) {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
				jsonObject.put("MEMBER_TYPE", member.getGroupid());
				jsonObject.put("LACK_KING_COUNTS", memberGroup.getGroupid());

				jsonObject.put("CONSUME_SHOP", "");
				jsonObject.put("CONSUME_TIME", "");
				jsonObject.put("FOOD_CARD_COUPON", "");
				jsonObject.put("FOOD_CARD_COUPON_TIME", "");
				jsonObject.put("GOODS_CARD_COUPON", "");
				jsonObject.put("GOODS_CARD_COUPON_TIME", "");
				jsonObject.put("MEMBER_ANNIVERSARY", getDateSeconds(member.getRegdate()));
				jsonObject.put("listOrder", listOrder.toArray());
				jsonObject.put("listCoupon", listCoupon.toArray());

				for (int i = 0; i < listOrder.size(); i++) {
					Order order = listOrder.get(i);
					if (i == 0) {
						jsonObject.put("CONSUME_SHOP", order.getDescription());
						jsonObject.put("CONSUME_TIME", getDateSeconds(order.getCreatetime()));
					}
				}

				for (int i = 0; i < listCoupon.size(); i++) {
					Coupon coupon = listCoupon.get(i);
					if (i == 0) {
						jsonObject.put("FOOD_CARD_COUPON", coupon.getTitle());
						jsonObject.put("FOOD_CARD_COUPON_TIME", getDateSeconds(coupon.getEndDate()));
					}
					if (i == 1) {
						jsonObject.put("GOODS_CARD_COUPON", coupon.getTitle());
						jsonObject.put("GOODS_CARD_COUPON_TIME", getDateSeconds(coupon.getEndDate()));
					}
				}

			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_FAIL);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	/**
	 * 5080009-获取星级信息
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080009)
	@ResponseBody
	public void memberGroupService_5080009(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			ResultObject<MemberGroup> retMemberGroup = null;
			// 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String MEMBER_TYPE = pkgFlag_json.getString("MEMBER_TYPE");

			retMemberGroup = memberGroupService.info(Integer.parseInt(MEMBER_TYPE));
			CBLogger.d("retObj>>>" + retMemberGroup.getResult());
			MemberGroup memberGroup = retMemberGroup.getResult();
			// 返回调用方的json对象
			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (retMemberGroup.isSuccess()) {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
				String Description = memberGroup.getDescription();
				jsonObject.put("CONTENT_1", Description);
				jsonObject.put("CONTENT_2", "");
				jsonObject.put("CONTENT_3", "");
			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, retMemberGroup.getMessage());
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	/**
	 * 5080012 -获取商店列表信息
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080012)
	@ResponseBody
	public void payService_5080012(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			// LONGITUDE 1 String 经度
			// LATITUDE 1 String 维度
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String LONGITUDE = pkgFlag_json.getString("LONGITUDE");
			CBLogger.d("LONGITUDE=" + LONGITUDE);

			String LATITUDE = pkgFlag_json.getString("LATITUDE");
			CBLogger.d("LATITUDE=" + LATITUDE);
			/**
			 * 商店列表
			 * 
			 */
			ShopQuery query = new ShopQuery();
			query.setLongitude(Double.parseDouble(LONGITUDE));
			query.setLatitude(Double.parseDouble(LATITUDE));
			Page<Shop> retShop = clubService.search(query);

			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (retShop.getResult().size() > 0) {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
				List<Shop> listShop = retShop.getResult();
				for (int i = 0; i < listShop.size(); i++) {
					Shop shop = listShop.get(i);
					jsonObject.put("SHOP_NAME", shop.getShopName());
					jsonObject.put("SHOP_ADDRESS", shop.getCity());
					jsonObject.put("SHOP_DISTANCE", "");
				}
				// jsonObject.put(Constants.SERVER_LIST, listShop);
			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_FAIL);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());

		}
	}

	/**
	 * 5080013 -门店的签到
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080013)
	@ResponseBody
	public void payService_5080013(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String SHOP_ID = pkgFlag_json.getString("SHOP_ID");
			CBLogger.d("SHOP_ID=" + SHOP_ID);
			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);
			Long userid = getUserIDByPhone(PHONE_NO);

			/**
			 * 会员签到
			 * 
			 */
			ResultObject<String> ret = clubService.memberSign(SHOP_ID, userid);

			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (ret.isSuccess()) {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, ret.getMessage());
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());

		}
	}

	/**
	 * 5080014 -优惠劵列表
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080014)
	@ResponseBody
	public void payService_5080014(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);
			String COUPON_TYPE = pkgFlag_json.getString("COUPON_TYPE");
			CBLogger.d("COUPON_TYPE=" + COUPON_TYPE);
			String PAGE_NO = pkgFlag_json.getString("PAGE_NO");
			CBLogger.d("PAGE_NO=" + PAGE_NO);
			String PAGE_NUM = pkgFlag_json.getString("PAGE_NUM");
			CBLogger.d("PAGE_NUM=" + PAGE_NUM);

			CouponQuery couponQuery = new CouponQuery();

			couponQuery.setCouponType(new BigDecimal(COUPON_TYPE));
			couponQuery.setPageNumber(Integer.parseInt(PAGE_NO));
			couponQuery.setPageSize(Integer.parseInt(PAGE_NUM));

			Page<Coupon> pageCoupon = clubService.search(couponQuery);
			List<Coupon> listCoupon = pageCoupon.getResult();

			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (pageCoupon.getPageSize() > 0) {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
				List list = new ArrayList();
				for (int i = 0; i < listCoupon.size(); i++) {
					Coupon coupon = listCoupon.get(i);
					Map<String, String> temp = new HashMap<String, String>();

					temp.put("COUPON_TITLE", coupon.getTitle());
					temp.put("COUPON_SUM", coupon.getDescription());
					list.add(temp);
				}
				jsonObject.put("COUPON_LIST", list.toArray());
			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_FAIL);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());

		}
	}

	/**
	 * 5080015 -查询绑定的银行卡列表
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080015)
	@ResponseBody
	public void cardService_5080015(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {

			Page<MemberCard> retObj = null;
			// 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);
			String PAGE_NO = pkgFlag_json.getString("PAGE_NO");
			String PAGE_NUM = pkgFlag_json.getString("PAGE_NUM");

			MemberCardQuery memberCardQuery = new MemberCardQuery();
			Long userid = getUserIDByPhone(PHONE_NO);
			memberCardQuery.setUserid(userid);
			memberCardQuery.setPageNumber(Integer.parseInt(PAGE_NO));
			memberCardQuery.setPageSize(Integer.parseInt(PAGE_NUM));
			memberCardQuery.setVerified("");// 1 表示绑卡成功，0未成功

			retObj = cardService.search(memberCardQuery);

			CBLogger.d("retObj>>>" + retObj.getResult());

			// 返回调用方的json对象
			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (retObj.getResult().size() > 0) {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
				List<MemberCard> listMemberCard = retObj.getResult();
				List list = new ArrayList();
				for (int i = 0; i < listMemberCard.size(); i++) {
					MemberCard memberCard = listMemberCard.get(i);
					Map<String, String> temp = new HashMap<String, String>();
					String CARD_NO = memberCard.getCardno();
					Map cardInfo = getCardInfo(CARD_NO);
					temp.put("BANK_NAME", cardInfo.get("Bankname").toString());
					temp.put("CARD_TYPE", memberCard.getCardtype());
					temp.put("CARD_NO", CARD_NO);
					temp.put("SUM_LIMIT", cardInfo.get("MonthLimit").toString());
					temp.put("SINGLE_LIMIT", cardInfo.get("SingleLimit").toString());
					temp.put("DAY_LIMIT", cardInfo.get("DayLimit").toString());
					list.add(temp);
				}
				jsonObject.put("BANK_LIST", list.toArray());
			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_FAIL);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	/**
	 * 5080016 -充值
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080016)
	@ResponseBody
	public void accountService_5080016(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			uatAPP5080016(request);
		}
	}

	/**
	 * 5080017 -消息收藏
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080017)
	@ResponseBody
	public void messageService_5080017(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			ResultObject<Message> retObj = null;
			// 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());

			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);
			String MSG_ID = pkgFlag_json.getString("MSG_ID");
			CBLogger.d("MSG_ID=" + MSG_ID);

			/**
			 * 查询消息 分页
			 * 
			 * @param query
			 * @return
			 */
			MessageQuery messageQuery = new MessageQuery();
			messageQuery.setId(Long.parseLong(MSG_ID));

			Page<Message> retMessage = messageService.search(messageQuery);
			CBLogger.d("retMessage>>>" + retMessage.getResult());

			Long userid = getUserIDByPhone(PHONE_NO);
			/**
			 * 收藏消息
			 * 
			 * @param query
			 * @return
			 */
			retObj = messageService.bindMessage(userid, Long.parseLong(MSG_ID));

			// 返回调用方的json对象
			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (retObj.isSuccess()) {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
				// jsonObject.put(Constants.SERVER_LIST, retObj.getResult());
			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, retObj.getMessage());
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	/**
	 * 5080018 -获取勋章信息
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080018)
	@ResponseBody
	public void messageService_5080018(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			// Page<Message> retObj = null;
			// 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);

			List<Badge> retBadgeList = clubService.findall();
			CBLogger.d("retBadgeList=" + retBadgeList.size());
			Long userid = getUserIDByPhone(PHONE_NO);
			List<Long> ret = clubService.findAll(userid);
			CBLogger.d("ret=" + ret.toArray().toString());
			// 返回调用方的json对象
			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (!retBadgeList.isEmpty()) {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
				jsonObject.put("TOTAL_NUMBER", retBadgeList.size());
				jsonObject.put("OWN_NUMBER", ret.size());

				List list = new ArrayList();
				for (int i = 0; i < retBadgeList.size(); i++) {
					Badge badge = retBadgeList.get(i);
					String flag = "0";
					for (int j = 0; j < ret.size(); j++) {
						CBLogger.d("=============" + ret.get(j).toString());
						Long id = ret.get(j);
						if (badge.getId() == id) {
							flag = "1";
						}
					}

					Map map = new HashMap();
					map.put("IS_HAVE", flag);
					map.put("BADGE_ID", badge.getId());
					map.put("BADGE_NAME", badge.getName());
					map.put("ICON_NAME", badge.getImage());
					map.put("BADGE_INDEX", badge.getBadgeIndex());
					map.put("BADGE_DESC", badge.getDescription());
					list.add(map);
				}
				jsonObject.put("BADGE_LIST", list.toArray());
			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_FAIL);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	/**
	 * 5080019 -账户信息
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080019)
	@ResponseBody
	public void memberService_5080019(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			ResultObject<Member> retMember = null;
			// 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);

			// 根据手机号，查询userid，
			Long userid = getUserIDByPhone(PHONE_NO);

			/**
			 * 
			 * 查询会员基本信息
			 * 
			 * @param userId
			 * @return
			 */
			retMember = memberService.info(userid);
			CBLogger.d("retObj>>>" + retMember.getResult());

			/**
			 * 查询会员详细信息
			 * 
			 * @param userId
			 * @return
			 */
			ResultObject<MemberDetail> retMemberDetail = memberService.detail(userid);
			MemberDetail memberDetail = retMemberDetail.getResult();
			CBLogger.d("memberDetail zip=" + memberDetail.getZip());
			// 返回调用方的json对象
			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (retMember.isSuccess()) {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
				Member member = retMember.getResult();
				jsonObject.put("USER_REAL_NAME", member.getRealname());
				jsonObject.put("USER_NAME", member.getNickname());
				jsonObject.put("USER_ADDRESS", memberDetail.getCountry());
				jsonObject.put("USER_POSTCODES", memberDetail.getZip());
				jsonObject.put("LOG_PASSWORD", member.getPassword());
				jsonObject.put("PAY_PASSWORD", "");
				jsonObject.put("MAIL_BOX", member.getEmail());
			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_FAIL);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	/**
	 * 5080020-权益俱乐部主界面信息
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080020)
	@ResponseBody
	public void memberService_5080020(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {

			// 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);
			String LONGITUDE = pkgFlag_json.getString("LONGITUDE");
			CBLogger.d("LONGITUDE=" + LONGITUDE);
			String LATITUDE = pkgFlag_json.getString("LATITUDE");
			CBLogger.d("LATITUDE=" + LATITUDE);
			// String PHONE_NO="18623457896";
			
			if(PHONE_NO.length()>0){				
			}else{
				CouponQuery couponQuery = new CouponQuery();

				/**
				 * 获取商店优惠券信息
				 * 
				 */
				Page<Coupon> pageCoupon = clubService.search(couponQuery);
				List<Coupon> listCoupon = pageCoupon.getResult();

				/**
				 * 商店列表
				 * 
				 */
				ShopQuery queryShop = new ShopQuery();
				// queryShop.setLongitude(Double.parseDouble(LONGITUDE));
				// queryShop.setLatitude(Double.parseDouble(LATITUDE));

				Page<Shop> pageShop = clubService.search(queryShop);
				List<Shop> listShop = pageShop.getResult();

				/**
				 * 查询消息 分页
				 * 
				 * @param query
				 * @return
				 */
				MessageQuery queryMessage = new MessageQuery();

				Page<Message> pageMessage = messageService.search(queryMessage);
				List<Message> listMessage = pageMessage.getResult();

				// 返回调用方的json对象
				JSONObject jsonObject = new JSONObject();
				// 根据 retObj，进行处理
				if (pageCoupon!=null) {

					jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
					jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
					jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
					jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
					jsonObject.put("MEMBER_TYPE", "");
					jsonObject.put("LACK_KING_COUNTS", "");
					jsonObject.put("MEMBER_ANNIVERSARY", 0);

					jsonObject.put("SHOP_NAME", "");
					jsonObject.put("SHOP_ADDRES", "");
					jsonObject.put("SHOP_DISTANCE", "");
					jsonObject.put("MSG_TITLE", "");
					for (int i = 0; i < listShop.size(); i++) {
						Shop shop = listShop.get(i);
						if (i == 0) {
							jsonObject.put("SHOP_NAME", shop.getShopName());

							// systemService
							String idProvice = shop.getProvince();
							String idCity = shop.getCity();
							String SHOP_ADDRES = getAddressByProviceIDCityID(idProvice, idCity);

							jsonObject.put("SHOP_ADDRES", SHOP_ADDRES);
							jsonObject.put("SHOP_DISTANCE", "");
						}
					}

					for (int i = 0; i < listMessage.size(); i++) {
						Message message = listMessage.get(i);
						jsonObject.put("MSG_TITLE", message.getTitle());
					}
//					jsonObject.put("listOrder", listOrder.toArray());
//					jsonObject.put("listCoupon", listCoupon.toArray());
//					jsonObject.put("listShop", listShop.toArray());
//					jsonObject.put("listMessage", listMessage.toArray());
				} else {
					jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
					jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_FAIL);
					jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
					jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
				}
				request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
				return ;
			}
			
			Long userid = getUserIDByPhone(PHONE_NO);
			/**
			 * 
			 * 查询会员基本信息
			 * 
			 * @param userId
			 * @return
			 */
			ResultObject<Member> retmember = memberService.info(userid);
			Member member = retmember.getResult();
			Integer groupid = member.getGroupid();
			/**
			 * 查询单个会员组详情
			 * 
			 * @param groupId
			 * @return
			 */
			ResultObject<MemberGroup> retMemberGroup = memberGroupService.info(groupid);
			MemberGroup memberGroup = retMemberGroup.getResult();

			OrderQuery query = new OrderQuery();
			query.setStatus("1");
			// String userid1=(String) Constants.mobileMap.get(PHONE_NO);
			query.setUserid(userid);
			query.setPageNumber(1);

			query.setPageSize(5);
			/**
			 * 订单查询 分页
			 * 
			 * @param query
			 * @return
			 */
			Page<Order> orders = this.payService.orderQuery(query);
			List<Order> listOrder = orders.getResult();
			// 优惠券

			CouponQuery couponQuery = new CouponQuery();

			/**
			 * 获取商店优惠券信息
			 * 
			 */
			Page<Coupon> pageCoupon = clubService.search(couponQuery);
			List<Coupon> listCoupon = pageCoupon.getResult();

			/**
			 * 商店列表
			 * 
			 */
			ShopQuery queryShop = new ShopQuery();
			// queryShop.setLongitude(Double.parseDouble(LONGITUDE));
			// queryShop.setLatitude(Double.parseDouble(LATITUDE));

			Page<Shop> pageShop = clubService.search(queryShop);
			List<Shop> listShop = pageShop.getResult();

			/**
			 * 查询消息 分页
			 * 
			 * @param query
			 * @return
			 */
			MessageQuery queryMessage = new MessageQuery();

			Page<Message> pageMessage = messageService.search(queryMessage);
			List<Message> listMessage = pageMessage.getResult();

			// 返回调用方的json对象
			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (retmember.isSuccess()) {

				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
				jsonObject.put("MEMBER_TYPE", member.getGroupid());
				jsonObject.put("LACK_KING_COUNTS", memberGroup.getGroupid());
				jsonObject.put("MEMBER_ANNIVERSARY", getDateSeconds(member.getRegdate()));

				jsonObject.put("SHOP_NAME", "");
				jsonObject.put("SHOP_ADDRES", "");
				jsonObject.put("SHOP_DISTANCE", "");
				jsonObject.put("MSG_TITLE", "");
				for (int i = 0; i < listShop.size(); i++) {
					Shop shop = listShop.get(i);
					if (i == 0) {
						jsonObject.put("SHOP_NAME", shop.getShopName());

						// systemService
						String idProvice = shop.getProvince();
						String idCity = shop.getCity();
						String SHOP_ADDRES = getAddressByProviceIDCityID(idProvice, idCity);

						jsonObject.put("SHOP_ADDRES", SHOP_ADDRES);
						jsonObject.put("SHOP_DISTANCE", "");
					}
				}

				for (int i = 0; i < listMessage.size(); i++) {
					Message message = listMessage.get(i);
					jsonObject.put("MSG_TITLE", message.getTitle());
				}
				jsonObject.put("listOrder", listOrder.toArray());
				jsonObject.put("listCoupon", listCoupon.toArray());
				jsonObject.put("listShop", listShop.toArray());
				jsonObject.put("listMessage", listMessage.toArray());
			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_FAIL);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	/**
	 * 5080021-门店查找
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080021)
	@ResponseBody
	public void memberService_5080021(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {

			// 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);
			String SHOP_NAME = pkgFlag_json.getString("SHOP_NAME");
			CBLogger.d("SHOP_NAME=" + SHOP_NAME);
			String LONGITUDE = pkgFlag_json.getString("LONGITUDE");
			CBLogger.d("LONGITUDE=" + LONGITUDE);
			String LATITUDE = pkgFlag_json.getString("LATITUDE");
			CBLogger.d("LATITUDE=" + LATITUDE);

			ShopQuery shopQuery = new ShopQuery();
			shopQuery.setShopName(SHOP_NAME);

			Page<Shop> pageShop = clubService.search(shopQuery);
			// 返回调用方的json对象
			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (pageShop.getTotalCount() > 0) {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);

				ArrayList list = new ArrayList();
				Map map = new HashMap();
				List<Shop> listShop = pageShop.getResult();
				for (int j = 0; j < listShop.size(); j++) {
					Shop shop = listShop.get(j);
					map.put("SHOP_ID", shop.getShopId());
					map.put("SHOP_NAME", shop.getShopName());

					String idCity = shop.getCity();
					String idProvice = shop.getProvince();
					String SHOP_ADDRES = getAddressByProviceIDCityID(idProvice, idCity);
					map.put("SHOP_ADDRES", SHOP_ADDRES);
					map.put("SHOP_DISTANCE", "");
					map.put("LONGITUDE", shop.getLongitude());
					map.put("LATITUDE", shop.getLatitude());
					map.put("LONGITUDE", "116.494689");
					map.put("LATITUDE", "39.984947");
					map.put("BUSINESS_TIME", "");
					list.add(map);
				}

				jsonObject.put("SHOP_LIST", list.toArray());
			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_FAIL);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	/**
	 * 5080033-勋章收藏
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080033)
	@ResponseBody
	public void memberService_5080033(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			// 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);
			String BADGE_ID = pkgFlag_json.getString("BADGE_ID");
			CBLogger.d("BADGE_ID=" + BADGE_ID);

			/**
			 * 关联徽章
			 * 
			 * @param userId
			 * @param badgeId
			 * @return
			 */
			ResultObject<String> retString = clubService.associateBadge(Long.parseLong(PHONE_NO), Long.parseLong(BADGE_ID));
			String ret = retString.getResult();
			CBLogger.d("关联徽章=" + ret);

			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (retString.isSuccess()) {

				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);

			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, retString.getMessage());
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	/**
	 * 5080034-勋章收藏
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080034)
	@ResponseBody
	public void memberService_5080034(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			// 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);
			String CARD_NO = pkgFlag_json.getString("CARD_NO");
			CBLogger.d("CARD_NO=" + CARD_NO);
			String SMS_CODE = pkgFlag_json.getString("SMS_CODE");
			CBLogger.d("SMS_CODE=" + SMS_CODE);
			/**
			 * 帮卡验证
			 * 
			 * @param userId
			 * @param cardno
			 * @param code
			 * @return
			 */
			Long userid = getUserIDByPhone(PHONE_NO);

			ResultObject<String> retString = cardService.verify(userid, CARD_NO, SMS_CODE);

			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (retString.isSuccess()) {

				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);

			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, retString.getMessage());
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	/**
	 * 5080035用户信息修改
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080035)
	@ResponseBody
	public void memberService_5080035(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			// 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);
			String USER_NAME = pkgFlag_json.getString("USER_NAME");//
			CBLogger.d("USER_NAME=" + USER_NAME);
			// String USER_ADDRESS = pkgFlag_json.getString("USER_ADDRESS");
			// CBLogger.d("USER_ADDRESS=" + USER_ADDRESS);
			String USER_POSTCODES = pkgFlag_json.getString("USER_POSTCODES");
			CBLogger.d("USER_POSTCODES=" + USER_POSTCODES);

			String MAIL_BOX = pkgFlag_json.getString("MAIL_BOX");//
			CBLogger.d("MAIL_BOX=" + MAIL_BOX);
			Long userid = getUserIDByPhone(PHONE_NO);

			// PHONE_NO 1 String 用户账户
			// USER_NAME 1 String 用户昵称 Username
			// USER_ADDRESS 1 String 居住地址 没有
			// USER_POSTCODES 1 String 用户邮编 没有
			// LOG_PASSWORD 1 String 登录密码 Password
			// PAY_PASSWORD 1 String 支付密码 没有
			// MAIL_BOX 1 String 邮箱地址 Email
			JSONObject jsonObject = new JSONObject();
			/**
			 * 查询会员详细信息
			 * 
			 * @param userId
			 * @return
			 */
			ResultObject<MemberDetail> retMemberDetail = memberService.detail(userid);
			MemberDetail memberDetail = retMemberDetail.getResult();
			memberDetail.setZip(USER_POSTCODES);

			/**
			 * 编辑会员详细信息
			 * 
			 * @param member
			 * @param memberDetail
			 * @return
			 */

			ResultObject<String> retString = memberService.edit(memberDetail);
			if (retString.isSuccess()) {

				/**
				 * 编辑会员昵称
				 * 
				 * @param member
				 * @param memberDetail
				 * @return
				 */

				ResultObject<String> retNickName = memberService.changeNickname(userid, USER_NAME);
				CBLogger.d("retNickName=" + retNickName.getMessage());
				if (retNickName.isSuccess()) {
					/**
					 * 修改邮箱
					 * 
					 * @param userId
					 * @param email
					 * @return
					 */
					ResultObject<String> retEmail = memberService.changeEmail(userid, MAIL_BOX);
					CBLogger.d("retEmail=" + retEmail.getMessage());
					if (retEmail.isSuccess()) {
						jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
						jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
						jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
						jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
					} else {
						jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
						jsonObject.put(Constants.RETMSG, "修改邮箱失败");
						jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
						jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
					}

				} else {
					jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
					jsonObject.put(Constants.RETMSG, "编辑会员昵称失败");
					jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
					jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
				}
			} else {

				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, "编辑会员详细信息失败");
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}

			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	/**
	 * 5080036 修改登录密码
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080036)
	@ResponseBody
	public void memberService_5080036(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			// 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);
			String LOG_PASSWORD = pkgFlag_json.getString("LOG_PASSWORD");
			CBLogger.d("LOG_PASSWORD=" + LOG_PASSWORD);
			LOG_PASSWORD = changPassword(request, LOG_PASSWORD);

			String LOG_PASSWORD_OLD = pkgFlag_json.getString("LOG_PASSWORD_OLD");
			CBLogger.d("LOG_PASSWORD_OLD=" + LOG_PASSWORD_OLD);
			LOG_PASSWORD_OLD = changPassword(request, LOG_PASSWORD_OLD);

			/**
			 * 修改登陆密码
			 * 
			 * @param userId
			 * @param oldPass
			 * @param newPass
			 * @return
			 */

			Long userid = getUserIDByPhone(PHONE_NO);
			ResultObject<String> retPass = memberService.changePass(userid, LOG_PASSWORD_OLD, LOG_PASSWORD);
			CBLogger.d("retPass=" + retPass.getMessage());
			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (retPass.isSuccess()) {

				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);

			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, retPass.getMessage());
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	/**
	 * 5080037 修改支付密码
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080037)
	@ResponseBody
	public void memberService_5080037(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			// 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);
			String PAY_PASSWORD = pkgFlag_json.getString("PAY_PASSWORD");
			CBLogger.d("PAY_PASSWORD=" + PAY_PASSWORD);
			PAY_PASSWORD = changPassword(request, PAY_PASSWORD);

			String PAY_PASSWORD_OLD = pkgFlag_json.getString("PAY_PASSWORD_OLD");
			CBLogger.d("PAY_PASSWORD=" + PAY_PASSWORD);
			PAY_PASSWORD_OLD = changPassword(request, PAY_PASSWORD_OLD);

			/**
			 * 修改支付密码
			 * 
			 * @param userId
			 * @param oldPass
			 * @param newPass
			 * @return
			 */

			Long userid = getUserIDByPhone(PHONE_NO);
			ResultObject<String> retPayPass = memberService.changePaypass(userid, PAY_PASSWORD_OLD, PAY_PASSWORD);
			CBLogger.d("retPayPass=" + retPayPass.getMessage());

			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (retPayPass.isSuccess()) {

				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);

			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				jsonObject.put(Constants.RETMSG, retPayPass.getMessage());
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}
	}

	/**
	 * 5080038
	 */
	@RequestMapping(params = Constants.TRADECODEFLAG_ACT + "=" + Constants.TRADECODEFLAG_5080038)
	@ResponseBody
	public void memberService_5080038(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (Constants.MOSHIFLAG) {
			testAPP(request);
		} else {
			// 生产的数据
			String jsonString = request.getAttribute("paramObject").toString();
			JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
			CBLogger.d(pkgFlag_json.toJSONString());
			String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
			CBLogger.d("PHONE_NO=" + PHONE_NO);
			String MSG_CODE = pkgFlag_json.getString("MSG_CODE");
			CBLogger.d("MSG_CODE=" + MSG_CODE);

			String SAFETY_PROBLEM_1 = pkgFlag_json.getString("SAFETY_PROBLEM_1");
			CBLogger.d("SAFETY_PROBLEM_1=" + SAFETY_PROBLEM_1);
			String SAFETY_PROBLEM_2 = pkgFlag_json.getString("SAFETY_PROBLEM_2");
			CBLogger.d("SAFETY_PROBLEM_2=" + SAFETY_PROBLEM_2);
			String SAFETY_PROBLEM_3 = pkgFlag_json.getString("SAFETY_PROBLEM_3");
			CBLogger.d("SAFETY_PROBLEM_3=" + SAFETY_PROBLEM_3);

			String SAFETY_PROBLEM_ANSWER_1 = pkgFlag_json.getString("SAFETY_PROBLEM_ANSWER_1");
			CBLogger.d("SAFETY_PROBLEM_ANSWER_1=" + SAFETY_PROBLEM_ANSWER_1);
			String SAFETY_PROBLEM_ANSWER_2 = pkgFlag_json.getString("SAFETY_PROBLEM_ANSWER_2");
			CBLogger.d("SAFETY_PROBLEM_ANSWER_2=" + SAFETY_PROBLEM_ANSWER_2);
			String SAFETY_PROBLEM_ANSWER_3 = pkgFlag_json.getString("SAFETY_PROBLEM_ANSWER_3");
			CBLogger.d("SAFETY_PROBLEM_ANSWER_3=" + SAFETY_PROBLEM_ANSWER_3);
			String LOG_PASSWORD = pkgFlag_json.getString("LOG_PASSWORD");
			CBLogger.d("LOG_PASSWORD=" + LOG_PASSWORD);

			Long userid = getUserIDByPhone(PHONE_NO);

			// PHONE_NO 1 String 手机号
			// MSG_CODE 1 String 短信验证码
			//
			// SAFETY_PROBLEM_1 1 String 安全问题1
			// SAFETY_PROBLEM_2 1 String 安全问题2
			// SAFETY_PROBLEM_3 1 String 安全问题3
			// SAFETY_PROBLEM_ANSWER_1 1 String 安全问题1 answer
			// SAFETY_PROBLEM_ANSWER_2 1 String 安全问题2
			// SAFETY_PROBLEM_ANSWER_3 1 String 安全问题3
			// LOG_PASSWORD 1 String 新的登录密码

			// ResultObject<MemberDetail>
			// retMemberDetail=memberService.detail(Long.parseLong(userid));

			JSONObject jsonObject = new JSONObject();
			// 根据 retObj，进行处理
			if (true) {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
				jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
			} else {
				jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
				// jsonObject.put(Constants.RETMSG,
				// retMemberDetail.getMessage());
				jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
				jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
			}
			request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
		}

	}

	/**
	 * PHONE_NO 1 String 账户 转入账户 CARD_NO 1 String 银行卡卡号 BANK_CODE 1 String 发卡行代码
	 * NAME 1 String 姓名 SUM 1 String 金额 PAY_PASSWORD 1 String 支付密码
	 * 
	 * @param request
	 */
	public void uatAPP5080016(HttpServletRequest request) {
		ResultObject<String> retObj = null;
		// 生产的数据
		String jsonString = request.getAttribute("paramObject").toString();
		JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
		CBLogger.d(pkgFlag_json.toJSONString());
		String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
		CBLogger.d("PHONE_NO=" + PHONE_NO);
		String CARD_NO = pkgFlag_json.getString("CARD_NO");
		String BANK_CODE = pkgFlag_json.getString("BANK_CODE");
		String NAME = pkgFlag_json.getString("NAME");
		String SUM = pkgFlag_json.getString("SUM");
		String PAY_PASSWORD = pkgFlag_json.getString("PAY_PASSWORD");
		PAY_PASSWORD = changPassword(request, PAY_PASSWORD);

		// 身份认证-验证
		// ResultObject<String> retidverify = memberService.idverify(userId,
		// idType, idcard, realname, cardno);

		Recharge recharge = new Recharge();

		recharge.setUserid(Long.parseLong(PHONE_NO));
		recharge.setAmount(Double.parseDouble(SUM));
		recharge.setOrderno("111111");
		recharge.setDescription("充值操作");
		recharge.setCreatetime(new Date());
		recharge.setCrytype("156");
		retObj = accountService.recharge(recharge);

		String msg = retObj.getMessage();
		CBLogger.d("============" + msg);

		CBLogger.d("retObj>>>" + retObj.getResult());

		// 返回调用方的json对象
		JSONObject jsonObject = new JSONObject();
		// 根据 retObj，进行处理
		if (retObj.isSuccess()) {
			jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
			jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
			jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
			jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
			// jsonObject.put(Constants.SERVER_LIST, retObj.getResult());
		} else {
			jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
			jsonObject.put(Constants.RETMSG, retObj.getMessage());
			jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
			jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
		}
		request.getSession().setAttribute("jsonBusiness", jsonObject.toString());
	}

	public Map getCardInfo(String CARD_NO) {
		/**
		 * 获取所有卡bin
		 * 
		 * @param card
		 * @return
		 */
		List<CardBin> listCardBin = cardService.findAllCardBin();

		List<Bank> retBank = cardService.findAllBank();
		Map<Object, Object> map = new HashMap<>();
		for (int i = 0; i < listCardBin.size(); i++) {
			CardBin cardBin = listCardBin.get(i);
			CBLogger.d("=========================" + cardBin.getCardBin());

			String bin = cardBin.getCardBin();
			if (CARD_NO.startsWith(bin)) {
				String Bankid = cardBin.getBankid();
				Integer CardType = cardBin.getCardType();
				Double SingleLimit = cardBin.getSingleLimit();
				Double DayLimit = cardBin.getDayLimit();
				Double MonthLimit = cardBin.getMonthLimit();
				map.put("Bankid", Bankid);
				map.put("CardType", CardType);
				map.put("SingleLimit", SingleLimit);
				map.put("DayLimit", DayLimit);
				map.put("MonthLimit", MonthLimit);

				for (int j = 0; j < retBank.size(); j++) {
					Bank bank = retBank.get(j);
					if (bank.getBankid().equals(Bankid)) {
						String Bankname = bank.getBankname();
						map.put("Bankname", Bankname);
					}
				}
			}
		}
		return map;
	}

	public long getDateSeconds(Date date) {
		long seconds = date.getTime();
		return seconds;
	}

	public String getAddressByProviceIDCityID(String idProvice, String idCity) {
		String SHOP_ADDRES = "";
		List<Province> listProvice = systemService.findAllProvince();
		for (int j = 0; j < listProvice.size(); j++) {
			Province provice = listProvice.get(j);
			if (idProvice.equals(provice.getProvinceid())) {
				String nameProvice = provice.getProvincename();
				SHOP_ADDRES = SHOP_ADDRES + nameProvice;
				break;
			}
		}

		List<City> listCity = systemService.findCitysByProvince(idProvice);
		for (int j = 0; j < listProvice.size(); j++) {
			City city = listCity.get(j);
			if (idCity.equals(city.getCityid())) {
				String nameCity = city.getCityname();
				SHOP_ADDRES = SHOP_ADDRES + nameCity;
				break;
			}
		}
		return SHOP_ADDRES;
	}

	public void testAPP(HttpServletRequest request) {
		ResultObject<Member> retObj = null;
		// 解析从客户端，接收的json对象
		// String jsonString =
		// "{'PHONE_NO':'18611888989','LOG_PASSWORD':'112233'}";
		// String jsonString =
		// "{'QUERYNM':'10','STARTNM':'1','STARTDATE':'20150215','CURRTYPE':'01','ACCNO':'61fadb29','ENDDATE':'20150316','BASEACC':'e26ece21'}";
		// JSONObject jb = JSONObject.parseObject(jsonString);

		String jsonString = request.getAttribute("paramObject").toString();
		JSONObject pkgFlag_json = JSONObject.parseObject(jsonString);
		CBLogger.d(pkgFlag_json.toJSONString());
		String PHONE_NO = pkgFlag_json.getString("PHONE_NO");
		CBLogger.d("PHONE_NO=" + PHONE_NO);
		String LOG_PASSWORD = pkgFlag_json.getString("LOG_PASSWORD");
		CBLogger.d("LOG_PASSWORD=" + LOG_PASSWORD);

		String DEVICEID = pkgFlag_json.getString("DEVICEID");
		CBLogger.d("DEVICEID=" + DEVICEID);

		// 调用服务端接口，进行交易处理
		// String username = request.getParameter("username");
		// username = jb.getString("PHONE_NO");
		// String password = request.getParameter("password");
		// password = jb.getString("LOG_PASSWORD");
		String ip = request.getRemoteAddr();// 实际根据前置部署来获得，此处仅仅是演示
		// String hardcode = request.getParameter("hardcode");
		CBLogger.d("loginService=" + loginService);
		retObj = loginService.login(Constants.CHANNEL, PHONE_NO, LOG_PASSWORD, ip, DEVICEID);

		// Constants.mobileMap.put(retObj.getResult().getUsername(),
		// retObj.getResult().getUserid());
		long userid = retObj.getResult().getUserid();
		CBLogger.d("++++++++++++++++++" + userid);
		// 返回调用方的json对象
		JSONObject jsonObject = new JSONObject();
		// 根据 retObj，进行处理
		// if (retObj.isSuccess()) {
		if (retObj != null) {
			jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_SUCCES);
			jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_SUCCES);
			jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_SUCCES);
			jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_SUCCES);
			// jsonObject.put(Constants.SERVER_LIST, retObj.getResult());
		} else {
			jsonObject.put(Constants.RETCODE, Constants.RETCODE_VALUE_FAIL);
			jsonObject.put(Constants.RETMSG, Constants.RETMSG_VALUE_FAIL);
			jsonObject.put(Constants.SUPPORT_VERSION, Constants.SUPPORT_VERSION_VALUE_FAIL);
			jsonObject.put(Constants.SERVER_TIME, Constants.SERVER_TIME_VALUE_FAIL);
		}
		request.getSession().setAttribute("jsonBusiness", jsonObject.toString());

	}
}