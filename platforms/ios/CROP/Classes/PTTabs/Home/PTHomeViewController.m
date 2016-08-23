//
//  PTHomeViewController.m
//  CROP
//
//  Created by 耿远超 on 16/8/11.
//
//

#import "PTHomeViewController.h"
#import "BoxComponentView.h"
#import "PTLoopScrollView.h"
#import "UIViewController+TabBar.h"

@interface PTHomeViewController ()


@property (weak, nonatomic) IBOutlet BoxComponentView *onlyBoxView;


@property (weak, nonatomic) IBOutlet PTLoopScrollView *scrollView;


@end

@implementation PTHomeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    //加载轮播图
    [self.scrollView updateAllDatas:nil];
//加载九宫格
    NSMutableArray *aaa = [NSMutableArray arrayWithCapacity:5];
    for (int i=0; i<10; i++) {
        
        NSDictionary *dic = @{@"title": @"aaaaaa"};
        [aaa addObject:dic];
    }
    [_onlyBoxView setRowType:BoxScrollViewTypeFour];
    [_onlyBoxView updateAllDatas:aaa];
    [_onlyBoxView addBoxView:@{@"title": @"aaaaaa"}];
    [_onlyBoxView addClickItemActionAtIndex:^(NSInteger index) {
        
         PTLogDebug(@"第%ld个视图",index);
         if (index == 0) {
             
             [self hiddenTabBar];
         }
         if (index == 1) {
             
             [self showTabBar];
         }
     }];
    
}


- (void)didStopAnimation
{
    PTLogDebug(@"动画执行完毕");
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
