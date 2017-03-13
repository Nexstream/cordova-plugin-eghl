#import "EGHL.h"
#import "EGHLPayViewController.h"


#pragma mark - "Private" variables

@interface EGHL ()

@property Boolean paymentInProgress;
@property UIViewController *contentViewController;
@property CDVInvokedUrlCommand* command;
@property NSArray *eGHLStringParams;

@end


#pragma mark - Implementation

@implementation EGHL

@synthesize paymentInProgress;
@synthesize contentViewController;
@synthesize command;
@synthesize eGHLStringParams;


#pragma mark - Plugin API

- (void)pluginInitialize
{
    if(self.eGHLStringParams == nil) {
        self.eGHLStringParams = @[
            @"Amount",
            @"PaymentID",
            @"OrderNumber",
            @"MerchantName",
            @"ServiceID",
            @"PymtMethod",
            @"MerchantReturnURL",
            @"CustEmail",
            @"Password",
            @"CustPhone",
            @"CurrencyCode",
            @"CustName",
            @"LanguageCode",
            @"PaymentDesc",
            @"PageTimeout",
            @"CustIP",
            @"MerchantApprovalURL",
            @"CustMAC",
            @"MerchantUnApprovalURL",
            @"CardHolder",
            @"CardNo",
            @"CardExp",
            @"CardCVV2",
            @"BillAddr",
            @"BillPostal",
            @"BillCity",
            @"BillRegion",
            @"BillCountry",
            @"ShipAddr",
            @"ShipPostal",
            @"ShipCity",
            @"ShipRegion",
            @"ShipCountry",
            @"TokenType",
            @"Token",
            @"SessionID",
            @"IssuingBank",
            @"MerchantCallBackURL",
            @"B4TaxAmt",
            @"TaxAmt",
            @"Param6",
            @"Param7"
        ];
    }
}

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

    // Get Staging or production environment
    if ([[[NSBundle mainBundle] objectForInfoDictionaryKey:@"RealHost"] boolValue] == true) {
        payParams.realHost = YES;
    }else{
        payParams.realHost = NO;
    }

    // Set Service Password from plist.
    payParams.Password = [[NSBundle mainBundle] objectForInfoDictionaryKey:@"ServicePassword"];

    for(NSString *paramName in self.eGHLStringParams) {
        NSString *paramValue = [args objectForKey:paramName];
        if(paramValue != nil) {
            [payParams setValue:paramValue forKey:paramName];
        }
    }

    EGHLPayViewController *payViewController =
        [[EGHLPayViewController alloc] initWithEGHLPlugin:self
                                       andPayment:payParams];
    self.contentViewController = [[UINavigationController alloc] initWithRootViewController:payViewController];
    [self.viewController presentViewController:self.contentViewController
                         animated:YES
                         completion:^(void){}];
}


#pragma mark - Return to JS methods

- (void)endPaymentSuccessfullyWithResult: (PaymentRespPARAM*)result
{
    [self dismissContentView];
    self.paymentInProgress = NO;

    // TODO send some fields e.g. TxnID, AuthCode, etc back to JS
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK]
                          callbackId:[self.command callbackId]];
}

- (void)endPaymentWithFailureMessage: (NSString*)message
{
    [self dismissContentView];
    self.paymentInProgress = NO;
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:message]
                          callbackId:[self.command callbackId]];
}

- (void)endPaymentWithCancellation
{
    [self dismissContentView];

    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"cancelled"]
                          callbackId:[self.command callbackId]];
    self.paymentInProgress = NO;
}


#pragma mark - Internal helpers

- (void)dismissContentView
{
    [self.viewController dismissViewControllerAnimated:YES
                         completion:^(void){}];
}

@end
