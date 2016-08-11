//
//  AppDelegate+TabMain.m
//  HelloWorld
//
//  Created by 耿远超 on 16/7/28.
//
//

#import "AppDelegate+TabMain.h"

#import "BrowserViewController.h"
#import "MoreBrowserViewController.h"
#import "SecondViewController.h"

@implementation AppDelegate (TabMain)

- (UIViewController *)getMainComponent {
    
    PTBrowser *browser = [PTBrowser sharedBrowser];
    
    // 初始化 1 个 多窗口浏览器
    UITabBarController *tabBarController = [[MoreBrowserViewController alloc] init];
    browser.browserManager = tabBarController;
    
    BrowserViewController *browserController1 = [[BrowserViewController alloc] init];
    [browserController1 markHome:YES];
    browserController1.title = NSLocalizedString(@"First", @"First");
    browserController1.tabBarItem.image = [UIImage imageNamed:@"browserImages.bundle/Contents/Resources/first"];
    id __weak browserContainerTemp = browserController1.BrowserContainer;
    browserController1.viewDidCompleteCallBack = ^(id param){
        [browser nativeInitBrowserHome:@"app/index.html" params:nil callbackBlock:nil forBrowserContainer:browserContainerTemp];
    };
    
    UIViewController *viewController2 = [[SecondViewController alloc] initWithNibName:@"SecondViewController" bundle:nil];
    viewController2.title = NSLocalizedString(@"Second", @"Second");
    viewController2.tabBarItem.image = [UIImage imageNamed:@"browserImages.bundle/Contents/Resources/second"];
    
    BrowserViewController *browserController2 = [[BrowserViewController alloc] init];
    [browserController2 markHome:YES];
    browserController2.title = NSLocalizedString(@"Second", @"Second");
    browserController2.tabBarItem.image = [UIImage imageNamed:@"browserImages.bundle/Contents/Resources/second"];
    id __weak browserContainerTemp2 = browserController2.BrowserContainer;
    browserController2.viewDidCompleteCallBack = ^(id param){
        [browser nativeInitBrowserHome:@"app/index.html" params:nil callbackBlock:nil forBrowserContainer:browserContainerTemp2];
    };
    
    // 初始化正常的首页
    tabBarController.viewControllers = @[browserController1, viewController2, browserController2];
    
    return tabBarController;
}

@end
