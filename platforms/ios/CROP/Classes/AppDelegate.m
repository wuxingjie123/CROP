/*
 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */

//
//  AppDelegate.m
//  CROP
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#import "AppDelegate.h"
#import <Cordova/CDVPlugin.h>

@interface AppDelegate ()

@property (nonatomic, strong) UIViewController *mainViewController;

@end

@implementation AppDelegate

#pragma mark UIApplicationDelegate implementation

/**
 * 1、配置 data.zip 解压页面 插件
 */
- (id<PTUnzipInterface>)getZipComponent {
    
    Class unzipComponentClass = NSClassFromString(@"PTUnZipViewController");
    NSString *unzipNibName = @"PTUnZipViewController";
    id<PTUnzipInterface> unzipComponent = [[unzipComponentClass alloc] initWithNibName:unzipNibName bundle:nil];
    if (unzipComponent == nil) {
        
        NSString *tipStr =
        @"\n!!! 没有选择对应的解压页面插件 !!! \n"
        "可选解压页面插件有 \n"
        "pastry-plugin-unzip 更多插件参考: https://pastryteam.github.io/pastry/#!plugins/plugins-cordova.md \n"
        "使用 pastry bake plugin add 插件名称|插件GitHub地址 \n"
        "插件安装完成后，运行程序查看结果. \n";
        
        NSException *exception = [NSException exceptionWithName:@"配置解压页面插件错误" reason:tipStr userInfo:nil];
        
        @throw exception;
    }
    
    return unzipComponent;
}

/**
 * 2、配置 引导页面 插件
 */
- (id<PTGuideInterface>)getGuideComponent {
    
    Class guideComponentClass = NSClassFromString(@"PTGestureUnlockViewController");
    NSString *guideNibName = @"PTGestureUnlockViewController";
    id<PTGuideInterface> guideComponent = [[guideComponentClass alloc] initWithNibName:guideNibName bundle:nil];
    if (guideComponent == nil) {
        
        NSString *tipStr =
        @"\n!!! 没有选择对应的引导页面插件 !!! \n"
        "可选引导页面插件有 \n"
        "pastry-plugin-guide 更多插件参考: https://pastryteam.github.io/pastry/#!plugins/plugins-cordova.md \n"
        "使用 pastry bake plugin add 插件名称|插件GitHub地址 \n"
        "插件安装完成后，运行程序查看结果. \n";
        
        NSException *exception = [NSException exceptionWithName:@"配置引导页面插件错误" reason:tipStr userInfo:nil];
        
        @throw exception;
    }
    
    return guideComponent;
}

/**
 *  3、配置 主页面 插件<br/>
 *  备注：<br/>
 *      请查看 AppDelegate 的类别 类<br/>
 */
- (UIViewController *)getMainComponent {
    
    NSString *tipStr =
    @"\n!!! 没有选择对应的主页模版插件 !!! \n"
    "可选主页模版插件有 \n"
    "pastry-plugin-blankMain  pastry-plugin-sideMain  pastry-plugin-tabMain 更多插件参考: https://pastryteam.github.io/pastry/#!plugins/plugins-cordova.md \n"
    "使用 pastry bake plugin add 插件名称|插件GitHub地址 \n"
    "插件安装完成后，运行程序查看结果. \n"
    "确保对应的 AppDelegate+XXX.m 文件里实现了 getMainComponent 方法 !!! \n";
    
    NSException *exception = [NSException exceptionWithName:@"配置主页模版插件错误" reason:tipStr userInfo:nil];
    
    @throw exception;
}

/**
 * This is main kick off after the app inits, the views and Settings are setup here. (preferred - iOS4 and up)
 */
- (BOOL)application:(UIApplication*)application didFinishLaunchingWithOptions:(NSDictionary*)launchOptions
{
    
    CGRect screenBounds = [[UIScreen mainScreen] bounds];
    self.window = [[UIWindow alloc] initWithFrame:screenBounds];
    self.window.autoresizesSubviews = YES;
    
    // 添加协议必须放在 unpackResources 和 initialization 方法之前
    [[PTFramework getInstance] addListener:self];
    
    id<PTUnzipInterface> zipComponent = [self getZipComponent];
    
    id<PTGuideInterface> guideComponent = [self getGuideComponent];
    
    [zipComponent completion:^(id object) {
        
        [[PTFramework getInstance] initialization];
        // 初始化正常的首页
        self.mainViewController = [self getMainComponent];
        
        [self restoreRootViewController:(guideComponent.displayView == YES ? (UIViewController*)guideComponent : self.mainViewController) options:UIViewAnimationOptionTransitionCrossDissolve];
        
        [guideComponent completion:^(id object) {
            [self restoreRootViewController:self.mainViewController options:UIViewAnimationOptionTransitionCrossDissolve];
        }];
    }];
    
    self.window.rootViewController = (zipComponent.displayView == YES ? (UIViewController*)zipComponent : (guideComponent.displayView == YES ? (UIViewController*)guideComponent : self.mainViewController));
    
    [self.window makeKeyAndVisible];
    
    return YES;
    
    // Set your app's start page by setting the <content src='foo.html' /> tag in config.xml.
    // If necessary, uncomment the line below to override it.
    // self.viewController.startPage = @"index.html";
    
    // NOTE: To customize the view's frame size (which defaults to full screen), override
    // [self.viewController viewWillAppear:] in your view controller.
    
}

#pragma mark - 切换 RootViewController 动画
- (void)restoreRootViewController:(UIViewController *)rootViewController
                          options:(UIViewAnimationOptions)options
{
    if (self.window.rootViewController == nil) {
        return;
    }
    
    typedef void (^Animation)(void);
    UIWindow* windowSelf = self.window;
    
    rootViewController.modalTransitionStyle = UIModalTransitionStylePartialCurl;
    Animation animation = ^{
        BOOL oldState = [UIView areAnimationsEnabled];
        [UIView setAnimationsEnabled:NO];
        windowSelf.rootViewController = rootViewController;
        [UIView setAnimationsEnabled:oldState];
    };
    
    [UIView transitionWithView:windowSelf
                      duration:0.5f
                       options:options
                    animations:animation
                    completion:^(BOOL finished) {
                        
                    }];
}

#pragma mark - 所有类型工程共工部分

@synthesize window;

- (id)init
{
    /** If you need to do any extra app-specific initialization, you can do it here
     *  -jm
     **/
    NSHTTPCookieStorage* cookieStorage = [NSHTTPCookieStorage sharedHTTPCookieStorage];
    
    [cookieStorage setCookieAcceptPolicy:NSHTTPCookieAcceptPolicyAlways];
    
    int cacheSizeMemory = 8 * 1024 * 1024; // 8MB
    int cacheSizeDisk = 32 * 1024 * 1024; // 32MB
#if __has_feature(objc_arc)
    NSURLCache* sharedCache = [[NSURLCache alloc] initWithMemoryCapacity:cacheSizeMemory diskCapacity:cacheSizeDisk diskPath:@"nsurlcache"];
#else
    NSURLCache* sharedCache = [[[NSURLCache alloc] initWithMemoryCapacity:cacheSizeMemory diskCapacity:cacheSizeDisk diskPath:@"nsurlcache"] autorelease];
#endif
    [NSURLCache setSharedURLCache:sharedCache];
    
    self = [super init];
    return self;
}

// this happens while we are running ( in the background, or from within our own app )
// only valid if CROP-Info.plist specifies a protocol to handle
- (BOOL)application:(UIApplication*)application openURL:(NSURL*)url sourceApplication:(NSString*)sourceApplication annotation:(id)annotation
{
    if (!url) {
        return NO;
    }

    // all plugins will get the notification, and their handlers will be called
    [[NSNotificationCenter defaultCenter] postNotification:[NSNotification notificationWithName:CDVPluginHandleOpenURLNotification object:url]];

    return YES;
}

// repost all remote and local notification using the default NSNotificationCenter so multiple plugins may respond
- (void)            application:(UIApplication*)application
    didReceiveLocalNotification:(UILocalNotification*)notification
{
    // re-post ( broadcast )
    [[NSNotificationCenter defaultCenter] postNotificationName:CDVLocalNotification object:notification];
}

#ifndef DISABLE_PUSH_NOTIFICATIONS

    - (void)                                 application:(UIApplication*)application
        didRegisterForRemoteNotificationsWithDeviceToken:(NSData*)deviceToken
    {
        // re-post ( broadcast )
        NSString* token = [[[[deviceToken description]
            stringByReplacingOccurrencesOfString:@"<" withString:@""]
            stringByReplacingOccurrencesOfString:@">" withString:@""]
            stringByReplacingOccurrencesOfString:@" " withString:@""];

        [[NSNotificationCenter defaultCenter] postNotificationName:CDVRemoteNotification object:token];
    }

    - (void)                                 application:(UIApplication*)application
        didFailToRegisterForRemoteNotificationsWithError:(NSError*)error
    {
        // re-post ( broadcast )
        [[NSNotificationCenter defaultCenter] postNotificationName:CDVRemoteNotificationError object:error];
    }
#endif

#if __IPHONE_OS_VERSION_MAX_ALLOWED < 90000
- (NSUInteger)application:(UIApplication*)application supportedInterfaceOrientationsForWindow:(UIWindow*)window
#else
- (UIInterfaceOrientationMask)application:(UIApplication*)application supportedInterfaceOrientationsForWindow:(UIWindow*)window
#endif
{
    // iPhone doesn't support upside down by default, while the iPad does.  Override to allow all orientations always, and let the root view controller decide what's allowed (the supported orientations mask gets intersected).
    NSUInteger supportedInterfaceOrientations = (1 << UIInterfaceOrientationPortrait) | (1 << UIInterfaceOrientationLandscapeLeft) | (1 << UIInterfaceOrientationLandscapeRight) | (1 << UIInterfaceOrientationPortraitUpsideDown);

    return supportedInterfaceOrientations;
}

- (void)applicationDidReceiveMemoryWarning:(UIApplication*)application
{
    [[NSURLCache sharedURLCache] removeAllCachedResponses];
}

- (void)dealloc{
    // 移除监听
    [[PTFramework getInstance] removeListener:self];
}

#pragma mark - PTFrameworkDelegate 协议

#pragma mark 握手相关方法

- (void)didHandShakeWithResult:(NSDictionary *)dictionary{
    int result = [[dictionary objectForKey:@"result"] intValue];
    if (result == 0) {
        // 握手成功
        NSString *data = [dictionary objectForKey:@"data"];
        PTLogDebug(@"%@", data);
    }
    else {
        // 握手失败
        NSString *serverError = [dictionary objectForKey:@"serverError"];
        PTLogDebug(@"%@", serverError);
    }
}

#pragma mark 热更新相关方法

- (BOOL)isNeedHotReload {
    // 影响最终生产包
    return YES;
}

/**
 * 获取menu.xml加载完成结果事件
 * @param  result  准备结果
 *            0    成功<br/>
 *            1    失败<br/>
 *            2    准备中<br/>
 * @note 框架初始化，menu.xml的最新数据；客户端操作后，框架才能继续执行
 */
- (void)didDataPreparedWithResult:(int)result{
    if (result == 0) {
        // 获取 menu.xml 的根节点
        GDataXMLElement *menuRoot = [[PTMenusManager getInstance] getRootMenu];
        
        // 对 menu.xml 的根节点进行处理 开始
        
        
        // 对 menu.xml 的根节点进行处理 结束
        
        // 对 menu.xml 的根节点处理完成后，保存到 menu.xml 文件中；
        [[PTMenusManager getInstance] saveMenusXMLFile];
    }
}



@end
