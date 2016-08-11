package com.icitic.netpay.api;

import com.xunda.common.model.ResultObject;

public interface VerifyCodeService {
	/**
	 * 发送验证码
	 * 手机登陆，注册等发送验证请求， 帮卡发送验证码请求 快捷支付请求（一种） 修改手机号 等等需要发送验证码的场景调用此方法发送验证码
	 * @param channel
	 * @param userId
	 * @param codeType
	 * @return
	 */
	public ResultObject<String> generateCode(String channel,Long userId,int codeType,String target);
	/**
	 * 
	 * 验证generateCode生成的验证码
	 * @param channel
	 * @param userId
	 * @param code
	 * @return
	 */
	public ResultObject<String> verify(String channel,Long userId,String code);
}
