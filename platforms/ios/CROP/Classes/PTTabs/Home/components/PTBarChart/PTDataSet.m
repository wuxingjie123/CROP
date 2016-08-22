//
//  PTDataSet.m
//  myTestTest
//
//  Created by 董阳阳 on 16/8/22.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import "PTDataSet.h"

@implementation PTDataSet

- (instancetype)initWithDataPoints:(NSArray *)dataPoints
                         fillColor:(UIColor *)fillColor;
{
    self = [super init];
    if (self) {
        _dataPoints = dataPoints.mutableCopy;
        _fillColor = fillColor;
    }
    return self;
    
    
}



@end
