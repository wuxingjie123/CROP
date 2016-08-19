//
//  OtherTableViewCell.m
//  CROP
//
//  Created by 董阳阳 on 16/8/19.
//
//

#import "OtherTableViewCell.h"

@implementation OtherTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

- (void)updateTitle:(NSString *)title withImageName:(NSString *)imgName
{
    self.titleLeb.text = title;
    self.imgView.image = [UIImage imageNamed:imgName];
}

@end
