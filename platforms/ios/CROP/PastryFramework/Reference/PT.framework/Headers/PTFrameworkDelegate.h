//
//  PTFrameworkDelegate.h
//  PT
//
//  Created by gengych on 16/3/14.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 * @ingroup frameModuleProtocol
 *    本代理协议是框架层的代理，所有方法都是可选实现的，<br/>
 * 协议实现者可以根据需要实现其中的一个、几个或全部
 */
@protocol PTFrameworkDelegate <NSObject>

@optional
/**
 * 获取menu.xml加载完成结果事件:menu.xml
 * @param  result  准备结果
 *            0    成功<br/>
 *            1    失败<br/>
 *            2    准备中<br/>
 */
- (void)didDataPreparedWithResult:(int)result;

/**
 * 密钥协商结果
 * @param   dictionary  密钥协商结果
 *     dictionary字典中可取的关键字有"result"、"data"、"serverError"
 *     另外，当握手成功时还包含了所有服务器返回的握手信息<br/>
 *        result : 0  表示密钥协商成功，此时data非空<br/>
 *                 1  表示密钥协商失败，此时serverError非空<br/>
 *                 2  表示密钥协商正在进行中<br/>
 *        data : 握手成功 服务返回的数据<br/>
 *        serverError:握手失败 错误参数；
 */
- (void)didHandShakeWithResult:(NSDictionary *)dictionary;

/**
 * 菜单验证结果:menu.xml 文件
 * @param   result  菜单验证结果
 *      0   菜单验证成功<br/>
 *      1   菜单验证失败<br/>
 *      2   菜单验证进行中<br/>
 */
- (void)didCheckMenuWithResult:(int)result;

/**
 * 文件列表验证结果:js css pic 等文件
 * @param   result  文件列验证结果
 *      0   文件列表验证成功<br/>
 *      1   文件列表验证失败<br/>
 *      2   文件列表验证进行中<br/>
 */
- (void)didCheckManifestWithResult:(int)result;

/**
 * 文件列表更新结果:resource.json 文件
 * @param   result  文件列验证结果
 *      0   文件列表验证成功<br/>
 *      1   文件列表验证失败<br/>
 *      2   文件列表验证进行中<br/>
 */
- (void)didUpdateManifestWithResult:(int)result;

/**
 * session超时
 */
- (void)didSessionTimeout;

/**
 * session退出状态
 */
- (void)didSessionLogout;

/**
 * session登录状态
 */
- (void)didSessionLogin;

/**
 * 服务器返回需要登陆的提示
 */
- (void)didLoginRequired;

/**
 * 网络状态改变时
 * @param   state   网络状态
 *    0   网络不可用<br/>
 *    1   wifi网络可用<br/>
 *    2   3g网络可用<br/>
 */
- (void)didNetworkWithState:(int)state;

#pragma mark - 加密存储模块方法
/**
 * 加密存储模块是否可用状态更新
 * @param   result  文件列验证结果
 *      0   加密存储模块可用<br/>
 *      1   加密存储模块不可用<br/>
 *      2   加密存储模块进行中<br/>
 */
- (void)didStorageChangeWithState:(int)result;

#pragma mark - 对比 menu.xml、business.xml、allmenu.xml 文件节点

/**
 *  客户端设置是否需要热更新
 * @return  
        YES : 需要热更新
        NO  : 不需要热更新
 */
- (BOOL)isNeedHotReload;

/**
 * menu.xml business.xml allmenu.xml 文件加载完成。
 * @param   result  相关文件加载完成
 *      0   文件加载成功<br/>
 *      1   文件加载失败<br/>
 *      2   文件加载中<br/>
 */
- (void)didCompareMenuData:(int)result;

@end
