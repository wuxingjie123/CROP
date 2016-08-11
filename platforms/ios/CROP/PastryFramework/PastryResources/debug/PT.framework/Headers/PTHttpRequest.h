//
//  PTHttpRequest.h
//  PT
//
//  Created by gengych on 16/3/15.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>

@class PTHttpResponse;
@class MKNetworkOperation;

/**
 @ingroup httpRequestModuleClass
 * 本类为类似于java中的interface<br />
 * 所有的有返回值的方法，指针类型返回值均返回nil<br />
 * NSInteger类型的参见具体的方法返回值说明
 */
@interface PTHttpRequest : NSObject

#pragma mark - 属性
/** url地址信息 */
@property (nonatomic,strong,setter=setUrl:,getter=getUrl) NSString *url PT_DEPRECATED(1.2.0, "使用 PTHttpRequestAFN 类");

/** 请求方式GET或POST */
@property (nonatomic,strong,setter=setMethod:,getter=getMethod) NSString *method PT_DEPRECATED(1.2.0, "使用 PTHttpRequestAFN 类");

/** 组装好的请求参数字符串 */
@property (nonatomic,strong,setter=setParameter:,getter=getParameter) NSString *parameter PT_DEPRECATED(1.2.0, "使用 PTHttpRequestAFN 类");

/** 获取Request请求中保存的Http头 
 备注：添加 header 使用 addRequestHeader:(NSString *)key value:(NSString *)value; 方法 */
@property (readonly,nonatomic,strong,getter=getHeaders)  NSDictionary *headers PT_DEPRECATED(1.2.0, "使用 PTHttpRequestAFN 类");


#pragma mark - 方法

#pragma mark - http 请求操作方法

#pragma mark 停止请求
/**
 * <b>弃用</b><br/>
 * 判断请求是否已取消
 * @return  返回请求是否已取消标记
 */
- (BOOL)isAborted PT_DEPRECATED(1.2.0, "未实现");

/**
 * <b>弃用</b><br/>
 * 关闭当前请求
 */
- (void)abort PT_DEPRECATED(1.2.0, "未实现");

#pragma mark 异步请求
/**
 * 发送异步请求
 * @return PTHttpResponse对象
 */
- (PTHttpResponse *)asynchDataRequest PT_DEPRECATED(1.2.0, "使用 asynchronousRequest: 方法");

/**
 @brief 开启新线程，发送异步请求
 @param block   block对象
 */
- (void)asynchronousRequest:(void(^)(PTHttpResponse *httpResponse,NSError *error))block;

/**
 * 大文件下载请求
 * @param   filepath        文件下载后存放的地方(全路径)
 * @param   progressBlock   文件下载进度PTFileRequestListener对象
 * @return
 *     YES   文件下载成功<br />
 *      NO   文件下载失败
 */
- (BOOL)fileRequest:(NSString *)filepath progressBlock:(void(^)(NSInteger length,NSInteger position))progressBlock PT_DEPRECATED(1.2.0, "使用 asynchFileRequest:progressBlock:completeBlock: 方法");

- (void)asynchFileRequest:(NSString *)targetPath
            progressBlock:(void(^)(NSInteger length, NSInteger position))progressBlock
            completeBlock:(void(^)(BOOL flag, NSInteger statusCode))completeBlock ;
/**
 * 小文件下载请求，并将下载后的文件以NSData形式返回
 * @return
 *     非空   文件下载成功<br />
 *     nil   文件下载失败
 */
- (NSData *)streamRequest PT_DEPRECATED(1.2.0, "使用 asynchFileRequest:progressBlock:completeBlock: 方法");

#pragma mark 同步请求
/**
 * 发送同步请求
 * @return PTHttpResponse对象
 */
- (PTHttpResponse *)synchDataRequest;

- (BOOL)synchFileRequest:(NSString *)targetPath
           progressBlock:(void(^)(NSInteger length, NSInteger position))progressBlock
           completeBlock:(void(^)(NSInteger statusCode))completeBlock;

#pragma mark - 获取 http 请求相关信息

#pragma mark 请求头信息
/**
 * 添加Http Request头
 * @param   key   http头关键字
 * @param   value http头值
 */
- (void)addRequestHeader:(NSString *)key value:(NSString *)value PT_DEPRECATED(1.2.0, "使用 PTHttpRequestAFN 类");

#pragma mark 其他
/**
 * 获取请求对象ID
 * @return 请求对象ID
 */
- (NSString *)getRequestID PT_DEPRECATED(1.2.0, "使用 PTHttpRequestAFN 类");

/**
 * 获取http请求的操作对象
 * @return http请求的操作对象
 */
- (MKNetworkOperation *)getMKNetworkOperation PT_DEPRECATED(1.2.0, "使用 PTHttpRequestAFN 类");

/**
 * 根据url取消请求
 */
- (void)cancelOperation PT_DEPRECATED(1.2.0, "使用 PTHttpRequestAFN 类");

@end
