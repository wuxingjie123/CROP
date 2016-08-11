//
//  PTGestureUnlockViewController.m
//  CROP
//
//  Created by 耿远超 on 16/8/11.
//
//

#import "PTGestureUnlockViewController.h"
#import "GestureViewController.h"

@interface PTGestureUnlockViewController () {
    
    void(^_completeBlock)(id object);
}

@end

@implementation PTGestureUnlockViewController

- (void)viewDidLoad {
    self.type = GestureViewControllerTypeSetting;
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
//    GestureViewController *gestureVc = [[GestureViewController alloc] init];
//    gestureVc.type = GestureViewControllerTypeSetting;
//    [self.navigationController pushViewController:gestureVc animated:YES];
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

#pragma mark - PTGuideInterface 接口
- (BOOL)displayView {
    
    return NO;
    
    NSUserDefaults *def = [NSUserDefaults standardUserDefaults];
    BOOL isUpDated = ![[def objectForKey:@"currentVersion"] isEqualToString:[[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleShortVersionString"]];
    
    return isUpDated;
}

- (void)completion:(void(^)(id object))completeBlock {
    if (self.displayView) {
        // 显示 引导页面
        _completeBlock = completeBlock;
    } else {
        // 不显示 引导页面
        completeBlock(nil);
    }
}


@end
