//
//  PTBrowserDelegate.h
//  PTCordova
//
//  Created by gengych on 16/3/10.
//
//

#import <Foundation/Foundation.h>

@protocol PTBrowserDelegate <NSObject>

@optional

/**
 * 1、单组浏览器：单组浏览器不使用此接口；
 * 2、多组浏览器：标志当前Window为首页
 * @param     url         html地址
 * @param     params      html携带的参数
 */
- (void)nativeMarkHome;

/**
 * 1、单组浏览器：直接打开一个Window；
 * 2、多组浏览器：方法回调到TabbarController，TabbarController选择正在显示的 Browser 打开一个 Window；
 * @param     url         html地址
 * @param     params      html携带的参数
 * @param       callbackBlock   Window关闭 回调方法
 * @return      result 结果
 */
- (void)nativeOpenWindow:(NSString*)url
                  params:(id)params
           callbackBlock:(void(^)(id commandDelegate,id result))callbackBlock;

/**
 * 1、单组浏览器：记录 Window 跳转前的 Browser Title；
 * 2、多组浏览器：方法回调到TabbarController，TabbarController选择正在显示的 Browser ，记录 Window 跳转前的 Browser Title；
 * 时机：Browser 的 Window 跳转前调用；记录跳转前的 Browser Title；
 */
- (void)registerBeforeWindowTitle;

/**
 * 1、单组浏览器：将 Window 跳转前的 Browser Title显示到当前的 Browser Title；
 * 2、多组浏览器：方法回调到TabbarController，TabbarController选择正在显示的 Browser，将 Window 跳转前的 Browser Title显示到当前的 Browser Title；；
 * 时机：浏览器的window关闭调用；将跳转前的Title显示到当前浏览器的Tile上；
 */
- (void)unRegisterBeforeWindowTitle;

/**
 * 1、单组浏览器：直接刷新当前显示 Window；
 * 2、多组浏览器：方法回调到TabbarController，TabbarController选择正在显示的 Browser 刷新当前显示的 Window；
 * @param     url         html地址
 * @param     params      html携带的参数
 */
- (void)nativeRefreshCurrentWindow:(NSString*)url params:(id)params;

/**
 * 1、单组浏览器：直接刷新当前 Browser Title；
 * 2、多组浏览器：方法回调到TabbarController，TabbarController选择正在显示的 Browser Title；
 */
- (void)nativeSetBrowserTitle:(NSString*)title;

/**
 * 1、单组浏览器：返回按钮功能；
 * 2、多组浏览器：方法回调到TabbarController，TabbarController选择正在显示的 Browser 的返回按钮功能；
 */
- (void)nativeGoBack;

/**
 * 1、单组浏览器：直接关闭 Browser 的当前显示 Window；
 * 2、多组浏览器：方法回调到TabbarController，TabbarController选择正在显示的 Browser，关闭当前显示的 Window；
 */
- (void)nativeCloseWindow:(id)params;

/**
 * 1、单组浏览器：查询是否关闭 Browser，返回YES，则需要关闭 Browser；
 * 2、多组浏览器：多窗口浏览器不使用此接口；
 */
- (BOOL)nativeIsCloseBrowser;

/**
 * 1、单组浏览器：关闭 Browser，返回ViewController跳转前的ViewController；
 * 2、多组浏览器：多窗口浏览器不使用此接口；
 */
- (void)nativeCloseBrowser;

@optional
/**
 * 1、单组浏览器：由客户端设置密码强度
 * 2、多组浏览器：由客户端设置密码强度
 */
- (int)passwordStrength:(NSDictionary *)keyStat encryptData:(NSData *)encryptData;

/**
 * 1、单组浏览器：js和业务端进行交互的接口（待定）
 * 2、多组浏览器：js和业务端进行交互的接口（待定）
 */
- (void)callNativeViewController;

@end
