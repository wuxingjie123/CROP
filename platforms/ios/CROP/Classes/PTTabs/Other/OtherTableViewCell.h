//
//  OtherTableViewCell.h
//  CROP
//
//  Created by 董阳阳 on 16/8/19.
//
//

#import <UIKit/UIKit.h>

@interface OtherTableViewCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *titleLeb;


@property (weak, nonatomic) IBOutlet UIImageView *imgView;



- (void)updateTitle:(NSString *)title withImageName:(NSString *)imgName;


@end
