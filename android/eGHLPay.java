// This code can only handle one payment at any given moment - no concurrent payments
// are allowed!

package my.com.nexstream.cordovaPlugins;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

import com.eghl.sdk.EGHL;
import com.eghl.sdk.interfaces.MasterpassCallback;
import com.eghl.sdk.params.MasterpassParams;
import com.eghl.sdk.params.PaymentParams;
import com.eghl.sdk.params.Params;
import com.eghl.sdk.payment.PaymentActivity;

import com.google.gson.Gson;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class eGHLPay extends CordovaPlugin {

    // Constants -------------------------------------------------------
    private static final String TAG = "eGHLPay";


    // Indicate payment are in progress --------------------------------
    private static boolean isInProgress = false;

    private CallbackContext cordovaCallbackContext;

    private EGHL eghl;

    private EghlPayment eghlPayParams;

    // Methods ---------------------------------------------------------

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        if (requestCode == EGHL.REQUEST_PAYMENT) {
            isInProgress = false;
            String message = data.getStringExtra(EGHL.TXN_MESSAGE);
            switch (resultCode) {
                case EGHL.TRANSACTION_SUCCESS:
                    Log.d(TAG, "onActivityResult: payment successful");
                    cordovaCallbackContext.success(resultCode);

                    break;
                case EGHL.TRANSACTION_FAILED:
                    if(message == null) {
                        Log.d(TAG, "onActivityResult: payment failure");
                        cordovaCallbackContext.error(resultCode);
                    } else {
                        // Check for "buyer cancelled" string in JS
                        Log.d(TAG, "onActivityResult: payment failure or cancelled '"+message+"'");
                        cordovaCallbackContext.error(message);
                    }

                    break;
                default:
                    Log.d(TAG, "onActivityResult: " + resultCode);
                    if(message == null) {
                        cordovaCallbackContext.error(resultCode);
                    } else {
                        cordovaCallbackContext.error(message);
                    }

                    break;
            }
        }
    }

    // Required in case Android kills our CordovaActivity while we are in iPay88
    // activity. See https://cordova.apache.org/docs/en/latest/guide/platforms/android/plugin.html#launching-other-activities
    // NOTE: we don't implement onSaveInstanceState() as we don't need to save anything...
    @Override
    public void onRestoreStateForActivityResult(Bundle state, CallbackContext callbackContext)
    {
        cordovaCallbackContext = callbackContext;
    }

    @Override
    public boolean execute (String action, JSONArray args, final CallbackContext callbackContext)
            throws JSONException
    {

        if(action.equals("makePayment")) {
            if(isInProgress) {
                callbackContext.error("Another request is in progress. Please wait a few seconds.");

            } else {
                cordovaCallbackContext = callbackContext;
                try{
                    eghlPayParams = new Gson().fromJson(args.getJSONObject(0).toString(), EghlPayment.class);

                } catch (Exception e) {
                    cordovaCallbackContext.error("Required parameter missing or invalid. " + e.getMessage());
                    return false;
                }

                eghl = EGHL.getInstance();

                payViaEGHL();

            }

            return true;

        } else if(action.equals("mpeRequest")) {
            if(isInProgress) {
                callbackContext.error("Another request is in progress. Please wait a few seconds.");

            } else {

                isInProgress = true;

                try{
                    EghlPayment arg0 = new Gson().fromJson(args.getJSONObject(0).toString(), EghlPayment.class);

                    MasterpassParams.Builder params = new MasterpassParams.Builder();
                    params.setPaymentGateway(arg0.getPaymentGateway());
                    params.setServiceID(arg0.getServiceId());
                    params.setPassword(arg0.getPassword());
                    params.setCurrencyCode(arg0.getCurrencyCode());
                    params.setAmount(arg0.getAmount());
                    params.setTokenType(arg0.getTokenType());
                    params.setToken(arg0.getToken());
                    params.setPaymentDesc(arg0.getPaymentDesc());

                    EGHL eghl = EGHL.getInstance();
                    eghl.executeMasterpassRequest(
                        cordova.getActivity(),
                        params.build(),
                        new MasterpassCallback() {
                            @Override
                            public void onResponse(final String response) {
                                isInProgress = false;
                                // Note: response string needs to be checked on
                                // JavaScript side, as it could be successful or erronous.
                                callbackContext.success(response);
                            }

                            @Override
                            public void onError(Exception e) {
                                isInProgress = false;
                                callbackContext.error(e.getMessage());
                            }
                        }
                    );

                } catch (Exception e) {
                    callbackContext.error("Required parameter missing or invalid. " + e.getMessage());
                }

            }

            return true;

        } else {
            return false;
        }
    }

    private void payViaEGHL ()
    {
        PaymentParams.Builder params;
        params = new PaymentParams.Builder()
                .setB4TaxAmt(eghlPayParams.getB4TaxAmt())
                .setTaxAmt(eghlPayParams.getTaxAmt())
                .setMerchantApprovalUrl(eghlPayParams.getMerchantApprovalUrl())
                .setMerchantUnapprovalUrl(eghlPayParams.getMerchantUnapprovalUrl())
                .setMerchantReturnUrl(eghlPayParams.getMerchantReturnUrl())
                .setMerchantCallbackUrl(eghlPayParams.getMerchantCallbackUrl())
                .setPaymentDesc(eghlPayParams.getPaymentDesc())
                .setLanguageCode(eghlPayParams.getLanguageCode())
                .setPageTimeout(eghlPayParams.getPageTimeout())
                .setPaymentGateway(eghlPayParams.getPaymentGateway())
                .setServiceId(eghlPayParams.getServiceId())
                .setPassword(eghlPayParams.getPassword())
                .setIssuingBank(eghlPayParams.getIssuingBank())
                .setAmount(eghlPayParams.getAmount())
                .setEppMonth(eghlPayParams.getEppMonth())
                .setBillAddr(eghlPayParams.getBillAddr())
                .setBillCity(eghlPayParams.getBillCity())
                .setBillCountry(eghlPayParams.getBillCountry())
                .setBillPostal(eghlPayParams.getBillPostal())
                .setBillRegion(eghlPayParams.getBillRegion())
                .setShipAddr(eghlPayParams.getShipAddr())
                .setShipCity(eghlPayParams.getShipCity())
                .setShipCountry(eghlPayParams.getShipCountry())
                .setShipPostal(eghlPayParams.getShipPostal())
                .setShipRegion(eghlPayParams.getShipRegion())
                .setCustName(eghlPayParams.getCustName())
                .setCustEmail(eghlPayParams.getCustEmail())
                .setCustMac(eghlPayParams.getCustMac())
                .setCustPhone(eghlPayParams.getCustPhone())
                .setCustIp(eghlPayParams.getCustIp())
                .setMerchantName(eghlPayParams.getMerchantName())
                .setCurrencyCode(eghlPayParams.getCurrencyCode())
                .setToken(eghlPayParams.getToken())
                .setTokenType(eghlPayParams.getTokenType())
                .setTransactionType(eghlPayParams.getTransactionType())
                .setPaymentMethod(eghlPayParams.getPaymentMethod())
                .setPaymentTimeout(eghlPayParams.getPaymentTimeout())
                .setPaymentId(eghlPayParams.getPaymentId())
                .setSessionId(eghlPayParams.getSessionId())
                .setOrderNumber(eghlPayParams.getOrderNumber())
                .setPromoCode(eghlPayParams.getPromoCode())
                .setReqToken(eghlPayParams.getReqToken())
                .setReqVerifier(eghlPayParams.getReqVerifier())
                .setPairingToken(eghlPayParams.getPairingToken())
                .setPairingVerifier(eghlPayParams.getPairingVerifier())
                .setCheckoutResourceURL(eghlPayParams.getCheckoutResourceURL())
                .setCardID(eghlPayParams.getCardID())
                .setCardHolder(eghlPayParams.getCardHolder())
                .setCardNo(eghlPayParams.getCardNo())
                .setCardExp(eghlPayParams.getCardExp())
                .setCardCvv2(eghlPayParams.getCardCvv2())
                .setPreCheckoutID(eghlPayParams.getPreCheckoutID())
                .setParam6(eghlPayParams.getParam6())
                .setParam7(eghlPayParams.getParam7());

        // eGHL intent.
        Intent payment = new Intent(cordova.getActivity(), PaymentActivity.class);
        payment.putExtras(params.build());

        // Launch the EGHL activity.
        isInProgress = true;
        cordova.startActivityForResult(this, payment, EGHL.REQUEST_PAYMENT);
    }
}
