package com.icitic.netpay.api;

import java.util.List;

import com.xunda.common.model.ResultObject;
import com.xunda.model.MemberGroup;

public interface MemberGroupService {
	/**
	 * 
	 * 查询所有的会员组
	 * @return
	 */
	public List<MemberGroup> searchAll();
	/**
	 * 查询单个会员组详情
	 * @param groupId
	 * @return
	 */
	public ResultObject<MemberGroup> info(Integer groupId);
}
