//
//  PTHttpRequestAFN.h
//  PT
//
//  Created by gengych on 16/5/24.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <PT/PT.h>

@class PTHttpResponse;

@interface PTHttpRequestAFN : PTHttpRequest

@property (nonatomic, strong, readonly) PTHttpRequestAFN*   (^setUrlAction)(NSString *action);
@property (nonatomic, strong, readonly) PTHttpRequestAFN*   (^setUrl)(NSString *url);
@property (nonatomic, strong, readonly) PTHttpRequestAFN*   (^setData)(NSData *data);
@property (nonatomic, strong, readonly) PTHttpRequestAFN*   (^setParams)(NSString *requestParams);
@property (nonatomic, strong, readonly) PTHttpRequestAFN*   (^setHttpMethod)(NSString *method);

/**
 * flag
 */
@property (nonatomic, strong, readonly) PTHttpRequestAFN*   (^addRequestHeader)(NSString *key, NSString *value);
@property (nonatomic, strong, readonly) PTHttpRequestAFN*   (^useSessions)(BOOL flag);
@property (nonatomic, strong, readonly) PTHttpRequestAFN*   (^useCookies)(BOOL flag);
@property (nonatomic, strong, readonly) PTHttpRequestAFN*   (^allowCompressedResponses)(BOOL flag);
@property (nonatomic, strong, readonly) PTHttpRequestAFN*   (^timeouts)(NSInteger timeout);



+ (PTHttpRequestAFN *)makeRequest:(void(^)(PTHttpRequestAFN *request))block;



- (PTHttpResponse *)getResponse;

@end
