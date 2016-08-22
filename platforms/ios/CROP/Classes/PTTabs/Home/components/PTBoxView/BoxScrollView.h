//
//  BoxScrollView.h
//  DEMO
//
//  Created by 董阳阳 on 16/8/15.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import <UIKit/UIKit.h>


typedef NS_ENUM(NSInteger, BoxScrollViewType) {
    BoxScrollViewTypeTwo = 2,
    BoxScrollViewTypeThree,
    BoxScrollViewTypeFour
};

@interface BoxScrollView : UIView

- (instancetype)initWithFrame:(CGRect)frame withType:(BoxScrollViewType)type withBoxView:(NSArray *)views;

- (void)setRowType:(BoxScrollViewType)type;

- (void)updateAllBoxViews:(NSArray *)views;

- (void)setItemHeight:(CGFloat)height;

@end
