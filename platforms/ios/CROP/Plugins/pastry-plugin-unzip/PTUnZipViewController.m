//
//  PTUnZipViewController.m
//  CBCordova
//
//  Created by gengych on 16/3/24.
//
//

#import "PTUnZipViewController.h"

const BOOL isDisplayView = YES;

@interface PTUnZipViewController () <PTComponentInterface> {
    void(^_completeBlock)(id object);
    BOOL _displayView;
}

@end

@implementation PTUnZipViewController

PT_REGISTER_COMPONENT(PTComponentType_Native, 解压缩组件集合, 解压缩组件, PTUnZipViewController, PTUnZipViewController,,)

- (BOOL)displayView {
    
    return _displayView;
    
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        _displayView = isDisplayView;
    }
    
    return self;
}

- (void)completion:(void(^)(id object))completeBlock {
    
    if ([[PTFramework getInstance] isNeedResources] == PTDataZip_NoLoad) {
        _displayView = NO;
        completeBlock(nil);
        return;
    }
    
    _completeBlock = completeBlock;
    
    // 不支持解压进度展示
    if (!isDisplayView) {
        
        [[PTFramework getInstance] unpackResources];
        
        if (_completeBlock != nil) {
            _completeBlock(nil);
        }
    }
    
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    [[PTFramework getInstance] unpackResources:^(void) {
        
        self.unzipProgressView.hidden = NO;
        self.unzipProgressView.progress = 0;
        self.titleLabel.text = [NSString stringWithFormat:@"(释放资源:%d%@)", 0, @"%"];
        // 注册 资源解压进度 通知
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(displayUnzipProgress:) name:[PTNotificationNameManager getNotificationName:PTNotification_DATAZIP_PROGRESS] object:nil];
        
    } completeBlock:^(BOOL unzipResult) {
        dispatch_sync(dispatch_get_main_queue(), ^{
            
            // 注销 资源解压进度 通知
            [[NSNotificationCenter defaultCenter] removeObserver:self name:[PTNotificationNameManager getNotificationName:PTNotification_DATAZIP_PROGRESS] object:nil];
            
            if (_completeBlock) {
                _completeBlock(nil);
            }
        });
    }];
}

/**
 * 显示 资源解压进度 方法
 */
- (void)displayUnzipProgress:(NSNotification *)notification{
    
    dispatch_sync(dispatch_get_main_queue(), ^{
        NSDictionary *userinfo = [notification userInfo];
        int progress = [((NSNumber*)[userinfo objectForKey:@"percentage"]) intValue];
        self.unzipProgressView.progress = (float)progress / 100;
        self.titleLabel.text = [NSString stringWithFormat:@"(释放资源:%d%@)", progress, @"%"];
    });
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

@end
