package com.icitic.netpay.api;

import com.xunda.common.model.Page;
import com.xunda.common.model.ResultObject;
import com.xunda.model.MemberAddress;
import com.xunda.vo.query.MemberAddressQuery;

public interface AddressService {
	/**
	 * 查询收货地址
	 * @param query
	 * @return
	 */
	public Page<MemberAddress> search(MemberAddressQuery query);
	/**
	 * 增加收获地址
	 * @param address
	 * @return
	 */
	public ResultObject<String> add(MemberAddress address);
}
