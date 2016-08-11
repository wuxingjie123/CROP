//
//  AppDelegate+SideMain.m
//  HelloWorld
//
//  Created by 耿远超 on 16/7/28.
//
//

#import "AppDelegate+SideMain.h"
// 使用 side 工程
#import "MainViewController.h"

#import "MMDrawerController.h"
#import "PTCenterTableViewController.h"
#import "PTLeftSideDrawerViewController.h"
#import "PTRightSideDrawerViewController.h"
#import "MMDrawerVisualState.h"
#import "PTDrawerVisualStateManager.h"
#import "MMNavigationController.h"

#import "BrowserViewController.h"
#import "PTBrowser.h"

@implementation AppDelegate (SideMain)

- (UIViewController *)getMainComponent {
    
    UIViewController *leftSideDrawerViewController = [[PTLeftSideDrawerViewController alloc] init];
    
    UIViewController *rightSideDrawerViewController = [[PTRightSideDrawerViewController alloc] init];
    
    PTBrowser *browser = [PTBrowser sharedBrowser];
    BrowserViewController *singleBrowser = [[BrowserViewController alloc] init];
    browser.browserManager = singleBrowser;
    singleBrowser.viewDidCompleteCallBack = ^(id param){
        [browser nativeInitBrowserHome:@"app/index.html" params:nil callbackBlock:nil];
    };
    MainViewController *mainTestController = [[MainViewController alloc] initWithNibName:@"MainViewController" bundle:nil];
    UINavigationController *centerNatigationController = [[MMNavigationController alloc] initWithRootViewController:mainTestController];
    [centerNatigationController setRestorationIdentifier:@"PTCenterNavigationControllerRestorationKey"];
    UINavigationController * rightSideNavController = [[MMNavigationController alloc] initWithRootViewController:rightSideDrawerViewController];
    [rightSideNavController setRestorationIdentifier:@"PTRightNavigationControllerRestorationKey"];
    UINavigationController * leftSideNavController = [[MMNavigationController alloc] initWithRootViewController:leftSideDrawerViewController];
    [leftSideNavController setRestorationIdentifier:@"PTLeftNavigationControllerRestorationKey"];
    
    MMDrawerController *drawerController = [[MMDrawerController alloc]
                                            initWithCenterViewController:centerNatigationController
                                            leftDrawerViewController:leftSideNavController
                                            rightDrawerViewController:rightSideNavController];
    [drawerController setShowsShadow:NO];
    [drawerController setRestorationIdentifier:@"MMDrawer"];
    [drawerController setMaximumRightDrawerWidth:200.0];
    [drawerController setOpenDrawerGestureModeMask:MMOpenDrawerGestureModeAll];
    [drawerController setCloseDrawerGestureModeMask:MMCloseDrawerGestureModeAll];
    
    [drawerController
     setDrawerVisualStateBlock:^(MMDrawerController *drawerController, MMDrawerSide drawerSide, CGFloat percentVisible) {
         MMDrawerControllerDrawerVisualStateBlock block;
         block = [[PTDrawerVisualStateManager sharedManager]
                  drawerVisualStateBlockForDrawerSide:drawerSide];
         if(block){
             block(drawerController, drawerSide, percentVisible);
         }
     }];
    
    return drawerController;
}




//- (BOOL)application:(UIApplication*)application didFinishLaunchingWithOptions:(NSDictionary*)launchOptions
//{
//    
//    return YES;
//}

- (UIViewController *)application:(UIApplication *)application viewControllerWithRestorationIdentifierPath:(NSArray *)identifierComponents coder:(NSCoder *)coder
{
    NSString * key = [identifierComponents lastObject];
    if([key isEqualToString:@"MMDrawer"]){
        return self.window.rootViewController;
    }
    else if ([key isEqualToString:@"PTCenterNavigationControllerRestorationKey"]) {
        return ((MMDrawerController *)self.window.rootViewController).centerViewController;
    }
    else if ([key isEqualToString:@"PTRightNavigationControllerRestorationKey"]) {
        return ((MMDrawerController *)self.window.rootViewController).rightDrawerViewController;
    }
    else if ([key isEqualToString:@"PTLeftNavigationControllerRestorationKey"]) {
        return ((MMDrawerController *)self.window.rootViewController).leftDrawerViewController;
    }
    else if ([key isEqualToString:@"PTLeftSideDrawerController"]){
        UIViewController * leftVC = ((MMDrawerController *)self.window.rootViewController).leftDrawerViewController;
        if([leftVC isKindOfClass:[UINavigationController class]]){
            return [(UINavigationController*)leftVC topViewController];
        }
        else {
            return leftVC;
        }
        
    }
    else if ([key isEqualToString:@"PTRightSideDrawerController"]){
        UIViewController * rightVC = ((MMDrawerController *)self.window.rootViewController).rightDrawerViewController;
        if([rightVC isKindOfClass:[UINavigationController class]]){
            return [(UINavigationController*)rightVC topViewController];
        }
        else {
            return rightVC;
        }
    }
    return nil;
}

@end
