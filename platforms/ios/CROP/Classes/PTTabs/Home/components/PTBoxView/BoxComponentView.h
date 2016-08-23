//
//  OnlyBoxView.h
//  DEMO
//
//  Created by 董阳阳 on 16/8/18.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import "BoxScrollView.h"
typedef void(^ClickItemViewBlock)(NSInteger index);



@interface BoxComponentView : BoxScrollView

- (instancetype)initWithFrame:(CGRect)frame withType:(BoxScrollViewType)type withDatas:(NSArray *)datas;

- (void)updateAllDatas:(NSArray *)datas;

- (void)addBoxView:(NSDictionary *)dic;

- (void)removeBoxViewAtIndex:(NSInteger)index;

- (void)removeLastBoxView;

- (void)addClickItemActionAtIndex:(ClickItemViewBlock)block;

@end
