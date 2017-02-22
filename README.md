Cs-eGHL
=======

Cordova Library for integrating with the eGHL payment gateway's mobile SDK.


Installation
------------

`cordova plugin add my.com.nexstream.cordovaplugins.eghl`

Usage
-----

```javascript
eGHL.makePayment(
    {
        /*
        * Mandatory payment parameters list:
        * ----------------------------------
        * [All Field are defined as String]
        */

        Amount: "123.10" // e.g. 1000.00 for IDR
                       // Invalid format: 1,000.00 or 1000
        MerchantName: "TGV"
        CurrencyCode: "MYR" | ...
        LanguageCode: "MY" | "EN" | "CN" ...
        PymtMethod: "ANY" | "AUTH"
        PageTimeout: "780" // timeout in seconds
        TransactionType: "SALE"
        PaymentDesc: "eGHL Payment testing"

        CustName: "Beta Tester"
        CustEmail: "Tester@mail.com"
        CustPhone: "60123456789"

        PaymentID: "P0000001" // Unique string for each payment
        OrderNumber: "B0001231" // Order number to refer current payment, can duplicate.

        // Fixed parameters for every payment
        ServiceID: "sit" //Merchant Code or Service ID given by eGHL
        Password: "sit12345" //Password for Merchant
        MerchantReturnURL: "https://*" // Callback url after successful payment
        PaymentGateway: "https://*" // provided by eGHL
        MerchantCallbackURL: "https://*" // server callback url
    },
    function (resp) {
        // Success callback
        // resp =
        //     0 = Transaction successful.
        //
    },
    function (err) {
        // Failure callback
        // err =
        //     1 = Transaction failed ||
        //     2 = Transaction Pending
        //
    }
)
```
