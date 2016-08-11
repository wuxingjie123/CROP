//
//  PTComPackage.h
//  PT
//
//  Created by gengych on 16/3/16.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>
@class PTDataPackage;

/**
 * @ingroup httpSecurityModuleClass
 * 功能：
 *      1、加密 客户端发给服务器的 数据
 *      2、加密 服务器发给客户端的 数据
 */
@interface PTComPackage : NSObject

#pragma mark - 属性
/** 数据包标志类型标志 */
@property (readonly) PTComPackageFlag pkgFlag;
/** 错误码(一般用于下行时) */
@property (readonly) NSInteger errCode;
/** 错误内容(一般用于下行时) */
@property (readonly) NSString *errMessage;
/** 菜单相关数据 */
@property (readonly) NSMutableDictionary *menuDataDic;
/** 数据包 */
@property (readonly) PTDataPackage *dataPackage;


#pragma mark - 方法
/**
 * 初始化
 * @param  pkgFlag   数据封装包标志
 * @param  errCode   错误码
 * @param  errMsg    错误信息
 * @param  dataPkg   数据包
 * @return PTComPackage对象
 * @note    pkgFlag @see PTComPackageFlag
 *          errCode : errMsg @see PTCommunicationErrorCode
 *          <br/> + 服务器返回的错误码 : 服务器返回的错误信息
 *          dataPkg @see PTDataPackage
 */
- (id)init:(PTComPackageFlag)pkgFlag errCode:(NSInteger)errCode errMsg:(NSString *)errMsg data:(PTDataPackage *)dataPkg;

/**
 * 初始化
 * @param  pkgFlag   数据封装包标志
 * @param  errCode   错误码
 * @param  errMsg    错误信息
 * @param  menuData  菜单相关数据对象
 * @param  dataPkg   数据包
 * @return PTComPackage对象
 * @note    pkgFlag @see PTComPackageFlag
 *          errCode : errMsg @see PTCommunicationErrorCode
 *          <br/> + 服务器返回的错误码 : 服务器返回的错误信息
 *          dataPkg @see PTDataPackage
 */
- (id)init:(PTComPackageFlag)pkgFlag errCode:(NSInteger)errCode errMsg:(NSString *)errMsg menuData:(NSDictionary *)menuData data:(PTDataPackage *)dataPkg;

/**
 * 将对象流化成JSON串(加密)
 * @return JSON的字符串
 */
- (NSString *)toJSONString;

/**
 * 将对象流转成JSON串(不加密)
 * @return JSON的字符串
 */
- (NSString *)toPlainJSONString;

#pragma mark - 静态方法
/**
 * 解析JSON字符串，并赋值给对象各成员变量
 * @param  json  JSON字符串
 * @exception PTException(MPCC002 -- 参数错误)
 * @exception PTException(MPCC004 -- 解析Data Package失败)
 * @return
 *         非空   PTComPackage对象
 *         nil   JSON解析失败
 */
+ (PTComPackage *)parseJSONString:(NSString *)json;

@end
