//
//  PTBrowser.h
//  PTCordova
//
//  Created by gengych on 16/3/3.
//
//

//#import <Cordova/Cordova.h>
#import <Cordova/CDV.h>
#import "PTBrowserDelegate.h"

@class PTBrowserContainer;
/**
 负责多个业务间的跳转；
 */
@interface PTBrowser : CDVPlugin

#pragma mark - 属性

@property   id<PTBrowserDelegate>   browserManager;


#pragma mark - 初始化方法

+ (id)sharedBrowser;


#pragma mark - JS调用方法

/**
 * js端发送给原生标识 Window 是首页 的消息；
 * 原生：
 *      1、多组浏览器中，首页无返回按钮，需要JS端标志页面为首页
 */
- (void)jsMarkHome:(CDVInvokedUrlCommand*)command;

/**
 * js端发送给原生打开 Window 的消息；
 * 原生：
 *      1、记录 上一个 Window 要显示的 Browser 的 Title 内容；（Window 的关闭 JS端不参与，所以无法获取上一个Window的Browser Title，需要原生来记录）
 *      2、打开新的 Window；
 *      3、页面传值 怎么解决；？？？？？？
 */
- (void)jsOpenWindow:(CDVInvokedUrlCommand*)command;

/**
 * js端发送给原生刷新当前的 Window 的消息；
 * 原生：
 *      1、刷新当前的 Window；
 */
- (void)jsLoadWindowPage:(CDVInvokedUrlCommand*)command;

/**
 * js端发送给原生关闭 Window 的消息；
 * 原生：
 *      1、 替换Title；
 *      2、 原生查询关闭 Window 后，浏览器是否需要关闭；
 *      3、 需要则关闭浏览器，页面返回 浏览器前的跳转的ViewController；
 */
- (void)jsCloseWindow:(CDVInvokedUrlCommand*)command;

/**
 * js端发送给原生 Window 的 Title 信息；
 * 原生：
 *      1、单组浏览器 ：只替换 Browser 的 Title；
 *      2、多组浏览器 ：
 *          2.1 替换 Browser 的 Title
 *          2.2 记录页面跳转次数，查看是否是 浏览器的首页，显示或者隐藏 返回 按钮；
 */
- (void)jsSetBrowserTitle:(CDVInvokedUrlCommand*)command;



#pragma mark - 本地调用方法

/**
 * 单组浏览器初始化首页
 * @param       homeUrl     webview首页html地址
 * @param       params      html使用的参数（NSDictionary类型）
 * @param       callbackBlock   Window关闭 回调方法
 * @return      result 结果
 */
- (void)nativeInitBrowserHome:(NSString*)homeUrl
                       params:(id)params
                callbackBlock:(void(^)(id commandDelegate,id result))callbackBlock;

/**
 * 多组浏览器初始化首页
 * @param       homeUrl             webview首页html地址
 * @param       params              html使用的参数（NSDictionary类型）
 * @param       callbackBlock   Window关闭 回调方法
 * @param       browserContainer    指定的browser容器
 */
- (void)nativeInitBrowserHome:(NSString *)homeUrl
                       params:(id)params
                callbackBlock:(void(^)(id commandDelegate,id result))callbackBlock
          forBrowserContainer:(PTBrowserContainer*)browserContainer;

/**
 *  1、单组浏览器 ：返回按钮功能
 *  1、多组浏览器 ：返回按钮功能
 */
- (void)nativeBrowserGoBack;

@end
