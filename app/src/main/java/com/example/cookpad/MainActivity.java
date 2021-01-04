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

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final String SHARED_PREFS = "SHARED_PREFS";
    private static final String USER_ID = "user_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getPref();
        if(!AccountInfo.getAccountInfoHolder().getUserID().equals("")){
            checkStillLogin(AccountInfo.getAccountInfoHolder().getUserID());
        }

        //setContentView(R.layout.activity_login);

        /*if(AccountInfo.getUserID().equals("")) {
        }else{
            setContentView(R.layout.activity_main);
        }*/
        //getPref();
        //Toast.makeText(this, AccountInfo.getUserID(), Toast.LENGTH_LONG).show();
//        setContentView(R.layout.activity_menu);
//
//        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
//
//        ViewPager viewPager = findViewById(R.id.view_pager);
//        viewPager.setAdapter(sectionsPagerAdapter);
//
//        TabLayout tabs = findViewById(R.id.tabs);
//        tabs.setupWithViewPager(viewPager);
    }
    private String getPref(){
        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String userID = sharedPreferences.getString(USER_ID, "");
        AccountInfo.getAccountInfoHolder().setupInfo(userID);
        return userID;
    }

    private void checkStillLogin(String userID){

        String url = "http://"+ getResources().getString(R.string.serverSocket) +"/42519?id="+userID;
        //setContentView(R.layout.activity_login);

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Yes")){
                    Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }else{
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);

    }

}