//
//  PTDataPackage.h
//  PT
//
//  Created by gengych on 16/3/16.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 * @ingroup httpSecurityModuleClass
 * 功能：
 *      解密 服务器返回给客户端的 数据
 */
@interface PTDataPackage : NSObject

#pragma mark - 属性
/** 加密标志 */
@property (readonly) PTDataPackageCryptType cryptFlag;

/** 内容hash标志 */
@property (readonly) PTDataPackageHashType hashFlag;

/** 数据内容签名标志 */
@property (readonly) PTDataPackageSignatureFlag signatureFlag;

/** 数据内容 */
@property (readonly) NSData *business;

#pragma mark - 方法
/**
 * 初始化方法
 * @param      cryptFlag     数据内容加密标志
 *            CRYPT_NONE     不加密<br />
 *            CRYPT_3DES     3DES加密<br />
 *             CRYPT_AES     AES加密<br />
 *             CRYPT_RC5     RC5 加密
 * @param       hashFlag     数据内容hash标志
 *             HASH_NONE     不做Hash计算<br />
 *              HASH_MD5     用MD5算法做Hash计算<br />
 *             HASH_SHA1     用sha1算法做Hash计算
 * @param       signFlag     数据内容做签名标志
 *        SIGNATURE_NONE     不做签名<br />
 *     SIGNATURE_MD5_RSA     对MD5的hash值进行RSA签名<br />
 *    SIGNATURE_SHA1_RSA     对SHA1的hash值进行RSA签名
 * @param           busi     数据内容
 *
 */
- (id)init:(PTDataPackageCryptType)cryptFlag hashFlag:(PTDataPackageHashType)hashFlag signFlag:(PTDataPackageSignatureFlag)signFlag busi:(NSData *)busi;

/**
 * 将对象流化成JSON串
 * @return 结果
 */
- (NSString *)toJSONString;

/**
 * 将对象流化成JSON串(明文)
 * @return 结果
 */
- (NSString *)toPlainJSONString;

#pragma mark - 静态方法
/**
 * 解析JSON字符串，并赋值给对象各成员变量
 * @param  json  JSON字符串
 * @exception PTException(MPCC003 -- 参数错误)
 *            PTException(MPCC005 -- 验签异常(MD5))<br />
 *            PTException(MPCC006 -- 验签异常(SHA1))<br />
 *            PTException(MPCC007 -- HASH比对异常(MD5))<br />
 *            PTException(MPCC008 -- HASH比对异常(SHA1))<br />
 *            PTException(MPCC009 -- 解密business数据异常)
 * @return
 *         非空   PTDataPackage对象
 *         nil   JSON解析失败
 */
+ (PTDataPackage *)parseJSONString:(NSString *)json;

@end
