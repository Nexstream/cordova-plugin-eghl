package my.com.nexstream.cordovaPlugins;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.epay.eghl.EGHLAPI;
import com.epay.eghl.EGHLInterface;
import com.epay.eghl.EGHLSaleActivity;
import com.epay.eghl.EGHLSerializableMap;

import java.util.HashMap;

public class EGHLStart extends EGHLSaleActivity{

	private EGHLInterface EPayCallback = null;
	private HashMap<String, String> eGHLParams = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//this.setContentView(R.layout.activity_demo);  Don`t call 'setContentView' if extend EGHLSaleActivity at this activity.

		EGHLSerializableMap serializableMap = (EGHLSerializableMap)getIntent().getSerializableExtra("EGHL_User_Params");
		eGHLParams = serializableMap.getMap();// get all parameters list

		EPayCallback = new EGHLInterface(){

            Intent result = new Intent();

			@Override
			public void EGHLGetStatus(int sta, String status) {

				if(EGHLAPI.EGHL_STA_OK == sta){

                    result.putExtra("sta", sta);
                    result.putExtra("status", status);

                    setResult(RESULT_OK, result);

                    finish();
				}
			}

			@Override
			public void EGHLGetError(int sta, String status) {

                Toast.makeText(EGHLStart.this, status, Toast.LENGTH_LONG).show();

                result.putExtra("sta", sta);
                result.putExtra("status", status);

                setResult(RESULT_CANCELED, result);

                finish();
            }
		};
		this.setEPayCallBack(EPayCallback);
	}
}
