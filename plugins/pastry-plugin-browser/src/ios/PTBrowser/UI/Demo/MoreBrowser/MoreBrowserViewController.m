//
//  MoreBrowserViewController.m
//  PTCordova
//
//  Created by gengych on 16/3/10.
//
//

#import "MoreBrowserViewController.h"
#import "BrowserViewController.h"
#import "PTBrowserContainer.h"

@implementation MoreBrowserViewController


- (void)nativeOpenWindow:(NSString*)url
                  params:(id)params
           callbackBlock:(void(^)(id commandDelegate,id result))callbackBlock{
    
    BrowserViewController *browserVC = nil;
    
    UIViewController *selectViewController = [self selectedViewController];
    if ([selectViewController isKindOfClass:[BrowserViewController class]]) {
        
        browserVC = (BrowserViewController*)selectViewController;
        [browserVC.BrowserContainer containerOpenWindow:url params:params callbackBlock:callbackBlock];
        
    }else{
        PTLogDebug(@"没有选中的 Browser");
        @throw @"没有选中的 Browser";
    }
}

- (void)nativeRefreshCurrentWindow:(NSString*)url params:(id)params{
    BrowserViewController *browserVC = nil;
    
    UIViewController *selectViewController = [self selectedViewController];
    if ([selectViewController isKindOfClass:[BrowserViewController class]]) {
        
        browserVC = (BrowserViewController*)selectViewController;
        [browserVC.BrowserContainer containerRefreshCurrentWindowPage:url params:params];
        
    }else{
        PTLogDebug(@"没有选中的 Browser");
        @throw @"没有选中的 Browser";
    }
}

- (void)nativeSetBrowserTitle:(NSString*)title{
    BrowserViewController *browserVC = nil;
    
    UIViewController *selectViewController = [self selectedViewController];
    if ([selectViewController isKindOfClass:[BrowserViewController class]]) {
        
        browserVC = (BrowserViewController*)selectViewController;
        [browserVC nativeSetBrowserTitle:title];
        
    }else{
        PTLogDebug(@"没有选中的 Browser");
        @throw @"没有选中的 Browser";
    }

}

- (void)nativeGoBack{
    BrowserViewController *browserVC = nil;
    
    UIViewController *selectViewController = [self selectedViewController];
    if ([selectViewController isKindOfClass:[BrowserViewController class]]) {
        
        browserVC = (BrowserViewController*)selectViewController;
        return [browserVC nativeGoBack];
        
    }else{
        PTLogDebug(@"没有选中的 Browser");
        @throw @"没有选中的 Browser";
    }
}

- (void)nativeCloseWindow:(id)params{
    BrowserViewController *browserVC = nil;
    
    UIViewController *selectViewController = [self selectedViewController];
    if ([selectViewController isKindOfClass:[BrowserViewController class]]) {
        
        browserVC = (BrowserViewController*)selectViewController;
        return [browserVC nativeCloseWindow:params];
        
    }else{
        PTLogDebug(@"没有选中的 Browser");
        @throw @"没有选中的 Browser";
    }
}


- (void)registerBeforeWindowTitle{
    BrowserViewController *browserVC = nil;
    
    UIViewController *selectViewController = [self selectedViewController];
    if ([selectViewController isKindOfClass:[BrowserViewController class]]) {
        
        browserVC = (BrowserViewController*)selectViewController;
        [browserVC registerBeforeWindowTitle];
        
    }else{
        PTLogDebug(@"没有选中的 Browser");
        @throw @"没有选中的 Browser";
    }
}

- (void)unRegisterBeforeWindowTitle{
    BrowserViewController *browserVC = nil;
    
    UIViewController *selectViewController = [self selectedViewController];
    if ([selectViewController isKindOfClass:[BrowserViewController class]]) {
        
        browserVC = (BrowserViewController*)selectViewController;
        [browserVC unRegisterBeforeWindowTitle];
        
    }else{
        PTLogDebug(@"没有选中的 Browser");
        @throw @"没有选中的 Browser";
    }
}

- (BOOL)nativeIsCloseBrowser{
    return NO;
}


@end
