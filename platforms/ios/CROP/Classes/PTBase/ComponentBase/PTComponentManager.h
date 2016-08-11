//
//  PTComponentManager.h
//  HelloWorld
//
//  Created by 耿远超 on 16/7/30.
//
//

#import <Foundation/Foundation.h>

@interface PTComponentDetail : NSObject

@property   (nonatomic, strong)     NSString        *componentType;
@property   (nonatomic, strong)     NSString        *level;
@property   (nonatomic, strong)     NSString        *componentName;
@property   (nonatomic, strong)     NSString        *viewControllerName;
@property   (nonatomic, strong)     NSString        *nibName;
@property   (nonatomic, strong)     NSString        *bundleName;
@property   (nonatomic, strong)     id              args;

@end

@interface PTComponentManager : NSObject

+ (NSArray *)getComponentClasses;

@end
