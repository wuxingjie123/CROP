//
//  BaseView.h
//  DEMO
//
//  Created by 董阳阳 on 16/8/17.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef void(^ClickOneActionBlock)();

@interface BaseView : UIView


@property (nonatomic,copy)ClickOneActionBlock theBlock;

@end
