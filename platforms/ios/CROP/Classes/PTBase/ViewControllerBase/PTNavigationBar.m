//
//  PTNavigationBar.m
//  CROP
//
//  Created by 董阳阳 on 16/8/22.
//
//

#import "PTNavigationBar.h"

@implementation PTNavigationBar

- (void)awakeFromNib
{
    [super awakeFromNib];
    
    [self setBarTintColor:[UIColor colorWithRed:217/255.0 green:40/255.0 blue:55/255.0 alpha:1]];
    self.translucent = NO;
}

@end
