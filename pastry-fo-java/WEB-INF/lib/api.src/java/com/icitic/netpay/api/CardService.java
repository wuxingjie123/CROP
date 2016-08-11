package com.icitic.netpay.api;

import java.util.List;

import com.xunda.common.model.Page;
import com.xunda.common.model.ResultObject;
import com.xunda.model.Bank;
import com.xunda.model.CardBin;
import com.xunda.model.MemberCard;
import com.xunda.vo.query.MemberCardQuery;

public interface CardService {
	/**
	 * 获取所有银行
	 * @param card
	 * @return
	 */
	public List<Bank> findAllBank();
	/**
	 * 获取所有卡bin
	 * @param card
	 * @return
	 */
	public List<CardBin> findAllCardBin();
	/**
	 * 绑定银行卡
	 * @param card
	 * @return
	 */
	public ResultObject<String> bind(MemberCard card);
	/**
	 * 解绑银行卡
	 * @param userId
	 * @param cardno
	 * @return
	 */
	public ResultObject<String> unbind(Long userId,String cardno);
	/**
	 * 帮卡验证
	 * @param userId
	 * @param cardno
	 * @param code
	 * @return
	 */
	public ResultObject<String> verify(Long userId,String cardno,String code);
	/**
	 * 查询绑定卡的列表 分页查询
	 * @param query
	 * @return
	 */
	public Page<MemberCard> search(MemberCardQuery query);
}
