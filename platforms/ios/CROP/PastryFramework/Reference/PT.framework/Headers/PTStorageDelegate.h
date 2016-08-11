//
//  PTStorageDelegate.h
//  PT
//
//  Created by gengych on 16/3/14.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 @ingroup storageModuleProtocol
 @protocol PTStorageDelegate
 @brief PTStorageDelegate 协议
 */
@protocol PTStorageDelegate <NSObject>
@required
/**
 * 获取存储器名称
 * @return 名称
 */
- (NSString *)getName;

/**
 * 获取存储器中的字符串
 * @param  key   存储变量的key
 * @return       key对应的值
 *     nil       未找到对应值<br/>
 *    非nil      找到对应值<br/>
 */
- (NSString *)getString:(NSString *)key;

/**
 * 向存储器中写入字符串
 * @param  key    存储变量的key
 * @param  value  存储的字符串
 * @return
 *     YES        保存成功<br/>
 *      NO        保存失败<br/>
 */
- (BOOL)put:(NSString *)key value:(NSString *)value;

/**
 * 从存储器中删除字符串
 * @param  key   存储变量的key
 */
- (void)remove:(NSString *)key;

/**
 * 提交修改
 * @return 提交结果
 */
- (BOOL)commit;

@end
