//
//  PTDB.h
//  HelloCordova
//
//  Created by gengych on 16/1/26.
//
//

//#import <Cordova/Cordova.h>
#import <Cordova/CDV.h>

// 接收 JS 端发送的数据库请求；然后将数据库结果返回发送给 JS 端；
@interface PTDB : CDVPlugin

- (void)insertDB:(CDVInvokedUrlCommand*)command;

- (void)deleteDB:(CDVInvokedUrlCommand*)command;

- (void)updateDB:(CDVInvokedUrlCommand*)command;

- (void)queryDB:(CDVInvokedUrlCommand*)command;

@end
