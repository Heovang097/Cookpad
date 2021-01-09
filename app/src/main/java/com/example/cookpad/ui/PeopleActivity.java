package com.example.cookpad.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cookpad.AccountInfo;
import com.example.cookpad.NetWork;
import com.example.cookpad.R;
import com.example.cookpad.ui.activity.MainPageActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeopleActivity extends AppCompatActivity {
    Boolean isFriend;
    TextView tvNumberFollow;
    TextView tvNumberFriend;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffFFFFFF));
        tvNumberFollow = findViewById(R.id.peopleNumberFollow);
        tvNumberFriend = findViewById(R.id.peopleNumberFriend);
        id = getIntent().getExtras().getString("id");
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
        TextView follower = findViewById(R.id.peopleFollow);
        TextView friend = findViewById(R.id.peopleFriend);
        follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFollow(id);
            }
        });
        tvNumberFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFollow(id);
            }
        });
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFriend(id);
            }
        });
        tvNumberFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFriend(id);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/fnum?id=" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Integer num = response.getInt("follow");
                    tvNumberFollow.setText(num.toString());
                    num = (Integer) response.getInt("friend");
                    tvNumberFriend.setText(num.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("@@@", error.toString());
            }
        });
        queue.add(jsonObjectRequest);
    }
    private void startFollow(String id){
        Intent intent = new Intent(this, FActivity.class);
        intent.putExtra("name", "follow");
        intent.putExtra("tittle", " Followers");
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void startFriend(String id){
        Intent intent = new Intent(this, FActivity.class);
        intent.putExtra("name", "friend");
        intent.putExtra("tittle", " Following");
        intent.putExtra("id", id);
        startActivity(intent);
    }
}