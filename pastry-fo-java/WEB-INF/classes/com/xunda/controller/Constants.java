package com.xunda.controller;

public class Constants {
	public static String CHANNEL = "APP";
	public static String IP = "192.168.3.195";
	public static final int USER_NEW = 0;// 新用户
	public static final int USER_REG = 1;// 注册，未激活用户

	public static final boolean MOSHIFLAG = false;
	public static final boolean MOSHIFLAGJIAOYI = true;
	public static final String RETCODE = "RETCODE";
	public static final String RETMSG = "RETMSG";
	public static final String SUPPORT_VERSION = "SUPPORT_VERSION";
	public static final String SERVER_TIME = "SERVER_TIME";
	public static final String SERVER_LIST = "LIST";

	public static final String SERVER_MOB = "18623457896";

	public static final String RETCODE_VALUE_SUCCES = "AAAAAAA";
	public static final String RETMSG_VALUE_SUCCES = "交易成功";
	public static final String SUPPORT_VERSION_VALUE_SUCCES = "接口支持的最低版本";
	public static final String SERVER_TIME_VALUE_SUCCES = "服务器时间";

	public static final String RETCODE_VALUE_FAIL = "9999999";
	public static final String RETMSG_VALUE_FAIL = "交易失败";
	public static final String SUPPORT_VERSION_VALUE_FAIL = "SUPPORT_VERSION";
	public static final String SERVER_TIME_VALUE_FAIL = "SERVER_TIME";
	public static final String TRADECODEFLAG_SHAKEHANG = "handshake";
	public static final String TRADECODEFLAG_ACT = "act";
	public static final String TRADECODEFLAG_5080001 = "5080001";
	public static final String TRADECODEFLAG_5080002 = "5080002";
	public static final String TRADECODEFLAG_5080003 = "5080003";
	public static final String TRADECODEFLAG_5080004 = "5080004";
	public static final String TRADECODEFLAG_5080005 = "5080005";
	public static final String TRADECODEFLAG_5080006 = "5080006";
	public static final String TRADECODEFLAG_5080007 = "5080007";
	public static final String TRADECODEFLAG_5080008 = "5080008";
	public static final String TRADECODEFLAG_5080009 = "5080009";
	public static final String TRADECODEFLAG_5080010 = "5080010";
	public static final String TRADECODEFLAG_5080011 = "5080011";
	public static final String TRADECODEFLAG_5080012 = "5080012";
	public static final String TRADECODEFLAG_5080013 = "5080013";
	public static final String TRADECODEFLAG_5080014 = "5080014";
	public static final String TRADECODEFLAG_5080015 = "5080015";
	public static final String TRADECODEFLAG_5080016 = "5080016";
	public static final String TRADECODEFLAG_5080017 = "5080017";
	public static final String TRADECODEFLAG_5080018 = "5080018";
	public static final String TRADECODEFLAG_5080019 = "5080019";
	public static final String TRADECODEFLAG_5080020 = "5080020";
	public static final String TRADECODEFLAG_5080021 = "5080021";
	public static final String TRADECODEFLAG_5080033 = "5080033";
	public static final String TRADECODEFLAG_5080034 = "5080034";
	public static final String TRADECODEFLAG_5080035 = "5080035";
	public static final String TRADECODEFLAG_5080036 = "5080036";
	public static final String TRADECODEFLAG_5080037 = "5080037";
	public static final String TRADECODEFLAG_5080038 = "5080038";
	public static final String TRADECODEFLAG_5080031 = "5080031";
	public static final String TRADECODEFLAG_5080041 = "5080041";
	public static final String TRADECODEFLAG_5080051 = "5080051";
	public static final String TRADECODEFLAG_5080061 = "5080061";

	public static boolean isMobilePhone(String str) {
		
		try {
			long i = Long.parseLong(str);
			boolean is = str.matches("^(13|15|18|17)\\d{9}$");
			System.out.println(is);
			return true;
		} catch (NumberFormatException e) {
			System.out.println("请输入正确的手机号,你输入的数据为"+str);
			return false;
		}

	}
}
