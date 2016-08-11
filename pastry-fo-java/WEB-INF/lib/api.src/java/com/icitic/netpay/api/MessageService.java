package com.icitic.netpay.api;

import com.xunda.common.model.Page;
import com.xunda.model.Message;
import com.xunda.vo.query.MessageQuery;

public interface MessageService {
	/**
	 * 查询会员消息 分页
	 * @param query
	 * @return
	 */
	public Page<Message> search(MessageQuery query);
}
