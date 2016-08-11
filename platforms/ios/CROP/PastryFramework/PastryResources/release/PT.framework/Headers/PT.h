//
//  PT.h
//  PT
//
//  Created by gengych on 16/3/14.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <UIKit/UIKit.h>

//! Project version number for PT.
FOUNDATION_EXPORT double PTVersionNumber;

//! Project version string for PT.
FOUNDATION_EXPORT const unsigned char PTVersionString[];

// In this header, you should import all the public headers of your framework using statements like #import <PT/PublicHeader.h>

#define PT_PT_h
#ifndef PT_PT_h

#import "PT.h"

// 主入口模块
#import "PTFramework.h"
#import "PTFrameworkDelegate.h"
#import "PTFrameworkConstant.h"

// 存储模块
#import "PTStorage.h"
#import "PTStorageManager.h"
#import "PTSecurityStorage.h"

// 工具模块
// 字符转换模块
#import "PTConverter.h"

// Hash计算模块
#import "PTHash.h"

// 加解密模块
#import "PT3Des.h"
#import "PTRSA.h"

// 密码键盘模块
#import "PTKeyboardDelegate.h"
#import "PTKeyboard.h"
#import "PTKeyboardiPhone.h"
#import "PTKeyboardPasswordNumerPhone.h"
#import "PTKeyboardHybridkey.h"
#import "PTKeyboardPublickey.h"
#import "PTKeyboardKey.h"
#import "Metrics.h"
#import "PhoneKeyboardMetrics.h"
#import "PTKeyboardConstant.h"

// 框架通知名称 集合模块
#import "PTNotificationNameManager.h"

// 资源管理模块
#import "PTBusinessManager.h"
#import "PTMenusManager.h"
#import "PTAllMenusManager.h"

// app资源文件目录管理模块
#import "PTPathManager.h"

// 加密的网络传输模块
#import "PTCommunicationHelper.h"
#import "PTRequestFactory.h"
#import "PTHttpRequest.h"
#import "PTHttpRequestImp.h"
#import "PTComPackage.h"
#import "PTDataPackage.h"

// 加密的网络传输扩展模块
#import "PTCommunicationHelper+AFN.h"
#import "PTHttpRequestAFN.h"
// 网络第三方
#import "AFHTTPSessionManager.h"
#import "AFNetworking.h"
#import "AFNetworkReachabilityManager.h"
#import "AFSecurityPolicy.h"
#import "AFURLRequestSerialization.h"
#import "AFURLResponseSerialization.h"
#import "AFURLSessionManager.h"
#import "AFAutoPurgingImageCache.h"
#import "AFImageDownloader.h"
#import "AFNetworkActivityIndicatorManager.h"
#import "UIActivityIndicatorView+AFNetworking.h"
#import "UIButton+AFNetworking.h"
#import "UIImage+AFNetworking.h"
#import "UIImageView+AFNetworking.h"
#import "UIKit+AFNetworking.h"
#import "UIProgressView+AFNetworking.h"
#import "UIRefreshControl+AFNetworking.h"
#import "UIWebView+AFNetworking.h"

// 提供第三方依赖库的接口
#import "JSONKit.h"
#import "CJSONDeserializer.h"
#import "CJSONSerializer.h"
#import "CDataScanner.h"
#import "CJSONScanner.h"

// 会话模块
#import "PTSessionManager.h"

#import "PTDeviceManager.h"

// xml节点操作模块
#import "GDataXMLNode.h"
#import "GDataXMLElement+PTXMLUtil.h"

#import "CocoaLumberjack.h"
#import "CLIColor.h"
#import "DDMultiFormatter.h"
#import "DDAbstractDatabaseLogger.h"
#import "DDContextFilterLogFormatter.h"
#import "DDDispatchQueueLogFormatter.h"
#import "PTLog.h"

// 开发模式模块
#import "PTDeveloperManager.h"

#else

// 外部项目 在 pch 中直接 #import <PT/PT.h>
#import <PT/PT.h>

// 主入口模块
#import <PT/PTFramework.h>
#import <PT/PTFrameworkDelegate.h>
#import <PT/PTFrameworkConstant.h>

// 存储模块
#import <PT/PTStorageDelegate.h>
#import <PT/PTStorageManager.h>
#import <PT/PTSecurityStorage.h>

// 工具模块
// 字符转换模块
#import <PT/PTConverter.h>

// Hash计算模块
#import <PT/PTHash.h>

// 加解密模块
#import <PT/PT3Des.h>
#import <PT/PTRSA.h>

// 密码键盘模块
#import <PT/PTKeyboardDelegate.h>
#import <PT/PTKeyboard.h>
#import <PT/PTKeyboardiPhone.h>
#import <PT/PTKeyboardPasswordNumerPhone.h>
#import <PT/PTKeyboardHybridkey.h>
#import <PT/PTKeyboardPublickey.h>
#import <PT/PTKeyboardKey.h>
#import <PT/Metrics.h>
#import <PT/PhoneKeyboardMetrics.h>
#import <PT/PTKeyboardConstant.h>

// 框架通知名称 集合模块
#import <PT/PTNotificationNameManager.h>

// 资源管理模块
#import <PT/PTBusinessManager.h>
#import <PT/PTMenusManager.h>
#import <PT/PTAllMenusManager.h>

// app资源文件目录管理模块
#import <PT/PTPathManager.h>

// 加密的网络传输模块
#import <PT/PTCommunicationHelper.h>
#import <PT/PTRequestFactory.h>
#import <PT/PTHttpRequest.h>
#import <PT/PTHttpRequestImp.h>

#import <PT/PTComPackage.h>
#import <PT/PTDataPackage.h>

// 加密的网络传输扩展模块
#import <PT/PTCommunicationHelper+AFN.h>
#import <PT/PTHttpRequestAFN.h>
// 网络第三方
#import "AFHTTPSessionManager.h"
#import "AFNetworking.h"
#import "AFNetworkReachabilityManager.h"
#import "AFSecurityPolicy.h"
#import "AFURLRequestSerialization.h"
#import "AFURLResponseSerialization.h"
#import "AFURLSessionManager.h"
#import "AFAutoPurgingImageCache.h"
#import "AFImageDownloader.h"
#import "AFNetworkActivityIndicatorManager.h"
#import "UIActivityIndicatorView+AFNetworking.h"
#import "UIButton+AFNetworking.h"
#import "UIImage+AFNetworking.h"
#import "UIImageView+AFNetworking.h"
#import "UIKit+AFNetworking.h"
#import "UIProgressView+AFNetworking.h"
#import "UIRefreshControl+AFNetworking.h"
#import "UIWebView+AFNetworking.h"

// 提供第三方依赖库的接口
#import "JSONKit.h"
#import "CJSONDeserializer.h"
#import "CJSONSerializer.h"
#import "CDataScanner.h"
#import "CJSONScanner.h"

// 会话模块
#import <PT/PTSessionManager.h>
#import <PT/PTDeviceManager.h>

// xml节点操作模块
#import "GDataXMLNode.h"
#import "GDataXMLElement+PTXMLUtil.h"

#import "CocoaLumberjack.h"
#import "CLIColor.h"
#import "DDMultiFormatter.h"
#import "DDAbstractDatabaseLogger.h"
#import "DDContextFilterLogFormatter.h"
#import "DDDispatchQueueLogFormatter.h"
#import <PT/PTLog.h>

// 开发模式模块
#import <PT/PTDeveloperManager.h>

#endif


