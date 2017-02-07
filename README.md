Cs-eGHL
=======

Cordova Library for integrating with the eGHL payment gateway's mobile SDK.


Installation
------------

`cordova plugin add org.cloudsky.cordovaplugins.eghl`

Usage
-----

```javascript
cloudSky.eGHL.makePayment(
    {
        /*
        * Mandatory payment parameters list:
        * ------------------------------
        * "ServiceID","Amount", "MerchantName","PaymentID","OrderNumber","CurrencyCode","LanguageCode",
        * "PymtMethod","PageTimeout","TransactionType","MerchantReturnURL", "PaymentGateway"
        * ====================================================================================
        * Optional payment parameters list:
        * ---------------------------------
        * "TransactionType","PymtMethod","ServiceID","PaymentID","OrderNumber","PaymentDesc",
        * "Amount","CustIP","CustName","CustEmail","CustPhone", "MerchantName",
        * "CurrencyCode","LanguageCode","PageTimeout","PromoCode","B4TaxAmt","TaxAmt","EPPMonth",
        * "CardHolder", "CardNo", "CardExp", "CardCVV2","IssuingBank","TokenType","Token",
        * "MerchantReturnURL", "MerchantCallbackURL", "MerchantApprovalURL","MerchantUnApprovalURL",
        * "Param6", "Param7", "HashValue"
        */


        ServiceID: 123 //Merchant Service ID given by eGHL
        Amount: 123.10 // e.g. 1000.00 for IDR
                             // Invalid format: 1,000.00 or 1000
        MerchantName: "TGV"
        CurrencyCode: "MYR" | ...
        LanguageCode: "MY" | "EN" | "CN" ...
        PymtMethod: "ANY" | "AUTH"
        PageTimeout: "30" // timeout in seconds
        TransactionType: "SALE"
        PaymentDesc: "eGHL Payment testing"

        CustName: "Beta Tester"
        CustEmail: "Tester@mail.com"
        CustPhone: "60123456789"

        MerchantReturnURL: "https://*" // Callback url after successful payment
        PaymentGateway: "https://*" // provided by eGHL

        PaymentID: "STRING" //EGHLAPI().getEGHLRandomID("CNASIT");
        OrderNumber: "STRING" //EGHLAPI().getEGHLRandomID("CNASIT");

    },
    function (resp) {
        // Success callback
        // resp = {
        //     "OK"      0 = Transaction successful ||
        // }
    },
    function (err) {
        // Failure callback
        // err = {
        //     "FAILED"  1 = Transaction failed ||
        //     "Pending" 2 = Transaction Pending
        // }
    }
)
```
