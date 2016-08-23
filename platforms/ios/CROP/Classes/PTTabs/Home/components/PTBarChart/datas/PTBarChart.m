//
//  PTBarChart.m
//  myTestTest
//
//  Created by 董阳阳 on 16/8/22.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import "PTBarChart.h"

@implementation PTBarChart


- (instancetype)initWithLabels:(NSArray *)labels
                   dataSetLeft:(PTDataSet *)dataSetLeft
                  dataSetRight:(PTDataSet *)dataSetRight;
{
    self = [super init];
    if (self) {
        _labels = labels.mutableCopy;
        _dataPointLeft = dataSetLeft.dataPoints.mutableCopy;
        _fillColorLeft = dataSetLeft.fillColor;
        _dataPointRight = dataSetRight.dataPoints.mutableCopy;
        _fillColorRight = dataSetRight.fillColor;
    }
    return self;
}

@end
