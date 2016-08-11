//
//  PTRequest.m
//  HelloCordova
//
//  Created by gengych on 16/1/26.
//
//

#import "PTRequest.h"
#import "PTServletRequest.h"

@implementation PTRequest

- (void)request:(CDVInvokedUrlCommand*)command
{
    PTServletRequest *request = [PTServletRequest PTServletRequestWithDic:command.arguments[0]];
    
    // 1 检查网络状态
    NSMutableDictionary *responseMsg = nil;
    
    // 2、检查参数
    NSString *url = [request getUrl];
    
    if (url == nil || [url length] == 0) {
        //js传递的参数有误，直接回传错误给js
        responseMsg = [NSMutableDictionary dictionary];
        [responseMsg setObject:@"请求参数错误，请联系客服人员！" forKey:@"ERRMSG"];
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:responseMsg];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        
        return;
    }
    else{
        // 3、 检查是否握手成功
        if ([[PTFramework getInstance] getSessionState] >= STATE_UNLOGIN) {
            
            // 4、握手成功，向服务器发送请求
            [NSThread detachNewThreadSelector:@selector(sendRequest:) toTarget:self withObject:command];
        }
        else{
            // 4、握手失败，返回失败结果
            NSMutableDictionary *errDic = [NSMutableDictionary dictionary];
            [errDic setObject:@"客户端握手失败！" forKey:@"ERRMSG"];
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errDic];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }
    
}

-(NSMutableDictionary *)checkNetworkUsableWithRequest:(PTServletRequest *)request
{
    if ([[PTFramework getInstance] getCurrentNetworkState] == NetworkStateNotReachable) {//网络不可用
        //直接返回错误
        NSMutableDictionary *errDic = [NSMutableDictionary dictionary];
        [errDic setObject:@"网络不可用，请重新设置！" forKey:@"RETMSG"];
        [errDic setObject:[NSString stringWithFormat:@"%d",NetworkUnreachableError] forKey:@"RETCODE"];
        return errDic;
    }
    else{
        return nil;
    }
}

- (void)sendRequest:(CDVInvokedUrlCommand *)command{
    
    PTServletRequest *request = [PTServletRequest PTServletRequestWithDic:command.arguments[0]];
    
    if (request == nil) {
        return;
    }
    PTLogDebug(@"sendCommand");
    NSMutableDictionary *jsonDic = [NSMutableDictionary dictionary];
    NSDictionary *dataDictionary = [request getData];
    [jsonDic setObject:[NSNumber numberWithBool:[request getAbolishable]] forKey:@"abolishable"];
    [jsonDic setObject:[NSNumber numberWithBool:[request getBlockState]] forKey:@"preventable"];
    [jsonDic setObject:[NSNumber numberWithFloat:[request getWaitingTime]] forKey:@"timeout"];
    if (dataDictionary == nil) {
        dataDictionary = [NSMutableDictionary dictionary];
    }
    [jsonDic setObject:dataDictionary forKey:@"data"];
    PTLogDebug(@"sendCommand  jsonDic = %@",jsonDic);
    
    [PTCommunicationHelper asynchBusinessRequest_AFN:[request getUrl]
                                                json:jsonDic
                                           cryptFlag:DATA_CRYPT_3DES
                                     timeOutInterval:0
                                           callblock:^(PTComPackage *comPackage, NSError *error) {
                                               PTLogDebug(@"------ compkg = %@", comPackage);
                                               
                                               CDVPluginResult* pluginResult = nil;
                                               if (comPackage != nil && [comPackage errCode] == 0) {//密钥协商成功
                                                   
                                                   NSMutableDictionary *backDictionary = [NSMutableDictionary dictionary];
                                                   if (comPackage != nil) {
                                                       backDictionary = [[[comPackage dataPackage] business] objectFromJSONData];
                                                   }
                                                   
                                                   pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:backDictionary];
                                                   
                                                   [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
                                                   
                                               }
                                               else{
                                                   NSMutableDictionary *backDictionary = [NSMutableDictionary dictionary];
                                                   if (comPackage != nil) {
                                                       backDictionary = [[[comPackage dataPackage] business] objectFromJSONData];
                                                   }
                                                   pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:backDictionary];
                                                   [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
                                               }
                                           }];
    
//    // 之前的网络请求格式；
    
//    PTComPackage *comPackage =[PTCommunicationHelper asynchBusinessRequest:[request getUrl] json:jsonDic cryptFlag:DATA_CRYPT_3DES timeOutInterval:0];
//    
//    PTLogDebug(@"------ compkg = %@", comPackage);
//    
//    CDVPluginResult* pluginResult = nil;
//    if (comPackage != nil && [comPackage errCode] == 0) {//密钥协商成功
//        
//        NSMutableDictionary *backDictionary = [NSMutableDictionary dictionary];
//        if (comPackage != nil) {
//            backDictionary = [[[comPackage dataPackage] business] objectFromJSONData];
//        }
//        
//        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:backDictionary];
//        
//        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
//        
//    }
//    else{
//        NSMutableDictionary *backDictionary = [NSMutableDictionary dictionary];
//        if (comPackage != nil) {
//            backDictionary = [[[comPackage dataPackage] business] objectFromJSONData];
//        }
//        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:backDictionary];
//        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
//    }
}

@end
