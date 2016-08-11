
//
//  PTBrowser.m
//  PTCordova
//
//  Created by gengych on 16/3/3.
//
//

#import "PTBrowser.h"
#import "PTBrowserContainer.h"
#import "BrowserViewController.h"

/**
 * 备注：
 * 向JS发送回调方法使用的 commandDelegate ,不能使用 self.commandDelegate ，
 * 需要内部的CordovaWebView提供自身的 commandDelegate 来进行回调；
 *
 * 原因：
 * 多个CordovaWebView使用同一个PTBrowser，所以加载多个CordovaWebview
 * 导致PTBrowser使用的self.commandDelegate是最后一个CordovaWebview的commmandDelegate，
 * 会使消息消息发送到错误的Webview的JS端，导致消息发送失败；（找不到cordovaId）
 */
@implementation PTBrowser

#pragma mark - 初始化方法

static PTBrowser *browser = nil;

+ (id)sharedBrowser{
    static dispatch_once_t once;
    dispatch_once(&once, ^{
        browser=[[PTBrowser alloc]init];
        [[NSNotificationCenter defaultCenter] addObserver:browser selector:@selector(innerCallCloseBrowser:) name:@"NotificationCloseBrowser_Innert" object:nil];
    });
    
    return browser;
}

+ (id)allocWithZone:(struct _NSZone *)zone{
    
    if (browser == nil) {
        browser = [super allocWithZone:zone];
    }
    return browser;
}

- (id)init{
    if (browser == nil) {
        browser = [super init];
    }
    
    return browser;
}

- (void)dealloc{
    [[NSNotificationCenter defaultCenter] removeObserver:browser name:@"NotificationCloseBrowser_Innert" object:nil];
}

#pragma mark - JS调用方法

- (void)jsMarkHome:(CDVInvokedUrlCommand*)command{
//    NSString *url = [[[command.arguments objectAtIndex:0] objectForKey:@"data"] objectForKey:@"url"];
//    id param = [[[command.arguments objectAtIndex:0] objectForKey:@"data"] objectForKey:@"param"];
    
    if ([self.browserManager respondsToSelector:@selector(nativeMarkHome)]) {
        [self.browserManager nativeMarkHome];
    }
    
    CDVPluginResult* pluginResult = nil;
    
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"deleteCordovaId"];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)jsOpenWindow:(CDVInvokedUrlCommand*)command{
    
    // PTLogDebug(@"........................ jsOpenWindow callbackid = %@",command.callbackId);
    
    if ([self.browserManager respondsToSelector:@selector(registerBeforeWindowTitle)]) {
        [self.browserManager registerBeforeWindowTitle];
    }
    
    NSString *url = [[[command.arguments objectAtIndex:0] objectForKey:@"data"] objectForKey:@"url"];
    id param = [[[command.arguments objectAtIndex:0] objectForKey:@"data"] objectForKey:@"param"];
    
    
    if ([self.browserManager respondsToSelector:@selector(nativeOpenWindow:params:callbackBlock:)]) {
        
        [self.browserManager nativeOpenWindow:url params:param callbackBlock:^(id commandDelegate, id result) {
            
            CDVPluginResult* pluginResult = nil;
            
            if (result == nil) {
                // 说明不需要返回给js端结果
                PTLogDebug(@"返回结果为空，不发送");
                
            }else{
                if ([result isKindOfClass:[NSDictionary class]]) {
                    
                    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:result];
                    
                    [commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
                }else{
                    PTLogDebug(@"返回结果不是字典类型，不发送");
                }
            }
        }];
    }
}


- (void)jsLoadWindowPage:(CDVInvokedUrlCommand*)command{
    
    NSString *url = [[[command.arguments objectAtIndex:0] objectForKey:@"data"] objectForKey:@"url"];
    id params = [[[command.arguments objectAtIndex:0] objectForKey:@"data"] objectForKey:@"param"];
    
    if ([self.browserManager respondsToSelector:@selector(nativeRefreshCurrentWindow:params:)]) {
        [self.browserManager nativeRefreshCurrentWindow:url params:params];
    }
    
    CDVPluginResult* pluginResult = nil;
    
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"deleteCordovaId"];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    
}


- (void)jsCloseWindow:(CDVInvokedUrlCommand*)command{
    
    PTLogDebug(@"........................ jsCloseWindow callbackid = %@",command.callbackId);
    
    id params = [[[command.arguments objectAtIndex:0] objectForKey:@"data"] objectForKey:@"param"];
    
    if ([self.browserManager respondsToSelector:@selector(unRegisterBeforeWindowTitle)]) {
        [self.browserManager unRegisterBeforeWindowTitle];
    }
    
    if ([self.browserManager respondsToSelector:@selector(nativeCloseWindow:)]) {
        [self.browserManager nativeCloseWindow:params];
        
        if ([self.browserManager respondsToSelector:@selector(nativeIsCloseBrowser)]) {
            BOOL isCloseBrowser = [self.browserManager nativeIsCloseBrowser];
            if (isCloseBrowser) {
                if ([self.browserManager respondsToSelector:@selector(nativeCloseBrowser)]) {
                    [self.browserManager nativeCloseBrowser];
                }
            }
        }
    }
    
    CDVPluginResult* pluginResult = nil;
    
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"deleteCordovaId"];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


- (void)jsSetBrowserTitle:(CDVInvokedUrlCommand*)command{
    
    // PTLogDebug(@"........................ jsSetBrowserTitle callbackid = %@",command.callbackId);
    
    NSDictionary *dic = [command.arguments objectAtIndex:0];
    NSString *title = [[dic objectForKey:@"data"] objectForKey:@"title"];
    
    if ([self.browserManager respondsToSelector:@selector(nativeSetBrowserTitle:)]) {
        [self.browserManager nativeSetBrowserTitle:title];
    }
    
    CDVPluginResult* pluginResult = nil;
    
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"deleteCordovaId"];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];

}



#pragma mark - 本地调用方法


- (void)nativeInitBrowserHome:(NSString*)homeUrl
                       params:(id)params
                callbackBlock:(void(^)(id commandDelegate,id result))callbackBlock
{
    if ([self.browserManager respondsToSelector:@selector(nativeOpenWindow:params:callbackBlock:)]) {
        [self.browserManager nativeOpenWindow:homeUrl params:params callbackBlock:nil];
    }
}


- (void)nativeInitBrowserHome:(NSString *)homeUrl
                       params:(id)params
                callbackBlock:(void(^)(id commandDelegate,id result))callbackBlock
          forBrowserContainer:(PTBrowserContainer*)browserContainer
{
    
    [browserContainer containerOpenWindow:homeUrl params:params callbackBlock:callbackBlock];
}


- (void)nativeBrowserGoBack{
    if ([self.browserManager respondsToSelector:@selector(nativeGoBack)]) {
        [self.browserManager nativeGoBack];
    }
}

/**
 * 特殊情况，需要由本地自动关闭浏览器：
 *  例如：
 *      当Browser打开的第一个页面是在线网页时；这时候无法调用 js 的browser_back 方法，
 *      所以，原生无法接收到 jsCloseWindow 的方法，进而无法判断Browser是否可以关闭，导致 nativeCloseBrowser 无法执行；
 *  解决方法：
 *      当Browser的第一个页面时在线网页时，如果判断需要关闭Browser，将发送 通知 NotificationCloseBrowser_Innert 来执行关闭 Browser 的操作；
 */
- (void)innerCallCloseBrowser:(NSNotification *)notification{
    if ([self.browserManager respondsToSelector:@selector(nativeCloseBrowser)]) {
        [self.browserManager nativeCloseBrowser];
    }
}

@end
