//
//  PTLog.h
//  PT
//
//  Created by gengych on 16/5/15.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 @addtogroup loggerModuleContant
 @{
 */

#ifdef DEBUG
static const DDLogLevel ddLogLevel = DDLogLevelVerbose;
#else
static const DDLogLevel ddLogLevel = DDLogLevelOff;
#endif

// 提供客户端的 日志输出 ，替换 NSLog 方法；
#ifdef DEBUG
/** 
 * 客户端使用 宏定义 PTLogDebug 代替 NSLog； 
 */
#define PTLogDebug(fmt, ...) do {                         \
NSString *file = [[NSString alloc] initWithFormat:@"%s", __FILE__]; \
DDLogDebug((@"%@(%d)" fmt), [file lastPathComponent], __LINE__, ##__VA_ARGS__); \
} while (0)

/**
 * 原生开发者不需要关注、不需要使用
 * H5使用 宏定义 H5LogDebug 来显示来自 html 的日志；
 */
#define H5LogDebug(fmt, ...) do {                         \
NSString *file = [[NSString alloc] initWithFormat:@"%s", __FILE__]; \
DDLogVerbose((@"%@(%d)" fmt), [file lastPathComponent], __LINE__, ##__VA_ARGS__); \
} while (0)

#else
/** 
 * 客户端使用 宏定义 PTLogDebug 代替 NSLog； 
 */
#define PTLogDebug(...)

/** 
 * 原生开发者不需要关注、不需要使用
 * H5使用 宏定义 H5LogDebug 来显示来自 html 的日志； 
 */
#define H5LogDebug(...)

#endif




////框架开发者使用，其他开发者不需要关注；
//static const DDLogLevel ddLogLevel = DDLogLevelVerbose;
//
///**
// * 客户端使用 宏定义 PTLogDebug 代替 NSLog；
// */
//#define PTLogDebug(fmt, ...) do {                         \
//NSString *file = [[NSString alloc] initWithFormat:@"%s", __FILE__]; \
//DDLogDebug((@"%@(%d)" fmt), [file lastPathComponent], __LINE__, ##__VA_ARGS__); \
//} while (0)
//
///**
// * 原生开发者不需要关注、不需要使用
// * H5使用 宏定义 H5LogDebug 来显示来自 html 的日志；
// */
//#define H5LogDebug(fmt, ...) do {                         \
//NSString *file = [[NSString alloc] initWithFormat:@"%s", __FILE__]; \
//DDLogVerbose((@"%@(%d)" fmt), [file lastPathComponent], __LINE__, ##__VA_ARGS__); \
//} while (0)

/*! @} */

