//
//  PTNotificationNameManager.h
//  PT
//
//  Created by gengych on 16/3/17.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 *  @ingroup resourceConfigModuleEnum
 *  PT框架发送的通知名称枚举   <br/>
 *  <table>
 *     <tr>
 *         <td>对外通知 </td>
 *     </tr>
 *     <tr>
 *         <td>PTNotification_DATAZIP_PROGRESS</td>
 *         <td>对外通知 : 通知data.zip的解压进度，用于客户端UI显示进度</td>
 *     </tr>
 *     <tr>
 *         <td>对内通知 </td>
 *     </tr>
 *     <tr>
 *         <td>PTNotification_SESSION_LOGOUT</td>
 *         <td>对内通知 : 通知Session退出</td>
 *     </tr>
 *     <tr>
 *         <td>PTNotification_SESSION_LOGIN</td>
 *         <td>对内通知 : 通知Session登录</td>
 *     </tr>
 *     <tr>
 *         <td>PTNotification_LOGIN_REQUIRED</td>
 *         <td>对内通知 : 发送需要用户登陆状态改变通知</td>
 *     </tr>
 *     <tr>
 *         <td>PTNotification_SESSION_TIMEOUT_NAME</td>
 *         <td>对内通知 : 发送session超时通知</td>
 *     </tr>
 *     <tr>
 *         <td>PTNotification_CONSULT_NAME</td>
 *         <td>对内通知 : 发送握手结果通知</td>
 *     </tr>
 *     <tr>
 *         <td>PTNotification_DATA_PREPARED</td>
 *         <td>对内通知 : 发送菜单(menu.xml)-加载 完成通知名称：结果：成功、失败</td>
 *     </tr>
 *     <tr>
 *         <td>PTNotification_MENU_CHECK_NAME</td>
 *         <td>对内通知 : 发送菜单(menu.xml)-检查更新 完成通知：结果：成功、失败</td>
 *     </tr>
 *     <tr>
 *         <td>PTNotification_ALLMENU_CHECK_NAME</td>
 *         <td>对内通知 : 发送文件(allmenu.xml)-检查更新 完成通知：结果：成功、失败</td>
 *     </tr>
 *     <tr>
 *         <td>PTNotification_ALLMENU_DATA_PREPARED</td>
 *         <td>对内通知 : 对内通知 : 发送文件(allmenu.xml)-加载 完成通知名称：结果：成功、失败</td>
 *     </tr>
 *     <tr>
 *         <td>PTNotification_RESOURCEJSON_UPDATE</td>
 *         <td>对内通知 : 发送文件(resource.json)-检查更新 完成通知名称：结果：成功、失败</td>
 *     </tr>
 *     <tr>
 *         <td>PTNotification_FILELIST_CHECK_NAME</td>
 *         <td>对内通知 : 发送文件(js，css，图片等文件)-检查更新 完成通知名称：结果：成功、失败</td>
 *     </tr>
 *     <tr>
 *         <td>PTNotification_ReachabilityChanged</td>
 *         <td>对内通知 : 发送网络状态变更</td>
 *     </tr>
 *     <tr>
 *         <td>PTNotification_StorageChanged</td>
 *         <td>对内通知 : 发送加密存储模块是否可用状态变更 完成通知名称：结果：成功、失败</td>
 *     </tr>
 *     <tr>
 *         <td>PTNotification_CompareMenuData</td>
 *         <td>对内通知 : 发送框架处理后的 menu.xml、business.xml、allmenu.xml 文件对比结果：结果：成功、失败</td>
 *     </tr>
 *  </table>
 */
typedef enum {
    PTNotification_DATAZIP_PROGRESS,        //!< 对外通知 : 通知data.zip的解压进度，用于客户端UI显示进度；
    
    
    PTNotification_SESSION_LOGOUT,          //!< 对内通知 : 通知Session退出
    PTNotification_SESSION_LOGIN,           //!< 对内通知 : 通知Session登录
    PTNotification_LOGIN_REQUIRED,          //!< 对内通知 : 发送需要用户登陆状态改变通知
    PTNotification_SESSION_TIMEOUT_NAME,    //!< 对内通知 : 发送session超时通知
    PTNotification_CONSULT_NAME,            //!< 对内通知 : 发送握手结果通知
    PTNotification_DATA_PREPARED,           //!< 对内通知 : 发送菜单(menu.xml)-加载 完成通知名称：结果：成功、失败；
    // 暂时未用
    PTNotification_MENU_CHECK_NAME,         //!< 对内通知 : 发送菜单(menu.xml)-检查更新 完成通知：结果：成功、失败；
    
    PTNotification_ALLMENU_CHECK_NAME,      //!< 对内通知 : 发送文件(allmenu.xml)-检查更新 完成通知：结果：成功、失败；
    PTNotification_ALLMENU_DATA_PREPARED,   //!< 对内通知 : 发送文件(allmenu.xml)-加载 完成通知名称：结果：成功、失败；
    
    PTNotification_RESOURCEJSON_UPDATE,     //!< 对内通知 : 发送文件(resource.json)-检查更新 完成通知名称：结果：成功、失败
    
    PTNotification_FILELIST_CHECK_NAME,     //!< 对内通知 : 发送文件(js，css，图片等文件)-检查更新 完成通知名称：结果：成功、失败
    
    PTNotification_ReachabilityChanged,     //!< 对内通知 : 发送网络状态变更
    
    PTNotification_StorageChanged,          //!< 对内通知 : 发送加密存储模块是否可用状态变更 完成通知名称：结果：成功、失败
    
    PTNotification_CompareMenuData,         //!< 对内通知 : 发送框架处理后的 menu.xml、business.xml、allmenu.xml 文件对比结果：结果：成功、失败

} PTNotificationType;

/**
 *  @ingroup resourceConfigModuleClass
 */
@interface PTNotificationNameManager : NSObject

+ (NSString *)getNotificationName:(PTNotificationType)pathType;

//+ (void)checkPath;

@end
