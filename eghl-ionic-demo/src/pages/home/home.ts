import { Component } from '@angular/core';
import { NavController,AlertController } from 'ionic-angular';

declare var eGHL :any;


@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {


  merchantName:any;
  email:any;
  amount:any;
  customerName:any;
  serviceId:any;
  currency:any;
  token:any;
  tokenType:any;
  transactionType:any;
  paymentMethod:any;
  isProd:any;
  password:any;


  constructor(public navCtrl: NavController,public alertCtrl: AlertController) {
    this.merchantName = "GHL ePayment";
    this.email = "somebody@somesite.com";
    this.amount = "1.00";
    this.customerName = "Somebody";
    this.serviceId = "SIT";
    this.currency = "MYR";
    this.token = "";
    this.tokenType = "";
    this.transactionType = "SALE";
    this.paymentMethod = "ANY";
    this.isProd = false;
    this.password = "sit12345";



  }

   handleSuccess(message) {
     console.log("result "+message);
     let alertBody = {  title: 'Payment Result',  buttons: ['Dismiss']};
     if(message==0){
        alertBody = {
          ...alertBody,
          subTitle: "Payment Success! Result code: "+ message,

        }

     }else{
       alertBody = {
         ...alertBody,
         subTitle: "Payment Failed! Result code: "+ message,
       }


     }

     let alert = this.alertCtrl.create(alertBody);

     alert.present();
  }

  checkout(){
    var paymentGateway = ""
    if(this.isProd){
      paymentGateway = "https://securepay.e-ghl.com/IPG/Payment.aspx";
    }else{
      paymentGateway = "https://test2pay.ghl.com/IPGSG/Payment.aspx";

    }
    let id = "DEMO"+new Date().getTime();
    console.log("merchantName "+this.merchantName);
    console.log("email "+this.email);
    console.log("amount "+this.amount);
    console.log("customerName "+this.customerName);
    console.log("serviceId "+this.serviceId);
    console.log("currency "+this.currency);
    console.log("token "+this.token);
    console.log("tokenType "+this.tokenType);
    console.log("transactionType "+this.transactionType);
    console.log("paymentMethod "+this.paymentMethod);
    console.log("isProd "+this.isProd);
    console.log("password "+this.password);
    console.log("id "+id);
    console.log("paymentGateway  "+paymentGateway);

    eGHL.makePayment({
      CurrencyCode : this.currency,
      PaymentMethod :this.paymentMethod,
      TransactionType : this.transactionType,
      PaymentGateway: paymentGateway, // Payment gateway URL given by eGHL
      ServiceID :this.serviceId, //Merchant Code or Service ID given by eGHL
      Password: this.password, // Merchant password given by eGHL
      PaymentID : id, // Unique string for each payment
      OrderNumber : id, // Order number to refer current payment, can duplicate.
      PaymentDesc : "eGHL Payment testing",
      MerchantReturnURL :  "SDK", // redirect when payment complete.
      Amount : this.amount, // e.g. 1000.00 for IDR
                    // Invalid format: 1,000.00 or 1000
      CustName : this.customerName,
      CustEmail :  this.email,
      CustPhone : "09123456789",
      MerchantName : this.merchantName,
      CustMAC : "",
      // MerchantApprovalURL : "https://*",
      // MerchantUnApprovalURL : "https://*",
      MerchantCallBackURL : "SDK", // server callback url
      LanguageCode : "EN",
      PageTimeout : "780", // timeout in seconds
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
      TokenType : this.tokenType,
      Token : this.token,
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
      PaymentTimeout : -1,
      sdkTimeout: 60, // seconds
      // _finaliseMessage: "Optional message for Finalising Payment (iOS Only)",
      // _cancelMessage: "Optional message for Cancelling Payment (iOS Only)"
  },this.handleSuccess.bind(this),function(error){
    console.log("error "+error);

  });

  }

}
