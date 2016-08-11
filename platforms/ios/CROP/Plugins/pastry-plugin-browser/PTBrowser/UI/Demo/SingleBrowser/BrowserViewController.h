//
//  BrowserViewController.h
//  PTCordova
//
//  Created by gengych on 16/3/3.
//
//

#import <UIKit/UIKit.h>
#import "PTBrowserDelegate.h"
#import "PTViewControllerBase.h"

@class PTBrowserContainer;

typedef void(^callbackBlock)(id param);

@interface BrowserViewController : PTViewControllerBase<PTBrowserDelegate, PTComponentInterface>

@property (strong,nonatomic) callbackBlock viewDidCompleteCallBack;

/**
 isHome = YES   :   作为首页，不显示返回按钮；
 isHome = NO    :   不作为首页，显示返回按钮；
 */
@property                       BOOL            isHome;

/**
 待加载的html地址；
 */
@property (strong, nonatomic)   NSString        *loadUrl;

@property (strong, nonatomic)   UIView          *browserView;

@property (strong, nonatomic)   UIView          *headerView;
@property (strong, nonatomic)   UILabel         *titleLabel;
@property (strong, nonatomic)   UIButton        *returnButton;

@property (strong, nonatomic)   PTBrowserContainer *BrowserContainer;


@property (strong, nonatomic)   NSMutableArray    *titleStack;


- (void)markHome:(BOOL)isHome;

@end
