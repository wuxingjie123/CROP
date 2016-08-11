//
//  PTCommunicationHelper.h
//  PT
//
//  Created by gengych on 16/3/16.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>

@class PTComPackage;
@class PTDataPackage;

/**
 @ingroup httpAssistModuleClass
 @brief PTCommunicationHelper 类
 */
@interface PTCommunicationHelper : NSObject

/**
 * 发送同步业务请求
 * @param   url    请求的相对url
 * @param   jsonObject   请求的数据json对象
 * @return
 *         nil     请求失败<br/>
 *       非nil     请求成功，返回PTComPackage对象
 * 注：该请求采用默认的3DES加密算法，如用其他算法请用下面的函数<br/>
 */
+ (PTComPackage *)synchBusinessRequest:(NSString *)url json:(NSDictionary *)jsonObject PT_DEPRECATED(1.2.0, "使用 synchBusinessRequest_AFN:json:cryptFlag:timeOutInterval 方法");
/**
 * 发送同步业务请求
 * @param   url    请求的相对url
 * @param   jsonObject   请求的数据json对象
 * @param   timeoutInterval 请求超时时间
 * @return
 *         nil     请求失败<br/>
 *       非nil     请求成功，返回PTComPackage对象
 * 注：该请求采用默认的3DES加密算法，如用其他算法请用下面的函数<br/>
 */
+ (PTComPackage *)synchBusinessRequest:(NSString *)url json:(NSDictionary *)jsonObject timeOutInterval:(CGFloat)timeoutInterval PT_DEPRECATED(1.2.0, "使用 synchBusinessRequest_AFN:json:cryptFlag:timeOutInterval 方法");

/**
 * 发送同步业务请求
 * @param   url        请求的相对url
 * @param   jsonObject       请求的数据json对象
 * @param   cryptFlag  加密算法标志
 * @return
 *         nil     请求失败<br/>
 *       非nil     请求成功，返回PTComPackage对象
 */
+ (PTComPackage *)synchBusinessRequest:(NSString *)url json:(NSDictionary *)jsonObject cryptFlag:(PTDataPackageCryptType)cryptFlag PT_DEPRECATED(1.2.0, "使用 synchBusinessRequest_AFN:json:cryptFlag:timeOutInterval 方法");

/**
 * 发送同步业务请求
 * @param   url        请求的相对url
 * @param   jsonObject       请求的数据json对象
 * @param   cryptFlag  加密算法标志
 * @param   timeoutInterval 请求超时时间
 * @return
 *         nil     请求失败<br/>
 *       非nil     请求成功，返回PTComPackage对象
 */
+ (PTComPackage *)synchBusinessRequest:(NSString *)url json:(NSDictionary *)jsonObject cryptFlag:(PTDataPackageCryptType)cryptFlag timeOutInterval:(CGFloat)timeoutInterval PT_DEPRECATED(1.2.0, "使用 synchBusinessRequest_AFN:json:cryptFlag:timeOutInterval 方法");

/**
 * 发送异步业务请求
 * @param   url    请求的相对url
 * @param   jsonObject   请求的数据json对象
 * @return
 *         nil     请求失败<br/>
 *       非nil     请求成功，返回PTComPackage对象
 * 注：该请求采用默认的3DES加密算法，如用其他算法请用下面的函数<br/>
 */
+ (PTComPackage *)asynchBusinessRequest:(NSString *)url json:(NSDictionary *)jsonObject PT_DEPRECATED(1.2.0, "使用 asynchBusinessRequest_AFN:json:cryptFlag:timeOutInterval:callblock 方法");

/**
 * 发送异步业务请求
 * @param   url    请求的相对url
 * @param   jsonObject   请求的数据json对象
 * @param   timeoutInterval 请求超时时间
 * @return
 *         nil     请求失败<br/>
 *       非nil     请求成功，返回PTComPackage对象
 * 注：该请求采用默认的3DES加密算法，如用其他算法请用下面的函数<br/>
 */
+ (PTComPackage *)asynchBusinessRequest:(NSString *)url json:(NSDictionary *)jsonObject timeOutInterval:(CGFloat)timeoutInterval PT_DEPRECATED(1.2.0, "使用 asynchBusinessRequest_AFN:json:cryptFlag:timeOutInterval:callblock 方法");


/**
 * 发送异步业务请求
 * @param   url        请求的相对url
 * @param   jsonObject       请求的数据json对象
 * @param   cryptFlag  加密算法标志
 * @return
 *         nil     请求失败<br/>
 *       非nil     请求成功，返回PTComPackage对象
 */
+ (PTComPackage *)asynchBusinessRequest:(NSString *)url json:(NSDictionary *)jsonObject cryptFlag:(PTDataPackageCryptType)cryptFlag PT_DEPRECATED(1.2.0, "使用 asynchBusinessRequest_AFN:json:cryptFlag:timeOutInterval:callblock 方法");

/**
 * 发送异步业务请求
 * @param   url        请求的相对url
 * @param   jsonObject       请求的数据json对象
 * @param   cryptFlag  加密算法标志
 * @param   timeoutInterval 请求超时时间
 * @return
 *         nil     请求失败<br/>
 *       非nil     请求成功，返回PTComPackage对象
 */
+ (PTComPackage *)asynchBusinessRequest:(NSString *)url json:(NSDictionary *)jsonObject cryptFlag:(PTDataPackageCryptType)cryptFlag timeOutInterval:(CGFloat)timeoutInterval PT_DEPRECATED(1.2.0, "使用 asynchBusinessRequest_AFN:json:cryptFlag:timeOutInterval:callblock 方法");

///**
// * 构建异步请求
// * @param   url        请求的相对url
// * @param   json
// *                     {
// *                      "data":"";//请求的数据json对象
// *                      "preventable":false;//是否阻塞
// *                      "abolishable":true//是否可取消
// *                      "timeout":500//超时时间，-1不记超时
// *                      }
// * @param   automatic  是否自动执行网络请求
// * @return
// *          nil        构建请求失败<br/>
// *          非nil       构建请求成功，返回PTTask对象
// * 注：该请求采用默认的3DES加密算法，如用其他算法请用下面的函数<br/>
// */
//+ (PTBaseTask *)asynchBusinessTask:(NSString *)url json:(NSDictionary *)jsonObject listener:(id<PTTaskListener>)listener automaticPerform:(BOOL)automatic;
///**
// * 构建异步请求
// * @param   url        请求的相对url
// * @param   json
// *                     {
// *                      "data":"";//请求的数据json对象
// *                      "preventable":false;//是否阻塞
// *                      "abolishable":true//是否可取消
// *                      "timeout":500//超时时间，-1不记超时
// *                      }
// * @param   cryptFlag  加密算法标志
// * @param   automatic  是否自动执行网络请求
// * @return
// *          nil        构建请求失败<br/>
// *          非nil       构建请求成功，返回PTTask对象
// */
//+ (PTBaseTask *)asynchBusinessTask:(NSString *)url json:(NSDictionary *)jsonObject listener:(id<PTTaskListener>)listener cryptFlag:(PTDataPackageCryptType)cryptFlag automaticPerform:(BOOL)automatic;
///**
// * 下载图片验证码
// * @param   url  请求的相对url
// * @param   param 请求的参数
// * @return
// *     nil     请求失败<br/>
// *    非nil    根据状态码判断是否成功<br/>
// * @note   字典返回内容说明
// *         RETCODE  为七个A表示成功，其余失败<br/>
// *         RETMSG   请求成功时为空串，失败时为失败原因<br/>
// *         content  下载成功后的图片流<br/>
// */
//+ (NSDictionary *)downloadVCodePicture:(NSString *)url param:(NSArray *)param;
//
//
///**
// * 判断请求是否可通过（网络状态，session状态）
// * @param url 请求url
// * @param error 错误信息
// * @return BOOL
// */
//+ (BOOL)allowSendRequest:(NSString *)url error:(NSError **)error;
//
//
///**
// * 下载图片验证码
// * @param   url  请求的相对url
// * @param   param 请求的参数
// * @return
// *     nil     请求失败<br/>
// *    非nil    根据状态码判断是否成功<br/>
// * @note   字典返回内容说明
// *         RETCODE  为七个A表示成功，其余失败<br/>
// *         RETMSG   请求成功时为空串，失败时为失败原因<br/>
// *         content  下载成功后的图片流<br/>
// */
////+ (NSDictionary *)downloadVCodePicture:(NSString *)url param:(NSArray *)param error:(NSError **)error;

@end
