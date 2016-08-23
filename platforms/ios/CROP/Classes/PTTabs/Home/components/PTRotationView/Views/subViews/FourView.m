//
//  FourView.m
//  DEMO
//
//  Created by 董阳阳 on 16/8/19.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import "FourView.h"
#import "PTChartView.h"

@implementation FourView

- (void)awakeFromNib
{
    [super awakeFromNib];
    
    PTDataSet *dataSet1 = [[PTDataSet alloc] initWithDataPoints:@[@90, @87, @67, @46, @34] fillColor:[UIColor orangeColor]];
    
    PTDataSet *dataSet2 = [[PTDataSet alloc] initWithDataPoints:@[@60, @57, @87, @76, @64] fillColor:[UIColor grayColor]];
    NSArray *labels = @[@"赵忠祥", @"赵忠祥", @"赵忠祥", @"赵忠祥", @"赵忠祥"];
    PTBarChart *barChart = [[PTBarChart alloc] initWithLabels:labels dataSetLeft:dataSet1 dataSetRight:dataSet2];
    [_chartView loadBarChart:barChart];
    
    
    
}

@end
