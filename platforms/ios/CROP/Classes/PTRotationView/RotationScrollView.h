//
//  RotationScrollView.h
//  DEMO
//
//  Created by 董阳阳 on 16/8/15.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RotationView.h"

@interface RotationScrollView : UIView

- (instancetype)initWithFrame:(CGRect)frame withViews:(NSArray *)views;

- (void)updateAllRotationViews:(NSArray *)views;


@end
