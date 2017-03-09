#import <Cordova/CDV.h>
#import "EGHLPayment.h"

@interface EGHL : CDVPlugin

- (void)pluginInitialize;

- (void)makePayment: (CDVInvokedUrlCommand*)command;

- (void)endPaymentSuccessfullyWithResult: (PaymentRespPARAM*)result;
- (void)endPaymentWithFailureMessage: (NSString*)message;
- (void)endPaymentWithCancellation;

@end
