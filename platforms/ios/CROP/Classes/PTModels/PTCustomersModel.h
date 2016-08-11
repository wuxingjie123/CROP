//
//  PTCustomersModel.h
//  CROP
//
//  Created by 耿远超 on 16/8/11.
//
//

#import <Foundation/Foundation.h>

@interface PTCustomersModel : NSObject

@property (nonatomic, strong)   NSString    *customerId;

@property (nonatomic, strong)   NSString    *customerName;

@property (nonatomic, strong)   NSString    *sex;

@property (nonatomic, strong)   NSString    *address;

@property (nonatomic, strong)   NSString    *phoneNum;

@property (nonatomic, strong)   NSString    *url;

@property (nonatomic, strong)   NSMutableDictionary     *other;

@end
