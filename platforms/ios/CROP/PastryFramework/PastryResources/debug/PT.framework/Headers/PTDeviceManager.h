//
//  PTDeviceManager.h
//  PT
//  功能说明: 实现各种公共方法，如生成UUID、获取设备Mac地址等
//  Created by gengych on 16/3/14.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//


#import <Foundation/Foundation.h>


/**
 * @ingroup utilModuleClass
 * 设备信息类
 */
@interface PTDeviceManager : NSObject
+ (id)getInstance;

/**
 * 获取当前设备类型，如iPhone或iPad或iPod等<br/>
 * 返回的字符串全部为小写
 * @return 当前设备类型
 */
- (NSString *)getIncaseDeviceType;

/**
 *  获取设备型号
 *
 *  @return 设备型号
 */
- (NSString *)getMachine;

/**
 * 获取设备IMEI号，因Apple设备不提供IMEI获取功能，因此此处获取到的IMEI是通过OpenUDID得到的
 * @return 设备IMEI号
 */
- (NSString *)getIMEI;

/**
 * 获取设备Key，从OpenUDID中截取出来的
 * @return 设备Key
 */
- (NSString *)getDeviceKey;

/**
 * 获取设备ID，同getIMEI
 * @return 设备ID
 */
- (NSString *)getDeviceId;

/**
 * 获取设备MAC address
 * @return 设备MAC address
 */
- (NSString *)getLocalMacAddress;

/**
 * 获取组装后的设备信息，用于同前置完成初始化工作
 * @return 设备信息
 */
- (NSString *)getAssembleEquipmentId;

/**
 * 获取系统平台信息
 * @return 系统平台信息
 */
- (NSString *)getPlatform;

/**
 * 获取设备总内存数量(单位: G)
 * @return 设备总内存数量
 */
- (NSString *)getTotalMemory;

/**
 * 获取设备当前可用内存数量(单位: G)
 * @return 设备当前可用内存数量(
 */
- (NSString *)getFreeMemory;
@end
