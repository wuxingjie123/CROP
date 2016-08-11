//
//  PTDeveloperManager.h
//  PT
//
//  Created by gengych on 16/4/12.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>
@class GDataXMLElement;

/**
 *  @ingroup resourceConfigModuleEnum
 *  HTML模版来源路径枚举   <br/>
 *  <table>
 *     <tr>
 *         <td>PTHTML_DataZip</td>
 *         <td>HTML模版来自 data.zip</td>
 *     </tr>
 *     <tr>
 *         <td>PTHTML_WWW</td>
 *         <td>HTML模版来自 ios平台内部的 www 目录</td>
 *     </tr>
 *     <tr>
 *         <td>PTHTML_RemoteWWW</td>
 *         <td>HTML模版来自 远程主机的 www 目录</td>
 *     </tr>
 *  </table>
 */
typedef enum : NSUInteger {
    PTHTML_DataZip              = 1,    //!< HTML模版来自 data.zip.
    PTHTML_WWW                  = 2,    //!< HTML模版来自 ios平台内部的 www 目录.
    PTHTML_RemoteWWW            = 3,    //!< HTML模版来自 远程主机的 www 目录.
} PTHTML_SourceType;

/**
 * @ingroup resourceConfigModuleClass
 */
@interface PTDeveloperManager : NSObject

/**
 * 以懒惰方式实现的单例模式
 * @return 返回PTDeveloperManager对象
 */
+ (id)getInstance;

/**
 *  初始化 开发配置 信息
 */
- (void)initialization;

/**
 * 获取xml中节点信息；
 * @param   key     以根节点为开始，依次是节点key1、key2、key3、... nil
 * @return  指定的节点对象；
 */
- (GDataXMLElement *)getValueOfKeys:(NSString *)key, ... NS_REQUIRES_NIL_TERMINATION;

/**
 *  是否支持真机远程调试
 *  @return
 *	<table>
 *		<tr>
 *			<td>
 *				YES
 *			</td>
 *			<td>
 *				支持真机远程调试
 *			</td>
 *		</tr>
 *		<tr>
 *			<td>
 *				NO
 *			</td>
 *			<td>
 *				不支持真机远程调试
 *			</td>
 *		</tr>
 *	</table>
 */
- (BOOL)DEV_REMOTE_DEBUG;

/**
 *  是否支持本地网络
 *  @return
 *	<table>
 *		<tr>
 *			<td>
 *				YES
 *			</td>
 *			<td>
 *				使用本地网络环境
 *			</td>
 *		</tr>
 *		<tr>
 *			<td>
 *				NO
 *			</td>
 *			<td>
 *				使用真实的网络环境
 *			</td>
 *		</tr>
 *	</table>
 */
- (BOOL)DEV_LOCAL_NETWORK;

/**
 *  是否从Settings.bundle中读取服务器地址
 *  @return
 *	<table>
 *		<tr>
 *			<td>
 *				YES
 *			</td>
 *			<td>
 *				从Settings.bundle中读取服务器地址
 *			</td>
 *		</tr>
 *		<tr>
 *			<td>
 *				NO
 *			</td>
 *			<td>
 *				使用生产的服务器地址
 *			</td>
 *		</tr>
 *	</table>
 */
- (BOOL)DEV_SERVER_URL_FROM_SETTINGS;

/**
 *  打开 文件校验失败后 文件下载功能
 */
- (BOOL)DEV_OPEN_FILE_CHECK;

/**
 *  压力测试，打开通信加密
 */
- (BOOL)DEV_OPEN_STRESS_TESTING;

/**
 *  打开 模板文件校验
 */
- (BOOL)DEV_OPEN_TEMPLATE_CHECK;

/**
 *  打开 握手成功后 资源文件更新
 */
- (BOOL)DEV_OPEN_RESOURCE_UPDATE;

/**
 *  打开resource.json文件更新
 */
- (BOOL)DEV_OPEN_UPDATE_Resource_Json;

/**
 *  使用HTML类型            data.zip|www目录|远程主机www目录
 *  @return html类型
 */
- (PTHTML_SourceType)getHTMLSourceType;
@end
