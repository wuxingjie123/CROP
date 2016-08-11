package com.icitic.netpay.api;

import com.xunda.common.model.Page;
import com.xunda.common.model.ResultObject;
import com.xunda.model.Order;
import com.xunda.model.Payment;
import com.xunda.vo.query.OrderQuery;

public interface PayService {
	/**
	 * 生成消费订单
	 * @param order
	 * @return
	 */
	public ResultObject<String> order(Order order);
	/**
	 * 支付订单
	 * @param payment
	 * @return
	 */
	public ResultObject<String> payment(Payment payment);
	/**
	 * 订单撤销
	 * 调用此方法之前要检查支付状态，未发货的才可以撤销，撤销得等待审核
	 * @param orderno
	 * @return
	 */
	public ResultObject<String> ordervoid(String orderno);
	/**
	 * 订单退货
	 * @param orderno
	 * @return
	 */
	public ResultObject<String> refund(String orderno,double amount);
	/**
	 * 订单查询 分页
	 * @param query
	 * @return
	 */
	public Page<Order> orderQuery(OrderQuery query);
}
