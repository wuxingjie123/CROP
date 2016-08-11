//
//  PTFrameworkConstant.h
//  PT
//
//  Created by gengych on 16/3/14.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#ifndef PTFrameworkConstant_h
#define PTFrameworkConstant_h

/**
 @addtogroup frameModuleEnum
 @{
 */

/**
 *  @ingroup resourceFileModuleEnum
 *  各种资源检查的状态 xml json js css文件的检查结果枚举   <br/>
 *  <table>
 *     <tr>
 *         <td>PTEventStateSuccess</td>
 *         <td>无网状态</td>
 *     </tr>
 *     <tr>
 *         <td>PTEventStatusFailed</td>
 *         <td>wifi网络</td>
 *     </tr>
 *     <tr>
 *         <td>PTEventStatusDoing</td>
 *         <td>3G网络</td>
 *     </tr>
 *  </table>
 */
typedef enum : NSUInteger{
    PTEventStateSuccess     = 0,    //!< 检查成功.
    PTEventStatusFailed     = 1,    //!< 检查失败.
    PTEventStatusDoing      = 2     //!< 检查进行中.
} PTEventState;

/*! @} */

#pragma mark - data.zip解压状态标识枚举
/**
 *  @ingroup resourceFileModuleEnum
 *  data.zip是否需要解压的状态枚举   <br/>
 *  <table>
 *     <tr>
 *         <td>PTDataZip_NewLoad</td>
 *         <td>需要解压data.zip：用户首次进行程序</td>
 *     </tr>
 *     <tr>
 *         <td>PTDataZip_OldToNewLoad</td>
 *         <td>需要解压data.zip：老用户升级新程序</td>
 *     </tr>
 *     <tr>
 *         <td>PTDataZip_BeforeLoadFail</td>
 *         <td>需要解压data.zip：上次解压data.zip失败</td>
 *     </tr>
 *     <tr>
 *         <td>PTDataZip_NoLoad</td>
 *         <td>不需要解压data.zip：资源最新</td>
 *     </tr>
 *  </table>
 */
typedef enum : NSUInteger {
    PTDataZip_NewLoad                   = 0,    //!< 需要解压data.zip：用户首次进行程序.
    PTDataZip_OldToNewLoad              = 1,    //!< 需要解压data.zip：老用户升级新程序.
    PTDataZip_BeforeLoadFail            = 2,    //!< 需要解压data.zip：上次解压data.zip失败.
    PTDataZip_NoLoad                    = 3,    //!< 不需要解压data.zip：资源最新.
} PTDataZipState;


#pragma mark - 网络状态标识枚举
/**
 *  @ingroup resourceConfigModuleEnum
 *  网络状态标识枚举   <br/>
 *  用于 PTNetStateManager 类
 *  <table>
 *     <tr>
 *         <td>NetworkStateNotReachable</td>
 *         <td>无网状态</td>
 *     </tr>
 *     <tr>
 *         <td>NetworkStateWifiReachable</td>
 *         <td>wifi网络</td>
 *     </tr>
 *     <tr>
 *         <td>NetworkState3GReachable</td>
 *         <td>3G网络</td>
 *     </tr>
 *     <tr>
 *         <td>NetworkStateLocalTest</td>
 *         <td>本地测试状态</td>
 *     </tr>
 *  </table>
 */
typedef enum : NSUInteger{
    NetworkStateNotReachable    = 0,    //!< 无网状态
    NetworkStateWifiReachable   = 1,    //!< wifi网络
    NetworkState3GReachable     = 2,    //!< 3G网络
    NetworkStateLocalTest       = 3     //!< 本地测试状态
} PTNetworkState;

#pragma mark - 状态管理模块 session network resource 的State
/**
 *  @ingroup httpAssistModuleEnum
 *  会话状态标识枚举   <br/>
 *  <table>
 *     <tr>
 *         <td>STATE_NULLSESSION</td>
 *         <td>未建立会话</td>
 *     </tr>
 *     <tr>
 *         <td>STATE_HANDSHAKEING</td>
 *         <td>正在握手</td>
 *     </tr>
 *     <tr>
 *         <td>STATE_HANDSHAKEFAILED</td>
 *         <td>握手失败</td>
 *     </tr>
 *     <tr>
 *         <td>STATE_SESSIONTIMEOUT</td>
 *         <td>会话超时，需要重新握手</td>
 *     </tr>
 *     <tr>
 *         <td>STATE_UNLOGIN</td>
 *         <td>已建立回话，握手成功，正在登陆</td>
 *     </tr>
 *     <tr>
 *         <td>STATE_LOGING</td>
 *         <td>沙箱目录下的 data.zip 文件路径</td>
 *     </tr>
 *     <tr>
 *         <td>STATE_LOGIN</td>
 *         <td>已建立会话，握手成功，登陆成功</td>
 *     </tr>
 *     <tr>
 *         <td>STATE_LOGOUTING</td>
 *         <td>正在登出</td>
 *     </tr>
 *  </table>
 *  @see 此枚举用于 PTSessionManager 类
 */
typedef enum : NSUInteger{
    STATE_NULLSESSION       = 0,    //!< 未建立会话
    STATE_HANDSHAKEING      = 1,    //!< 正在握手
    STATE_HANDSHAKEFAILED   = 2,    //!< 握手失败
    STATE_SESSIONTIMEOUT    = 3,    //!< 会话超时，需要重新握手              : 服务器 状态
    STATE_UNLOGIN           = 4,    //!< 已建立会话，握手成功，未登录         : 服务器 状态
    STATE_LOGING            = 5,    //!< 已建立会话，握手成功，正在登陆        : 客户端 状态    （无）
    STATE_LOGIN             = 6,    //!< 已建立会话，握手成功，登陆成功        : 客户端 状态
    STATE_LOGOUTING         = 7     //!< 正在登出                          : 客户端 状态     （无）
} PTSessionState;

#pragma mark - 向服务器发送请求，返回请求状态枚举类型
/**
 *  @ingroup httpAssistModuleEnum
 *  向服务器发送请求，返回请求状态枚举   <br/>
 *  <table>
 *     <tr>
 *         <td>CommunicationSuccess</td>
 *         <td>连接成功</td>
 *     </tr>
 *     <tr>
 *         <td>ParameterError</td>
 *         <td>参数错误<br/>错误的会话状态包括：<br/>1、STATE_NULLSESSION 未建立会话<br/> 2、STATE_HANDSHAKEING 正在握手<br/> 3、STATE_HANDSHAKEFAILED 握手失败<br/> 4、STATE_SESSIONTIMEOUT 会话超时，需要重新握手</td>
 *     </tr>
 *     <tr>
 *          <td>DataParseError</td>
 *          <td>通讯结果加解密解析错误</td>
 *     </tr>
 *     <tr>
 *         <td>NetworkUnreachableError</td>
 *         <td>网络不通</td>
 *     </tr>
 *     <tr>
 *         <td>SessionStateError</td>
 *         <td>会话状态错误</td>
 *     </tr>
 *     <tr>
 *         <td>ServerError</td>
 *         <td>服务器错误</td>
 *     </tr>
 *     <tr>
 *         <td>SessionTimeOut</td>
 *         <td>会话过期</td>
 *     </tr>
 *  </table>
 *  @note
 *  只适用于 网络请求，服务器返回请求状态时；<br/>
 *  不适用于 当前客户端所处的会话状态（客户端会话状态见 PTSessionState 枚举）；
 *  @see 此枚举用于 PTCommunicationHelper 类
 */
typedef enum : NSUInteger{
    CommunicationSuccess            = 0,        //!< 连接成功.
    ParameterError                  = 1,        //!< 参数错误.
    NetworkUnreachableError         = 2,        //!< 网络不通.
    SessionStateError               = 3,        //!< 会话状态错误.<br/>错误的会话状态包括：<br/>1、STATE_NULLSESSION<br/> 2、STATE_HANDSHAKEING<br/> 3、STATE_HANDSHAKEFAILED<br/> 4、STATE_SESSIONTIMEOUT
    ServerError                     = 4,        //!< 服务器错误.
    DataParseError                  = 5,        //!< 通讯结果解密解析错误.
}PTCommunicationErrorCode;


/**
 *  @ingroup httpSecurityModuleEnum
 *  加密方式枚举   <br/>
 *  <table>
 *     <tr>
 *         <td>DATA_CRYPT_NONE</td>
 *         <td>不加密</td>
 *     </tr>
 *     <tr>
 *         <td>DATA_CRYPT_3DES</td>
 *         <td>采用3DES算法加密</td>
 *     </tr>
 *     <tr>
 *         <td>DATA_CRYPT_AES</td>
 *         <td>采用AES算法加密</td>
 *     </tr>
 *     <tr>
 *         <td>DATA_CRYPT_RC5</td>
 *         <td>采用RC5算法加密</td>
 *     </tr>
 *     <tr>
 *         <td>DATA_CRYPT_PUBKEY</td>
 *         <td>采用默认公钥加密</td>
 *     </tr>
 *  </table>
 */
typedef enum : NSUInteger {
    DATA_CRYPT_NONE             = 0,    //!< 不加密.
    DATA_CRYPT_3DES             = 1,    //!< 采用3DES算法加密.
    DATA_CRYPT_AES              = 2,    //!< 采用AES算法加密.
    DATA_CRYPT_RC5              = 3,    //!< 采用RC5算法加密.
    DATA_CRYPT_PUBKEY           = 4,    //!< 采用默认公钥加密.
}PTDataPackageCryptType;

/**
 *  @ingroup httpSecurityModuleEnum
 *  Hash计算方式枚举   <br/>
 *  <table>
 *     <tr>
 *         <td>DATA_HASH_NONE</td>
 *         <td>不做Hash计算</td>
 *     </tr>
 *     <tr>
 *         <td>DATA_HASH_MD5</td>
 *         <td>采用MD5做hash计算</td>
 *     </tr>
 *     <tr>
 *         <td>DATA_HASH_SHA1</td>
 *         <td>采用SHA1做hash计算</td>
 *     </tr>
 *  </table>
 */
typedef enum : NSUInteger {
    DATA_HASH_NONE          = 0,    //!< 不做Hash计算.
    DATA_HASH_MD5           = 1,    //!< 采用MD5做hash计算.
    DATA_HASH_SHA1          = 2,    //!< 采用SHA1做hash计算.
}PTDataPackageHashType;

/**
 * @ingroup httpSecurityModuleEnum
 *  签名方式枚举   <br/>
 *  <table>
 *     <tr>
 *         <td>DATA_SIGNATURE_NONE</td>
 *         <td>不做签名计算</td>
 *     </tr>
 *     <tr>
 *         <td>DATA_SIGNATURE_MD5_RSA</td>
 *         <td>对MD5的hash值做RSA签名</td>
 *     </tr>
 *     <tr>
 *         <td>DATA_SIGNATURE_SHA1_RSA</td>
 *         <td>对SHA1的hash值做RSA签名</td>
 *     </tr>
 *  </table>
 */
typedef enum : NSUInteger {
    DATA_SIGNATURE_NONE             = 0,    //!< 不做签名计算.
    DATA_SIGNATURE_MD5_RSA          = 1,    //!< 对MD5的hash值做RSA签名.
    DATA_SIGNATURE_SHA1_RSA         = 2,    //!< 对SHA1的hash值做RSA签名.
}PTDataPackageSignatureFlag;


/**
 @ingroup httpSecurityModuleEnum
 @brief 通讯包类型枚举
 *  <table>
 *     <tr>
 *         <td>PKG_FLAG_CLIENT</td>
 *         <td>客户端 -> 服务器 的包</td>
 *     </tr>
 *     <tr>
 *         <td>PKG_FLAG_SERVER</td>
 *         <td>服务器 -> 客户端 的包</td>
 *     </tr>
 *  </table>
 */
typedef enum : NSUInteger {
    /** 客户端 -> 服务器 的包 */
    PKG_FLAG_CLIENT = 0,
    
    /** 服务器 -> 客户端 的包 */
    PKG_FLAG_SERVER
}PTComPackageFlag;

/**
 @addtogroup frameModuleContant
 @{
 */
#define PTCommunicationErrorDomain @"PTCommunicationErrorDomain"

// 备注：buiness.xml allmenu.xml 不进行检查更新；

#define VERSION_CODE_LENGTH 6      //!< 模版版本号长度
#define HTTP_CONTECT_TIMEOUT 60    //http连接超时时间，以秒位单位

// 提供的外部通知名名称

//#define NeedLogon    2     //httpResponse header.sessionState 为该值时发送“需要登录”的通知

// 整包data.zip的下载地址目录
#define PTFRAMEWORK_RESOURCE_ACTION @"ptframework/data/"

#define PTFRAMEWORK_HANDSHAKE_ACTION @"ptframework.do?act=handshake"


#define FLAG_UPDATETYPE_UPDATE          @"u"
/**	更新类型标识：安装 */
#define FLAG_UPDATETYPE_INSTALL         @"i"

/**	更新状态标识：需要更新 */
#define FLAG_UPDATESTATE_REQUIRED       @"r"
/**	更新状态标识：正在更新 */
#define FLAG_UPDATESTATE_DOING          @"i"
/**	更新状态标识：更新完成 */
#define FLAG_UPDATESTATE_DONE           @"d"
/**	更新状态标识：更新失败 */
#define FLAG_UPDATESTATE_FAILED         @"f"

#define ATTR_UPDATE                     @"update"
/**	菜单属性：菜单属性变化标识  */
#define ATTR_INNER_MARK_CHANGE          @"_inner_mark_change"
/**	菜单属性：菜单升级标识  */
#define ATTR_INNER_MARK_UPDATE          @"_inner_mark_update"


/**	更新参数数组索引：更新类型 */
#define INDEX_UPDATE_TYPE               0
/**	更新参数数组索引：更新状态 */
#define INDEX_UPDATE_STATE              1
/**	更新参数数组索引：更新附加信息 */
#define INDEX_UPDATE_MESSAGE            2
/**	更新参数数组索引：更新进度 */
#define INDEX_UPDATE_PROGRESS           3
/**	更新参数数组大小 */
#define INDEXCOUNT_UPDATE               3+1



#define ATTR_REF                        @"ref"

//typedef void(^crashCompletion)(NSDictionary *crashInfo);

/*! @} */


/**
 *  注释时，定义方法是否被废弃
 */
#ifdef __clang__
#define PT_DEPRECATED(version, msg) __attribute__((deprecated("该方法被废除 in PT " #version ".下一个版本中彻底删除该方法. " msg)))
#else
#define PT_DEPRECATED(version, msg) __attribute__((deprecated()))
#endif

#endif /* PTFrameworkConstant_h */
