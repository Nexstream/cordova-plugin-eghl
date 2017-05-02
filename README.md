Nexstream eGHL Cordova Plugin
=============================

Cordova Library for integrating with the eGHL payment gateway's mobile SDK.


Installation
------------

```
cordova plugin add cordova-plugin-eghl
```


Usage
-----

### Request payment

To request payment via any channel, including Masterpass Express:

```javascript
eGHL.makePayment(
    {
        /*
        * Mandatory payment parameters list:

        * transactionType, paymentMethod, serviceId, paymentId, orderNumber, paymentDesc,
        * merchantReturnUrl, amount, currencyCode, custIp, custName, custEmail, custPhone

        * Check the eGHL documentation for parameter names.

        * [All Field are defined as String], Except paymentTimeout and sdkTimeout.

        * NOTE: Parameter names and spelling follow iOS, wherever the Android and iOS
        * names differ!
        */

        // List of accepted params and some value detail (refer docs for more detail):
        CurrencyCode : "MYR" | ...
        PaymentMethod : "ANY" | "AUTH"
        TransactionType : "SALE"

        PaymentGateway: "https://..." // Payment gateway URL given by eGHL
        ServiceId : "abc" //Merchant Code or Service ID given by eGHL
        Password: "password" // Merchant password given by eGHL

        PaymentId : "P0000001" // Unique string for each payment
        OrderNumber : "P0000001" // Order number to refer current payment, can duplicate.
        PaymentDesc : "eGHL Payment testing"
        MerchantReturnUrl :  "https://*" // redirect when payment complete.
        Amount : "123.10" // e.g. 1000.00 for IDR
                       // Invalid format: 1,000.00 or 1000
        CustIP : "",
        CustName : "Beta Tester"
        CustEmail : "Tester@mail.com"
        CustPhone : "60123456789"
        B4TaxAmt : "",
        TaxAmt : "",
        MerchantName : "ABC Sdn Bhd"
        CustMAC : "",
        MerchantApprovalURL : "https://*"
        MerchantUnApprovalURL : "https://*"
        MerchantCallbackUrl : "https://*" // server callback url
        LanguageCode : "MY" | "EN" | "CN" ...
        PageTimeout : "780" // timeout in seconds
        CardHolder : "",
        CardNo : "",
        CardExp : "",
        CardCvv2 : "",
        IssuingBank : "",
        BillAddr : "",
        BillPostal : "",
        BillCity : "",
        BillRegion : "",
        BillCountry : "",
        ShipAddr : "",
        ShipPostal : "",
        ShipCity : "",
        ShipRegion : "",
        ShipCountry : "",
        SessionId : "",
        TokenType : "",
        Token : "",
        Param6 : "",
        Param7 : "",
        EPPMonth : "",
        PromoCode : "",
        ReqToken : "",
        PairingToken : "",
        ReqVerifier : "",
        PairingVerifier : "",
        CheckoutResourceURL : "",
        CardId : "",
        PreCheckoutId : "",
        PaymentTimeout : -1
        sdkTimeout: 60 // seconds

    },
    function (resp) {
        // Success callback
        // resp is the resultCode from the Android SDK on Android; undefined on
        // iOS.
    },
    function (err) {
        // Failure callback
        // err is either a string or `-999` on iOS;
        // on Android:
        //     1 = Transaction failed
        //     2 = Transaction Pending
        //     -999 = Transaction cancelled
        //     <other-integer> = The resultCode from eGHL's Android SDK, if a
        //          message string was not found.
        //     "an error message" = error message
    }
);
```


### Masterpass Express info

```javascript
eGHL.mpeRequest(
    {
        "PaymentGateway": "https://..." // payment gateway URL provided by eGHL
        "ServiceID": "abc", // merchant ID provided by eGHL
        "Password": "password" // merchant password provided by eGHL
        "CurrencyCode": "MYR",
        "Amount": "123.10",
        "TokenType": "MPE / MSC", // MPE = Masterpass Express, MSC = Masterpass Standard
            // Checkout.
        "Token": "User identifier, for TokenType==MPE only",
        "PaymentDesc": "Payment description...",
    },
    function (resp) {
        // Success callback
        // resp can take one of two forms, based on whether the user was already
        // paired with Masterpass Express or not.
        // 1. Not paired:
        //      {
        //          "ReqToken": "...",
        //          "PairingToken": "..."
        //      }
        // 2. Already paired:
        //      {
        //          "PreCheckoutId": "...",
        //          "Cards": [
        //              {
        //                  "LastFour": "0014",
        //                  "CardId": "358f9812-a99a-49d9-9385- f0a7b67e377c",
        //                  "BrandId": "master",
        //                  "CardAlias": null,
        //                  "ExpiryMonth": 12,
        //                  "SelectedAsDefault": true,
        //                  "BNBUnverified": null,
        //                  "CardHolderName": "OM Testing",
        //                  "ExtensionPoint": null,
        //                  "BillingAddress": {
        //                      "City": "KL",
        //                      "Country": "US",
        //                      "CountrySubdivision": "WA",
        //                      "Line1": "123, street 123",
        //                      "Line2": null,
        //                      "Line3": null,
        //                      "PostalCode": "11222",
        //                      "ExtensionPoint": null
        //                  },
        //                  "BrandName": "MasterCard",
        //                  "ExpiryYear": 2020
        //              },
        //              ...
        //          ]
        //      }
    },
    function (errMsg) {
        // Error callback
        // errMsg: "error string"
    }
);
```

### Special Android notes

Android may kill your Cordova activity while your user is in the eGHL activity.
When this happens, payment results will NOT be sent to the JavaScript callback
which you passed to the other methods (above). Instead, it will be sent to your
app with the "resume" Cordova event.

```javacript
document.addEventListener("resume", function (event) {
    /* Event:
        {
            action: "resume",
            pendingResult: {
                pluginServiceName: "eGHL",
                pluginStatus: "OK", // or "Error", etc.
                result: ... // same result that would have been passed to
                    // your normal callback function.
            }
        }
    */
}, false)
```

See also:

- https://cordova.apache.org/docs/en/latest/guide/platforms/android/index.html#lifecycle-guide
- https://cordova.apache.org/docs/en/latest/guide/platforms/android/plugin.html#launching-other-activities
