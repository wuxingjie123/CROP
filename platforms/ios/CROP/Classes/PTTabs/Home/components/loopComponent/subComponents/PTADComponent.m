//
//  PTADComponent.m
//  CROP
//
//  Created by 耿远超 on 16/8/11.
//
//

#import "PTADComponent.h"
#import "UIImageView+WebCache.h"

@implementation PTADComponent

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

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

#pragma mark 对外方法
- (void)setImageUrl:(NSString *)url {
//    self.adImage
    [self.adImage sd_setImageWithURL:[NSURL URLWithString:@"http://www.domain.com/path/to/image.jpg"]
                      placeholderImage:[UIImage imageNamed:@"blue"]];
}

@end
