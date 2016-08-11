package com.icitic.netpay.api;

import com.xunda.common.model.Page;
import com.xunda.common.model.ResultObject;
import com.xunda.model.Recharge;
import com.xunda.model.Withdraw;
import com.xunda.vo.query.RechargeQuery;
import com.xunda.vo.query.WithdrawQuery;

public interface AccountService {
	/**
	 * 银行卡充值到账户
	 * @return
	 */
	public ResultObject<String> recharge(Recharge recharge);
	/**
	 * 账户提现到银行卡
	 * @param withdraw
	 * @return
	 */
	public ResultObject<String> withdraw(Withdraw withdraw);
	/**
	 * 查询充值流水
	 * @param query
	 * @return
	 */
	public Page<Recharge> rechargeSearch(RechargeQuery query);
	/**
	 * 查询提现流水
	 * @param query
	 * @return
	 */
	public Page<Withdraw> withdrawSearch(WithdrawQuery query);
}
