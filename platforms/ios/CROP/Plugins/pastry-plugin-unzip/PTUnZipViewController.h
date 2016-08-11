//
//  PTUnZipViewController.h
//  CBCordova
//
//  Created by gengych on 16/3/24.
//
//

#import <UIKit/UIKit.h>

/**
 * 控制 data.zip 资源文件解压过程；<br/>
 *      如果编译前设置 isDisplayUnzipView = YES; 使用 PTFrameWork 的异步解压接口，显示解压进度页面；<br/>
 *      如果编译前设置 isDisplayUnzipView = NO; 使用 PTFrameWork 的同步解压接口，不显示解压进度页面；<br/>
 */
@interface PTUnZipViewController : UIViewController <PTUnzipInterface>

@property (weak, nonatomic) IBOutlet UIProgressView *unzipProgressView;
@property (weak, nonatomic) IBOutlet UIImageView *splashImage;
@property (weak, nonatomic) IBOutlet UILabel *titleLabel;

///**
// * 标识 是否显示解压进度页面，值取自 const BOOL isDisplayView <br/>
// *      YES:显示解压进度页面 <br/>
// *      NO:不显示解压进度页面 <br/>
// */
//@property (nonatomic, readonly)       BOOL    displayView;
//
///**
// *  添加 资源解压完成后的回调方法
// * @param   completeBlock   回调方法<br/>
// * 备注<br/>
// *      1、当 displayUnzipView = YES时，需要显示解压进度页面，使用 viewDidLoad 方法中的异步解压；<br/>
// *      2、当 displayUnzipView = NO时，不需要显示解压进度页面，使用 completeUnzip 方法中的同步解压；<br/>
// */
//- (void)completion:(void(^)(id object))completeBlock;

@end
