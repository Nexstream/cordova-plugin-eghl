// This code can only handle one payment at any given moment - no concurrent payments
// are allowed!

package my.com.nexstream.cordovaPlugins;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

import com.eghl.sdk.EGHL;
import com.eghl.sdk.params.PaymentParams;

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
            switch (resultCode) {
                case EGHL.TRANSACTION_SUCCESS:
                    Log.d(TAG, "onActivityResult: payment successful");
                    cordovaCallbackContext.success(resultCode);

                    break;
                case EGHL.TRANSACTION_FAILED:
                    Log.d(TAG, "onActivityResult: payment failure");
                    cordovaCallbackContext.error(resultCode);

                    break;
                default:
                    Log.d(TAG, "onActivityResult: " + resultCode);
                    cordovaCallbackContext.error(resultCode);

                    break;
            }
        }
    }

    @Override
    public boolean execute (String action, JSONArray args, CallbackContext callbackContext)
            throws JSONException
    {

        if(action.equals("makePayment")) {
            if(isInProgress) {
                callbackContext.error("Another payment is in progress!");

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
        } else {
            return false;
        }
    }

    private void payViaEGHL ()
    {
        PaymentParams.Builder params;
        params = new PaymentParams.Builder()
                .setMerchantReturnUrl(eghlPayParams.getMerchantReturnUrl())
                .setMerchantCallbackUrl(eghlPayParams.getMerchantCallbackUrl())
                .setPaymentDesc(eghlPayParams.getPaymentDesc())
                .setLanguageCode(eghlPayParams.getLanguageCode())
                .setPageTimeout(eghlPayParams.getPageTimeout())
                .setServiceId(eghlPayParams.getServiceId())
                .setIssuingBank(eghlPayParams.getIssuingBank())
                .setAmount(eghlPayParams.getAmount())
                .setCustName(eghlPayParams.getCustName())
                .setCustEmail(eghlPayParams.getCustEmail())
                .setCustPhone(eghlPayParams.getCustPhone())
                .setMerchantName(eghlPayParams.getMerchantName())
                .setCurrencyCode(eghlPayParams.getCurrencyCode())
                .setToken(eghlPayParams.getToken())
                .setTokenType(eghlPayParams.getTokenType())
                .setTransactionType(eghlPayParams.getTransactionType())
                .setPaymentMethod(eghlPayParams.getPaymentMethod())
                .setPaymentTimeout(8*60)
                .setPaymentId(eghlPayParams.getPaymentId())
                .setOrderNumber(eghlPayParams.getOrderNumber());

        // eGHL intent.
        Bundle paymentParams = params.build();

        // Launch the EGHL activity.
        isInProgress = true;
        eghl.executePayment(paymentParams, cordova.getActivity());

        // Set Callback
        cordova.setActivityResultCallback(this);
    }
}
