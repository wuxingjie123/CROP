//
//  PTChartView.m
//  myTestTest
//
//  Created by 董阳阳 on 16/8/22.
//  Copyright © 2016年 zhongxinwangke. All rights reserved.
//

#import "PTChartView.h"

@interface PTChartView ()

//圆柱的宽度
@property (nonatomic, assign)CGFloat width;
//原点

@property (nonatomic, assign)CGFloat pointX;
@property (nonatomic, assign)CGFloat pointY;


//间隔
@property (nonatomic, assign)CGFloat interval;

//柱高
@property (nonatomic, assign)CGFloat height;


//比例参数
@property (nonatomic, copy)NSArray *scalesLeft;
@property (nonatomic, copy)NSArray *scalesRight;

//下边人名

@property (nonatomic, copy)NSArray *names;
@property (nonatomic, strong)UIColor *colorLeft;
@property (nonatomic, strong)UIColor *colorRight;

@end


@implementation PTChartView


- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.backgroundColor = [UIColor whiteColor];
        NSLog(@"aaaaaaaaaaaaa");
    }
    return self;
}

- (void)loadBarChart:(PTBarChart *)barChart
{
    
    self.names = barChart.labels.mutableCopy;
    self.scalesLeft = barChart.dataPointLeft.mutableCopy;
    self.scalesRight = barChart.dataPointRight.mutableCopy;
    self.colorLeft = barChart.fillColorLeft;
    self.colorRight = barChart.fillColorRight;
    
    
}


// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    
    //圆柱的宽度
    CGFloat width = 15.0f;
    //原点
    
    CGFloat  yph = 12.0f;
    
    //间隔
    CGFloat mtbr  = 20.0f;
    
    
    CGFloat xph = ([UIScreen mainScreen].bounds.size.width-mtbr*6.0-width*10.0)/2.0f;
    
    //柱高
    CGFloat height = 100.0f;
    
    
    //比例参数

//    NSArray *bons1 = @[@(0.8), @(0.6), @(0.8), @(0.4), @(0.7)];
//    NSArray *bons2 = @[@(0.9), @(0.4), @(0.6), @(0.8), @(0.3)];
    NSArray *bons1 = (self.scalesLeft && self.scalesLeft.count>=5)?self.scalesLeft:@[@(80), @(60), @(56), @(45), @(75)];
    NSArray *bons2 = (self.scalesRight && self.scalesRight.count>=5)?self.scalesRight:@[@(90), @(24), @(54), @(80), @(34)];
    
    //下边人名
    NSArray *names = (self.names && self.names.count>=5)?self.names:@[@"李产权", @"李产权", @"李产权", @"李产权", @"李产权"];
    UIColor *colorLeft = self.colorLeft?self.colorLeft:[UIColor grayColor];
    UIColor *colorRight = self.colorRight?self.colorRight:[UIColor orangeColor];
    
    CGContextRef ctx1=UIGraphicsGetCurrentContext();
    for (int i=0; i<bons1.count; i++) {
        
        NSInteger plat = [bons1[i] integerValue];
        CGFloat bon1 = plat/100.0f;
        
        CGFloat pointX = xph+width*(2*i+.5)+mtbr*(i+1);
        CGFloat pointY1 = yph+(1-bon1)*height;
        CGFloat pointY2 = yph+height;
        CGContextMoveToPoint(ctx1, pointX, pointY1);
        CGContextAddLineToPoint(ctx1, pointX, pointY2);
        
        
        
        NSString *str = [NSString stringWithFormat:@"%ld",plat];
    
        UIFont *font = [UIFont systemFontOfSize:10.0f];
        [str drawInRect:CGRectMake(pointX-width*.5, pointY1-15, width, 12) withAttributes:@{NSFontAttributeName: font, NSForegroundColorAttributeName: colorLeft}];
        
        
        
    }
    
    
//    CGContextMoveToPoint(ctx1, xph+width*.5, yph+top*height);
//    CGContextAddLineToPoint(ctx1, xph+width*.5, yph+height);
//    CGContextMoveToPoint(ctx1, xph+width*2.5+mtbr, yph+top*height);
//    CGContextAddLineToPoint(ctx1, xph+width*2.5+mtbr, yph+height);
//    CGContextMoveToPoint(ctx1, xph+width*4.5+mtbr*2, yph+top*height);
//    CGContextAddLineToPoint(ctx1, xph+width*4.5+mtbr*2, yph+height);
//    CGContextMoveToPoint(ctx1, xph+width*6.5+mtbr*3, yph+top*height);
//    CGContextAddLineToPoint(ctx1, xph+width*6.5+mtbr*3, yph+height);
//    CGContextMoveToPoint(ctx1, xph+width*8.5+mtbr*4, yph+top*height);
//    CGContextAddLineToPoint(ctx1, xph+width*8.5+mtbr*4, yph+height);
    
    [colorLeft set];
    CGContextSetLineWidth(ctx1, width);
    //注意，线条只能画成是空心的
    CGContextStrokePath(ctx1);
    
    
    CGContextRef ctx2=UIGraphicsGetCurrentContext();
    
    for (int i=0; i<bons2.count; i++) {
        
        NSInteger plat = [bons2[i] integerValue];
        CGFloat bon2 = plat/100.0f;
        
        CGFloat pointX = xph+width*(2*i+1.5)+mtbr*(i+1);
        CGFloat pointY1 = yph+(1-bon2)*height;
        CGFloat pointY2 = yph+height;
        
        
        CGContextMoveToPoint(ctx2, pointX, pointY1);
        CGContextAddLineToPoint(ctx2, pointX, pointY2);
        
        
        
        NSString *str = [NSString stringWithFormat:@"%ld",plat];
        
        UIFont *font = [UIFont systemFontOfSize:10.0f];
        [str drawInRect:CGRectMake(pointX-width*.5, pointY1-15, width, 12) withAttributes:@{NSFontAttributeName: font, NSForegroundColorAttributeName: colorRight}];
        
    }
    
//    CGContextMoveToPoint(ctx2, xph+width*1.5, yph+top*height);
//    CGContextAddLineToPoint(ctx2, xph+width*1.5, yph+height);
//    CGContextMoveToPoint(ctx2, xph+width*3.5+mtbr, yph+top*height);
//    CGContextAddLineToPoint(ctx2, xph+width*3.5+mtbr, yph+height);
//    CGContextMoveToPoint(ctx2, xph+width*5.5+mtbr*2, yph+top*height);
//    CGContextAddLineToPoint(ctx2, xph+width*5.5+mtbr*2, yph+height);
//    CGContextMoveToPoint(ctx2, xph+width*7.5+mtbr*3, yph+top*height);
//    CGContextAddLineToPoint(ctx2, xph+width*7.5+mtbr*3, yph+height);
//    CGContextMoveToPoint(ctx2, xph+width*9.5+mtbr*4, yph+top*height);
//    CGContextAddLineToPoint(ctx2, xph+width*9.5+mtbr*4, yph+height);
    CGContextSetLineWidth(ctx2, width);
    [colorRight set];
    //注意，线条只能画成是空心的
    CGContextStrokePath(ctx2);
    
    
    CGContextRef ctx3 = UIGraphicsGetCurrentContext();
    CGContextMoveToPoint(ctx3, xph, yph+height+1);
    CGContextAddLineToPoint(ctx3, xph+mtbr*2+width*10+mtbr*4, yph+height+1);
    CGContextSetLineWidth(ctx3, 2);
    [[UIColor redColor] set];
    CGContextStrokePath(ctx3);
    
    
    UIFont *font = [UIFont systemFontOfSize:12.0f];
    
    for (int i=0; i<names.count; i++) {
        
        NSString *string = names[i];
        [string drawInRect:CGRectMake(xph+mtbr*.9+(width*2.0+mtbr)*i, yph+height+5, width*2.0+mtbr, 20) withAttributes:@{NSFontAttributeName: font}];
    }
    
    
    
    NSLog(@"bbbbbbb");
    
    
    
    
}


@end
