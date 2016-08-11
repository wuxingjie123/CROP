//
//  PTServletRequest.h
//  CBCordova
//
//  Created by gengych on 16/4/7.
//
//

#import <Foundation/Foundation.h>
typedef enum _PTJSRequestSchemeFlag
{
    JS_Scheme_Flag_Not_Found = 0,
    /* client：送往客户端的请求*/
    JS_Scheme_Flag_Client = 1,
    /* http： https: 送往服务器的请求*/
    JS_Scheme_Flag_Server = 2
}PTJSRequestSchemeFlag;

@interface PTServletRequest : NSObject

/**
 * 初始化PTServletRequest
 * @param  jsonMessage JS发送的message
 * @return
 */
+(PTServletRequest *)PTServletRequest:(NSString *)jsonMessage;

+ (PTServletRequest *)PTServletRequestWithDic:(NSDictionary *)dicMessage;

/**
 * 获取message中的requestId
 * return
 */
-(NSString *)getRequestID;
//-(NSString *)getQuery;

/**
 * 获取message中的data信息
 * return
 */
-(id)getData;

//"block":true,"cancelable":false
- (BOOL)getBlockState;

- (BOOL)getAbolishable;
/**
 * 获取message中url的host信息
 * return
 */
//-(NSString *)getHost;
/**
 * 获取message中url信息
 * return
 */
-(NSString *)getUrl;
/**
 * 获取message中url的path信息
 * return
 */
-(NSString *)getPath;
/**
 * 获取message中url的scheme信息
 * return
 */
-(NSString *)getScheme;

-(PTJSRequestSchemeFlag)getSchemeFlag;
/**
 * 获取message中url的host信息
 * return
 */
-(id)getRawRequest;
/**
 * 获取message中的param信息
 * return
 */
//-(NSDictionary *)getParameter;
/**
 * 获取message中url的parameterValue信息
 * return
 */
-(NSString *)getParameterValue;
/**
 * 获取message中的callBacks信息
 * return
 */
-(NSString *)getCallBacks;
/**
 * 获取message中的waiting状态
 * return
 */
-(BOOL)getWaiting;

-(CGFloat)getWaitingTime;
@end
