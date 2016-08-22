//
//  OnlyBoxView.m
//  DEMO
//
//  Created by 董阳阳 on 16/8/18.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import "OnlyBoxView.h"
#import "BoxView.h"

@interface OnlyBoxView ()

@property (nonatomic,copy)ClickItemViewBlock block;

@property (nonatomic, strong)NSArray *datas;

@end


@implementation OnlyBoxView

- (instancetype)initWithFrame:(CGRect)frame withType:(BoxScrollViewType)type withDatas:(NSArray *)datas
{
    
    NSArray *views = [self createBoxViews:datas];
    self.datas = datas;
    self = [super initWithFrame:frame withType:type withBoxView:views];
    if (self) {
        
        
    }
    
    return self;
}

- (NSArray *)createBoxViews:(NSArray *)datas;
{
    NSMutableArray *aaa = [NSMutableArray arrayWithCapacity:5];
    for (int i=0; i<datas.count; i++) {
        
        BoxView *aview = [[[NSBundle mainBundle] loadNibNamed:@"BoxView" owner:self options:nil] lastObject];
        
        NSDictionary *dic = datas[i];
        aview.title = dic[@"title"];
        [aaa addObject:aview];
        
        
        [aview addTheClickAction:^{
            
            self.block(i);
            
        }];
     }
     return aaa;
}

- (void)updateAllDatas:(NSArray *)datas
{
    self.datas = datas;
    NSArray *arr = [self createBoxViews:datas];
    [self updateAllBoxViews:arr];
}


- (void)addBoxView:(NSDictionary *)dic;
{
    NSMutableArray *arr = [NSMutableArray arrayWithArray:self.datas];
    [arr addObject:dic];
    [self updateAllDatas:arr];

}

- (void)removeBoxViewAtIndex:(NSInteger)index;
{
    NSMutableArray *arr = [NSMutableArray arrayWithArray:self.datas];
    if (index<self.datas.count) {
        
        [arr removeObjectAtIndex:index];
        [self updateAllDatas:arr];
    }
    
    
}

- (void)removeLastBoxView;
{
    NSMutableArray *arr = [NSMutableArray arrayWithArray:self.datas];
    [arr removeLastObject];
    [self updateAllDatas:arr];
}


- (void)addClickItemActionAtIndex:(ClickItemViewBlock)block
{
    self.block = block;
}


@end
