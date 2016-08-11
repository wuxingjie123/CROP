//
//  PTBusinessManager.h
//  PT
//
//  Created by gengych on 16/3/16.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>
@class GDataXMLElement;

// business.xml 格式:保存的 url 都是 html 类型的；
/*
 <?xml version="1.0" encoding="UTF-8"?>
	<business>
		<item version="16.01.19.17.19.34" url="html|MBTransferRemittance/phoneNumberTA.html"/>
		<item version="15.09.16.09.26.24" url="html|MBLifeService/openGuide.html"/>
		<item version="16.01.20.15.03.31" url="html|MBExchangeRate/BuyForeignExchange.html"/>
	</business>
	<!--EAEcDikzAgFoT2/uEtvIG5T7p/eY/hFWIBQx+KwwAuScZk0WTDd/LHbrBswjnhwbmlWWocNJCTNYaYXUMGqTrVNkLrXiyPcJ+I6/cCZPG9nOlhgNkSb1O3daJ2dPfOhUIvJhaceC3PMjaPED5bbEpae5YUn3IHb3rty/Ar1V/iP4UN7gUIginE/k6yXVSxoIVu54b9akWy4sF+ikFWuVJybNFy4/losRvISscwI7eRxWoYwKN/ibN8EC54QVKvYUHUvxp/dvo/9yh71AOwg/01pQ4R2PVgktahF3RDn+aZLpg3OAyP81HH28ZdnSqP54HKz3CIoWfN8tuSXfUOGbdIkTK/ypJA==-->
 */
/**
 * @ingroup resourceFileModuleClass
 */
@interface PTBusinessManager : NSObject

@property PTEventState businessCheckState;

@property PTEventState businessPreparedState;

/**
 * 以懒惰方式实现的单例模式
 * @return 返回PTBusinessManager对象
 */
+ (id)getInstance;

/**
 * Documents/menus 文件夹存在，说明用户使用过应用，需要保留用户的business.xml记录；
 * 所以，不在拷贝 Documents/release 文件夹里的 business.xml 等文件；
 */
- (void)initialization;

/**
 * 功能：<br/>
 *      1 对 business.xml 进行验签操作；<br/>
 *      2 加载 business.xml 文件到树形节点<br/>
 *      3 设置加载business.xml结果状态 businessPreparedState <br/>
 */
- (void)loadBusinessFile;
    
#pragma mark - 加载 business.xml
/**
 * 获取处理后的Business根节点
 * @return
 * rootElement
 */
- (GDataXMLElement *)getRootBusiness;

#pragma mark - 保存 business.xml
/**
 * 将内存中的rootElement保存到沙盒，覆盖原文件
 * 目标文件：
 *      1、documents/menus/business.xml
 *      2、documents/menus/business.xml.bak
 * @return YES
 */
- (BOOL)saveBusinessXMLFile;

#pragma mark - 获取指定 htmlUrl 的节点信息 || version信息
/**
 * 根据url获得Business节点
 * @param htmlUrl 指定模板名称
 * @return GDataXMLElement business.xml中htmlUrl对应的节点
 */
- (GDataXMLElement *)getElementByHTMLUrl:(NSString *)htmlUrl;

/**
 * 根据url获得本地模板版本号
 * @param htmlUrl 指定模板名称
 * @return NSString  business.xml中htmlUrl对应的version
 */
- (NSString *)getHTMLVersionByUrl:(NSString *)htmlUrl;

- (NSArray *)searchFatherIDWithID:(NSString *)menuId;

#pragma mark - 节点操作，主要是对 update 属性 新增、删除、刷新、查询 操作；
/**
 * 更新business中节点的UPDATE属性<br/>
    1 如果没有htmlUrl节点<br/>
        1)添加新节点<br/>
        2)添加 version 属性<br/>
        3)添加 update 属性<br/>
    2 存在htmlUrl节点,没有 version 属性<br/>
        1)添加 version 属性<br/>
        2)添加 update 属性<br/>
    3 存在htmlUrl节点,有 version 属性，但不同<br/>
        1)修改 version 属性<br/>
        2)添加 update 属性<br/>
 * @param htmlUrl    url名称
 * @param newVersion 新的模板版本号
 */
- (void)refreshBusinessWithHTMLUrl:(NSString *)htmlUrl andNewVersion:(NSString *)newVersion;

/**
 * 创建并添加新节点
 *
 */
- (void)addNewBusinessElementWithHTMLUrl:(NSString *)htmlUrl andVersion:(NSString *)version;

/**
 * 删除business中节点的UPDATE属性
 * @param htmlUrl    url名称
 */
- (void)removeUpdateAttributeOnElementWithHTMLUrl:(NSString *)htmlUrl;

/**
 * 刷新UPDATE属性<br/>
   示例一：<br/>
   @code
        NSString *updateString = [NSString stringWithFormat:@"%@|%@|%@|%@",FLAG_UPDATETYPE_INSTALL,FLAG_UPDATESTATE_REQUIRED,@" ",@"0"];
        NSString *htmlUrl = @"Demo/test1.html";
        [[PTBusinessManager getInstance] refreshUpdateAttribute:updateString andHTMLUrl:htmlUrl];
   @endcode

   示例二：<br/>
   @code
        NSArray *updateArr = [[NSArray alloc] initWithObjects:FLAG_UPDATETYPE_INSTALL,FLAG_UPDATESTATE_REQUIRED, @" ", @"0", nil];
        NSString *htmlUrl = @"Demo/test1.html";
        [[PTBusinessManager getInstance] refreshUpdateAttribute:updateArr andHTMLUrl:htmlUrl];
   @endcode
 * @param updateString  update属性值，限定为NSArray和NSString两种类型
 * @param htmlUrl       url名称<br/>
 */
- (void)refreshUpdateAttribute:(id)updateString andHTMLUrl:(NSString *)htmlUrl;

/**
 * 获取菜单的update属性，并将该属性转为数组
 * @param htmlUrl  url名称
 * @return
 * NSMutableArray
 */
- (NSMutableArray *)getUpdateParamsWithHTMLUrl:(NSString *)htmlUrl;

///**
// * 更新检测business文件
// * @return
// *     YES   初始化成功<br/>
// *      NO   初始化失败<br/>
// */
//- (BOOL)updateBusinessFile;

@end
