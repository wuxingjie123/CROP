//
//  modelTableView.h
//  mmmtest
//
//  Created by 董阳阳 on 16/8/1.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface PTDisplayComponentTableView : UITableView <UITableViewDataSource, UITableViewDelegate>


@property (nonatomic, strong)NSArray *data;


- (instancetype)initWithOriginFrame:(CGRect)frame style:(UITableViewStyle)style;


@end
