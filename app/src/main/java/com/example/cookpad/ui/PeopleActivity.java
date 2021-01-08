package com.example.cookpad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cookpad.AccountInfo;
import com.example.cookpad.NetWork;
import com.example.cookpad.R;
import com.example.cookpad.ui.activity.MainPageActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeopleActivity extends AppCompatActivity {
    Boolean isFriend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        getSupportActionBar().hide();
        String id = getIntent().getExtras().getString("id");
        CircleImageView avatar = findViewById(R.id.peopleAvatar);
        String url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/" + "44341?id=" + id;
        Picasso.get().load(url).into(avatar);
        ((TextView) findViewById(R.id.peopleName)).setText(getIntent().getExtras().getString("name"));
        Button follow = findViewById(R.id.peopleIsFriend);
        RequestQueue queue = Volley.newRequestQueue(this);
        url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/" + "isFriend?you=" +
                AccountInfo.getAccountInfoHolder().getUserID()  + "&people=" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("0")) {
                            isFriend = Boolean.FALSE;
                            follow.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            follow.setText("FOLLOW");
                        }
                        else {
                            isFriend = Boolean.TRUE;
                            follow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_confirm, 0, 0, 0);
                            follow.setText("FOLLOWING");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("@@@", error.toString());
            }
        });
        queue.add(stringRequest);
        follow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (isFriend.equals(Boolean.TRUE)){
                    uF(follow, id);
                }
                else {
                    mF(follow, id);
                }
            }
        });
    }
    private void uF(Button follow, String id){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/" + "uF?you=" +
                AccountInfo.getAccountInfoHolder().getUserID()  + "&people=" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            isFriend = Boolean.FALSE;
                            follow.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                            follow.setText("FOLLOW");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("@@@", error.toString());
            }
        });
        queue.add(stringRequest);
    }
    private void mF(Button follow, String id){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/" + "mF?you=" +
                AccountInfo.getAccountInfoHolder().getUserID()  + "&people=" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            isFriend = Boolean.TRUE;
                            follow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_confirm,0,0,0);
                            follow.setText("FOLLOWING");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("@@@", error.toString());
            }
        });
        queue.add(stringRequest);
    }
}