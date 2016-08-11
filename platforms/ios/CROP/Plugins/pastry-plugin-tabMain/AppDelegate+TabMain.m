//
//  AppDelegate+TabMain.m
//  HelloWorld
//
//  Created by 耿远超 on 16/7/28.
//
//

#import "AppDelegate+TabMain.h"

#import "MoreBrowserViewController.h"

#import "PTHomeViewController.h"
#import "PTCustomerViewController.h"
#import "PTTrankViewController.h"
#import "PTOtherViewController.h"

@implementation AppDelegate (TabMain)

- (UIViewController *)getMainComponent {
    
    PTBrowser *browser = [PTBrowser sharedBrowser];
    
    // 初始化 1 个 多窗口浏览器
    UITabBarController *tabBarController = [[MoreBrowserViewController alloc] init];
    browser.browserManager = tabBarController;
    
    // 首页
    PTHomeViewController *homeViewController = [[PTHomeViewController alloc] initWithNibName:@"PTHomeViewController" bundle:nil];
    homeViewController.title = NSLocalizedString(@"首页", @"Home");
    homeViewController.tabBarItem.image = [UIImage imageNamed:@"browserImages.bundle/Contents/Resources/second"];
    
    // 跟踪箱
    PTTrankViewController *trankViewController = [[PTTrankViewController alloc] initWithNibName:@"PTTrankViewController" bundle:nil];
    trankViewController.title = NSLocalizedString(@"跟踪箱", @"Trank");
    trankViewController.tabBarItem.image = [UIImage imageNamed:@"browserImages.bundle/Contents/Resources/first"];
    
    // 客户
    PTCustomerViewController *customerViewController = [[PTCustomerViewController alloc] initWithNibName:@"PTCustomerViewController" bundle:nil];
    customerViewController.title = NSLocalizedString(@"客户", @"Customer");
    customerViewController.tabBarItem.image = [UIImage imageNamed:@"browserImages.bundle/Contents/Resources/first"];
    
    // 其它
    PTOtherViewController *otherViewController = [[PTOtherViewController alloc] initWithNibName:@"PTOtherViewController" bundle:nil];
    otherViewController.title = NSLocalizedString(@"其他", @"Other");
    otherViewController.tabBarItem.image = [UIImage imageNamed:@"browserImages.bundle/Contents/Resources/first"];
    
    tabBarController.viewControllers = @[homeViewController, trankViewController, customerViewController, otherViewController];
    
    return tabBarController;
}

@end
