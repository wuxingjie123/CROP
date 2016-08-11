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
//  PTWindowPage.h
//  PTCordova
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#import "PTWindowPage.h"

@implementation PTWindowPage{
    void(^_openWindowCallbackBlock)(id commandDelegate, id result);
}

- (void)setOpenWindowCallbackBlock:(void(^)(id commandDelegate,id result))callbackBlock{
    _openWindowCallbackBlock = callbackBlock;
}

- (void(^)(id commandDelegate,id result))getOpenWindowCallbackBlock{
    return _openWindowCallbackBlock;
}

- (id)initWithNibName:(NSString*)nibNameOrNil bundle:(NSBundle*)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Uncomment to override the CDVCommandDelegateImpl used
         _commandDelegate = [[PTWindowPageCommandDelegate alloc] initWithViewController:self];
        // Uncomment to override the CDVCommandQueue used
        // _commandQueue = [[PTWindowCommandQueue alloc] initWithViewController:self];
        switch ([[PTDeveloperManager getInstance] getHTMLSourceType]) {
            case PTHTML_DataZip:
            {
                self.urlBasePath = [PTPathManager getPath:PTPath_Release_Directory];
            }
                break;
            case PTHTML_WWW:
            {
                self.urlBasePath = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:@"www"];
            }
                break;
            case PTHTML_RemoteWWW:
                
                break;
            default:
                
                break;
        }
    }
    return self;
}

- (id)init
{
    self = [super init];
    if (self) {
        // Uncomment to override the CDVCommandDelegateImpl used
         _commandDelegate = [[PTWindowPageCommandDelegate alloc] initWithViewController:self];
        // Uncomment to override the CDVCommandQueue used
        // _commandQueue = [[PTWindowCommandQueue alloc] initWithViewController:self];
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];

    // Release any cached data, images, etc that aren't in use.
}

#pragma mark View lifecycle

- (void)viewWillAppear:(BOOL)animated
{
    // View defaults to full size.  If you want to customize the view's size, or its subviews (e.g. webView),
    // you can do so here.

    [super viewWillAppear:animated];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return [super shouldAutorotateToInterfaceOrientation:interfaceOrientation];
}

/* Comment out the block below to over-ride */

/*
- (UIWebView*) newCordovaViewWithFrame:(CGRect)bounds
{
    return[super newCordovaViewWithFrame:bounds];
}
*/

#pragma mark UIWebDelegate implementation

- (void)webViewDidFinishLoad:(UIWebView*)theWebView
{
    // Black base color for background matches the native apps
    theWebView.backgroundColor = [UIColor blackColor];
    
    // 向 JS 端注册 window.CordovaBridge 对象；
    NSString *js = [NSString stringWithFormat:@";(function() {"
                    "if (window.CordovaBridge) { return; };"
                    "window.CordovaBridge = {"
                    "};"
                    "})();"];
    
    if (![[theWebView stringByEvaluatingJavaScriptFromString:@"typeof CordovaBridge == 'object'"] isEqualToString:@"true"]) {
        [theWebView stringByEvaluatingJavaScriptFromString:js];
        if (![[theWebView stringByEvaluatingJavaScriptFromString:@"typeof CordovaBridge == 'object'"] isEqualToString:@"true"]) {
//            NSString *str = @"";
        }
    }
    
    // 向 JS 端传递参数到 window.fw_param 对象；
    if (self.pageParams != nil && [self.pageParams isKindOfClass:[NSDictionary class]]) {
        
        NSString *strParam = [self.pageParams JSONString];
        
        NSString *paramsJS = [NSString stringWithFormat:@";(function() {"
                              "window.fw_param = %@;"
                              "})();",strParam];
        [theWebView stringByEvaluatingJavaScriptFromString:paramsJS];
        
        PTLogDebug(@"params JS = %@",paramsJS);
    }
    
    self.pageParams = nil;
    
    return [super webViewDidFinishLoad:theWebView];
}

/* Comment out the block below to over-ride */

/*

- (void) webViewDidStartLoad:(UIWebView*)theWebView
{
    return [super webViewDidStartLoad:theWebView];
}

- (void) webView:(UIWebView*)theWebView didFailLoadWithError:(NSError*)error
{
    return [super webView:theWebView didFailLoadWithError:error];
}

- (BOOL) webView:(UIWebView*)theWebView shouldStartLoadWithRequest:(NSURLRequest*)request navigationType:(UIWebViewNavigationType)navigationType
{
    return [super webView:theWebView shouldStartLoadWithRequest:request navigationType:navigationType];
}
*/

@end

@implementation PTWindowPageCommandDelegate

/* To override the methods, uncomment the line in the init function(s)
   in PTWindowPage.m
 */

#pragma mark CDVCommandDelegate implementation

- (id)getCommandInstance:(NSString*)className
{
    return [super getCommandInstance:className];
}

- (NSString*)pathForResource:(NSString*)resourcepath
{
//    NSString *rootWWWPath = [NSString stringWithFormat:@"%@/Documents/release", NSHomeDirectory()];
//    NSString *htmlPath = [NSString stringWithFormat:@"%@/%@", rootWWWPath, resourcepath];
    // Pastry 修改
    // 设置永远找不到 本地加载html的路径；从而使用加密的html路径
    
    switch ([[PTDeveloperManager getInstance] getHTMLSourceType]) {
        case PTHTML_DataZip:
        {
            // 使用 加密的 方式打开网页
            return nil;
        }
            break;
        case PTHTML_WWW:
        {
            // 使用 本地 www 方式打开网页
            return [super pathForResource:resourcepath];
        }
            break;
        case PTHTML_RemoteWWW:
        {
            return nil;
        }
            break;
        default:
        {
            return nil;
        }
            break;
    }
}

/** Pastry 修改
 * 读取本地的加密html文件
 */
- (NSDictionary*)loadEncryptHtml:(NSString*)resourcepath{
    
    NSString *releasePath = nil;
    NSString *htmlPath = nil;
    
    switch ([[PTDeveloperManager getInstance] getHTMLSourceType]) {
        case PTHTML_DataZip:
        {
            releasePath = [PTPathManager getPath:PTPath_Release_Directory];
            htmlPath = [NSString stringWithFormat:@"%@/%@", releasePath, resourcepath];
        }
            break;
        case PTHTML_WWW:
        {
            releasePath = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:@"www"];
            htmlPath = [NSString stringWithFormat:@"%@/%@", releasePath, resourcepath];
        }
            break;
        case PTHTML_RemoteWWW:
        {
            return nil;
        }
            break;
        default:
        {
            return nil;
        }
            break;
    }
    
    NSString *htmlStr = nil;
    
    NSString *errorHtmlStr = @"<!DOCTYPE html>"
    "<html>"
    "<head>"
    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=Utf-8\"/>"
    "<meta http-equiv=\"Content-Security-Policy\" content=\"default-src 'self' data: gap: https://ssl.gstatic.com 'unsafe-eval' 'unsafe-inline';style-src 'self' 'unsafe-inline'; media-src *\">"
    "<meta name=\"format-detection\" content=\"telephone=no\">"
    "<meta name=\"msapplication-tap-highlight\" content=\"no\">"
    "<meta name=\"viewport\" content=\"user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width\">"
    "<script type=\"text/javascript\" src=\"pastry/js/lib/require.js\" data-main=\"pastry/js/main.js\" data-debug></script>"
    "<title>Hello World</title>"
    "</head>"
    "<body>"
    "</body>"
    "</html>"
    "<script type=\"text/template\" id=\"tp_HomePage\" data-component=\"tp_HomePage\" data-title=\"业务A\">"
    "<div>"
    "</div>"
    "</script>"
    "<script type=\"text/javascript\">"
    "function framework_ready(param){"
    "console.log('js ___ = ' + JSON.stringify(param));"
    "var Browser = CBUI.Browser;"
    "var Component = CBUI.Component;"
    "Component.tp_HomePage = Component.Page.extend({"
    "    onSubmit: function (data, submitId) {"
    "        console.log('Home Next1111');"
    "    }"
    "});"
    "Browser.history_goto(\"tp_HomePage\");"
    "};"
    "</script>";
    
    // 以 pt_init_error.html 的页面 ，不进行 url 的 模版 的 解密
    if ([resourcepath hasSuffix:@"pt_init_error.html"]) {
        htmlStr = errorHtmlStr;
    }
    else{
        // 解析加密后的html文件；
        NSData *htmldata = [[PTFramework getInstance] getTemplateByUrl:resourcepath];
        htmlStr = [[NSString alloc] initWithData:htmldata encoding:NSUTF8StringEncoding];
        if ([htmlStr  isEqualToString: @""]) {
            
            htmlStr = errorHtmlStr;
            
            htmlPath = [NSString stringWithFormat:@"%@/%@", releasePath, @"main/null.html"];
        }
    }
    NSDictionary *dic = [[NSDictionary alloc] initWithObjectsAndKeys:htmlStr,@"htmlString",
                         [[NSURL alloc] initWithString:htmlPath],@"baseURL", nil];
    
    return dic;
}

@end

@implementation PTWindowCommandQueue

/* To override, uncomment the line in the init function(s)
   in PTWindowPage.m
 */
- (BOOL)execute:(CDVInvokedUrlCommand*)command
{
    return [super execute:command];
}

@end
