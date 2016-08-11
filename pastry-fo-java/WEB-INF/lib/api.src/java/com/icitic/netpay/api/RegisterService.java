package com.icitic.netpay.api;

import com.xunda.common.model.ResultObject;
import com.xunda.model.Member;

public interface RegisterService {
	/**
	 * 会员注册
	 * @param member
	 * @return
	 */
	public ResultObject<Long> register(Member member);
	
	public ResultObject<String> active(Long userId);
	/**
	 * 查询会员是否注册
	 * @param username
	 * @return
	 */
	public ResultObject<Member> search(String username);
}
