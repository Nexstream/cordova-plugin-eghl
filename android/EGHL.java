// This code can only handle one payment at any given moment - no concurrent payments
// are allowed!

package my.com.nexstream.cordovaPlugins;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.epay.eghl.EGHLAPI;
import com.epay.eghl.EGHLInterface;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class EGHL extends CordovaPlugin {

    // Constants -------------------------------------------------------

    public static Integer EGHL_ACT_REQUEST_CODE = 88;

    public static String SIT_SERVICE_ID = "sit";
    public static String SIT_SERVICE_PASSWORD = "sit12345";

    public static String SIT_PAYMENT_GATEWAY = "https://test2pay.ghl.com/IPGSG/Payment.aspx";
    public static String SIT_RETURN_URL = "https://test2pay.ghl.com/IPGSimulatorJeff/RespFrmGW.aspx";

    public static String SERVER_CALLBACK_URL = "https://dev.v2.tgv.api.appxtream.com/tgvBackend/test/test";


    // Configuration. CUSTOMISE THIS ACCORDING TO YOUR NEEDS! ----------

    public static String SERVICE_ID = SIT_SERVICE_ID;
    public static String SERVICE_PASSWORD = SIT_SERVICE_PASSWORD;

    public static String PYMT_GATEWAY = SIT_PAYMENT_GATEWAY;
    public static String RETURN_URL = SIT_RETURN_URL;


    // State -----------------------------------------------------------

    public final static int STATUS_OK = 0;
    public final static int STATUS_FAILED = 1;
    public final static int STATUS_CANCELED = 2;

    // Indicate payment are in progress --------------------------------
    public static boolean isInProgress = false;

    private CallbackContext cordovaCallbackContext;

    private EGHLAPI eGHLAPI = null;
    private EGHLInterface EGHLCallback = null;

    private JSONObject eGHLArgObj;


    // Methods ---------------------------------------------------------

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent intent)
    {
        if(requestCode == EGHL_ACT_REQUEST_CODE) {
            try {

//                eGHLAPI.EGHLTransaction(EGHLAPI.EGHL_TRANS_QUERY);
                String status = intent.getStringExtra("status");
                Integer sta = intent.getIntExtra("sta", -99);

                Toast.makeText(cordova.getActivity().getApplicationContext(), status, Toast.LENGTH_LONG).show();

                switch(sta) {
                    case EGHLAPI.EGHL_STA_OK:
                        cordovaCallbackContext.success(sta);
                        break;
                    case STATUS_FAILED:
                        cordovaCallbackContext.error(sta);
                        break;
                    case STATUS_CANCELED:
                        cordovaCallbackContext.error(sta);
                        break;
                    default:
                        cordovaCallbackContext.error(sta);
                }

            } catch (Exception e) {
                cordovaCallbackContext.error("Unexpected failure in eGHL plugin: "+e.getMessage());
            } finally {
                isInProgress = false;
            }
        }
    }

    @Override
    public boolean execute (String action, JSONArray args, CallbackContext callbackContext)
            throws JSONException
    {
        if(action.equals("makepayment")) {
            if(isInProgress) {
                callbackContext.error("Another payment is in progress!");
            } else {
                cordovaCallbackContext = callbackContext;
                eGHLArgObj = args.getJSONObject(0);

                eGHLCallBack();
                payViaEGHL();

            }

            return true;
        } else {
            return false;
        }
    }


    // eGHL callback receiver -----------------------------------------
    // It should call when query transaction finished.
    private void eGHLCallBack (){

        eGHLAPI = new EGHLAPI();
        EGHLCallback = new EGHLInterface(){

            @Override
            public void EGHLGetStatus(int sta, String status) {

                if(EGHLAPI.EGHL_STA_OK == sta){
                    Integer state = Integer.parseInt(eGHLAPI.EGHLGetResponseValue("TxnStatus"));

                    switch(state){
                        case EGHLAPI.EGHL_STA_OK:
                            cordovaCallbackContext.success(status);
                            break;
                        case STATUS_FAILED:
                            cordovaCallbackContext.error(status);
                            break;
                        case STATUS_CANCELED:
                            cordovaCallbackContext.error(status);
                            break;
                        default:
                            cordovaCallbackContext.error(status);
                    }
                }else{
                    cordovaCallbackContext.error("Something went wrong in eGHL gateway.");
                }
            }

            @Override
            public void EGHLGetError(int ret, String error) {
                Log.e("EGHL error! ", "Err: "+ret+" "+error);
            }
        };

        eGHLAPI.setEGHLCallBack(EGHLCallback);
    }

    private void payViaEGHL ()
    {
        String ServiceID, Password, Amount, PaymentDesc, CustName, CustEmail, CustPhone, MerchantName, PaymentID,
                OrderNumber, CurrencyCode, LanguageCode, PageTimeout, TransactionType, PymtMethod, PaymentGateway,
                MerchantReturnURL;

        eGHLAPI.clearEGHLResponse();

        try {
            ServiceID = eGHLArgObj.getString("ServiceID");
            Password = eGHLArgObj.getString("Password");
            Amount = eGHLArgObj.getString("Amount");
            PaymentDesc = eGHLArgObj.getString("PaymentDesc");
            CustName = eGHLArgObj.getString("CustName");
            CustEmail = eGHLArgObj.getString("CustEmail");
            CustPhone = eGHLArgObj.getString("CustPhone");
            MerchantName = eGHLArgObj.getString("MerchantName");
            PaymentID = eGHLArgObj.getString("PaymentID");
            OrderNumber = eGHLArgObj.getString("OrderNumber");
            CurrencyCode = eGHLArgObj.getString("CurrencyCode");
            LanguageCode = eGHLArgObj.getString("LanguageCode");
            PageTimeout = eGHLArgObj.getString("PageTimeout");
            TransactionType = eGHLArgObj.getString("TransactionType");
            PymtMethod = eGHLArgObj.getString("PymtMethod");
            PaymentGateway = eGHLArgObj.getString("PaymentGateway");
            MerchantReturnURL = eGHLArgObj.getString("MerchantReturnURL");

        } catch (Exception e) {
            cordovaCallbackContext.error("Required parameter missing or invalid. "+e.getMessage());
            return;
        }

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

        eGHLAPI.setEGHLParams("ServiceID", ServiceID);
        eGHLAPI.setEGHLParams("Password", Password);
        eGHLAPI.setEGHLParams("Amount", Amount);
        eGHLAPI.setEGHLParams("PaymentDesc", PaymentDesc);
        eGHLAPI.setEGHLParams("CustName", CustName);
        eGHLAPI.setEGHLParams("CustEmail", CustEmail);
        eGHLAPI.setEGHLParams("CustPhone", CustPhone);
        eGHLAPI.setEGHLParams("MerchantName", MerchantName);
        eGHLAPI.setEGHLParams("PaymentID", PaymentID);
        eGHLAPI.setEGHLParams("OrderNumber", OrderNumber);
        eGHLAPI.setEGHLParams("CurrencyCode", CurrencyCode);
        eGHLAPI.setEGHLParams("LanguageCode", LanguageCode);
        eGHLAPI.setEGHLParams("PageTimeout", PageTimeout);
        eGHLAPI.setEGHLParams("TransactionType", TransactionType);
        eGHLAPI.setEGHLParams("PymtMethod", PymtMethod);

        // Payment gateway.
        eGHLAPI.setEGHLParams("PaymentGateway", PaymentGateway);
        eGHLAPI.setEGHLParams("MerchantReturnURL", MerchantReturnURL);

        //eGHLAPI.setEGHLParams("MerchantCallbackURL", "MerchantCallbackURL");
        //eGHLAPI.setEGHLParams("MerchantApprovalURL", "");
        //eGHLAPI.setEGHLParams("MerchantUnApprovalURL", "");

        eGHLAPI.RemoveEGHLParam("TokenType");
        eGHLAPI.RemoveEGHLParam("Token");

        // eGHL intent.
        Context context =  cordova.getActivity().getApplicationContext();

        Intent intent = new Intent(context, EGHLStart.class);

        Bundle bundle=new Bundle();
        bundle.putSerializable("EGHL_User_Params", eGHLAPI.getEGHLPaymentParams());// here is fixed standard for eGHL SDK
        intent.putExtras(bundle);

        // Launch the EGHL activity.
        // 1st arg to startActivityForResult() must be null, otherwise all WebViews get pause
        // leading to stuck activity??
        isInProgress = true;
        cordova.startActivityForResult(null, intent, EGHL_ACT_REQUEST_CODE);
        cordova.setActivityResultCallback(this);
    }
}
