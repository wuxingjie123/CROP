//
//  RotationScrollView.m
//  DEMO
//
//  Created by 董阳阳 on 16/8/15.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import "RotationScrollView.h"
#import "RotationView.h"
#define MAXTAG 2000

@interface RotationScrollView ()<UIScrollViewDelegate>
{
    NSArray *allViews;
    UIPageControl *pageControl;
}

@property (nonatomic, strong)NSTimer *timer;

@property (nonatomic, strong)UIScrollView *scrollView;

@property (nonatomic, strong)NSArray *rotationViews;

@end

@implementation RotationScrollView

- (void)dealloc
{
    [self removeNSTimer];
}

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        
    }
    return self;
}

- (instancetype)initWithFrame:(CGRect)frame withViews:(NSArray *)views
{
    self = [super initWithFrame:frame];
    if (self) {
        
        self.rotationViews = views;
    }
    return self;
}

//从nib文件中加载
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
    self.scrollView = [[UIScrollView alloc] initWithFrame:self.bounds];
    self.scrollView.pagingEnabled = YES;
    self.scrollView.showsHorizontalScrollIndicator = NO;
    self.scrollView.bounces = NO;
    self.scrollView.delegate = self;
    [self addSubview:self.scrollView];
    
    CGFloat width = self.bounds.size.width;
    CGFloat height = self.bounds.size.height;
    self.scrollView.contentSize = CGSizeMake(width*allViews.count, height);
    for (int i = 0; i < allViews.count; i++) {
        
        UIView *view = allViews[i];
        if (![view isKindOfClass:[UIView class]]) {
         view = [[UIView alloc] init];
        }
        UIView *aview= [[UIView alloc] initWithFrame:CGRectMake(width*i, 0, width, height)];
        [aview addSubview:view];
        view.frame = aview.bounds;
        [self.scrollView addSubview:aview];

    }
    self.scrollView.contentOffset = CGPointMake(width, 0);
    
    pageControl = [[UIPageControl alloc] initWithFrame:CGRectMake(0, height*.8, width, 10)];
    pageControl.currentPageIndicatorTintColor = [UIColor orangeColor];
    pageControl.pageIndicatorTintColor = [UIColor redColor];
    [self addSubview:pageControl];
    pageControl.numberOfPages = allViews.count-2;
    
    [self addNSTimer];
    
}


- (void)setRotationViews:(NSArray *)rotationViews
{
    if (_rotationViews != rotationViews) {
        
        _rotationViews = rotationViews;
        [self gatherFromArray:rotationViews];
        
        [self setNeedsLayout];
    }
}



#pragma mark - privateMethod
//添加一个头视图  和一个尾视图
- (void)gatherFromArray:(NSArray *)imgs
{
    NSMutableArray *arr = [NSMutableArray arrayWithCapacity:imgs.count+2];
    UIView *firstView = [imgs firstObject];
    UIView *lastView = [imgs lastObject];
    [arr addObject:[self duplicate:lastView]];
    [arr addObjectsFromArray:imgs];
    [arr addObject:[self duplicate:firstView]];
    allViews = arr;
}


//复制视图
- (UIView*)duplicate:(UIView*)view
{
    NSData * tempArchive = [NSKeyedArchiver archivedDataWithRootObject:view];
    return [NSKeyedUnarchiver unarchiveObjectWithData:tempArchive];
}

//定时器事件
- (void)timerAction
{
    
    NSLog(@"aaaaaaaaaaaaaa");
    CGFloat width = self.bounds.size.width;
    CGFloat awith = self.scrollView.contentOffset.x;
    CGFloat myWith = awith == width*(allViews.count-2)?width:awith+width;
    self.scrollView.contentOffset = CGPointMake(myWith, 0);

    CGFloat pageFraction = myWith / width-1;
    pageControl.currentPage = roundf(pageFraction);

}

//移除定时器
- (void)removeNSTimer
{
    [self.timer invalidate];
    self.timer = nil;
}

//添加定时器
- (void)addNSTimer
{
    if (self.timer == nil) {
        
        NSTimer *timer = [NSTimer scheduledTimerWithTimeInterval:3.0 target:self selector:@selector(timerAction) userInfo:nil repeats:YES];
        [[NSRunLoop mainRunLoop]addTimer:timer forMode:NSRunLoopCommonModes];
        self.timer = timer;
    }
    
}



- (void)updateAllRotationViews:(NSArray *)views;
{
    self.rotationViews = views;
}

#pragma mark - UIScrollViewDelegate
- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView
{
    
    CGFloat width = scrollView.bounds.size.width;
    CGFloat pageFraction = scrollView.contentOffset.x / width;
    int index = roundf(pageFraction);

    pageControl.currentPage = index-1;
    if (index == allViews.count-1) {
        pageControl.currentPage = 0;
        scrollView.contentOffset = CGPointMake(width, 0);
    }
    else if (index == 0) {
        
        pageControl.currentPage = allViews.count-3;
        scrollView.contentOffset = CGPointMake(width*(allViews.count-2), 0);
    }
}


//当用户开始拖拽的时候就调用
- (void)scrollViewWillBeginDragging:(UIScrollView *)scrollView
{
    [self removeNSTimer];
}

//当用户停止拖拽的时候调用
- (void)scrollViewDidEndDragging:(UIScrollView *)scrollView willDecelerate:(BOOL)decelerate
{
        
        [self addNSTimer];
    
}





@end
