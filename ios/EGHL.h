#import <Cordova/CDV.h>
#import "EGHLPayment.h"

@interface EGHL : CDVPlugin <UINavigationControllerDelegate>

- (void)pluginInitialize;

- (void)makePayment: (CDVInvokedUrlCommand*)command;
- (void)mpeRequest: (CDVInvokedUrlCommand*)command;

- (void)endPaymentSuccessfullyWithResult: (PaymentRespPARAM*)result;
- (void)endPaymentWithFailureMessage: (NSString*)message;
- (void)endPaymentWithCancellation;

@end
