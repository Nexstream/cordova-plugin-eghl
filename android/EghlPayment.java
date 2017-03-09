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

    @SerializedName("Amount")
    private String amount;

    @SerializedName("MerchantName")
    private String merchantName = "";

    @SerializedName("CurrencyCode")
    private String currencyCode;

    @SerializedName("LanguageCode")
    private String languageCode = "";

    @SerializedName("PymtMethod")
    private String paymentMethod;

    @SerializedName("PageTimeout")
    private String pageTimeout = "";

    @SerializedName("TransactionType")
    private String transactionType = "";

    @SerializedName("PaymentDesc")
    private String paymentDesc = "";

    @SerializedName("CustName")
    private String custName = "";

    @SerializedName("CustEmail")
    private String custEmail = "";

    @SerializedName("CustPhone")
    private String custPhone = "";

    @SerializedName("PaymentID")
    private String paymentId = "";

    @SerializedName("OrderNumber")
    private String orderNumber = "";

    @SerializedName("IssuingBank")
    private String issuingBank = "";

    @SerializedName("ServiceID")
    private String serviceId = "";

    @SerializedName("MerchantReturnURL")
    private String merchantReturnUrl = "";

    @SerializedName("MerchantCallbackURL")
    private String merchantCallbackUrl = "";

    @SerializedName("TokenType")
    private String tokenType = "";

    @SerializedName("Token")
    private String token = "";

    @SerializedName("Param6")
    private String param6 = "";

    @SerializedName("Param7")
    private String param7 = "";

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPageTimeout() {
        return pageTimeout;
    }

    public void setPageTimeout(String pageTimeout) {
        this.pageTimeout = pageTimeout;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getPaymentDesc() {
        return paymentDesc;
    }

    public void setPaymentDesc(String paymentDesc) {
        this.paymentDesc = paymentDesc;
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

    public String getIssuingBank() {
        return issuingBank;
    }

    public void setIssuingBank(String issuingBank) {
        this.issuingBank = issuingBank;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getMerchantReturnUrl() {
        return merchantReturnUrl;
    }

    public void setMerchantReturnUrl(String merchantReturnUrl) {
        this.merchantReturnUrl = merchantReturnUrl;
    }

    public String getMerchantCallbackUrl() {
        return merchantCallbackUrl;
    }

    public void setMerchantCallbackUrl(String merchantCallbackUrl) {
        this.merchantCallbackUrl = merchantCallbackUrl;
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

}
