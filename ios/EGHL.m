#import "EGHL.h"
#import "EGHLPayViewController.h"
#import <objc/runtime.h>


#pragma mark - "Private" variables

@interface EGHL ()

@property Boolean paymentInProgress;
@property UINavigationController *contentViewController;
@property CDVInvokedUrlCommand* command;
@property NSArray *eGHLStringParams;
@property NSArray *eGHLStringParams_mpeRequest;

@end


#pragma mark - Implementation

@implementation EGHL

@synthesize paymentInProgress;
@synthesize contentViewController;
@synthesize command;
@synthesize eGHLStringParams;
@synthesize eGHLStringParams_mpeRequest;


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
            @"eghlDebugURL",
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

        self.eGHLStringParams_mpeRequest = @[
            @"ServiceID",
            @"CurrencyCode",
            @"Amount",
            @"TokenType",
            @"Token",
            @"PaymentDesc",
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
    NSString *gatewayUrl = [args objectForKey:@"PaymentGateway"];
    if([gatewayUrl isEqualToString:@"https://test2pay.ghl.com/IPGSGOM/Payment.aspx"]) {
        // Special Masterpass development gateway... For TEMPORARY usage only!
        // TMP FIXME Remove when Masterpass is available in the normal
        // staging/production gateways.
        [payParams eghlDebugURL: @"https://test2pay.ghl.com/IPGSGOM/Payment.aspx"];
    } else {
        payParams.realHost = [self isRealHost:gatewayUrl];
    }

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
    self.contentViewController.delegate = self;
    [self.viewController presentViewController:self.contentViewController
                         animated:YES
                         completion:^(void){}];
}

- (void)mpeRequest: (CDVInvokedUrlCommand*)command
{
    NSDictionary *args = (NSDictionary*) [command argumentAtIndex:0 withDefault:nil andClass:[NSDictionary class]];
    if(args == nil) {
        [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Argument must be an object."]
                              callbackId:[command callbackId]];
        return;
    }

    PaymentRequestPARAM *params = [[PaymentRequestPARAM alloc] init];

    // Get Staging or production environment
    NSString *gatewayUrl = [args objectForKey:@"PaymentGateway"];
    if([gatewayUrl isEqualToString:@"https://test2pay.ghl.com/IPGSGOM/Payment.aspx"]) {
        // Special Masterpass development gateway... For TEMPORARY usage only!
        // TMP FIXME Remove when Masterpass is available in the normal
        // staging/production gateways.
        [params eghlDebugURL: @"https://test2pay.ghl.com/IPGSGOM/Payment.aspx"];
    } else {
        params.realHost = [self isRealHost:gatewayUrl];
    }

    // Get other params from command arguments.
    for(NSString *paramName in self.eGHLStringParams_mpeRequest) {
        NSString *paramValue = [args objectForKey:paramName];
        if(paramValue != nil) {
            [params setValue:paramValue forKey:paramName];
        }
    }

    EGHLPayment *req = [[EGHLPayment alloc] init];
    [req eGHLMPERequest:params
         successBlock:^(PaymentRespPARAM *resp) {
             NSDictionary *dict = [self objectAsDictionary:resp];
             [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK
                                                                     messageAsDictionary:dict]
                                   callbackId:[command callbackId]];
         }
         failedBlock:^(NSString *errorCode, NSString *errorData) {
             [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR
                                                                     messageAsString:errorData]
                                   callbackId:[command callbackId]];
         }];
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
    self.paymentInProgress = NO;
    // TMP hard code -999 as cancel payment. (follow android SDK.)
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsInt:-999]
                          callbackId:[self.command callbackId]];
}


#pragma mark - UINavigationControllerDelegate

- (UIInterfaceOrientationMask)navigationControllerSupportedInterfaceOrientations: (UINavigationController*)navigationController;
{
    return UIInterfaceOrientationMaskPortrait;
}

#pragma mark - Internal helpers

- (void)dismissContentView
{
    [self.viewController dismissViewControllerAnimated:YES
                         completion:^(void){}];
}

- (Boolean)isRealHost: (NSString*)gateway
{
    return ![gateway isEqualToString:@"https://test2pay.ghl.com/IPGSG/Payment.aspx"];
}

// Get all non-nil fields in an NSObject and returns them in an NSDictionary.
// http://stackoverflow.com/a/31181746
- (NSDictionary*)objectAsDictionary: (NSObject*)object {
    // Get a list of all properties in the class.
    unsigned int count = 0;
    objc_property_t *properties = class_copyPropertyList([object class], &count);

    NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] initWithCapacity:count];
    for(int i=0; i<count; i++) {
        NSString *key = [NSString stringWithUTF8String:property_getName(properties[i])];
        NSString *value = [object valueForKey:key];

        // Only add to the NSDictionary if it's not nil.
        if (value) [dictionary setObject:value forKey:key];
    }

    free(properties);

    return dictionary;
}

@end
