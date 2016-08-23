//
//  PTDataSet.h
//  myTestTest
//
//  Created by 董阳阳 on 16/8/22.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface PTDataSet : NSObject

@property (strong, nonatomic) UIColor *fillColor;
@property (copy, nonatomic) NSMutableArray *dataPoints;



/**
 *  Initializing the Data Set (Line charts only)
 *
 *  @param dataPoints       an array of NSNumber objects representing the data to be plotted
 *  @param fillColor        an array of UIColor objects to go along with the provided data that represent the fill color of the bar / line
 *
 *  @return an instance of TWRDataSet
 */
- (instancetype)initWithDataPoints:(NSArray *)dataPoints
                         fillColor:(UIColor *)fillColor;


@end
