//
//  PTSessionManager.h
//  PT
//
//  Created by gengych on 16/3/14.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>

@class PTHttpResponse;

/**
 * @ingroup httpAssistModuleClass
 * Request请求会话管理
   @note 
    1、每次给服务器发送请求前，都需要验证会话状态。<br/>
        如果是handshake请求，或者会话状态>=STATE_UNLOGIN，允许向服务器发送请求；<br/>
        否则不允许向服务器发送请求；<br/>
    2、
 */
@interface PTSessionManager : NSObject

#pragma mark 属性

/** 会话状态标识 */
@property (nonatomic,setter=setSessionState:) PTSessionState    sessionState;

/** 获取用户端随机数 */
@property (nonatomic,getter=getClientRandom) NSString           *clientRandom;

/** 获取用户端随机数密钥 */
@property (nonatomic,getter=getClientRandomKey) NSString        *clientRandomKey;

/** 获取服务器随机数密钥(加密情况) */
@property (nonatomic,getter=getEncryptServerRandomKey) NSString  *encryptServerRandomKey;

/** 获取服务器随机数密钥(解密情况) */
@property (nonatomic,getter=getDecryptServerRandomKey) NSString  *decryptServerRandomKey;

/** 获取当前会话Session密钥 */
@property (nonatomic,getter=getSessionKey)             NSString  *sessionKey;

/**
 保存设备识别号key <br/>
 获取由服务器下发的设备绑定key <br/>
 */
@property (nonatomic,strong,setter=setBindKey:,getter=getBindKey) NSString  *bindKey;

/** 获取需要验证码标志 */
@property (nonatomic,strong,getter=getVerificationFlag) NSString        *verificationFlag;

#pragma mark - 方法
/**
 * 以懒惰方式实现的单例模式
 * @return 返回PTSessionManager对象
 */
+ (id)getInstance;

/**
 * 初始化 会话状态模块
 */
- (void)initialization;

/**
 * 更新用户端随机数
 */
- (void)updateClientRandom;

/**
 * 清空服务器随机数队列(每次做密钥协商之前，需要清空)
 */
- (void)emptyServerRandom;

/**
 * 更新当前会话中的随机数和Session密钥
 * @param sessionId  sessionId （来自Response对象属性）
 * @param headers    headers（来自Response对象属性）
 */
- (void)updateSession:(NSString *)sessionId headers:(NSDictionary *)headers;

/**
 * 获取系统参数--系统参数中包含诸如单笔限额、日累计限额
 * @param        key   key值
 * @return
 *      nil      获取失败<br/>
 *     非nil     获取成功<br/>
 */
- (NSDictionary *)getSystemParameter:(NSString *)key;

/**
 * 设置系统参数
 * @param    key    key值
 * @param    param  系统参数
 */
- (void)setSystemParameter:(NSString *)key param:(NSDictionary *)param;

@end
