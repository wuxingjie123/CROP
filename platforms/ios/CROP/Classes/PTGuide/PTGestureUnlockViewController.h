//
//  PTGestureUnlockViewController.h
//  CROP
//
//  Created by 耿远超 on 16/8/11.
//
//

#import <UIKit/UIKit.h>
//#import "GestureViewController.h"
typedef enum{
    GestureViewControllerTypeSetting = 1,
    GestureViewControllerTypeLogin
}GestureViewControllerType;

typedef enum{
    buttonTagReset = 1,
    buttonTagManager,
    buttonTagForget
    
}buttonTag;

@interface PTGestureUnlockViewController : PTViewControllerBase<PTGuideInterface>

/**
 *  控制器来源类型
 */
@property (nonatomic, assign) GestureViewControllerType type;

@end
