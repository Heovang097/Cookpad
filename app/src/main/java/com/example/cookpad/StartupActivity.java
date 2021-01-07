package com.example.cookpad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cookpad.ui.activity.MainPageActivity;

public class StartupActivity extends AppCompatActivity {
    private static final String SHARED_PREFS = "SHARED_PREFS";
    private static final String USER_ID = "user_ID";
    private static final int REQUEST_CODE = 1001;
    @Override
    protected void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.startapp_layout);
        getPref();
        if(!AccountInfo.getAccountInfoHolder().getUserID().equals("")){
            checkStillLogin(AccountInfo.getAccountInfoHolder().getUserID());
        }else{
            Intent intent = new Intent(StartupActivity.this, MainActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    private String getPref(){
        SharedPreferences sharedPreferences = StartupActivity.this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String userID = sharedPreferences.getString(USER_ID, "");
        AccountInfo.getAccountInfoHolder().setupInfo(userID);
        return userID;
    }

    private void checkStillLogin(String userID){

        String url = "http://"+ NetWork.getNetworkInfoHolder().getSERVER() +"/42519?id="+userID;

        RequestQueue queue = Volley.newRequestQueue(StartupActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent;
                if(response.equals("Yes")){
                    intent = new Intent(StartupActivity.this, MainPageActivity.class);
                }else{
                    intent = new Intent(StartupActivity.this, MainActivity.class);
                }
                startActivityForResult(intent, REQUEST_CODE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent = new Intent(StartupActivity.this, MainPageActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        queue.add(stringRequest);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode==REQUEST_CODE){
            finish();
        }
    }

}
