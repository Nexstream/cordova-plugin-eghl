#import <UIKit/UIKit.h>
#import "EGHL.h"
#import "EGHLPayment.h"

@interface EGHLPayViewController : UIViewController <eGHLDelegate>

- (id)initWithEGHLPlugin: (EGHL*)cdvPlugin andPayment:(PaymentRequestPARAM*)payment andOtherParams:(NSDictionary*)otherParams;

- (void)QueryResult: (PaymentRespPARAM*)result;

@end
