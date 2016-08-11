//
//  PTComponentInterface.h
//  HelloWorld
//
//  Created by 耿远超 on 16/7/30.
//
//

#import <Foundation/Foundation.h>

#pragma mark - 宏定义
///**
// * This is the main assert macro that you should use. Asserts should be compiled out
// * in production builds. You can customize the assert behaviour by setting a custom
// * assert handler through `RCTSetAssertFunction`.
// */
//#ifndef NS_BLOCK_ASSERTIONS
//#define RCTAssert(condition, ...) do { \
//if ((condition) == 0) { \
//_RCTAssertFormat(#condition, __FILE__, __LINE__, __func__, __VA_ARGS__); \
//if (RCT_NSASSERT) { \
//[[NSAssertionHandler currentHandler] handleFailureInFunction:@(__func__) \
//file:@(__FILE__) lineNumber:__LINE__ description:__VA_ARGS__]; \
//} \
//} \
//} while (false)
//#else
//#define RCTAssert(condition, ...) do {} while (false)
//#endif

/**
 * Make global functions usable in C++
 */
#if defined(__cplusplus)
#define PT_EXTERN extern "C" __attribute__((visibility("default")))
#else
#define PT_EXTERN extern __attribute__((visibility("default")))
#endif

#pragma mark - 枚举

#pragma mark component_Type 可选项
// PTComponentType_Native
// PTComponentType_Web

#pragma mark PTComponentGroup 可选项
// 解压缩组件集合
// 引导页面组件集合
// 登录组件集合
// 加密存储组件示例集合
// 密码键盘组件示例集合

#pragma mark PTComponentName 可选项
// 解压缩组件
// 引导页组件
// 登录组件
// 加密存储组件示例
// 密码键盘组件示例

#pragma mark - Component 接口
@protocol PTComponentInterface <NSObject>

/**
 *  注册组件到模块系统
 *  @param          component_Type              组件类型：参考 component_Type 可选项
 *  @param          component_Name              组件名称：参考 PTComponentName 可选项
 *  @param          viewController_Name         组件的ViewController名称
 *  @param          nib_Name                    组件的xib名称
 *  @param          bundle_Name                 组件所在的bundle名称
 *  <br/>
 *  示例 : <br/>
 *  @code
    1、注册 组件类型 = PTComponentType_Native; 组件名称 = PTComponentLevel_Unzip; 组件的ViewController名称 = PTUnzipViewController; 组件的xib名称 = PTUnzipViewController; 组件所在的bundle名称 = 没有bundle;
        
        PT_REGISTER_COMPONENT(PTComponentType_Native,PTComponentLevel_Unzip,PTUnzipViewController,PTUnzipViewController,)
    2、
 *  @endcode
 */
#define PT_REGISTER_COMPONENT(component_Type,component_Group,component_Name,viewController_Name,nib_Name,bundle_Name,is_Main) \
PT_EXTERN void PTRegisterComponent(Class); \
+ (NSString *)componentType { return @#component_Type; } \
+ (NSString *)componentGroup { return @#component_Group; } \
+ (NSString *)componentName { return @#component_Name; } \
+ (NSString *)viewControllerName { return @#viewController_Name; } \
+ (NSString *)nibName { return @#nib_Name; } \
+ (NSString *)bundleName { return @#bundle_Name; } \
+ (NSString *)isMain { return @#is_Main; } \
+ (void)load { PTRegisterComponent(self); }

// Implemented by PT_REGISTER_COMPONENT
+ (NSString *)componentType;
+ (NSString *)componentGroup;
+ (NSString *)componentName;
+ (NSString *)viewControllerName;
+ (NSString *)nibName;
+ (NSString *)bundleName;
// 是否是入口组件
+ (NSString *)isMain;



//#define RCT_EXPORT_MODULE(js_name) \
//RCT_EXTERN void RCTRegisterModule(Class); \
//+ (NSString *)moduleName { return @#js_name; } \
//+ (void)load { RCTRegisterModule(self); }
//
//// Implemented by RCT_EXPORT_MODULE
//+ (NSString *)moduleName;

@end
