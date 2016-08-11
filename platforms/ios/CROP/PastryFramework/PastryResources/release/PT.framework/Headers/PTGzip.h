//
//  PTGzip.h
//  PT
//
//  Created by gengych on 16/4/28.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 * @ingroup utilModuleClass
 * 实现gzip格式的文件及数据的解压缩
 */
@interface PTGzip : NSObject

/**
 * 压缩指定的数据
 * @param  src   压缩前数据
 * @return
 *      <b> nil : 压缩失败 </b><br/>
 *      <b> 非空 : 压缩成功，压缩后的数据 </b><br/>
 */
+ (NSData *)gzipData:(NSData *)src;

/**
 * 解压指定的数据
 * @param  src   解压前数据
 * @return
 *      <b> nil : 解压失败 </b><br/>
 *      <b> 非空 : 解压成功，解压后的数据 </b><br/>
 */
+ (NSData *)ungzipData:(NSData *)src;

@end
