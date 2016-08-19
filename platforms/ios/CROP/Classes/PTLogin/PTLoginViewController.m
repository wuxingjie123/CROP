//
//  PTLoginViewController.m
//  CROP
//
//  Created by 耿远超 on 16/8/11.
//
//

#import "PTLoginViewController.h"

@interface PTLoginViewController () {
    PTViewControllerCallbackBlock _completeBlock;
}


@property (weak, nonatomic) IBOutlet UITextField *accountField;




@property (weak, nonatomic) IBOutlet UITextField *passwordField;

@end

@implementation PTLoginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    UIColor *color = [UIColor whiteColor];
    
    self.accountField.attributedPlaceholder = [[NSAttributedString alloc] initWithString:@"填写用户名" attributes:@{NSForegroundColorAttributeName: color}];
    
    self.passwordField.attributedPlaceholder = [[NSAttributedString alloc] initWithString:@"填写密码" attributes:@{NSForegroundColorAttributeName: color}];
    
    
    // Do any additional setup after loading the view from its nib.
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

#pragma mark - PTLoginInterface 接口
- (BOOL)displayView {
    // 检验是否处于登录状态，登录：直接返回 NO，不显示页面；非登录：直接返回YES，显示页面；
    return YES;
}

- (void)completion:(void(^)(id object))completeBlock {
    _completeBlock = completeBlock;
    
    if (self.displayView == NO) {
        // 不显示 引导页面,直接完成
        completeBlock(nil);
    }
//    else {
//        // 显示 引导页面
//    }
}

#pragma mark 按钮点击事件
- (IBAction)loginClick:(id)sender {
    
    if ([self.accountField.text isEqualToString:@"111"]) {
        
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"您输入的账号错误" delegate:nil cancelButtonTitle:@"确定" otherButtonTitles:nil];
        [alert show];
        
        return;
        
    }
    else if ([self.passwordField.text isEqualToString:@"111"])
    {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"您输入的密码错误" delegate:nil cancelButtonTitle:@"确定" otherButtonTitles:nil];
        [alert show];
        return;
    }
    
    
    
    
    if (_completeBlock) {
        _completeBlock(nil);
    }
}

- (IBAction)backKeyboard:(id)sender {
    
    
    [self.accountField resignFirstResponder];
    [self.passwordField resignFirstResponder];
    
}



@end
