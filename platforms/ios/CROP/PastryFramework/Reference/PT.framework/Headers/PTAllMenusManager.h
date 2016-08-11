//
//  PTAllMenusManager.h
//  PT
//
//  Created by gengych on 16/4/29.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>
@class GDataXMLDocument;
@class GDataXMLElement;

/**
 * @ingroup resourceFileModuleClass
 */
@interface PTAllMenusManager : NSObject

@property PTEventState allmenuCheckState;

@property PTEventState allmenuUpdateState;

@property PTEventState allmenuPreparedState;

/**
 *  获取PTResourceJsonManager单例对象
 *
 *  @return 相应单例对象
 */
+ (instancetype)getInstance;

/**
 * 初始化 allmenu.xml 位置
 * @note 如果 documents/release/allmenu.xml 存在，将文件移动到 documents/menus/allmenu.xml，与menu.xml、business.xml 的处理方式不同；
 * Documents/menus 文件夹存在，说明用户使用过应用，需要保留用户的menu.xml记录；
 * 所以，不在拷贝 Documents/release 文件夹里的 menu.xml 等文件；
 */
- (void)initialization;

- (void)loadAllMenuFile;

- (void)asyncLoadAllMenuFile;

/**
 * 获取处理后的菜单
 * @return
 * rootElement
 */
- (GDataXMLElement *)getRootAllMenu;

- (GDataXMLDocument *)getDocumentAllMenu;

- (BOOL)updateAllMenuFile;

@end
