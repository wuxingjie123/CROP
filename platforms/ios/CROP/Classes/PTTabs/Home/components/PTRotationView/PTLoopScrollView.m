//
//  OnlyScrollView.m
//  DEMO
//
//  Created by 董阳阳 on 16/8/18.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import "PTLoopScrollView.h"
#import "FirstView.h"
#import "SecondView.h"
#import "ThreeView.h"
#import "FourView.h"
#import "FiveView.h"
#import "SixView.h"
#import "BaseView.h"

@interface PTLoopScrollView ()

@property (nonatomic,copy)ClickRotationViewBlock block;
@property (nonatomic, strong)NSArray *datas;

@end

@implementation PTLoopScrollView

- (instancetype)initWithFrame:(CGRect)frame withDatas:(NSArray *)datas
{
    NSArray *views = [self createRotationViews:datas];

    self = [super initWithFrame:frame withViews:views];
    if (self) {
        
    }
    return self;
}


- (NSArray *)createRotationViews:(NSArray *)datas;
{
    self.datas = datas;
    NSMutableArray *aaa = [NSMutableArray arrayWithCapacity:5];
    
    NSArray *allClasses = @[@"FirstView", @"SecondView", @"ThreeView", @"FourView", @"FiveView", @"SixView"];
    
    for (int i=0; i<allClasses.count; i++) {
        
        BaseView *aview = [[[NSBundle mainBundle] loadNibNamed:allClasses[i] owner:self options:nil] lastObject];

        [aaa addObject:aview];
        
        aview.theBlock = ^{
            
            self.block(i);
        };
        
    }
    return aaa;
}

- (void)updateAllDatas:(NSArray *)datas;
{
   NSArray *arr =  [self createRotationViews:datas];
    [self updateAllRotationViews:arr];
}

- (void)updateOneItem:(NSDictionary *)dic atIndex:(NSInteger)index;
{
    NSMutableArray *arr = [NSMutableArray arrayWithArray:self.datas];
    if (index<self.datas.count) {
        
        [arr replaceObjectAtIndex:index withObject:dic];
    }
    
}


- (void)addClickItemActionAtIndex:(ClickRotationViewBlock)block
{
    self.block = block;
}

@end
