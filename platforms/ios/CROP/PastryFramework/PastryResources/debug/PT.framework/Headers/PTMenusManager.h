//
//  PTMenusManager.h
//  PT
//
//  Created by gengych on 16/3/16.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>

@class GDataXMLElement;

/*
 <?xml version="1.0" encoding="UTF-8"?>
 <menus>
 <menu id="100100" fatherid="000000" title="我的账户" isleaf="0" isProfessional="0" level="1" url="native|WDZH" icon="acount_20141017100" minver="1.2.0" public="true" allowDelete="1" isShow="1" keywords="我的账户|账户|账户管理"/>
 <menu id="100101" fatherid="100100" title="资产查询" isleaf="1" isProfessional="0" level="2" url="native|ZCCX" icon="acount_20141017101" minver="1.2.0" public="false" allowDelete="1" isShow="1" keywords="资产查询|资产|查询|资产报告|我的资产|账单|我的账单"/>
 <menu id="100102" fatherid="100100" title="账户查询" isleaf="1" isProfessional="0" level="2" url="html|MBMyAccount/AccountSearch.html" version="16.01.05.10.31.04" icon="acount_20141017102" minver="1.2.0" public="false" allowDelete="1" isShow="1" keywords="账户查询|账户|查询"/>
 <menu id="100103" fatherid="100100" title="明细查询" isleaf="1" isProfessional="0" level="2" url="html|MBMyAccount/DetailInquiry.html" version="16.01.05.10.31.04" icon="acount_20141017103" minver="1.2.0" public="false" allowDelete="1" isShow="1" keywords="明细查询|明细|查询|交易记录|交易查询|交易记录查询"/>
 <menu id="7001002" fatherid="000000" title="日历设置" isleaf="0" isProfessional="0" level="1" url="html|MBFinancialCalendar/CalendarSet.html" version="15.09.16.09.26.17" icon="" minver="1.2.0" public="false" allowDelete="1" isShow="0" keywords="日历设置"/>
 </menus><!--AAAAAAAAOpw0DEV0XCmcHdFvhwZMGSyAqQ5KOb1Rl/KGoVtsOd6tA0UfJ7DJSMBw3uYieelwTnB7Y1iCt2/pkipCdAsCgaL4GQYu9dIAgDJXNgPSp2iie5rRi5OKBX9Mfa+XJRXSjxi6VWsfj4rIWteBZGp4ksiEPTUMh25LyCbOxpEU7ceSwmhrn2cLX57Zskq7faHw9Se0aEcWD7qBQAfuomvUxBkK+DYkvvnV/dIpedZSyFtQOGL+YDAYPVnUflQfXMRkXSNtvGreQiYtRd5DlEsh68gZx0r27qbEItM8Yf1eUaWwc2+lQYAizRQaV2xURDcLSaw7XwCuq2uBgsGERd9LMQ==-->
 */
/*
 属性：
 id             ：   唯一标志符；
 fatherid       ：   父Id；
 title          ：   Window的标题栏；(原生使用)
 version        ：   版本号：针对 html 模版；
 isleaf         ：   标志是否是子节点，1 表示是子节点；0 表示不是子节点；
 isProfessional ：   ？？？个人版和企业版，未使用；
 level          ：   节点的层级关系。1 表示一级节点； 2 表示二级节点； 3 表示三级节点；
 url            ：   1、原生url   ：native 原生类反射：类名；
                     2、html url ：html   html文件： html 文件路径；
                     3、         ：web
 icon           ：   icon 图标；
 minver         ：   原生客户端的版本；
 public         ：   登陆前交易和登陆后交易标识；
 allowDelete    ：   标志是否允许删除，1 表示允许删除；0 表示不允许删除；
 isShow         ：   是否是某个业务的入口；
 keyworkds      ：   语音使用属性；
 visit          ：   未使用？？？
 isnew          ：   模版是否需要更新，1 表示模版更新；0 表示模版不更新；
 icon           ：   icon 原生app包里
 iconUrl        ：   服务器上url （备注：icon 使用的地址，应该从服务器上下载，否则，每添加一个业务，都需要在原生里放一个图片，还得等原生app获取更新才能使用；）
 type           ：   开关：
 iosversion     ：   系统版本；
 updateversion  ：   控制某个版本以上更新和不更新；
 whiteflag      ：   根据设备的mac地址，用户登录
 showversion    ：   和isShow
 */

/**
 * @ingroup resourceFileModuleClass
 * menu.xml 操作类
 */
@interface PTMenusManager : NSObject

@property PTEventState menuCheckState;

@property PTEventState menuPreparedState;


/**
 * 以懒惰方式实现的单例模式
 * @return 返回PTMenus对象
 */
+ (id)getInstance;

/**
 * Documents/menus 文件夹存在，说明用户使用过应用，需要保留用户的menu.xml记录；
 * 所以，不在拷贝 Documents/release 文件夹里的 menu.xml 等文件；
 */
- (void)initialization;

/**
 * 功能：<br/>
 *      1 对 menu.xml 进行验签操作；<br/>
 *      2 加载 menu.xml 文件到树形节点<br/>
 *      3 设置加载menu.xml结果状态 menuPreparedState <br/>
 *      4 发送 对内通知（PTNotification_DATA_PREPARED） : 发送菜单(menu.xml)-加载 完成通知名称：结果：成功、失败<br/>
 */
- (void)loadMenuFile;

#pragma mark - 加载 menu.xml
/**
 * 获取处理后的菜单
 * @return
 * rootElement
 */
- (GDataXMLElement *)getRootMenu;

#pragma mark - 保存 menu.xml
/**
 * 将内存中的rootElement保存到沙盒，覆盖原文件 <br/>
 * 目标文件： <br/>
 *      1、Documents/menus/menu.xml <br/>
 *      2、Documents/menus/menu.xml.bak <br/>
 * @return YES 保存成功 <br/>
 *         NO  保存失败 <br/>
 */
- (BOOL)saveMenusXMLFile;

#pragma mark - 获取指定 menuId 的节点
/**
 * 根据菜单ID获取菜单节点
 * @param  menuId    菜单ID
 * @return
 *     nil  未找到对应ID的菜单
 *    非nil  找到对应ID的菜单
 */
- (GDataXMLElement *)getMenuById:(NSString *)menuId;

/**
 * 查找指定xml节点，如有引用，处理引用
 * @param key       key值
 * @param value     value值
 * @return
 *     nil  未找到对应的节点<br/>
 *   非nil  找到对应key=value的节点<br/>
 */
- (GDataXMLElement *)getFinalMenuByKey:(NSString *)key andValue:(NSString *)value;



///**
// * 设置刷新菜单标识为True，会在refreshMenus:andBlock:方法里统一发送刷新通知
// * 对应PTFrameworkDelegate中的handleMenuCheck:代理方法
// */
//- (void)markMenuChangeFlag;

/**
 * 刷新business.xml属性，合并服务器返回的同步信息（已经过转化的）
 * @param  synchroData    同步属性数组
 * @param  operatorBlock 客户端定制的同步属性操作
 * @note   void(^)(bool attributeChanged) block回调
 *         attributeChanged框架内操作属性，变化标识
 */
- (void)refreshMenus:(NSArray *)synchroData andBlock:(void(^)(bool attributeChanged))operatorBlock;

/**
 * 更新模板文件操作
 * @param  url          url地址
 * @param  delegate     添加代理
 * @exception PTException(MPUP001 -- 参数错误)
 */
//- (void)updateTemplateWithUrl:(NSString *)url addDelegate:(id<PTMenuManagerDelegate>)delegate;
- (void)updateTemplateWithUrl:(NSString *)url
                progressBlock:(void(^)(NSDictionary *dictionary))progressBlock
                completeBlock:(void(^)(NSDictionary *dictionary))completeBlock;

/**
 * 根据模版名称，获取对应的版本号
 * @param  url    模版关键字，即菜单中url属性
 * @return
 *     nil    未找到对应文件
 *    非nil   找到对应文件版本号
 */
- (NSString *)getTemplateVersionByUrl:(NSString *)url;

/**
 * 设置业务访问时间(临时接口，后续框架完善后该接口将被删除)
 * @param   menuID    业务模板url
 */
- (void)visit:(NSString *)menuID;

/**
 * 添加菜单，默认添加到根节点
 * @param dictionary 新菜单的属性字典
 * @return YES
 */

- (BOOL)addNewMenuByDictionary:(NSDictionary*)dictionary;
/**
 * 添加菜单
 * @param dictionary 新菜单的属性字典
 * @param specificElementID 指定父节点ID
 *        若为nil，默认为根节点
 * @return YES
 */
- (BOOL)addNewMenuByDictionary:(NSDictionary *)dictionary underSpecificElementID:(NSString *)specificElementID;
/**
 * 菜单点击事件
 * @param  menu  对应菜单
 * @param  mask  对应掩码
 * @param  targetDelegate 外部调用本方法的对象
 * @return
 */
//- (void)doAction:(GDataXMLElement *)menu mask:(PTMenuMask)mask targetDelegate:(id)targetDelegate;

/**
 * 查看对应模板是否已添加进下载队列
 * @param url  菜单ID
 * @return YES 已在下载队列中
 *         NO  不在下载队列
 */
- (BOOL)isTemplateDownloadingByUrl:(NSString *)url;

///**
// * 主动移除代理（客户端主动放弃代理接收时调用）
// * delegate        需要从代理列表中移除的对象
// */
//- (void)removeDelegate:(id)delegate;

///**
// * 检测更新menu.xml文件（不需要联网操作）
// * @return
// *     YES   初始化成功<br/>
// *      NO   初始化失败<br/>
// */
//- (BOOL)checkUpdateMenuFile;

@end
