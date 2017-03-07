#import <Cordova/CDV.h>

typedef enum {
    PAYMENT_SUCCESSFUL = 100,
    PAYMENT_FAILED,
    PAYMENT_CANCELLED
} PaymentStatus;

@interface EGHL : CDVPlugin

- (void)pluginInitialize;

- (void)makePayment: (CDVInvokedUrlCommand*)command;

- (void)endPaymentWithStatus: (PaymentStatus)status;
- (void)endPaymentWithFailureMessage: (NSString*)message;
- (void)endPaymentWithCancellation;

@end
