//
//  GDataXMLElement+PTXMLUtil.h
//  PT
//
//  Created by gengych on 16/3/14.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import "GDataXMLNode.h"

/**
 * @ingroup utilModuleClass
 */
@interface GDataXMLElement (PTXMLUtil)

/**
 * 移除所有子节点
 */
-(void)removeAllChild;

/**
 * 移除部分子节点
 * @param children 需要移除的子节点数组
 */
-(void)removeChildArray:(NSArray *)children;

/**
 * 获取本节点的直接父节点
 * @return 父节点
 */
-(GDataXMLElement *)getParent;

/**
 * 获取属性attrName的value
 * @param attrName 属性名称
 * @return  string 属性值
 */
-(NSString *)attribute:(NSString *)attrName;

/**
 * 获取指定名称的子节点数组
 * @param  nodeName 节点名称
 * @return array
 */
-(NSArray *)childrenWithNodeName:(NSString *)nodeName;


/**
 * 查找指定xml节点，如有引用，处理引用(废弃)
 * @param key
 * @param value
 * @param nodeName
 * @return
 *     nil  未找到对应的节点<br/>
 *   非nil  找到对应key=value的节点<br/>
 */
//- (GDataXMLElement *)childNodeWithName:(NSString *)nodeName Key:(NSString *)key andValue:(NSString *)value;

/**
 * 查找指定xml节点，如有引用，处理引用
 * @param nodeName  节点名称
 * @param key       key值
 * @param value     value值
 * @return
 *     nil  未找到对应的节点<br/>
 *   非nil  找到对应key=value的节点数组<br/>
 */
- (NSArray *)childrenNodesWithName:(NSString *)nodeName Key:(NSString *)key andValue:(NSString *)value;

/**
 * 添加属性，如存在同名属性则替换原属性(强制添加)
 * @param attribute 需要添加的属性
 */
- (void)forceAddAttribute:(GDataXMLNode *)attribute;

/**
 * 删除属性
 * @param attributeName 需要删除的属性名称
 */
- (void)removeAttributeWithName:(NSString *)attributeName;


/**
 * 批量添加属性
 * 如果属性字典里的属性与node的原有属性重名，则覆盖原属性
 * @param attributeDictionary 属性字典
 */
- (void)addAttributesFromDictionary:(NSDictionary *)attributeDictionary;

/**
 * 写文件
 * @param filePath  保存路径
 * @param doc       保存GDataXMLDocument
 * @param copyBak   是否保存.bak备份文件
 * @return YES
 */
+ (BOOL)saveXMLFile:(NSString *)filePath document:(GDataXMLDocument *)doc copyBak:(BOOL)copyBak;

/**
 * 将节点属性转化为字典
 * 属性name为key，stringValue为value
 * 只包含GDataXMLAttributeKind属性值
 */
- (NSMutableDictionary *)getDictionaryFromAttributes;

/**
 * 获取xml中节点信息；
 * @param   key     以根节点为开始，依次是节点key1、key2、key3、... nil
 * @return  指定的节点对象；
 */
- (GDataXMLElement *)getElementOfKey:(NSString *)key, ... NS_REQUIRES_NIL_TERMINATION;

/**
 * 获取xml中节点信息；
 * @param   keyArrays     以根节点为开始，依次是节点[key1、key2、key3]
 * @return  指定的节点对象；
 */
- (GDataXMLElement *)getElementOfKeyArrays:(NSArray *)keyArrays;

@end

@interface GDataXMLElement (FileOperator)

/**
 * 将XML节点写入目标路径文件
 * @param filePath 目标文件完整路径
 */
- (void)writeToFile:(NSString *)filePath;

/**
 * 通过文件获取DOM树根节点
 * @param filePath 源文件完整路径
 * @return GDataXMLElement对象
 */
+ (GDataXMLElement *)rootElementFromXMLFile:(NSString *)filePath;

@end



