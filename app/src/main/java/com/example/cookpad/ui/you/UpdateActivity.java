package com.example.cookpad.ui.you;


import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cookpad.AccountInfo;
import com.example.cookpad.NetWork;
import com.example.cookpad.R;
import com.example.cookpad.ui.activity.MainPageActivity;

import com.example.cookpad.utils.Volley.AppHelper;
import com.example.cookpad.utils.Volley.VolleyMultipartRequest;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    Button mbutton;
    ImageView mAvatar;
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mbutton = findViewById(R.id.UpdateAvatarButton);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
        getSupportActionBar().hide();
        EditText ed = findViewById(R.id.Name);
        ed.setText("abc", TextView.BufferType.EDITABLE);
        String url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/info?id=" + AccountInfo.getAccountInfoHolder().getUserID();

        TextInputEditText name = findViewById(R.id.Name);
        TextInputEditText email = findViewById(R.id.Email);
        TextInputEditText country = findViewById(R.id.Where);
        TextInputEditText intro = findViewById(R.id.Intro);

        mAvatar = (ImageView) findViewById(R.id.UpdateImageView);

        ActionMenuItemView item = findViewById(R.id.SaveUpdate);
        SharedPreferences sh = getSharedPreferences("Info", Context.MODE_PRIVATE);
        name.setText(sh.getString("name", ""));
        email.setText(sh.getString("email", ""));
        country.setText(sh.getString("country", ""));
        intro.setText(sh.getString("intro", ""));

        url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/44341?id=" + AccountInfo.getAccountInfoHolder().getUserID();
        Log.d("@@@", url);
        new DownloadImageTask(mAvatar).execute(url);

        item.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                update(name, email, country, intro);
            }
        });
    }
    private void update(TextInputEditText name, TextInputEditText email, TextInputEditText country, TextInputEditText intro){
        String sName = name.getText().toString();
        String sEmail = email.getText().toString();
        String sCountry = country.getText().toString();
        String sIntro =  intro.getText().toString();
        String url = null;
        try {

            url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/change?id=" + AccountInfo.getAccountInfoHolder().getUserID()
                    +"&name="+URLEncoder.encode(sName)+"&email="+URLEncoder.encode(sEmail)+
                    "&country="+URLEncoder.encode(sCountry)+"&intro="+URLEncoder.encode(sIntro);
        } catch (Exception e) {
            Log.d("@@@", e.toString());
        }

        url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/change";

        Log.d("@@@", url);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals( "Updated")) {
                    SharedPreferences sh = getSharedPreferences("Info", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    editor.putString("name", URLEncoder.encode(sName));
                    editor.putString("email", URLEncoder.encode(sEmail));
                    editor.putString("country", URLEncoder.encode(sCountry));
                    editor.putString("intro", URLEncoder.encode(sIntro));

                    editor.apply();
                    setResult(Activity.RESULT_OK);
                    finish();
                    Log.d("@@@", "Updated");
                }
                else {
                    Log.d("@@@", "Failed update");
                }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("@@@", error.toString());
            }
        });
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    String status = result.getString("status");

                    if (status.equals("200")) {
                        // tell everybody you have succed upload image and post strings
                        SharedPreferences sh = getSharedPreferences("Info", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sh.edit();
                        editor.putString("name",sName);
                        editor.putString("email", sEmail);
                        editor.putString("country", sCountry);
                        editor.putString("intro", sIntro);
                        editor.apply();
                        setResult(Activity.RESULT_OK);
                        Toast.makeText(mContext, "Successfully Update Profile", Toast.LENGTH_LONG).show();
                        finishActivity(0);
                        Log.d("@@@", "Updated");
                    } else {
                        Toast.makeText(mContext,"Error uploading profile",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message+" Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message+ " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message+" Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", AccountInfo.getAccountInfoHolder().getUserID());
                params.put("name", sName);
                params.put("email", sEmail);
                params.put("country", sCountry);
                params.put("intro", sIntro);
                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView

                params.put("avatar",new DataPart(AccountInfo.getAccountInfoHolder().getUserID() + ".jpg",AppHelper.getFileDataFromDrawable(getBaseContext(),mAvatar.getDrawable())));
                //params.put("cover", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), mCoverImage.getDrawable()), "image/jpeg"));
                return params;
            }
        };

        //queue.add(stringRequest);
        queue.add(multipartRequest);
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                mAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //TODO: action
            }
        }
    }
}