//
//  PTWindowPage+Operation.m
//  PTCordova
//
//  Created by gengych on 16/3/9.
//
//

#import "PTWindowPage+Operation.h"
#ifdef PTAppServer
    #import "PTAppServer.h"
#endif

@implementation PTWindowPage (Operation)

//@synthesize windowName;

//- (id)init{
//    self = [super init];
//    PTWindowPage *controller1 = [[PTWindowPage alloc] init];
//    self.webViewController = controller1;
//    
//    return self;
//}

- (void)setLocalBasePath:(NSString *)relativePath
{
    
    switch ([[PTDeveloperManager getInstance] getHTMLSourceType]) {
        case PTHTML_DataZip:
        {
            NSString *absoluteString = [[PTPathManager getPath:PTPath_Release_Directory] stringByAppendingPathComponent:relativePath];//绝对路径
            
            self.urlBasePath = [[absoluteString stringByDeletingLastPathComponent] stringByAppendingString:@"/"];
        }
            break;
        case PTHTML_WWW:
        {
            NSString *absoluteString = [[[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:@"www"] stringByAppendingPathComponent:relativePath];//绝对路径
            
            self.urlBasePath = [[absoluteString stringByDeletingLastPathComponent] stringByAppendingString:@"/"];
        }
            break;
        case PTHTML_RemoteWWW:
            
            break;
        default:
            
            break;
    }
}

#pragma mark - 本地调用方法

- (void)windowOpenPage:(NSString *)url params:(id)params{
    
    if ([url hasPrefix:@"http"] || [url hasPrefix:@"www"]){
        // 真实网络地址，不设置 urlBasePath 路径；
    }
    else{
        // 设置 urlBasePath 路径
        [self setLocalBasePath:url];
    }
    
    [self refreshWebView:url params:params];
}

- (void)windowRefreshCurrentPage:(NSString *)url params:(id)params{
    
    // 刷新页面，将相对url转换为以跟路径为准的相对路径url
    url = [self transRelativeString:url];
    
    [self refreshWebView:url params:params];
}

/**
 *  刷新Webview内容
 */
- (void)refreshWebView:(NSString *)url params:(id)params{
    
    self.pageParams = params;
#ifdef PTAppServer
    //PTWindowPage *viewcontroller = (PTWindowPage*)self.webViewController;
    if ([url hasPrefix:@"http"] || [url hasPrefix:@"www"]) {//使用PTUIWebview打开
        // 真实的网页路径
        self.startPage = url;
    }else{
        if ([[PTAppServer sharedAppServer].baseUrl  isEqualToString: @""]) {
            // 说明不在app内开启一个http服务器
            self.startPage = url;
        }else{
            // 说明在app内开启一个http服务器
            self.startPage = [NSString stringWithFormat:@"%@/%@",[PTAppServer sharedAppServer].baseUrl,url];
        }
    }
    //self = url;
    [self viewDidLoad];
#else
    if ([url hasPrefix:@"http"] || [url hasPrefix:@"www"]) {//使用PTUIWebview打开
        // 真实的网页路径
        self.startPage = url;
    }else{
        self.startPage = url;
    }
    //self = url;
    [self viewDidLoad];
#endif
}

- (BOOL)windowGoBack{
    
    BOOL isCloseWindow = NO;
    
    NSString *url = self.webView.request.URL.absoluteString;
    if ([url hasPrefix:@"http"] || [url hasPrefix:@"www"]) {//使用PTUIWebview打开
        
        if ([self.webView canGoBack]) {
            
            [self.webView goBack];
            
        }else{
            isCloseWindow = YES;
        }
        
    }else if ( [url hasSuffix:@"/pt_init_error.html"])
    {
        isCloseWindow = YES;
        return isCloseWindow;
    }
    else{
        
        [self.commandDelegate evalJs:@"cordova.fireDocumentEvent('browser_back');"];
        
    }
    
    return isCloseWindow;
}

- (NSString *)transRelativeString:(NSString *)url
{
    //    url = @"../test1.html";
    NSString *relativePath = nil;
    NSString *path = nil;
    if ([url hasPrefix:@"/"]) {
        switch ([[PTDeveloperManager getInstance] getHTMLSourceType]) {
            case PTHTML_DataZip:
            {
                path = [[PTPathManager getPath:PTPath_Release_Directory] stringByAppendingPathComponent:url];
            }
                break;
            case PTHTML_WWW:
            {
                path = [[[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:@"www"] stringByAppendingPathComponent:url];
            }
                break;
            case PTHTML_RemoteWWW:
                
                break;
            default:
                
                break;
        }
        
    } else {
        path = [self.urlBasePath stringByAppendingPathComponent:url];
    }
    if (path != nil) {
        NSURL *uP = [NSURL fileURLWithPath:[path substringFromIndex:1]];
        //    PTLogDebug(@"up = %@",[uP absoluteString]);
        NSString *absolutePath = [uP absoluteString];
        
        NSString *releaseAbsolute = nil;
        
        switch ([[PTDeveloperManager getInstance] getHTMLSourceType]) {
            case PTHTML_DataZip:
            {
                releaseAbsolute = [[NSURL fileURLWithPath:[PTPathManager getPath:PTPath_Release_Directory]] absoluteString];
            }
                break;
            case PTHTML_WWW:
            {
                releaseAbsolute = [[NSURL fileURLWithPath:[[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:@"www"]] absoluteString];
            }
                break;
            case PTHTML_RemoteWWW:
                
                break;
            default:
                
                break;
        }
        relativePath = [absolutePath substringFromIndex:releaseAbsolute.length];
        
    }
    return relativePath;
}


@end
