//
//  PTRequestFactory.h
//  PT
//
//  Created by gengych on 16/3/16.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>

@class PTHttpRequest;

/**
 * @ingroup httpRequestModuleClass
 * 完成PTHttpRequest创建
 */
@interface PTRequestFactory : NSObject

@property(atomic, retain) NSMutableArray *requestArray;

/**
 * 以懒惰方式实现的单例模式
 * @return 返回PTRequestFactory对象
 */
+ (id)getInstance;

/**
 * 完成PTHttpRequest的生成工作
 * @param  url            不带任何参数的http地址，否则会出现问题，如http://www.google.com.hk
 * @param  paramArray     按照流程指定顺序的参数数组，数组中的单个参数是以Key<->value形式保存的NSDictionary,<br />
 *                        且这些参数都已做过加密和URLencode等必要的处理,无可以为空
 * @param  postData       需要post给服务器的数据，无可以为空
 * @param  httpMethod     http请求方式，POST或GET
 * @param  timeout        请求超时时间
 * @return PTHttpRequest对象
 */
- (PTHttpRequest *)createRequest:(NSString *)url param:(NSArray *)paramArray data:(NSData *)postData httpMethod:(NSString *)httpMethod timeout:(NSInteger)timeout PT_DEPRECATED(1.2.0, "使用 createRequest: param: data: httpMethod: timeout: type: 方法");

/**
 * 完成PTHttpRequest的生成工作
 * @param  url            不带任何参数的http地址，否则会出现问题，如http://www.google.com.hk
 * @param  paramArray     按照流程指定顺序的参数数组，数组中的单个参数是以Key<->value形式保存的NSDictionary,<br />
 *                        且这些参数都已做过加密和URLencode等必要的处理,无可以为空
 * @param  postData       需要post给服务器的数据，无可以为空
 * @param  httpMethod     http请求方式，POST或GET
 * @param  timeout        请求超时时间
 * @param  type           请求的类型
 * @return PTHttpRequest对象
 */
- (PTHttpRequest *)createRequest:(NSString *)url param:(NSArray *)paramArray data:(NSData *)postData httpMethod:(NSString *)httpMethod timeout:(NSInteger)timeout type:(NSString *)type;

/**
 * 向队列中添加一个请求，以便取消时调用
 * @param  httpRequest   request请求对象
 */
- (void)addHttpRequest:(PTHttpRequest *)httpRequest PT_DEPRECATED(1.2.0, "待定");

/**
 * 从队列中删除一个请求(请求被取消或请求成功时)
 * @param  httpRequest   request请求对象
 */
- (void)removeHttpRequest:(PTHttpRequest *)httpRequest PT_DEPRECATED(1.2.0, "待定");

/**
 * 从队列钟删除一个请求
 * @param  httpRequestId     request对象编号
 */
- (void)removeHttpRequestById:(NSString *)httpRequestId PT_DEPRECATED(1.2.0, "待定");

/**
 * 取消所有的request请求
 */
- (void)cancelAllRequest PT_DEPRECATED(1.2.0, "待定");

@end
