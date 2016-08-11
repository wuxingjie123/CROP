package com.icitic.netpay.api;

import com.xunda.common.model.ResultObject;

public interface AvatarService {
	/**
	 * 更新头像
	 * @param userId
	 * @param fileName
	 * @param ext
	 * @param content
	 * @return
	 */
	public ResultObject<String> updateAvatar(Long userId,String fileName,String ext,byte[] content);
}
