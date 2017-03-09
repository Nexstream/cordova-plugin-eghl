/***

Initiates payment UI. Automatically re-queries eGHL for payment status if
necessary (at most one requery before giving up and failing).

***/

#import "EGHLPayViewController.h"
#import <Cordova/CDV.h>

#pragma mark - Constants

#define CANCEL_PAYMENT_TAG 0xBEEF


#pragma mark - "Private" variables

@interface EGHLPayViewController () <UIAlertViewDelegate>

@property PaymentRequestPARAM *paymentParams;
@property EGHLPayment *eghlpay;
@property EGHL *cdvPlugin;

@end


#pragma mark - Implementation

@implementation EGHLPayViewController

@synthesize paymentParams;
@synthesize eghlpay;
@synthesize cdvPlugin;


#pragma mark - Init

- (id)initWithEGHLPlugin: (EGHL*)cdvPlugin andPayment:(PaymentRequestPARAM*)payment;
{
    self = [super init];
    if(self) {
        self.cdvPlugin = cdvPlugin;
        self.paymentParams = payment;
        self.eghlpay = [[EGHLPayment alloc] init];
        self.eghlpay.delegate = self;
    }
    return self;
}


#pragma mark - View Lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];

    // Add cancel button
    UIBarButtonItem *cancelBtn =
        [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel
                                 target:self
                                 action:@selector(cancelPaymentPressed:)];
    [self.navigationItem setRightBarButtonItem:cancelBtn];

    // Add eGHL view
    [self.view addSubview:self.eghlpay];

    // Initiate payment process
    [self.eghlpay EGHLRequestSale:self.paymentParams
                  successBlock:^(NSString *successData) {}
                  failedBlock:^(NSString *errorCode, NSString *errorData) {
                      [self.cdvPlugin endPaymentWithFailureMessage:errorData];
                  } ];
}

- (void)viewDidLayoutSubviews{
    [super viewDidLayoutSubviews];
    self.eghlpay.frame = self.view.frame;
}


#pragma mark - Event Listeners

- (void)cancelPaymentPressed: (id)sender
{
    UIAlertView * alertExit =
        [[UIAlertView alloc] initWithTitle:@"Are you sure?"
                             message:@"Pressing Abort will abandon the payment session."
                             delegate:self
                             cancelButtonTitle:@"Continue with Payment"
                             otherButtonTitles:@"Abort", nil];

    [alertExit setTag:CANCEL_PAYMENT_TAG];
    [alertExit show];
}


#pragma mark - UIAlertView delegate

-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    switch ([alertView tag]) {
        case CANCEL_PAYMENT_TAG:
            if(buttonIndex == 1) {
                [self performRequery];
            }
            break;
    }
}


#pragma mark - Helpers

- (void)processResults: (PaymentRespPARAM*)result withRequery: (BOOL)shouldRequery
{
    if(!result.TxnExists || !result.TxnStatus) {
        // Unexpected nil fields.
        [self.cdvPlugin endPaymentWithFailureMessage:result.TxnMessage];
    } else if([result.TxnExists isEqualToString:@"0"]) {
        // Transaction exists.
        if([result.TxnStatus isEqualToString:@"0"]) {
            // Transaction successful.
            [self.cdvPlugin endPaymentSuccessfullyWithResult:result];
        } else if([result.TxnStatus isEqualToString:@"1"]) {
            // Transaction failed.
            [self.cdvPlugin endPaymentSuccessfullyWithResult:result];
        } else if([result.TxnStatus isEqualToString:@"2"]) {
            if(shouldRequery) {
                // Transaction pending and should requery.
                [self performRequery];
            } else {
                // Transaction pending and should NOT requery. Assume user cancelled.
                [self.cdvPlugin endPaymentWithCancellation];
            }
        } else {
            // Unknown status.
            [self.cdvPlugin endPaymentWithFailureMessage:result.TxnMessage];
        }
    } else if([result.TxnExists isEqualToString:@"1"]) {
        // Transaction not found in eGHL system. Assume user cancelled.
        [self.cdvPlugin endPaymentWithCancellation];
    } else if([result.TxnExists isEqualToString:@"2"]) {
        // Failed to query.
        if(shouldRequery) {
            [self performRequery];
        } else {
            [self.cdvPlugin endPaymentWithFailureMessage:result.TxnMessage];
        }
    } else {
        // Unknown TxnExists value.
        [self.cdvPlugin endPaymentWithFailureMessage:result.TxnMessage];
    }
}

- (void)performRequery
{
    EGHLPayment *queryEghlpay = [[EGHLPayment alloc] init];
    [queryEghlpay EGHLRequestQuery:self.paymentParams
                  successBlock:^(PaymentRespPARAM* result) {
                      [self processResults:result withRequery:NO];
                  }
                  failedBlock:^(NSString *errorCode, NSString *errorData) {
                      [self.cdvPlugin endPaymentWithCancellation];
                  }];
}


#pragma mark - eGHLDelegate

- (void)QueryResult: (PaymentRespPARAM*)result
{
    [self processResults:result withRequery:YES];
}

@end
