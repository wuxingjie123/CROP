//
//  PTGuideInterface.h
//  HelloWorld
//
//  Created by 耿远超 on 16/7/28.
//
//

#import <Foundation/Foundation.h>

@protocol PTGuideInterface <NSObject>

/**
 * 标识 是否显示解压进度页面，值取自 const BOOL isDisplayView <br/>
 *      YES:显示解压进度页面 <br/>
 *      NO:不显示解压进度页面 <br/>
 */
@property (nonatomic, readonly)       BOOL    displayView;

/**
 *  添加 资源解压完成后的回调方法
 * @param   completeBlock   回调方法<br/>
 * 备注<br/>
 *      1、当 displayUnzipView = YES时，需要显示解压进度页面，使用 viewDidLoad 方法中的异步解压；<br/>
 *      2、当 displayUnzipView = NO时，不需要显示解压进度页面，使用 completeUnzip 方法中的同步解压；<br/>
 */
- (void)completion:(void(^)(id object))completeBlock;


@end
