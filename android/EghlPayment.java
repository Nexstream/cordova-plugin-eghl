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

    @SerializedName("currencyCode")
    private String currencyCode = "";
    @SerializedName("paymentMethod")
    private String paymentMethod = "";
    @SerializedName("transactionType")
    private String transactionType = "";
    @SerializedName("serviceId")
    private String serviceId = "";
    @SerializedName("paymentId")
    private String paymentId = "";
    @SerializedName("orderNumber")
    private String orderNumber = "";
    @SerializedName("paymentDesc")
    private String paymentDesc = "";
    @SerializedName("merchantReturnUrl")
    private String merchantReturnUrl = "";
    @SerializedName("Amount")
    private String amount = "";
    @SerializedName("custIp")
    private String custIp = "";
    @SerializedName("custName")
    private String custName = "";
    @SerializedName("custEmail")
    private String custEmail = "";
    @SerializedName("custPhone")
    private String custPhone = "";
    @SerializedName("b4TaxAmt")
    private String b4TaxAmt = "";
    @SerializedName("taxAmt")
    private String taxAmt = "";
    @SerializedName("merchantName")
    private String merchantName = "";
    @SerializedName("custMac")
    private String custMac = "";
    @SerializedName("merchantApprovalUrl")
    private String merchantApprovalUrl = "";
    @SerializedName("merchantUnapprovalUrl")
    private String merchantUnapprovalUrl = "";
    @SerializedName("merchantCallbackUrl")
    private String merchantCallbackUrl = "";
    @SerializedName("languageCode")
    private String languageCode = "";
    @SerializedName("pageTimeout")
    private String pageTimeout = "";
    @SerializedName("cardHolder")
    private String cardHolder = "";
    @SerializedName("cardNo")
    private String cardNo = "";
    @SerializedName("cardExp")
    private String cardExp = "";
    @SerializedName("cardCvv2")
    private String cardCvv2 = "";
    @SerializedName("issuingBank")
    private String issuingBank = "";
    @SerializedName("billAddr")
    private String billAddr = "";
    @SerializedName("billPostal")
    private String billPostal = "";
    @SerializedName("billCity")
    private String billCity = "";
    @SerializedName("billRegion")
    private String billRegion = "";
    @SerializedName("billCountry")
    private String billCountry = "";
    @SerializedName("shipAddr")
    private String shipAddr = "";
    @SerializedName("shipPostal")
    private String shipPostal = "";
    @SerializedName("shipCity")
    private String shipCity = "";
    @SerializedName("shipRegion")
    private String shipRegion = "";
    @SerializedName("shipCountry")
    private String shipCountry = "";
    @SerializedName("sessionId")
    private String sessionId = "";
    @SerializedName("tokenType")
    private String tokenType = "";
    @SerializedName("token")
    private String token = "";
    @SerializedName("param6")
    private String param6 = "";
    @SerializedName("param7")
    private String param7 = "";
    @SerializedName("eppMonth")
    private String eppMonth = "";
    @SerializedName("promoCode")
    private String promoCode = "";
    @SerializedName("reqToken")
    private String reqToken = "";
    @SerializedName("pairingToken")
    private String pairingToken = "";
    @SerializedName("reqVerifier")
    private String reqVerifier = "";
    @SerializedName("pairingVerifier")
    private String pairingVerifier = "";
    @SerializedName("checkoutResourceURL")
    private String checkoutResourceURL = "";
    @SerializedName("cardID")
    private String cardID = "";
    @SerializedName("preCheckoutID")
    private String preCheckoutID = "";
    @SerializedName("paymentTimeout")
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
