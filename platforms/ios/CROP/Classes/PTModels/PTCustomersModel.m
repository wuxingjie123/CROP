//
//  PTCustomersModel.m
//  CROP
//
//  Created by 耿远超 on 16/8/11.
//
//

#import "PTCustomersModel.h"

@implementation PTCustomersModel

//主键
+(NSString *)getPrimaryKey
{
    return @"customerId";
}
///复合主键  这个优先级最高
+(NSArray *)getPrimaryKeyUnionArray
{
    return @[@"name",@"MyAge"];
}
//表名
+(NSString *)getTableName
{
    return @"CustomerTable";
}

@end
