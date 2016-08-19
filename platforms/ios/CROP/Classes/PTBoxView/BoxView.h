//
//  BoxView.h
//  DEMO
//
//  Created by 董阳阳 on 16/8/17.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef void(^ClickActionBlock)();

@interface BoxView : UIView

@property (nonatomic, copy)NSString *imgName;
@property (nonatomic, copy)NSString *title;


- (void)addTheClickAction:(ClickActionBlock)clickAction;


@end
