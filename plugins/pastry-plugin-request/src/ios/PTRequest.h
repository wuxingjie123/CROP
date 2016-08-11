//
//  PTRequest.h
//  HelloCordova
//
//  Created by gengych on 16/1/26.
//
//

//#import <Cordova/Cordova.h>
#import <Cordova/CDV.h>

// 接收 JS 端发送的网络请求，加密后转发给服务器；然后将服务器返回的结果发送给 JS 端；
@interface PTRequest : CDVPlugin

- (void)request:(CDVInvokedUrlCommand*)command;

@end
