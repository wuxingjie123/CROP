//
//  PTADComponent.h
//  CROP
//
//  Created by 耿远超 on 16/8/11.
//
//

#import <UIKit/UIKit.h>

@interface PTADComponent : UIView

@property (weak, nonatomic) IBOutlet UIView *view;

@property (weak, nonatomic) IBOutlet UIImageView *adImage;

#pragma mark 对外方法
- (void)setImageUrl:(NSString *)url;

@end
