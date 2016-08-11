//
//  PTHash.h
//  PT
//
//  Created by gengych on 16/3/14.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 @ingroup   hashModuleEnum
 *  HASH计算类型枚举   <br/>
 *  用于 PTHash 类
 *  <table>
 *     <tr>
 *         <td>PTHASH_TYPE_MD5</td>
 *         <td>MD5类型</td>
 *     </tr>
 *     <tr>
 *         <td>PTHASH_TYPE_SHA1</td>
 *         <td>SHA1类型</td>
 *     </tr>
 *  </table>
 */
typedef enum _PTHashType
{
    PTHASH_TYPE_MD5 = 0,        //!< MD5类型
    PTHASH_TYPE_SHA1            //!< SHA1类型
}PTHashType;


/**
 @ingroup hashModuleClass
 * 各种Hash算法的总入口类,目前支持MD5和SHA1两种Hash算法
 */
@interface PTHash : NSObject

/**
 * 根据指定的Hash算法计算出输入字符串的Hash值
 * @param  type  Hash算法类型,如MD5,SHA1等
 * @param  src   待计算字符串
 * @return       Hash值
 *      <b> nil : 计算失败 </b><br/>
 *      <b> 非空 : 计算成功，计算后的数据 </b><br/>
 */
+ (NSData *)getHashByString:(PTHashType)type str:(NSString *)src;

/**
 * 根据指定的Hash算法计算出输入流的Hash值
 * @param  type  Hash算法类型，如MD5,SHA1等
 * @param  is    待计算的输入流
 * @return       Hash值
 *      <b> nil : 计算失败 </b><br/>
 *      <b> 非空 : 计算成功，计算后的数据 </b><br/>
 */
+ (NSData *)getHashByStream:(PTHashType)type stream:(NSInputStream *)is;

/**
 * 根据指定的Hash算法计算出NSData的Hash值
 * @param  type  Hash算法类型，如MD5,SHA1等
 * @param  src   待计算的输入流
 * @return       Hash值
 *      <b> nil : 计算失败 </b><br/>
 *      <b> 非空 : 计算成功，计算后的数据 </b><br/>
 */
+ (NSData *)getHashByBytes:(PTHashType)type data:(NSData *)src;
@end

