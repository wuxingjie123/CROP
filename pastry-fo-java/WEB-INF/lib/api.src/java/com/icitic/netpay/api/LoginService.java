package com.icitic.netpay.api;

import com.xunda.common.model.ResultObject;

public interface LoginService {
	/**
	 * 登陆
	 * @param channel 必输www app
	 * @param username 必输
	 * @param password 必输
	 * @param ip 如果渠道www需要提供ip地址 app也可以提供，可不提供
	 * @param hardcode 如果app需要提供唯一的硬件码
	 */
	public ResultObject<String> login(String channel,String username,String password,String ip,String hardcode);
}
