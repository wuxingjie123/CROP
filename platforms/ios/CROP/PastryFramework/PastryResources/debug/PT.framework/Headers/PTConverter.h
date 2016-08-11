//
//  PTConverter.h
//  PT
//
//  Created by gengych on 16/3/14.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 @ingroup codeEncodeModuleClass
 * Byte数组转成不同编码方式的String
 *	<table>
 *		<tr>
 *			<td>
 *				Base64、byte互转
 *			</td>
 *			<td>
 *				支持
 *			</td>
 *		</tr>
 *		<tr>
 *			<td>
 *				Hex、byte互转
 *			</td>
 *			<td>
 *				支持
 *			</td>
 *		</tr>
 *		<tr>
 *			<td>
 *				Json、XML互转
 *			</td>
 *			<td>
 *				支持
 *			</td>
 *		</tr>
 *		<tr>
 *			<td>
 *				BCD、byte互转
 *			</td>
 *			<td>
 *				不支持
 *			</td>
 *		</tr>
 *	</table>
 */
@interface PTConverter : NSObject

#pragma mark - Base64
/**
 * 将NSData数据转换成URL Base64编码的字符串
 * @param  src 原始数据
 * @return
 *      <b> nil : 转换失败 </b><br/>
 *      <b> 非空 : 转换成功，转换后的字符串 </b><br/>
 *  @code
 *      NSString *result = [PTConverter bytesToURLBase64:nil];
 *  @endcode
 * @note 转换URL格式的字符串
 */
+ (NSString *)bytesToURLBase64:(NSData *)src;

/**
 * 将URL Base64字符串解码成NSData数据
 * @param  src   URL Base64编码的字符串
 * @return
 *      <b> nil : 转换失败 </b><br/>
 *      <b> 非空 : 转换成功，转换后的数据 </b><br/>
 * @note 转换URL格式的字符串
 */
+ (NSData *)urlBase64ToBytes:(NSString *)src;

/**
 * 将NSData数据转换成Base64编码的字符串
 * @param src  原始数据
 * @return
 *      <b> nil : 转换失败 </b><br/>
 *      <b> 非空 : 转换成功，转换后的字符串 </b><br/>
 * @note 转换普通的字符串
 */
+ (NSString *)bytesToBase64:(NSData *)src;

/**
 * 将Base64字符串解码成NSData数据
 * @param src  base64编码的字符串
 * @return
 *      <b> nil : 转换失败 </b><br/>
 *      <b> 非空 : 转换成功，转换后的数据 </b><br/>
 * @note 转换普通的字符串
 */
+ (NSData *)base64ToBytes:(NSString *)src;

#pragma mark - Hex
/**
 * 将NSData数据转换成十六进制字符串
 * @param src 原始数据
 * @return  全部为小写字母的十六进制字符串<br/>
 *      <b> nil : 转换失败 </b><br/>
 *      <b> 非空 : 转换成功，转换后的字符串 </b><br/>
 */
+ (NSString *)bytesToHex:(NSData *)src;

/**
 * 将HEX字符串转换成NSData
 * @param src HEX字符串
 * @return
 *      <b> nil : 转换失败 </b><br/>
 *      <b> 非空 : 转换成功，转换后的字符串 </b><br/>
 */
+ (NSData *)hexToBytes:(NSString *)src;

#pragma mark - BCD
/**
 * 将NSData数据转换成BCD编码的字符串
 * @param src 原始数据
 * @return
 *      <b> nil : 转换失败 </b><br/>
 *      <b> 非空 : 转换成功，转换后的字符串 </b><br/>
 */
+ (NSString *)bytesToBCD:(NSData *)src;

/**
 * 将BCD编码字符串转换成NSData
 * @param src BCD编码的字符串
 * @return
 *      <b> nil : 转换失败 </b><br/>
 *      <b> 非空 : 转换成功，转换后的字符串 </b><br/>
 */
+ (NSData *)BCDToBytes:(NSString *)src;

#pragma mark - Json XML
/**
 * 将XML格式的字符串转换成JSON格式的字符串
 * @param xml  xml格式的字符串
 * @return
 *      <b> nil : 转换失败 </b><br/>
 *      <b> 非空 : 转换成功 </b><br/>
 */
+ (NSString *)XMLToJSON:(NSString *)xml;

/**
 * 将JSON格式的字符串转换成XML格式字符串
 * @param json  json格式的字符串
 * @return
 *      <b> nil : 转换失败 </b><br/>
 *      <b> 非空 : 转换成功 </b><br/>
 */
+ (NSString *)JSONToXML:(NSString *)json;

@end
