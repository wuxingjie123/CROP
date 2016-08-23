//
//  OnlyScrollView.h
//  DEMO
//
//  Created by 董阳阳 on 16/8/18.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import "RotationScrollView.h"

typedef void(^ClickRotationViewBlock)(NSInteger index);


@interface PTLoopScrollView : RotationScrollView

- (instancetype)initWithFrame:(CGRect)frame withDatas:(NSArray *)datas;

- (void)updateAllDatas:(NSArray *)datas;

- (void)updateOneItem:(NSDictionary *)dic atIndex:(NSInteger)index;

- (void)addClickItemActionAtIndex:(ClickRotationViewBlock)block;



@end
