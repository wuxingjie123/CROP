//
//  PTBrowserContainer.h
//  PTCordova
//
//  Created by gengych on 16/3/3.
//
//

#import <UIKit/UIKit.h>
@class PTWindowPage;

@interface PTBrowserContainer : UIViewController

#pragma mark - 本地调用方法

/**
 * 单组浏览器 容器 打开 Window
 * @param       homeUrl     webview首页html地址
 * @param       params      html使用的参数（NSDictionary类型）
 * @param       callbackBlock   Window关闭 回调方法
 * @return      result 结果
 */
- (void)containerOpenWindow:(NSString*)urlPath
                     params:(id)params
              callbackBlock:(void(^)(id commandDelegate,id result))callbackBlock;

/**
 * 单组浏览器 容器 刷新 Window
 * @param       urlPath     webview首页urlPath地址
 * @param       params      html使用的参数（NSDictionary类型）
 */
- (void)containerRefreshCurrentWindowPage:(NSString*)urlPath params:(id)params;

/**
 * 单组浏览器 容器 关闭 Window
 * @param       params      js发送的两个window间跳转的参数（NSDictionary类型）
 */
- (void)containerCloseWindow:(id)params;

/**
 * 单组浏览器 容器 返回
 * @result  是否可以返回 YES：可以返回； NO：不可以返回；
 */
- (BOOL)containerGoBack;

/**
 * 返回 Browser 是否需要关闭
 * YES ： 需要关闭
 * NO  ： 不关闭
 */
- (BOOL)containerIsCloseBrowser;

/**
 * 获取最上层的Webview的ViewController
 * return PTWindowPage对象
 */
- (PTWindowPage *)getCurrentWindowPage;

/**
 * 设置 BrowserContainer的View、Webview的 frame 大小（因为keyboard存在，所以frame需要调整大小）
 * @param       frame       frame大小
 */
- (void)setWindowPageFrame:(CGRect)frame;

/**
 * 设置 BrowserContainer 的原始 frame 大小（因为keyboard存在，所以frame需要调整大小）
 * @param       frame       原始frame大小
 */
- (void)setOriginFrame:(CGRect)frame;

/**
 * 获取 BrowserContainer 的原始 frame 大小（因为keyboard存在，所以frame需要调整大小）
 * @return      原始frame大小
 */
- (CGRect)getOriginFrame;

@end
