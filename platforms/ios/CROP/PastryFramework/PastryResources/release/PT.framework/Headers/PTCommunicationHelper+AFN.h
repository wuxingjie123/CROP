//
//  PTCommunicationHelper+AFN.h
//  PT
//
//  Created by gengych on 16/5/27.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "PTCommunicationHelper.h"

@interface PTCommunicationHelper (AFN)

#pragma mark - 异步操作
+ (void)asynchBusinessRequest_AFN:(NSString *)action
                               json:(NSDictionary *)jsonObject
                          cryptFlag:(PTDataPackageCryptType)cryptFlag
                    timeOutInterval:(CGFloat)timeoutInterval
                          callblock:(void(^)(PTComPackage *comPackage, NSError *error))block;

#pragma mark - 同步操作
+ (PTComPackage *)synchBusinessRequest_AFN:(NSString *)action
                                        json:(NSDictionary *)jsonObject
                                   cryptFlag:(PTDataPackageCryptType)cryptFlag
                             timeOutInterval:(CGFloat)timeoutInterval;

@end
