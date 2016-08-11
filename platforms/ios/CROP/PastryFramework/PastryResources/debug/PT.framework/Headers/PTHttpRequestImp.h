//
//  PTHttpRequestImp.h
//  PT
//
//  Created by gengych on 16/3/16.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import "PTHttpRequest.h"
@class MKNetworkEngine;

/**
 @ingroup httpRequestModuleClass
 @class PTHttpRequestImp
 @brief http请求子类
 */
@interface PTHttpRequestImp : PTHttpRequest

#pragma mark - 初始化请求
/**
 * 初始化PTHttpRequestImp类
 * @param   request   MKNetworkEngine对象，完成实际的请求发送和处理工作
 * @param   data      post给服务器的数据
 * @return PTHttpRequestImp对象
 */
- (id)init:(MKNetworkEngine *)request postData:(NSData *)data;

/**
 * 是否开启使用Session功能
 * @param flag
 *         YES  表示开启session功能<br />
 *          NO  表示关闭session功能<br/>
 */
- (void)setUseSession:(BOOL)flag;

/**
 * 是否开启使用Cookies功能
 * @param   flag
 *           YES  表示开启Cookies功能<br/>
 *            NO  表示关闭Cookies功能<br/>
 */
- (void)setUseCookie:(BOOL)flag;

/**
 * 是否开启安全证书校验
 * @param  flag
 *          YES  表示开启安全证书校验功能<br/>
 *           NO  表示关闭安全证书校验功能<br/>
 */
- (void)setValidatesSecureCertificate:(BOOL)flag;

/**
 * 是否允许压缩Response信息
 * @param   flag
 *           YES   表示开启允许服务器压缩response<br/>
 *            NO   表示关闭允许服务器压缩response<br/>
 */
- (void)setAllowCompressedResponse:(BOOL)flag;

/**
 * 设置请求超时时间
 * @param  timeout
 *               0  不设置超时，使用http默认超时时间<br/>
 *             非0  请求超时时间(秒)<br/>
 */
- (void)setTimeout:(NSInteger)timeout;

/**
 * 获取Request请求中保存的Cookies
 * @return
 *     非nil   返回带有Cookies的NSArray<br/>
 *       nil   无可用cookies <br/>
 */
- (NSArray *)getResponseCookies;

/**
 * 获取Response返回的NSData类型数据
 * @return
 *     非nil  返回Response内容，以NSData形式保存<br/>
 *       nil  无返回内容
 * @return Response的NSData类型数据
 */
- (NSData *)getResponseData;

/**
 * 获取Response返回的NSString类型数据
 * @return
 *     非nil   返回response内容，以NSString形式保存<br/>
 *       nil   无response内容或失败
 * @return Response的NSString类型数据
 */
- (NSString *)getResponseString;

/**
 * 获取Response返回的Http头
 * @return
 *     非nil   返回Http头<br/>
 *       nil   无http头或失败<br/>
 */
- (NSDictionary *)getResponseHeaders;

/**
 * 获取Response返回的状态码
 * @return
 *     成功  返回服务器实际返回的http请求状态码<br/>
 *     失败  返回-1
 */
- (NSInteger)getResponseStatus;

@end
