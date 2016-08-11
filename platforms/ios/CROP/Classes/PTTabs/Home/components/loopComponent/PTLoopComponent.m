//
//  PTLoopComponent.m
//  CROP
//
//  Created by 耿远超 on 16/8/11.
//
//

#import "PTLoopComponent.h"

@interface PTLoopComponent() {
    
    // 传递 url ;
    // 类型 type:native|web
    // 地址 location:tab1|tab2|tab3|tab4
    // 参数 params:{}
    // 回调方法 callback:fun()
    PTComponentCallbackBlock _completeBlock;
}
@property (weak, nonatomic) IBOutlet UIView *view;

@end

@implementation PTLoopComponent


// Only override drawRect: if you perform custom drawing.
//// An empty implementation adversely affects performance during animation.
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
- (IBAction)loopItemClick:(id)sender {
    if (_completeBlock) {
        _completeBlock(sender);
    }
}


//
- (void)completion:(void(^)(id object))completeBlock {
    _completeBlock = completeBlock;
}

@end
