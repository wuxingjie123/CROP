//
//  BoxScrollView.m
//  DEMO
//
//  Created by 董阳阳 on 16/8/15.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import "BoxScrollView.h"

@interface BoxScrollView ()



@property (nonatomic, assign)CGFloat itemHeight;

@property (nonatomic, assign)BoxScrollViewType index;


@property (nonatomic, strong)NSArray *boxViews;

@end



@implementation BoxScrollView

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        
    }
    return self;
}


- (instancetype)initWithFrame:(CGRect)frame withType:(BoxScrollViewType)type withBoxView:(NSArray *)views
{
    self = [super initWithFrame:frame];
    if (self) {
        self.index = type;
        self.boxViews = views;
        [self setNeedsLayout];
        
    }
    return self;
}

//从nib中加载视图
- (void)awakeFromNib
{
    [super awakeFromNib];
}



- (void)layoutSubviews
{
    [super layoutSubviews];
    [self createUI];
}

//创建视图
- (void)createUI
{
    for (UIView *view in self.subviews) {
        [view removeFromSuperview];
    }
    self.backgroundColor = [UIColor clearColor];
    UIScrollView *scrollView = [[UIScrollView alloc] initWithFrame:self.bounds];
    scrollView.showsVerticalScrollIndicator = NO;
    [self addSubview:scrollView];
    
    self.index = self.index==0?4:self.index;
    CGFloat width = (self.bounds.size.width-self.index+1)/self.index;
    
    CGFloat height = self.itemHeight < 1?width*0.9:self.itemHeight;
    NSInteger aa = self.boxViews.count/self.index+1;
    scrollView.contentSize = CGSizeMake(self.bounds.size.width, aa*(height+1)-1);
    
    for (int i=0; i<self.boxViews.count; i++) {
        
        NSInteger lo = i%self.index;
        NSInteger dd = i/self.index;
        CGFloat xx = lo*(width+1);
        CGFloat yy = dd*(height+1);
        UIView *view = [[UIView alloc] initWithFrame:CGRectMake(xx, yy, width, height)];
        view.backgroundColor = [UIColor whiteColor];
        UIView *new = self.boxViews[i];
        if (![new isKindOfClass:[UIView class]]) {
            
            new = [[UIView alloc] init];
        }
        new.frame = view.bounds;
        [view addSubview:new];
        [scrollView addSubview:view];
    }

}

- (void)setRowType:(BoxScrollViewType)type
{
    self.index = type;
    if (self.boxViews && self.boxViews.count!=0) {
        
        [self setNeedsLayout];
    }
}


//更新格子视图
- (void)updateAllBoxViews:(NSArray *)views;
{
    self.boxViews = views;
    [self setNeedsLayout];
    
}

- (void)setItemHeight:(CGFloat)height
{
    self.itemHeight = height;
    if (self.boxViews) {
        
        [self setNeedsLayout];
    }
}


@end
