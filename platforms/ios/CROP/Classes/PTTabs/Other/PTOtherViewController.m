//
//  PTOtherViewController.m
//  CROP
//
//  Created by 耿远超 on 16/8/11.
//
//

#import "PTOtherViewController.h"
#import "OtherTableViewCell.h"
#import "PTGestureUnlockViewController.h"

static NSString *idne = @"cell_other";

@interface PTOtherViewController ()<UITableViewDelegate, UITableViewDataSource>


@property (weak, nonatomic) IBOutlet UITableView *otherTableView;




@end

@implementation PTOtherViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    
    [self.otherTableView registerNib:[UINib nibWithNibName:@"OtherTableViewCell" bundle:[NSBundle mainBundle]] forCellReuseIdentifier:idne];
    
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section;
{
    
    return 2;
    
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath;
{
    NSArray *aa = @[@"账号管理", @"手势密码设置"];
    NSArray *aaa = @[@"account", @""];
    
    OtherTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:idne forIndexPath:indexPath];
    [cell updateTitle:aa[indexPath.row] withImageName:aaa[indexPath.row]];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    
    return cell;
    
    
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return 10;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{
    return 1;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row == 1) {

        PTGestureUnlockViewController *gestureVc = [[PTGestureUnlockViewController alloc] init];
        gestureVc.type = 0;
        [self presentViewController:gestureVc animated:YES completion:nil];

    }
}





@end
