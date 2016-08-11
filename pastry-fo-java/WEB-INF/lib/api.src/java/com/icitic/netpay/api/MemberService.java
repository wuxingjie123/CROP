package com.icitic.netpay.api;

import com.xunda.common.model.ResultObject;
import com.xunda.model.Member;
import com.xunda.model.MemberDetail;
import com.xunda.model.MemberVerify;

public interface MemberService {
	/**
	 * 
	 * 查询会员基本信息
	 * @param userId
	 * @return
	 */
	public ResultObject<Member> info(Long userId);
	/**
	 * 查询会员详细信息
	 * @param userId
	 * @return
	 */
	public ResultObject<MemberDetail> detail(Long userId);
	/**
	 * 查询会员的安全验证信息
	 * @return
	 */
	public ResultObject<MemberVerify> security(Long userId);
	/**
	 * 编辑会员昵称
	 * @param member
	 * @param memberDetail
	 * @return
	 */
	public ResultObject<String> changeNickname(Long userId,String nickname);
	/**
	 * 编辑会员详细信息
	 * @param member
	 * @param memberDetail
	 * @return
	 */
	public ResultObject<String> edit(MemberDetail memberDetail);
	/**
	 * 修改会员的安全问题
	 * @param memberDetail
	 * @return
	 */
	public ResultObject<String> securityQuestion(MemberDetail memberDetail);
	/**
	 * 绑定邮箱
	 * @param userId
	 * @param email
	 * @return
	 */
	public ResultObject<String> bindEmail(Long userId,String email);
	/**
	 * 绑定手机
	 * @param userId
	 * @param mobile
	 * @return
	 */
	public ResultObject<String> bindMobile(Long userId,String mobile);
	/**
	 * 修改邮箱
	 * @param userId
	 * @param email
	 * @return
	 */
	public ResultObject<String> changeEmail(Long userId,String email);
	/**
	 * 修改手机
	 * @param userId
	 * @param mobile
	 * @return
	 */
	public ResultObject<String> changeMobile(Long userId,String mobile);
	/**
	 * 修改登陆密码
	 * @param userId
	 * @param oldPass
	 * @param newPass
	 * @return
	 */
	public ResultObject<String> changePass(Long userId,String oldPass,String newPass);
	/**
	 * 设置支付密码
	 * @param userId
	 * @param pass
	 * @return
	 */
	public ResultObject<String> setPaypass(Long userId,String pass);
	/**
	 * 修改支付密码
	 * @param userId
	 * @param oldPass
	 * @param newPass
	 * @return
	 */
	public ResultObject<String> changePaypass(Long userId,String oldPass,String newPass);
	/**
	 * 身份认证
	 * @param userId
	 * @param idType 默认为0 目前只支持身份证
	 * @param idcard 身份证号
	 * @param realname 姓名
	 * @param 卡号
	 * @return
	 */
	public ResultObject<String> idverify(Long userId,Integer idType,String idcard,String realname,String cardno);
}
