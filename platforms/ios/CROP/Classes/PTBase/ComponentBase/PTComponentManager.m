//
//  PTComponentManager.m
//  HelloWorld
//
//  Created by 耿远超 on 16/7/30.
//
//

#import "PTComponentManager.h"

static NSMutableArray<Class> *PTComponentClasses;
NSArray<Class> *PTGetComponentClasses(void);
NSArray<Class> *PTGetComponentClasses(void)
{
    return PTComponentClasses;
}

/**
 * Register the given class as a bridge module. All modules must be registered
 * prior to the first bridge initialization.
 */
void PTRegisterComponent(Class);
void PTRegisterComponent(Class moduleClass)
{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        PTComponentClasses = [NSMutableArray new];
    });
    
    //        RCTAssert([moduleClass conformsToProtocol:@protocol(PTComponentInterface)],
    //                  @"%@ does not conform to the RCTBridgeModule protocol",
    //                  moduleClass);
    
    // Register module
    [PTComponentClasses addObject:moduleClass];
}

@implementation PTComponentDetail

@end

@implementation PTComponentManager

+ (NSArray *)getComponentClasses {
    return PTGetComponentClasses();
}

@end
