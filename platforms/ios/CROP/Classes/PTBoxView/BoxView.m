//
//  BoxView.m
//  DEMO
//
//  Created by 董阳阳 on 16/8/17.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import "BoxView.h"

@interface BoxView ()


@property (weak, nonatomic) IBOutlet UIButton *imgButton;

@property (weak, nonatomic) IBOutlet UILabel *titleLeb;

@property(nonatomic, copy)ClickActionBlock clickAction;

@end


@implementation BoxView

- (void)awakeFromNib
{
    [super awakeFromNib];
    
    [self layoutIfNeeded];
}

- (void)layoutSubviews
{
    
    if (self.imgName) {
        
        [self.imgButton setImage:[UIImage imageNamed:self.imgName] forState:UIControlStateNormal];
    }
    
    if (self.title) {
        
        self.titleLeb.text = self.title;
    }
    
}


- (IBAction)clickActon:(UIButton *)sender {
    
    self.clickAction();
    
}

- (void)addTheClickAction:(ClickActionBlock)clickAction
{
    self.clickAction = clickAction;
}


@end
