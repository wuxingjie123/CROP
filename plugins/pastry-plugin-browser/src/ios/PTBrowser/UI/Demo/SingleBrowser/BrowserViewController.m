//
//  BrowserViewController.m
//  PTCordova
//
//  Created by gengych on 16/3/3.
//
//

#import "BrowserViewController.h"

#import "PTBrowserContainer.h"
#import "PTBrowser.h"

@interface BrowserViewController (){
    /**
     标记 第几次进入页面
     根据js发送的title来进行的判断；
     */
    int enterIndex;
    /**
     标记 是否点击了 返回按钮；
     enterIndex与isGoBack组合完成功能：
     js发送第一次加载title设置为enterindex = 1 isGoBack = NO，不显示返回按钮；
     之后每次加载title enterIndex + 1 ：显示返回按钮；
     返回按钮点击回退：每次回退：enterIndex - 1 isGoBack ＝ YES；显示返回按钮；
     当 enterIndex = 1 isGoBack = YES 时，不显示返回按钮；
     */
    BOOL isGoBack;
    
    /**
     返回按钮的状态 根据 isHome 有两种不同的显示效果；
     */
    BOOL _isHome;
}

@end

@implementation BrowserViewController

PT_REGISTER_COMPONENT(PTComponentType_Native, 解压缩组件集合, 浏览器组件, BrowserViewController, BrowserViewController, , )

- (id)init{
    self = [super init];
    if (self) {
        self.titleStack = [[NSMutableArray alloc] initWithCapacity:5];
        self.BrowserContainer = [[PTBrowserContainer alloc] init];
    }
    
    return self;
}

- (void)dealloc{
    PTLogDebug(@"BrowserViewController dealloc");
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    if (self.isHome) {
        enterIndex = 0;
        isGoBack = false;
    }
    
    // 初始化 browserView
    self.browserView = [[UIView alloc] initWithFrame:CGRectMake(0, 64, self.view.frame.size.width, self.view.frame.size.height - 64)];
//    self.browserView.backgroundColor = [UIColor redColor];
    self.browserView.backgroundColor = [UIColor whiteColor];
    [self.view addSubview:self.browserView];
    
    CGRect webViewFrame = CGRectMake(0, 0, self.browserView.frame.size.width, self.browserView.frame.size.height);
    [self.BrowserContainer setOriginFrame:webViewFrame];
    [self.BrowserContainer setWindowPageFrame:webViewFrame];
    // 添加 PTBrowserContainer 浏览器容器
    [self addSubView:self.BrowserContainer.view toParentView:self.browserView subViewController:self.BrowserContainer toParentViewController:self];
    
    if (self.viewDidCompleteCallBack) {
        self.viewDidCompleteCallBack(@"");
    }
    
    // 初始化 headerView
    self.headerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, 64)];
    self.headerView.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:self.headerView];
    
    // 添加返回 button
    self.returnButton = [UIButton buttonWithType:UIButtonTypeCustom];
    self.returnButton.frame = CGRectMake(8, 5+22, 30, 30);
    [self.returnButton setImage:[UIImage imageNamed:@"browserImages.bundle/Contents/Resources/back_white"] forState:UIControlStateNormal];
    [self.returnButton addTarget:self action:@selector(GoBack:) forControlEvents:UIControlEventTouchUpInside];
    [self.headerView addSubview:self.returnButton];
    self.returnButton.hidden = self.isHome;
    
    
    // 添加 Title
    CGFloat titleW = 160;
    CGFloat kWidth = [UIScreen mainScreen].bounds.size.width;
    self.titleLabel = [[UILabel alloc] initWithFrame:CGRectMake((kWidth-titleW)/2.0, 20+6, titleW, 30)];
    self.titleLabel.textColor = [UIColor whiteColor];
    self.titleLabel.textAlignment = NSTextAlignmentCenter;
    self.titleLabel.font = [UIFont systemFontOfSize:17];
    [self.headerView addSubview:self.titleLabel];
}

/**
 * 将 PTBrowser 控件 添加到 指定的UIView,执行的UIViewController
 * @param       parentView              父UIView
 * @param       parentViewcontroller    父UIViewConroller
 * @return
 */
- (void)addSubView:(UIView*)subView
      toParentView:(UIView*)parentView
 subViewController:(UIViewController*)subViewController
toParentViewController:(UIViewController*)parentViewController
{
    [parentViewController addChildViewController:subViewController];
    //subView.frame = parentView.frame;
    [parentView addSubview:subView];
    [subViewController didMoveToParentViewController:parentViewController];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
 */


#pragma mark - PTBrowser执行返回操作
- (void)GoBack:(UIButton *)button
{
    if (self.isHome) {
        enterIndex--;
        if (enterIndex == 0) {
            self.returnButton.hidden = YES;
        }
        isGoBack = YES;
    }
    //self.returnButton.enabled = false;
//    PTLogDebug(@"goBack enterIndex = %d hidden = %hhd",enterIndex,self.returnButton.hidden);
//    [self.BrowserContainer naviteGoBack];
    PTBrowser *browser = [PTBrowser sharedBrowser];
    [browser nativeBrowserGoBack];
}

- (void)markHome:(BOOL)isHome{
    self.isHome = isHome;
}

#pragma mark - 转屏操作

- (BOOL)shouldAutorotate
{
    return NO;
}

-(UIInterfaceOrientationMask)supportedInterfaceOrientations{
    return UIInterfaceOrientationMaskPortrait;
}


#pragma mark - PTBrowserDelegate

// 这个标识首页 ， 原生 判断 js 页面是否是首页 无法判定,所以接收到此消息，返回按钮就会隐藏；
- (void)nativeMarkHome{
    self.isHome = YES;
    self.returnButton.hidden = YES;
}

- (void)nativeOpenWindow:(NSString *)url
                  params:(id)params
           callbackBlock:(void(^)(id commandDelegate,id result))callbackBlock{
    [self.BrowserContainer containerOpenWindow:url params:params callbackBlock:callbackBlock];
}

- (void)nativeRefreshCurrentWindow:(NSString *)url params:(id)params{
    [self.BrowserContainer containerRefreshCurrentWindowPage:url params:params];
}

- (void)nativeSetBrowserTitle:(NSString *)title{
    //self.returnButton.enabled = true;
    
    [self.titleLabel setText:title];
    if (self.isHome) {
        if (enterIndex == 1) {
            if (isGoBack) {
                self.returnButton.hidden = YES;
            }else{
                self.returnButton.hidden = NO;
            }
        }
        
        if (!isGoBack) {
            enterIndex++;
        }
        
        isGoBack = NO;
    }
}

- (void)registerBeforeWindowTitle{
    if (self.titleLabel.text == nil) {
        self.titleLabel.text = @"";
    }
    
    [self.titleStack addObject:self.titleLabel.text];
}

- (void)unRegisterBeforeWindowTitle{
    NSString *beforeWindowTitle = @"";
    if (self.titleStack != nil && self.titleStack.count != 0) {
        beforeWindowTitle = [self.titleStack objectAtIndex:(self.titleStack.count - 1)];
        [self.titleStack removeObjectAtIndex:(self.titleStack.count - 1)];
    }
    self.titleLabel.text = beforeWindowTitle;
}

- (void)nativeGoBack{
    [self.BrowserContainer containerGoBack];
}

- (void)nativeCloseWindow:(id)params{
    
    [self.BrowserContainer containerCloseWindow:params];
}

- (BOOL)nativeIsCloseBrowser{
    return [self.BrowserContainer containerIsCloseBrowser];
}

- (void)nativeCloseBrowser{
    if (self.isHome) {
        
        self.returnButton.hidden = YES;
        
        
    }else{
        
        //[self.titleLabel setText:notification.object];
        // 执行该页面的回退；
        [self.navigationController popViewControllerAnimated:YES];
        [self dismissViewControllerAnimated:YES completion:^{
            
        }];
    }
}

- (int)passwordStrength:(NSDictionary *)keyStat encryptData:(NSData *)encryptData{
    return 1;
}

@end
