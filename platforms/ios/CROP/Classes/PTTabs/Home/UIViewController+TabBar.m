//
//  UIViewController+TabBar.m
//  CROP
//
//  Created by 董阳阳 on 16/8/22.
//
//

#import "UIViewController+TabBar.h"

@implementation UIViewController (TabBar)


- (void)hiddenTabBar;
{
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:1.0];
    [UIView setAnimationDelegate:self];
    
    
    CGRect frame = self.tabBarController.tabBar.frame;
    frame.origin.y = [UIScreen mainScreen].bounds.size.height;
    self.tabBarController.tabBar.frame = frame;
    
    [UIView commitAnimations];
}


- (void)showTabBar;
{
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:1.0];
    [UIView setAnimationDelegate:self];
    
    
    CGRect frame = self.tabBarController.tabBar.frame;
    frame.origin.y = [UIScreen mainScreen].bounds.size.height-49;
    self.tabBarController.tabBar.frame = frame;
    
    [UIView commitAnimations];
    
}

@end
