'use strict';

var argscheck = require('cordova/argscheck'),
    exec      = require('cordova/exec');

var eGHL = function() {
    var name = "eGHL Cordova Plugin";
    var version = "v0.1"
}

eGHL.prototype = {
    // FUNCTION MAKEPAYMENT
    // params =
    // {
    //     Amount: "123.10" // e.g. 1000.00 for IDR
    //                     // Invalid format: 1,000.00 or 1000
    //     MerchantName: "TGV"
    //     CurrencyCode: "MYR" | ...
    //     LanguageCode: "MY" | "EN" | "CN" ...
    //     PymtMethod: "ANY" | "AUTH"
    //     PageTimeout: "780" // timeout in seconds
    //     TransactionType: "SALE"
    //     PaymentDesc: "eGHL Payment testing"

    //     CustName: "Beta Tester"
    //     CustEmail: "Tester@mail.com"
    //     CustPhone: "60123456789"

    //     PaymentID: "P0000001" // Unique string for each payment
    //     OrderNumber: "B0001231" // Order number to refer current payment, can duplicate.

    //     // Fixed parameters for every payment
    //     ServiceID: "sit" //Merchant Code or Service ID given by eGHL
    //     Password: "sit12345" //Password for Merchant
    //     MerchantReturnURL: "https://*" // Callback url after successful payment
    //     PaymentGateway: "https://*" // provided by eGHL
    //     MerchantCallbackURL: "https://*" // server callback url
    // }
    //
    // Return:
    // - success(successObject) // Successful. See SUCCESS OBJECT.
    // - error(failureObject)  // Failure or pending. See FAILURE OBJECT.
    //
    // Success String
    //   0: "OK"
    //
    //
    // Failure String = "unexpected error string" or
    //   1: "Failed" or
    //   2: "Pending"
    //
    makePayment: function(params, success, error)
    {
        console.log(params, success, error);
        argscheck.checkArgs('ofF', 'eGHL.makePayment', arguments);
        exec(success, error, 'eGHL', 'makePayment', [params]);
    },
};

module.exports = new eGHL();
