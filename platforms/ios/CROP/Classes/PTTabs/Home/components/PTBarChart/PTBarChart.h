//
//  PTBarChart.h
//  myTestTest
//
//  Created by 董阳阳 on 16/8/22.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "PTDataSet.h"
@interface PTBarChart : NSObject


@property (copy, nonatomic) NSArray *labels;
@property (strong, nonatomic) UIColor *fillColorLeft;
@property (copy, nonatomic) NSMutableArray *dataPointLeft;
@property (strong, nonatomic) UIColor *fillColorRight;
@property (copy, nonatomic) NSMutableArray *dataPointRight;
//@property (assign, nonatomic) BOOL animated;

/**
 *  Initializing the Bar Chart object
 *
 *  @param labels   an NSArray of labels (NSString) to go along with the data
 *  @param dataSets an NSArray of TWRDataSet objects containing the data to be plotted
 *  @param animated a BOOL defining whether the chart should be animated or not
 *
 *  @return an instance of TWRBarChart
 */
- (instancetype)initWithLabels:(NSArray *)labels
                   dataSetLeft:(PTDataSet *)dataSetLeft
                  dataSetRight:(PTDataSet *)dataSetRight;

@end
