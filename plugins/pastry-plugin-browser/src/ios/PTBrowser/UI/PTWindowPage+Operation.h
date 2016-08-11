//
//  PTWindowPage+Operation.h
//  PTCordova
//
//  Created by gengych on 16/3/9.
//
//

#import "PTWindowPage.h"

@interface PTWindowPage (Operation)

#pragma mark - 属性

//@property (strong, nonatomic) UIViewController *webViewController;

//@property (strong, nonatomic) NSString* windowName;



#pragma mark - 本地调用方法

- (void)windowOpenPage:(NSString *)url params:(id)params;

- (void)windowRefreshCurrentPage:(NSString *)url params:(id)params;

- (BOOL)windowGoBack;

/**
 * 转换Url路径；
 */
- (NSString *)transRelativeString:(NSString *)url;

@end
