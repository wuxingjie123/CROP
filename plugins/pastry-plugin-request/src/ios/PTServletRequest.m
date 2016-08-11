//
//  PTServletRequest.m
//  CBCordova
//
//  Created by gengych on 16/4/7.
//
//

#import "PTServletRequest.h"

@interface PTServletRequest()
{
    
    NSString *requestID;//servletRequest ID,，请求的唯一标识
    NSString *callback;//JS发送的请求中内嵌的callback字符串
    NSString *scheme;//请求类型  http https client server
    NSURL *url; //JS请求url
    
    NSString *host;
    NSString *path;
    NSDictionary *param;
    NSString *parameterValue;
    
    id data;
    id rawRequest;
    BOOL waiting;
    CGFloat timeout;
    PTJSRequestSchemeFlag schemeFlag;
    
    BOOL abolishable;
    BOOL blockable;
    
}
@end

@implementation PTServletRequest
- (void)dealloc
{
}

+ (PTServletRequest *)PTServletRequest:(NSString *)jsonMessage
{
    NSDictionary *dictionary = [jsonMessage objectFromJSONString];
    PTServletRequest *request = [[PTServletRequest alloc]initPTservletRequest:dictionary];
    return request;
}

+ (PTServletRequest *)PTServletRequestWithDic:(NSDictionary *)dicMessage{
    return [[PTServletRequest alloc] initPTservletRequest:dicMessage];
}

-(PTServletRequest *)initPTservletRequest:(NSDictionary *)dictionary
{
    if (self = [super init]) {
//        NSDictionary *dictionary = [jsonMessage objectFromJSONString];
        if (dictionary != nil && [dictionary count]!=0) {
            NSString *urlString = [dictionary objectForKey:@"url"];
            url = [NSURL URLWithString:urlString];
            data  = [dictionary objectForKey:@"data"];
            host = [self setHost];
            path = [self setPath];
            //            if ([dictionary objectForKey:@"param"] != nil) {
            //                param = [[dictionary objectForKey:@"param"] retain];
            //            }
            parameterValue = [self setParameterValue];
            
            if (url!=nil) {
                NSString *tmpScheme = [url scheme];
                if (tmpScheme == nil || [tmpScheme isEqualToString:@"server"]) {
                    scheme = [NSString stringWithFormat:@"http"];
                }
                else if ([tmpScheme isEqualToString:@"client"]){
                    host = nil;
                    scheme = tmpScheme;
                }
                else
                    scheme = [url scheme];
            }
            
            schemeFlag = [self schemeFlagFromSchemeString:scheme];
            if ([dictionary objectForKey:@"timeout"] != nil && [dictionary objectForKey:@"timeout"] != [NSNull null]) {
                timeout = [[dictionary objectForKey:@"timeout"] floatValue];
            } else {
                timeout = -1;
            }
            if ([dictionary objectForKey:@"waiting"] != nil && [dictionary objectForKey:@"waiting"] != [NSNull null]) {
                waiting = [[dictionary objectForKey:@"waiting"] boolValue];
            } else {
                waiting = false;
            }
            callback = [dictionary objectForKey:@"callback"];
            requestID = [dictionary objectForKey:@"requestId"];
            abolishable = NO;
            blockable = NO;
            if ([dictionary objectForKey:@"block"] != nil && [dictionary objectForKey:@"block"] != [NSNull null]) {
                blockable = [[dictionary objectForKey:@"block"] boolValue];
            }
            if ([dictionary objectForKey:@"cancelable"] != nil && [dictionary objectForKey:@"cancelable"] != [NSNull null]) {
                abolishable = [[dictionary objectForKey:@"cancelable"] boolValue];
            }
        }
    }
    return self;
}

-(PTJSRequestSchemeFlag)schemeFlagFromSchemeString:(NSString*)schemeString
{
    PTJSRequestSchemeFlag tmpFlag = JS_Scheme_Flag_Not_Found;
    if (schemeString != nil && schemeString.length != 0) {
        if ([schemeString isEqualToString:@"client"])
            tmpFlag = JS_Scheme_Flag_Client;
        else if ([schemeString isEqualToString:@"http"])
            tmpFlag = JS_Scheme_Flag_Server;
        else if ([schemeString isEqualToString:@"https"])
            tmpFlag = JS_Scheme_Flag_Server;
    }
    return tmpFlag;
}

- (PTJSRequestSchemeFlag)getSchemeFlag
{
    return schemeFlag;
}

-(NSString *)setHost
{
    NSString *tmpHost = [url host];
    NSNumber *tmpPort = [url port];
    if (tmpHost!=nil) {
        if (tmpPort!=nil) {
            return [NSString stringWithFormat:@"%@:%@",tmpHost,tmpPort];
        }
        else
            return tmpHost;
    }
    return [url host];
}

-(NSString *)setPath
{
    NSString *tmpPath = [url path];
    if (tmpPath!=nil && tmpPath.length>0 && [tmpPath hasPrefix:@"/"]) {
        return [tmpPath substringFromIndex:1];
    }
    else
        return nil;
}

-(NSString *)setParameter
{
    NSString *query = [url query];
    if (query!=nil&&query.length>0) {
        NSRange range = [query rangeOfString:@"="];
        if (range.length>0) {
            return [query substringToIndex:range.location];
        }
    }
    return nil;
}

-(NSString *)setParameterValue
{
    NSString *query = [url query];
    if (query!=nil&&query.length>0) {
        NSRange range = [query rangeOfString:@"="];
        if (range.length>0) {
            return [query substringFromIndex:range.location+range.length];
        }
    }
    return nil;
}

-(NSString *)getPath
{
    return path;
}

-(NSString *)getHost
{
    return host;
}

-(NSDictionary *)getParameter
{
    return param;
}

-(NSString *)getParameterValue
{
    return parameterValue;
}

-(NSString *)getScheme
{
    if (scheme!=nil && scheme.length>0) {
        return scheme;
    }
    return nil;
}

-(NSString *)getUrl
{
    if (url!=nil) {
        if ([url path] != nil) {
            NSString *urlAbsolute = [url absoluteString];
            NSRange range = [urlAbsolute rangeOfString:[url path]];
            if ([[url path] hasPrefix:@"/"]) {
                return [urlAbsolute substringFromIndex:range.location + 1];
            } else {
                return [urlAbsolute substringFromIndex:range.location];
            }
        } else if([url query] != nil){
            return [url query];
        }
    }
    return nil;
}

-(NSString *)getCallBacks
{
    return callback;
}

-(BOOL)getWaiting
{
    //TODO
    return waiting;
}

-(NSString *)getRequestID
{
    return requestID;
}

-(id)getData
{
    return data;
}

-(id)getRawRequest
{
    return rawRequest;
}

-(CGFloat)getWaitingTime
{
    return timeout;
}

- (BOOL)getAbolishable
{
    return abolishable;
}

- (BOOL)getBlockState
{
    return blockable;
}
@end
