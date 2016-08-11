//
//  PTRSA.h
//  PT
//
//  Created by gengych on 16/3/14.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 @ingroup encryptionDecryptModuleClass
 @class PTRSA
 @brief PTRSA类
 @note  优化1.1注释：<br/>
 PTRSA 和 PTKeyStore 配合使用
 */
@interface PTRSA : NSObject
/**
 * RSA解密
 * @param  src       解密前的密文
 * @param  key       RSA公钥
 * @return           解密后的内容
 */
+ (NSData *)decrypt:(NSData *)src key:(SecKeyRef)key;

/**
 * RSA加密
 * @param  src       加密前明文
 * @param  key       RSA公钥
 * @return           加密后的内容
 */
+ (NSData *)encrypt:(NSData *)src key:(SecKeyRef)key;


/**
 *  对数据加密写入本地
 *
 *  @param filePath 写入文件路径
 *  @param src      写入数据
 *  @param key     密钥
 *
 *  @return 写入结果 成功与否
 */
+ (bool)encryptWriteFile:(NSString *)filePath encryptData:(NSData *)src key:(SecKeyRef)key;

/**
 *  读取本地加密文件
 *
 *  @param filePath 文件路径
 *  @param key     密钥
 *
 *  @return 解密后文件数据
 */
+ (NSData *)decryptReadFile:(NSString *)filePath key:(SecKeyRef)key;

@end
