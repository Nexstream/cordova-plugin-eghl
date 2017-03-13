package my.com.nexstream.cordovaPlugins;

import com.eghl.sdk.params.PaymentParams;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kuccilim on 07/03/2017.
 */

 /* Mandatory payment parameters list:
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

public class EghlPayment {

    @SerializedName("CurrencyCode")
    private String currencyCode = "";
    @SerializedName("PymtMethod")
    private String paymentMethod = "";
    @SerializedName("TransactionType")
    private String transactionType = "";
    @SerializedName("ServiceID")
    private String serviceId = "";
    @SerializedName("PaymentID")
    private String paymentId = "";
    @SerializedName("OrderNumber")
    private String orderNumber = "";
    @SerializedName("PaymentDesc")
    private String paymentDesc = "";
    @SerializedName("MerchantReturnURL")
    private String merchantReturnUrl = "";
    @SerializedName("Amount")
    private String amount = "";
    @SerializedName("CustIp")
    private String custIp = "";
    @SerializedName("CustName")
    private String custName = "";
    @SerializedName("CustEmail")
    private String custEmail = "";
    @SerializedName("CustPhone")
    private String custPhone = "";
    @SerializedName("B4TaxAmt")
    private String b4TaxAmt = "";
    @SerializedName("TaxAmt")
    private String taxAmt = "";
    @SerializedName("MerchantName")
    private String merchantName = "";
    @SerializedName("CustMac")
    private String custMac = "";
    @SerializedName("MerchantApprovalUrl")
    private String merchantApprovalUrl = "";
    @SerializedName("MerchantUnapprovalURL")
    private String merchantUnapprovalUrl = "";
    @SerializedName("MerchantCallBackURL")
    private String merchantCallbackUrl = "";
    @SerializedName("LanguageCode")
    private String languageCode = "";
    @SerializedName("PageTimeout")
    private String pageTimeout = "";
    @SerializedName("CardHolder")
    private String cardHolder = "";
    @SerializedName("CardNo")
    private String cardNo = "";
    @SerializedName("CardExp")
    private String cardExp = "";
    @SerializedName("CardCvv2")
    private String cardCvv2 = "";
    @SerializedName("IssuingBank")
    private String issuingBank = "";
    @SerializedName("BillAddr")
    private String billAddr = "";
    @SerializedName("BillPostal")
    private String billPostal = "";
    @SerializedName("BillCity")
    private String billCity = "";
    @SerializedName("BillRegion")
    private String billRegion = "";
    @SerializedName("BillCountry")
    private String billCountry = "";
    @SerializedName("ShipAddr")
    private String shipAddr = "";
    @SerializedName("ShipPostal")
    private String shipPostal = "";
    @SerializedName("ShipCity")
    private String shipCity = "";
    @SerializedName("ShipRegion")
    private String shipRegion = "";
    @SerializedName("ShipCountry")
    private String shipCountry = "";
    @SerializedName("SessionId")
    private String sessionId = "";
    @SerializedName("TokenType")
    private String tokenType = "";
    @SerializedName("Token")
    private String token = "";
    @SerializedName("Param6")
    private String param6 = "";
    @SerializedName("Param7")
    private String param7 = "";
    @SerializedName("EppMonth")
    private String eppMonth = "";
    @SerializedName("PromoCode")
    private String promoCode = "";
    @SerializedName("ReqToken")
    private String reqToken = "";
    @SerializedName("PairingToken")
    private String pairingToken = "";
    @SerializedName("ReqVerifier")
    private String reqVerifier = "";
    @SerializedName("PairingVerifier")
    private String pairingVerifier = "";
    @SerializedName("CheckoutResourceURL")
    private String checkoutResourceURL = "";
    @SerializedName("CardID")
    private String cardID = "";
    @SerializedName("PreCheckoutID")
    private String preCheckoutID = "";
    @SerializedName("PaymentTimeout")
    private int paymentTimeout = -1;

    public String getCustIp() {
        return custIp;
    }

    public void setCustIp(String custIp) {
        this.custIp = custIp;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPaymentDesc() {
        return paymentDesc;
    }

    public void setPaymentDesc(String paymentDesc) {
        this.paymentDesc = paymentDesc;
    }

    public String getMerchantReturnUrl() {
        return merchantReturnUrl;
    }

    public void setMerchantReturnUrl(String merchantReturnUrl) {
        this.merchantReturnUrl = merchantReturnUrl;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getB4TaxAmt() {
        return b4TaxAmt;
    }

    public void setB4TaxAmt(String b4TaxAmt) {
        this.b4TaxAmt = b4TaxAmt;
    }

    public String getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(String taxAmt) {
        this.taxAmt = taxAmt;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCustMac() {
        return custMac;
    }

    public void setCustMac(String custMac) {
        this.custMac = custMac;
    }

    public String getMerchantApprovalUrl() {
        return merchantApprovalUrl;
    }

    public void setMerchantApprovalUrl(String merchantApprovalUrl) {
        this.merchantApprovalUrl = merchantApprovalUrl;
    }

    public String getMerchantUnapprovalUrl() {
        return merchantUnapprovalUrl;
    }

    public void setMerchantUnapprovalUrl(String merchantUnapprovalUrl) {
        this.merchantUnapprovalUrl = merchantUnapprovalUrl;
    }

    public String getMerchantCallbackUrl() {
        return merchantCallbackUrl;
    }

    public void setMerchantCallbackUrl(String merchantCallbackUrl) {
        this.merchantCallbackUrl = merchantCallbackUrl;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getPageTimeout() {
        return pageTimeout;
    }

    public void setPageTimeout(String pageTimeout) {
        this.pageTimeout = pageTimeout;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardExp() {
        return cardExp;
    }

    public void setCardExp(String cardExp) {
        this.cardExp = cardExp;
    }

    public String getCardCvv2() {
        return cardCvv2;
    }

    public void setCardCvv2(String cardCvv2) {
        this.cardCvv2 = cardCvv2;
    }

    public String getIssuingBank() {
        return issuingBank;
    }

    public void setIssuingBank(String issuingBank) {
        this.issuingBank = issuingBank;
    }

    public String getBillAddr() {
        return billAddr;
    }

    public void setBillAddr(String billAddr) {
        this.billAddr = billAddr;
    }

    public String getBillPostal() {
        return billPostal;
    }

    public void setBillPostal(String billPostal) {
        this.billPostal = billPostal;
    }

    public String getBillCity() {
        return billCity;
    }

    public void setBillCity(String billCity) {
        this.billCity = billCity;
    }

    public String getBillRegion() {
        return billRegion;
    }

    public void setBillRegion(String billRegion) {
        this.billRegion = billRegion;
    }

    public String getBillCountry() {
        return billCountry;
    }

    public void setBillCountry(String billCountry) {
        this.billCountry = billCountry;
    }

    public String getShipAddr() {
        return shipAddr;
    }

    public void setShipAddr(String shipAddr) {
        this.shipAddr = shipAddr;
    }

    public String getShipPostal() {
        return shipPostal;
    }

    public void setShipPostal(String shipPostal) {
        this.shipPostal = shipPostal;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipRegion() {
        return shipRegion;
    }

    public void setShipRegion(String shipRegion) {
        this.shipRegion = shipRegion;
    }

    public String getShipCountry() {
        return shipCountry;
    }

    public void setShipCountry(String shipCountry) {
        this.shipCountry = shipCountry;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getParam6() {
        return param6;
    }

    public void setParam6(String param6) {
        this.param6 = param6;
    }

    public String getParam7() {
        return param7;
    }

    public void setParam7(String param7) {
        this.param7 = param7;
    }

    public String getEppMonth() {
        return eppMonth;
    }

    public void setEppMonth(String eppMonth) {
        this.eppMonth = eppMonth;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getReqToken() {
        return reqToken;
    }

    public void setReqToken(String reqToken) {
        this.reqToken = reqToken;
    }

    public String getPairingToken() {
        return pairingToken;
    }

    public void setPairingToken(String pairingToken) {
        this.pairingToken = pairingToken;
    }

    public String getReqVerifier() {
        return reqVerifier;
    }

    public void setReqVerifier(String reqVerifier) {
        this.reqVerifier = reqVerifier;
    }

    public String getPairingVerifier() {
        return pairingVerifier;
    }

    public void setPairingVerifier(String pairingVerifier) {
        this.pairingVerifier = pairingVerifier;
    }

    public String getCheckoutResourceURL() {
        return checkoutResourceURL;
    }

    public void setCheckoutResourceURL(String checkoutResourceURL) {
        this.checkoutResourceURL = checkoutResourceURL;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getPreCheckoutID() {
        return preCheckoutID;
    }

    public void setPreCheckoutID(String preCheckoutID) {
        this.preCheckoutID = preCheckoutID;
    }

    public int getPaymentTimeout() {
        return paymentTimeout;
    }

    public void setPaymentTimeout(int paymentTimeout) {
        this.paymentTimeout = paymentTimeout;
    }
}
