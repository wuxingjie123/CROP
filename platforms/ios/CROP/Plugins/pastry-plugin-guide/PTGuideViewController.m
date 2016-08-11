//
//  PTGuideViewController.m
//  HelloWorld
//
//  Created by 耿远超 on 16/7/26.
//
//

#import "PTGuideViewController.h"

//尺寸
#define kScreenHeight [UIScreen mainScreen].bounds.size.height
#define kScreenWidth [UIScreen mainScreen].bounds.size.width
#define ONE_PIXEL (1 / [UIScreen mainScreen].scale)//高度为一个像素
#define kFrameGap 15
#define kNavigationBarHeight 64

//字体
#define kFontSize_44 [UIFont systemFontOfSize:22]
#define kFontSize_36 [UIFont systemFontOfSize:18]
#define kFontSize_32 [UIFont systemFontOfSize:16]
#define kFontSize_30 [UIFont systemFontOfSize:15]
#define kFontSize_28 [UIFont systemFontOfSize:14]

#define VIEW_FULL CGRectMake(0, 0, kScreenWidth, kScreenHeight)

@interface PTGuideViewController () <UIScrollViewDelegate, PTComponentInterface> {
    
    void(^_completeBlock)(id object);
    
    UIButton *doneButton;
    
    NSArray *imgNames;
    
    NSArray *bigImgNames;
}

@property (strong, nonatomic)  UIScrollView *scrollView;

@property (strong, nonatomic)  UIPageControl *pageControl;

@end

@implementation PTGuideViewController

PT_REGISTER_COMPONENT(PTComponentType_Native, 引导页面组件集合, 引导页组件, PTGuideViewController, PTGuideViewController,,)

#pragma mark - 设置引导图页面的相关参数
/**
 *  设置 图片的位置，来自 guideImage.bundle
 */
- (void)setImagePath {
    
    imgNames = @[@"guide01", @"guide02", @"guide03"];
    
}

/**
 *  设置 完成 按钮的位置
 */
- (void)setDoneButtonLocation:(UIImageView *) imageview {
    //Done Button
    doneButton = [[UIButton alloc] initWithFrame:CGRectMake(80, kScreenHeight - 135 - 45, kScreenWidth - 160, 45)];
    [doneButton setTintColor:[UIColor whiteColor]];
    [doneButton setTitle:@"" forState:UIControlStateNormal];
    doneButton.titleLabel.font = kFontSize_36;
    doneButton.backgroundColor = [UIColor clearColor];
//    doneButton.layer.borderColor = [UIColor clearColor].CGColor;
    doneButton.layer.borderColor = [UIColor redColor].CGColor;
    [doneButton addTarget:self action:@selector(onFinishedIntroButtonPressed:) forControlEvents:UIControlEventTouchUpInside];
    doneButton.layer.borderWidth = ONE_PIXEL;
    doneButton.layer.cornerRadius = 3.f;
    [imageview addSubview:doneButton];
    imageview.userInteractionEnabled = YES;
}

#pragma  mark - 视图加载
- (void)viewDidLoad {
    [super viewDidLoad];
    
    // 在此处设定引导图片
    [self setImagePath];
    
    [self initView];
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

- (void)initView {
    
    self.view.frame = [[UIScreen mainScreen] bounds];
    self.scrollView = [[UIScrollView alloc] initWithFrame:self.view.frame];
    self.scrollView.pagingEnabled = YES;
    self.scrollView.showsHorizontalScrollIndicator = NO;
    self.scrollView.bounces = NO;
    [self.view addSubview:self.scrollView];
    
    [self createImageView];
    
    self.pageControl = [[UIPageControl alloc] initWithFrame:CGRectMake(0, self.view.frame.size.height * .8, self.view.frame.size.width, 10)];
    
    self.pageControl.currentPageIndicatorTintColor = [UIColor orangeColor];
    
    [self.view addSubview:self.pageControl];
    
    self.pageControl.hidden = YES;
    
    NSInteger pageCount = imgNames.count;
    
    self.pageControl.numberOfPages = pageCount;
    
    self.scrollView.contentSize = CGSizeMake(self.view.frame.size.width * pageCount, self.scrollView.frame.size.height);
    
    CGPoint scrollPoint = CGPointMake(0, 0);
    
    [self.scrollView setContentOffset:scrollPoint animated:YES];
}

- (void)onFinishedIntroButtonPressed:(id)sender {
    
    [[NSUserDefaults standardUserDefaults] setObject:[[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleShortVersionString"] forKey:@"currentVersion"];
    [[NSUserDefaults standardUserDefaults] synchronize];
    
    _completeBlock(nil);
}

- (void)scrollViewDidScroll:(UIScrollView *)scrollView {
    
    CGFloat pageWidth = CGRectGetWidth(self.view.bounds);
    CGFloat pageFraction = self.scrollView.contentOffset.x / pageWidth;
    self.pageControl.currentPage = roundf(pageFraction);
    
}


#pragma mark - createImageView
- (void)createImageView
{
    for (int i = 0; i < imgNames.count; i++) {
        
        NSString *resPath = [[NSBundle mainBundle] resourcePath];
        NSString* path = [resPath stringByAppendingPathComponent:@"guideImage.bundle"];
        NSBundle *bundle = [NSBundle bundleWithPath:path];
        
        UIImageView *imageview = [[UIImageView alloc] initWithFrame:CGRectMake(kScreenWidth*i, 0, kScreenWidth, kScreenHeight)];
        imageview.image = (kScreenHeight == 480) ? [UIImage imageNamed:bigImgNames[i]] : [UIImage imageNamed:imgNames[i] inBundle:bundle compatibleWithTraitCollection:nil];
        self.scrollView.delegate = self;
        [self.scrollView addSubview:imageview];
        
        if (i == imgNames.count - 1) {
            
            [self setDoneButtonLocation:imageview];
        }
        
    }
}

#pragma mark - PTGuideInterface 接口
- (BOOL)displayView {
    
    return NO;
    
//    NSUserDefaults *def = [NSUserDefaults standardUserDefaults];
//    BOOL isUpDated = ![[def objectForKey:@"currentVersion"] isEqualToString:[[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleShortVersionString"]];
//    
//    return isUpDated;
}

- (void)completion:(void(^)(id object))completeBlock {
    if (self.displayView) {
        // 显示 引导页面
        _completeBlock = completeBlock;
    } else {
        // 不显示 引导页面
        completeBlock(nil);
    }
}


@end
