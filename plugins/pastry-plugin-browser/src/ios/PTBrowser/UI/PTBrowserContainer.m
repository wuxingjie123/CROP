//
//  PTBrowserContainer.m
//  PTCordova
//
//  Created by gengych on 16/3/3.
//
//

#import "PTBrowserContainer.h"

#import "PTWindowPage+Operation.h"

#import "PTKeyboards.h"

/**
 * 设置 最大window 显示个数
 */
#define MaxWindowCount 3
/**
 * 直接调用js代码，改变WebView大小；
 */
#define DOJS_STRING(webView,JSString) [webView stringByEvaluatingJavaScriptFromString:JSString]

@implementation PTBrowserContainer{
    /**
     * Browser是否关闭的标识
     */
    BOOL    _isCloseBrowser;
    
    /**
     * window的数量
     */
    int     _windowCount;
    
    /**
     * 当前显示window的索引值
     */
    int     _currentVisibleIndex;
    
    /**
     * UIView类型
     */
    NSMutableDictionary *_viewContainerDic;
    
    /**
     * PTWindowPage类型
     */
    NSMutableDictionary *_windowPageDic;
    
    
    // 记录 BrowserContainer 的原始 frame 大小；
    CGRect  _originFrame;
    
    // 记录系统键盘是否显示；YES：系统键盘显示；NO：系统键盘隐藏；
    BOOL systemKeyboardIsShow;
    
    
//    void(^_callbackBlock)(id commandDelegate, id result);
}

- (void)dealloc{
    
    [[NSNotificationCenter defaultCenter]removeObserver:self name:UIKeyboardDidShowNotification object:nil];
    [[NSNotificationCenter defaultCenter]removeObserver:self name:UIKeyboardWillShowNotification object:nil];
    [[NSNotificationCenter defaultCenter]removeObserver:self name:UIKeyboardWillHideNotification object:nil];
}

- (id)init{
    
    _windowCount = MaxWindowCount;
    _isCloseBrowser = YES;
    _currentVisibleIndex = -1;
    
    _viewContainerDic = [[NSMutableDictionary alloc] initWithCapacity:_windowCount];
    _windowPageDic = [[NSMutableDictionary alloc] initWithCapacity:_windowCount];
    
    
    for (int index = 0; index < _windowCount; index++) {
        NSString *key = [NSString stringWithFormat:@"%d",index];
        PTWindowPage *valueWindow = [[PTWindowPage alloc] init];
        [valueWindow setWindowPageName:key];
        
        UIView *valueViewContainer = [[UIView alloc] init];
        
        // 添加 viewContainer 容器
        [_viewContainerDic setObject:valueViewContainer forKey:key];
        
        // 添加 PTWindowPage
        [_windowPageDic setObject:valueWindow forKey:key];
    }
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardDidShow:)
                                                 name:UIKeyboardDidShowNotification
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillShow:)
                                                 name:UIKeyboardWillShowNotification
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillHide:)
                                                 name:UIKeyboardWillHideNotification
                                               object:nil];
    
    
    return self;
}


#pragma mark - SystemKeyboardNotification

- (void)keyboardWillShow:(NSNotification *)notification{
    
    systemKeyboardIsShow = YES;
    PTWindowPage *currentWindowPage = [self getCurrentWindowPage];
    
    if (currentWindowPage == nil) {
        return;
    }
    //打印js可视区域
    CGSize JSInnerSize = CGSizeZero;
    JSInnerSize.width  = [DOJS_STRING(currentWindowPage.webView, @"window.innerWidth") integerValue];
    JSInnerSize.height = [DOJS_STRING(currentWindowPage.webView, @"window.innerHeight") integerValue];
    PTLogDebug(@"keyboard show before frame height = %f", currentWindowPage.webView.frame.size.height);
    PTLogDebug(@"keyboard show before frame x = %f", currentWindowPage.webView.frame.origin.x);
    CGRect rect           = currentWindowPage.webView.frame;
    rect.size             = JSInnerSize;
    CGRect keyboardFrames = [[[notification userInfo] valueForKey:UIKeyboardFrameEndUserInfoKey] CGRectValue];
    rect.size             = CGSizeMake(_originFrame.size.width, _originFrame.size.height - keyboardFrames.size.height);
    //        rect.size = size;
    //    if (keyboardFrames.size.height >= 216 && !CGRectEqualToRect(jsWebView.frame, rect)) {
    //        jsWebView.frame            = originFrame;
//    currentWindowPage.webView.frame            = rect;
    PTLogDebug(@"keyboard show after frame height = %f", currentWindowPage.webView.frame.size.height);
    PTLogDebug(@"keyboard show after frame x = %f", currentWindowPage.webView.frame.origin.x);
}

- (void)keyboardDidShow:(NSNotification *)notification{
    
    PTWindowPage *currentWindowPage = [self getCurrentWindowPage];
    
    if (currentWindowPage == nil) {
        return;
    }
    
    float height    = [DOJS_STRING(currentWindowPage.webView, @"$('.fw-page-current').height()") floatValue];
    float scrollTop = [DOJS_STRING(currentWindowPage.webView, @"$('body')[0].scrollTop") floatValue];
    PTLogDebug(@"js内容高度:%f",height);
    PTLogDebug(@"scrollTop:%f",scrollTop);
    /*JS input光标对齐测试*/
    //    DOJS_STRING(jsWebView, @"setInterval(function(){$('.fw-page-current').toggleClass('fw-force-refresh')},100)");
    //    [jsWebView reload];
    //    [jsWebView reloadInputViews];
}

- (void)keyboardWillHide:(NSNotification *)notification{
    
    systemKeyboardIsShow = NO;
    
    PTWindowPage *currentWindowPage = [self getCurrentWindowPage];
    
    if (currentWindowPage == nil) {
        return;
    }
    PTLogDebug(@"----------_keyboardWillHide----------");
    if(!CGRectEqualToRect(currentWindowPage.webView.frame, _originFrame)){
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW,
                                     (int64_t)(.005*NSEC_PER_SEC)),
                       dispatch_get_main_queue(), ^{
                           if (!systemKeyboardIsShow) {
                               currentWindowPage.webView.frame = _originFrame;
                           }
                       });
    }
    PTLogDebug(@"keyboard info :%@",notification.userInfo);
}


#pragma mark - 本地调用方法

- (void)containerOpenWindow:(NSString*)urlPath
                     params:(id)params
              callbackBlock:(void(^)(id commandDelegate,id result))callbackBlock{
    
    // 设置 window 关闭的回调方法
//    _callbackBlock = callbackBlock;
    
    // 当前显示的 WindowPage
    PTWindowPage *currentWindowPage = [self updateWindowPageState];
    // 设置 window 关闭的回调方法
    [currentWindowPage setOpenWindowCallbackBlock:callbackBlock];
    
    // 下一个将要显示的 WindowPage
    _currentVisibleIndex++;
    
    PTWindowPage *windowPage = [self updateWindowPageState];
    
    if ([urlPath hasPrefix:@"http"] || [urlPath hasPrefix:@"www"]) {//使用PTUIWebview打开
        // 真实的网页路径;不需要转换 url 路径
        
    }
    else{
        // 本地html路径，需要转换url路径；
        PTWindowPage *previousWindowPage = [self getPreviousWindowPage];
        if (previousWindowPage != nil) {
            
            // 使用上一个WindowPage，进行url路径的转换；
            urlPath = [previousWindowPage transRelativeString:urlPath];
        }
    }
    
    [windowPage windowOpenPage:urlPath params:params];
}

- (void)containerRefreshCurrentWindowPage:(NSString*)urlPath params:(id)params{
    PTWindowPage *windowPage =[self getCurrentWindowPage];
    [windowPage windowRefreshCurrentPage:urlPath params:params];
}

- (BOOL)containerIsCloseBrowser{

    return _isCloseBrowser;

}

- (void)containerCloseWindow:(id)params{
    
    // 清空当前显示的 WindowPage 的内容：屏蔽 无网状态下，打开在线网页，然后再进行页面跳转，页面总是显示空白页；
    PTWindowPage *currentWindowPage = [self getCurrentWindowPage];
    [currentWindowPage clear];
    
    _currentVisibleIndex--;
    PTWindowPage *windowPage =[self getCurrentWindowPage];
    
    void(^callbackBlock)(id commandDelegate,id result) = [windowPage getOpenWindowCallbackBlock];
    
    if (callbackBlock && windowPage && windowPage.commandDelegate) {
        // 备注 ： 使用 需要发送 JS 消息的 webview 进行 对 JS 插件的回调，否则回调消息错乱导致失败；
        callbackBlock(windowPage.commandDelegate, params);
    }
    // 清空 WindowPage 的 callbackBlock；
    [windowPage setOpenWindowCallbackBlock:nil];
    
//    if (_callbackBlock && windowPage && windowPage.commandDelegate) {
//        // 备注 ： 使用 需要发送 JS 消息的 webview 进行 对 JS 插件的回调，否则回调消息错乱导致失败；
//        _callbackBlock(windowPage.commandDelegate, params);
//    }
    
    if (_currentVisibleIndex < 0) {
        // 超过 windowCount 最大数
        PTLogDebug(@"索引值为负数!!! 需要关闭浏览器");
        _isCloseBrowser = YES;
        return;
    }
    
    _isCloseBrowser = NO;
    
    for (int index = 0; index < _windowCount; index++) {
        
        NSString *key = [NSString stringWithFormat:@"%d", index];
        if (index == _currentVisibleIndex) {
            // 设置 窗口显示
            ((UIView*)_viewContainerDic[key]).hidden = NO;
        }
        else
        {
            // 设置 窗口不显示
            ((UIView*)_viewContainerDic[key]).hidden = YES;
        }
    }
}

- (BOOL)containerGoBack{
    
    PTWindowPage *currentWindow = [self getCurrentWindowPage];
    
    // 关闭密码键盘；
    [self closeKeyboards:currentWindow];
    
    BOOL isNativeCloseWindow = [currentWindow windowGoBack];
    if (isNativeCloseWindow) {
        [self containerCloseWindow:nil];
        BOOL isNativeCloseBrowser = [self containerIsCloseBrowser];
        if (isNativeCloseBrowser) {
            [[NSNotificationCenter defaultCenter] postNotificationName:@"NotificationCloseBrowser_Innert" object:nil];
            return NO;
        }
        return YES;
    }
    return NO;
}

/**
 * 通过 PTKeyboards 插件，关闭密码键盘；
 */
- (void)closeKeyboards:(PTWindowPage *) windowPage{
    
    NSEnumerator* enumerator = [windowPage.pluginObjects objectEnumerator];
    CDVPlugin* plugin;
    
    while ((plugin = [enumerator nextObject])) {
        if ([plugin isKindOfClass:[PTKeyboards class]]) {
            [((PTKeyboards*)plugin) tapViewToCloseTheKeyboard];
        }
    }
}


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    for (int index = 0; index < _windowCount; index++) {
        
        NSString *key = [NSString stringWithFormat:@"%d", index];
        
        UIView *viewContainer = _viewContainerDic[key];
        viewContainer.frame = self.view.frame;
        [self.view addSubview:viewContainer];
        
        PTWindowPage *windowPage = _windowPageDic[key];
        [self addChildViewController:windowPage];
        windowPage.view.frame = self.view.frame;
        [viewContainer addSubview:windowPage.view];
        [windowPage didMoveToParentViewController:self];
        
        if (key == [NSString stringWithFormat:@"%d",_currentVisibleIndex]) {//window%
            
            ((UIView*)_viewContainerDic[key]).hidden = NO;
            
        }else{
            
            ((UIView*)_viewContainerDic[key]).hidden = YES;
            
        }
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (PTWindowPage *)getCurrentWindowPage{
    
    PTWindowPage *returnResult = _windowPageDic[[NSString stringWithFormat:@"%d", _currentVisibleIndex]];
    
    if (returnResult == nil) {
        PTLogDebug(@"没有可使用的窗口");
    }
    
    return returnResult;
}


#pragma mark - 内部方法
- (void)setWindowPageFrame:(CGRect)frame{
    
    self.view.frame = frame;
    for (int index = 0; index < _windowCount; index++) {
        
        NSString *key = [NSString stringWithFormat:@"%d", index];
        ((UIView*)_viewContainerDic[key]).frame = frame;
        ((PTWindowPage*)_windowPageDic[key]).webView.frame = frame;
    }
}

- (void)setOriginFrame:(CGRect)frame{
    // 记录 BrowserContainer 的 frame 原始大小
    _originFrame = frame;
}

- (CGRect)getOriginFrame{
    return _originFrame;
}

- (PTWindowPage *)updateWindowPageState{
    
    if (_currentVisibleIndex >= _windowCount) {
        // 超过 windowCount 最大数
        return nil;
    }

    for (int index = 0; index < _windowCount; index++) {
        
        NSString *key = [NSString stringWithFormat:@"%d", index];
        if (index == _currentVisibleIndex) {
            // 设置 窗口显示
            ((UIView*)_viewContainerDic[key]).hidden = NO;
        }
        else
        {
            // 设置 窗口不显示
            ((UIView*)_viewContainerDic[key]).hidden = YES;
        }
    }
    
    return _windowPageDic[[NSString stringWithFormat:@"%d", _currentVisibleIndex]];
}

- (PTWindowPage *)getPreviousWindowPage{
    
    if (_currentVisibleIndex == 0) {
        // 说明是第一个 WindowPage ，不需要做url转换；
        return nil;
    }
    
    int previousIndex = _currentVisibleIndex - 1;
    
    return _windowPageDic[[NSString stringWithFormat:@"%d", previousIndex]];
}

/*
 #pragma mark - Navigation
 
 // In a storyboard-based application, you will often want to do a little preparation before navigation
 - (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
 // Get the new view controller using [segue destinationViewController].
 // Pass the selected object to the new view controller.
 }
 */

@end
