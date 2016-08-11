//
//  PTFramework.h
//  PT
//
//  Created by gengych on 16/3/14.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "PTFrameworkDelegate.h"
#import "PTFrameworkConstant.h"

@class GDataXMLElement;

/**
 * @ingroup frameModuleClass
 * 功能：
 *      1、data.zip 资源文件的解压；<br/>
 *      2、初始化执行握手操作；<br/>
 */
@interface PTFramework : NSObject

@property (strong, nonatomic)   NSMutableArray *listeners;

#pragma mark - app初始化

/**
 * 以懒惰方式实现的单例模式，完成各种系统级事件的接收和转发
 * @return PTFramework对象
 */
+ (id)getInstance;

/**
 * 同步解压，阻塞UI线程；
 * 解压缩模版文件包，需在所有用户界面调用之前调用，以保证所有的资源文件能够正常
 */
- (void)unpackResources;

/**
 * 获取模版资源包的状态，来判断是否需要解压；
 * @return data.zip状态
 * @note    新接口
 */
- (PTDataZipState)isNeedResources;

/**
 * 异步解压，不阻塞UI线程（推荐使用）；
 * 解压缩模版文件包，需在所有用户界面调用之前调用，以保证所有的资源文件能够正常
 * @param       beginBlock          解压开始 回调方法；
 * @param       completeBlock       解压完成 回调方法；
 * @note    新接口 异步解压（推荐使用）
 */
- (void)unpackResources:(void(^)(void)) beginBlock
          completeBlock:(void(^)(BOOL unzipResult))completeBlock;

/**
 * 初始化系统，实现的功能有:<br />
 * 1、定位客户终端当前位置<br />
 * 2、协商初始会话密钥<br />
 * 3、检测文件清单列表，并下载(如需要)<br />
 * 4、检测菜单文件，并下载(如需要)<br />
 */
- (void)initialization;

/**
 * 客户端根据需要对框架进行重启操作<br/>
 * 重启以下模块<br/>
 *      1、服务器配置信息模块 PTConfigurationManager<br/>
 *      2、加密存储管理模块 PTStorageManager<br/>
 *      3、会话状态管理模块 PTSessionManager<br/>
 * 不重启以下模块<br/>
 *      1、日志管理模块 PTLogManager<br/>
 *      2、开发模式模块 PTDeveloperManager<br/>
 *      3、网络状态管理模块 PTNetStateManager<br/>
 *      4、不清空 PTFramework 的 监听队列 listeners；
 */
- (void)reset;

#pragma mark - 解密html文件

/**
 * 获取指定业务的模板
 * @param  url  模版在包中的相对路径
 * @return NSData对象
 */
- (NSData *)getTemplateByUrl:(NSString *)url;

#pragma mark - 握手相关

/**
 * 配置客户端握手信息，只需配置一次
 * @param handShakeData 自定义握手信息
 *                      例如：{
 *                            "sys-version":1.3,
 *                            "clientVersion":1.5
 *                           }
 */
- (void)setCustomHandShakeData:(NSDictionary *)handShakeData;

/**
 * 获取客户端配置的握手信息
 * @return NSDictionary
 */
- (NSDictionary *)getCustomHandShakeData;

/**
 * 密钥协商
 * @return
 *     YES   协商成功<br/>
 *      NO   协商失败<br/>
 */
- (BOOL)handShake;

#pragma mark - menu.xml操作方法
/**
 * 获取菜单文件的rootElement
 * @return 内存中只保存一份
 */
- (GDataXMLElement *)getMenuRootElement;

/**
 * 将内存中的rootElement保存到沙盒，覆盖原文件
 * 目标文件：
 *      1、Documents/menus/menu.xml
 *      2、Documents/menus/menu.xml.bak
 * @return YES 保存成功
 *         NO  保存失败
 */
- (BOOL)saveMenusXMLFile;

#pragma mark - business.xml操作方法

/**
 * 获取business.xml文件的rootElement
 * @return 内存中只保存一份
 */
- (GDataXMLElement *)getBusinessRootElement;

/**
 * 将内存中的rootElement保存到沙盒，覆盖原文件
 * 目标文件：
 *      1、Documents/release/business.xml
 *      2、Documents/release/business.xml.bak
 * @return YES 保存成功
 *         NO  保存失败
 */
- (BOOL)saveBusinessXMLFile;

#pragma mark - 获取框架内部的各种状态

#pragma mark 网络状态
/**
 * 获取当前的网络状态是否可用
 * @return
 *       NetworkStateNotReachable   网络不可用<br/>
 *       NetworkStateWifiReachable   wifi网络可用<br/>
 *       NetworkState3GReachable   3g网络可用<br/>
 */
- (PTNetworkState)getCurrentNetworkState;

#pragma mark 会话状态
/**
 * 设置当前客户端的session状态
 * @param  state   session状态
 */
- (void)setSessionState:(PTSessionState)state;

/**
 * 获取当前客户端的登陆状态
 * @return
 *      STATE_NULLSESSION       = 0,    //未建立会话
 *      STATE_HANDSHAKEING      = 1,    //正在握手
 *      STATE_HANDSHAKEFAILED   = 2,    //握手失败
 *      STATE_SESSIONTIMEOUT    = 3,    //会话超时，需要重新握手
 *      STATE_UNLOGIN           = 4,    //已建立会话，握手成功，未登录
 *      STATE_LOGING            = 5,    //已建立回话，握手成功，正在登陆
 *      STATE_LOGIN             = 6     //已建立会话，握手成功，登陆成功
 *      STATE_LOGOUTING         = 7     //正在登出
 */
- (PTSessionState)getSessionState;

#pragma mark menu.xml状态
/**
 * 获取当前客户端数据准备状态menu.xml文件
 * @return
 *      PTEventStateSuccess  准备成功<br/>
 *      PTEventStateFailed   准备失败<br/>
 *      PTEventStateDoing    准备中<br/>
 */
- (PTEventState)getMenuPreparedState;

/**
 * 获取当前客户端菜单检测状态menu.xml文件
 * @return
 *      PTEventStateSuccess  菜单检测成功<br/>
 *      PTEventStateFailed   菜单检测失败<br/>
 *      PTEventStateDoing    菜单检测中<br/>
 */
- (PTEventState)getMenuCheckState;

#pragma mark resource.json状态
/**
 * 获取当前客户端文件清单检测状态resoure.json文件
 * @return
 *      PTEventStateSuccess  文件清单检测成功<br/>
 *      PTEventStateFailed   文件清单检测失败<br/>
 *      PTEventStateDoing    文件清单检测中<br/>
 */
- (PTEventState)getResourceJsonCheckState;

/**
 * 获取当前客户端文件清单更新状态resource.json文件
 * @return
 *      PTEventStateSuccess  文件清单更新成功<br/>
 *      PTEventStateFailed   文件清单更新失败<br/>
 *      PTEventStateDoing    文件清单更新中<br/>
 */
- (PTEventState)getResourceJsonUpdataState;

//#pragma mark business.xml状态
///**
// * 获取当前客户端文件business.xml检测状态
// * @return
// *      PTEventStateSuccess  文件business.xml检测成功<br/>
// *      PTEventStateFailed   文件business.xml检测失败<br/>
// *      PTEventStateDoing    文件business.xml检测中<br/>
// */
//- (PTEventState)getBusinessCheckState;
//
///**
// * 获取当前客户端文件business.xml更新状态
// * @return
// *      PTEventStateSuccess  文件business.xml更新成功<br/>
// *      PTEventStateFailed   文件business.xml更新失败<br/>
// *      PTEventStateDoing    文件business.xml更新中<br/>
// */
//- (PTEventState)getBusinessUpdataState;

#pragma mark 握手状态
/**
 * 获取当前客户端密钥协商状态
 * @return
 *      PTEventStateSuccess  密钥协商成功<br/>
 *      PTEventStateFailed   密钥协商失败<br/>
 *      PTEventStateDoing    密钥协商中<br/>
 */
- (PTEventState)getHandshakeState;

/**
 * 获取当前客户端系统初始化状态
 * @return
 *      PTEventStateSuccess  文件清单检测成功<br/>
 *      PTEventStateFailed   文件清单检测失败<br/>
 *      PTEventStateDoing    文件清单检测中<br/>
 */
- (PTEventState)getInitState;

#pragma mark - 事件监听处理
/**
 * 添加一个事件监听对象
 * @param  listener   事件监听对象(需要实现PTFrameworkDelegate代理)
 */
- (void)addListener:(id<PTFrameworkDelegate>)listener;

/**
 * 删除一个事件监听对象
 * @param  listener  事件监听对象
 */
- (void)removeListener:(id<PTFrameworkDelegate>)listener;

//#pragma mark - 崩溃日志上传功能

///**
// * 开启错误日志收集功能
// * 默认是关闭的
// * @param
// * @return
// */
//- (void)openCrashCollect;
//
///**
// *  添加崩溃日志回调
// *
// *  @param onCompletion   回调block
// *
// *  @return
// */
//- (void)addCrashCompletion:(crashCompletion)onCompletion;
//
///**
// * 发送错误报告
// * 前提是调用了openCrashCollect方法开启收集功能
// */
//- (void)sendCrashReports;

//#pragma mark - 定义远程测试方法
//#ifdef REMOTE_DEBUG
///**
// * 设置系统远程debug调试模式状态
// * @param   mode   调试模式
// * @return
// */
//- (void)setRemoteDebugMode:(BOOL)mode;
//
///**
// * 获取当前系统远程debug调试模式状态
// * @return
// *     YES    远程debug调试模式打开<br/>
// *      NO    远程debug调试模式关闭<br/>
// */
//- (BOOL)getRemoteDebugMode;
//#endif

@end
