Nexstream-eGHL
==============

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

        * transactionType, paymentMethod, serviceId, paymentId, orderNumber, paymentDesc,
        * merchantReturnUrl, amount, currencyCode, custIp, custName, custEmail, custPhone
        * ----------------------------------
        * [All Field are defined as String], Except paymentTimeout.
        */

        // List of accepted params and some value detail (refer docs for more detail):
        currencyCode : "MYR" | ...
        paymentMethod : "ANY" | "AUTH"
        transactionType : "SALE"
        serviceId : "sit" //Merchant Code or Service ID given by eGHL
        paymentId : "P0000001" // Unique string for each payment
        orderNumber : "P0000001" // Order number to refer current payment, can duplicate.
        paymentDesc : "eGHL Payment testing"
        merchantReturnUrl :  "https://*" // redirect when payment complete.
        amount : "123.10" // e.g. 1000.00 for IDR
                       // Invalid format: 1,000.00 or 1000
        custIp : "",
        custName : "Beta Tester"
        custEmail : "Tester@mail.com"
        custPhone : "60123456789"
        b4TaxAmt : "",
        taxAmt : "",
        merchantName : "ABC Sdn Bhd"
        custMac : "",
        merchantApprovalUrl : "https://*"
        merchantUnapprovalUrl : "https://*"
        merchantCallbackUrl : "https://*" // server callback url
        languageCode : "MY" | "EN" | "CN" ...
        pageTimeout : "780" // timeout in seconds
        cardHolder : "",
        cardNo : "",
        cardExp : "",
        cardCvv2 : "",
        issuingBank : "",
        billAddr : "",
        billPostal : "",
        billCity : "",
        billRegion : "",
        billCountry : "",
        shipAddr : "",
        shipPostal : "",
        shipCity : "",
        shipRegion : "",
        shipCountry : "",
        sessionId : "",
        tokenType : "",
        token : "",
        param6 : "",
        param7 : "",
        eppMonth : "",
        promoCode : "",
        reqToken : "",
        pairingToken : "",
        reqVerifier : "",
        pairingVerifier : "",
        checkoutResourceURL : "",
        cardID : "",
        preCheckoutID : "",
        paymentTimeout : -1

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
