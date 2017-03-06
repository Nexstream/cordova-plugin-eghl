#import "EGHL.h"
#import "EGHLPayViewController.h"


#pragma mark - "Private" variables

@interface EGHL ()

@property Boolean paymentInProgress;
@property UIViewController *contentViewController;
@property CDVInvokedUrlCommand* command;

@end


#pragma mark - Implementation

@implementation EGHL

@synthesize paymentInProgress;
@synthesize contentViewController;
@synthesize command;


#pragma mark - Plugin API

- (void)makePayment: (CDVInvokedUrlCommand*)command
{
    if(self.paymentInProgress) {
        [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"A payment is already in progress."]
                              callbackId:[command callbackId]];
        return;
    }

    NSDictionary *args = (NSDictionary*) [command argumentAtIndex:0 withDefault:nil andClass:[NSDictionary class]];
    if(args == nil) {
        [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Argument must be an object."]
                              callbackId:[command callbackId]];
        return;
    }

    self.paymentInProgress = YES;
    self.command = command;

    PaymentRequestPARAM *payParams = [[PaymentRequestPARAM alloc] init];
    payParams.Amount =       [args objectForKey:@"Amount"];
    payParams.MerchantName = [args objectForKey:@"MerchantName"];
    payParams.CustEmail =    [args objectForKey:@"CustEmail"];
    payParams.CustName =     [args objectForKey:@"CustName"];
    payParams.ServiceID =    [args objectForKey:@"ServiceID"];
    payParams.Password =     [args objectForKey:@"Password"];
    payParams.CurrencyCode = [args objectForKey:@"CurrencyCode"];
    payParams.PaymentID =    [args objectForKey:@"PaymentID"];
    payParams.OrderNumber =  [args objectForKey:@"OrderNumber"];
    payParams.MerchantReturnURL = [args objectForKey:@"MerchantReturnURL"];
    payParams.MerchantCallBackURL = [args objectForKey:@"MerchantCallBackURL"];
    payParams.CustPhone =    [args objectForKey:@"CustPhone"];
    payParams.LanguageCode = [args objectForKey:@"LanguageCode"];
    payParams.PageTimeout =  [args objectForKey:@"PageTimeout"];
    payParams.PaymentDesc =  [args objectForKey:@"PaymentDesc"];
    payParams.IssuingBank =  [args objectForKey:@"IssuingBank"];
    payParams.PymtMethod =   [args objectForKey:@"PymtMethod"];
    payParams.RealHost =     [args objectForKey:@"RealHost"];

    EGHLPayViewController *payViewController =
        [[EGHLPayViewController alloc] initWithEGHLPlugin:self
                                       andPayment:payParams];
    self.contentViewController = [[UINavigationController alloc] initWithRootViewController:payViewController];
    [self.viewController presentViewController:self.contentViewController
                         animated:YES
                         completion:^(void){}];
}


#pragma mark - Return to JS methods

- (void)endPaymentWithStatus: (PaymentStatus)status
{
    [self dismissContentView];

    // TODO return meaningful value to JS
    switch(status) {
        case PAYMENT_SUCCESSFUL:
            break;
        case PAYMENT_FAILED:
            // [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:errorData]
            //                                 callbackId:[self.command callbackId]];
            break;
        case PAYMENT_CANCELLED:
            break;
    }
}

- (void)endPaymentWithFailureMessage: (NSString*)message
{
    [self dismissContentView];

    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:message]
                          callbackId:[self.command callbackId]];
}

- (void)endPaymentWithCancellation
{
    [self dismissContentView];

    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"cancelled"]
                          callbackId:[self.command callbackId]];
}


#pragma mark - Internal helpers

- (void)dismissContentView
{
    [self.viewController dismissViewControllerAnimated:YES
                         completion:^(void){}];
}

@end
