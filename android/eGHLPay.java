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
                        if(message.equals("Buyer cancelled") || message.equals("Buyer%20cancelled")) {
                            // Detect cancellation like iOS and return -999 (which eGHL SDK
                            // used to return for user cancellation).
                            Log.d(TAG, "onActivityResult: payment cancelled");
                            cordovaCallbackContext.error(-999);
                        } else {
                            Log.d(TAG, "onActivityResult: payment failure");
                            cordovaCallbackContext.error(message);
                        }
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

    @Override
    public boolean execute (String action, JSONArray args, final CallbackContext callbackContext)
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

        } else if(action.equals("mpeRequest")) {

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
                            // Note: response string needs to be checked on
                            // JavaScript side, as it could be successful or erronous.
                            callbackContext.success(response);
                        }

                        @Override
                        public void onError(Exception e) {
                            callbackContext.error(e.getMessage());
                        }
                    }
                );

                return true;
            } catch (Exception e) {
                callbackContext.error("Required parameter missing or invalid. " + e.getMessage());
                return false;
            }

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
                .setPaymentGateway(eghlPayParams.getPaymentGateway())
                .setServiceId(eghlPayParams.getServiceId())
                .setPassword(eghlPayParams.getPassword())
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
                .setPaymentTimeout(eghlPayParams.getPaymentTimeout())
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
