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
                [self.cdvPlugin endPaymentWithCancellation];
            }
            break;
    }
}


#pragma mark - eGHLDelegate

- (void)QueryResult: (PaymentRespPARAM*)result
{
    // TODO
}

@end
