//
//  PTMenuComponent.m
//  CROP
//
//  Created by 耿远超 on 16/8/11.
//
//

#import "PTMenuComponent.h"

@interface PTMenuComponent () {
    PTComponentCallbackBlock _completeBlock;
}
@property (weak, nonatomic) IBOutlet UIView *view;

@end

@implementation PTMenuComponent

// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
//- (void)drawRect:(CGRect)rect {
//    // Drawing code
//}

- (instancetype)initWithCoder:(NSCoder *)aDecoder
{
    self = [super initWithCoder:aDecoder];
    if (self) {
        
        self.backgroundColor = [UIColor redColor];
        NSString *className = NSStringFromClass([self class]);
        [[[NSBundle mainBundle] loadNibNamed:className owner:self options:nil] firstObject];
        self.view.frame = CGRectMake(0, 0, self.frame.size.width, self.frame.size.height);
        [self addSubview:self.view];
    }
    
    return self;
}

// button click 方法
- (IBAction)menuClick:(id)sender {
    if (_completeBlock) {
        _completeBlock(sender);
    }
}

//
- (void)completion:(void(^)(id object))completeBlock {
    _completeBlock = completeBlock;
}

@end
