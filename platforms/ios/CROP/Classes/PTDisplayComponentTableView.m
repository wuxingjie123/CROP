//
//  modelTableView.m
//  mmmtest
//
//  Created by 董阳阳 on 16/8/1.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import "PTDisplayComponentTableView.h"
#import "BrowserViewController.h"


@implementation PTDisplayComponentTableView

- (instancetype)initWithOriginFrame:(CGRect)frame style:(UITableViewStyle)style
{
    if (self = [super initWithFrame:frame style:style]) {
      
        self.delegate = self;
        self.dataSource = self;
        
        NSArray *array = [PTComponentManager getComponentClasses];
        
        NSMutableArray *webResult = [[NSMutableArray alloc] initWithCapacity:0];
        NSMutableArray *nativeResult = [[NSMutableArray alloc] initWithCapacity:0];
        
        for (int index = 0; index < array.count; index++) {
            
            Class component = [array objectAtIndex:index];
            NSString *componentType = [component componentType];
            NSString *componentName = [component componentName];
            NSString *viewControllerName = [component viewControllerName];
            NSString *nibNames = [component nibName];
            NSString *bundleName = [component bundleName];
            NSString *isMain = [component isMain];
            
            if ([isMain isEqualToString:@"YES"]) {
                // 说明是入口组件
            }
            
            if ([componentType isEqualToString:@"PTComponentType_Native"]) {
                // native
                [nativeResult addObject:@{@"interfaceType": @"Native", @"moduleName": componentName, @"viewControllerName": viewControllerName, @"nibName": nibNames, @"bundleName": bundleName}];
                
            } else {
                // web
                [webResult addObject:@{@"interfaceType": @"Web", @"moduleName": componentName, @"viewControllerName": viewControllerName, @"nibName": nibNames, @"bundleName": bundleName}];
                
            }
        }
        
        NSMutableArray *result = [[NSMutableArray alloc] init];
        
        if (nativeResult.count != 0) {
            [result addObject:nativeResult];
        }
        
        if (webResult.count != 0) {
            [result addObject:webResult];
        }
        
        self.data = result;
    }

    return self;
}


#pragma mark - UITableViewDataSource, UITableViewDelegate

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return self.data.count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section;
{
    NSArray *arr = self.data[section];
    return arr.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath;
{
    static NSString *idne = @"cell_new";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:idne];
    if (cell == nil) {
        
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:idne];
    }
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    NSDictionary *dic = self.data[indexPath.section][indexPath.row];
    cell.textLabel.text = dic[@"moduleName"];
    cell.detailTextLabel.text = dic[@"moduleName"];
    return cell;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(20, 5, 0, 20)];
    NSDictionary *dic = self.data[section][0];
    label.text = dic[@"interfaceType"];
    return label;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return 30;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{
    return 1;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSDictionary *dic = self.data[indexPath.section][indexPath.row];
    
    NSString *className = [dic objectForKey:@"viewControllerName"];
    NSString *nibName = [dic objectForKey:@"nibName"];
    NSString *bundleName = [dic objectForKey:@"bundleName"];
    
    [self goToViewController:className nibName:nibName bundleName:bundleName];
    
}

- (UIViewController *)nextViewcontroller
{
    UIView *next = self;
    while (1) {
        if ([next.nextResponder isKindOfClass:[UIViewController class]]) {
            
            break;
            
        }
        else {
            next = [next superview];
        }
    }
    return (UIViewController *)next.nextResponder;
    
}

- (void)goToViewController:(NSString *)className nibName:(NSString *)nibName bundleName:(NSString *)bundleName{
    
    Class class = NSClassFromString(className);
    
    UIViewController *viewController = nil;
    
    if ([className  isEqualToString: @"BrowserViewController"]) {
        
        PTBrowser *browser = [PTBrowser sharedBrowser];
        viewController = [[class alloc] init];
        browser.browserManager = (BrowserViewController*)viewController;
        ((BrowserViewController*)viewController).viewDidCompleteCallBack = ^(id param){
            [browser nativeInitBrowserHome:@"app/index.html" params:nil callbackBlock:nil];
        };
        
    } else {
        
        if (nibName == nil || [nibName  isEqualToString: @""]) {
            viewController = [[class alloc] init];
        } else {
            viewController = [[class alloc] initWithNibName:nibName bundle:nil];
        }
        
    }
    
    viewController.modalTransitionStyle = UIModalTransitionStyleCoverVertical;
    [[self nextViewcontroller] presentViewController:viewController animated:YES completion:^{
        
    }];
}

@end
