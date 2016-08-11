//
//  NXJsonKit.h
//  NextiveJson
//
//  Created by Guy on 14-12-25.
//
//

#import <Foundation/Foundation.h>
#import "CJSONDeserializer.h"
#import "CJSONSerializer.h"

////////////
#pragma mark Serializing methods
////////////

@interface NSString (JSONKitSerializing)
- (NSData *)JSONData;     // Invokes JSONDataWithOptions:JKSerializeOptionNone   includeQuotes:YES
- (NSString *)JSONString; // Invokes JSONStringWithOptions:JKSerializeOptionNone includeQuotes:YES
@end

@interface NSArray (JSONKitSerializing)
- (NSData *)JSONData;
@end

@interface NSDictionary (JSONKitSerializing)
- (NSData *)JSONData;
- (NSString *)JSONString;
@end

@interface NSMutableDictionary (JSONKitSerializing)
- (NSData *)JSONData;
- (NSString *)JSONString;
@end

////////////
#pragma mark Deserializing methods
////////////

@interface NSString (JSONKitDeserializing)
- (id)objectFromJSONString;
@end

@interface NSData (JSONKitDeserializing)
- (id)objectFromJSONData;
@end

