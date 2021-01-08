package com.example.cookpad.ui.you;

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
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UpdateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().hide();
        EditText ed = findViewById(R.id.Name);
        ed.setText("abc", TextView.BufferType.EDITABLE);
        String url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/info?id=" + AccountInfo.getAccountInfoHolder().getUserID();
        TextInputEditText name = findViewById(R.id.Name);
        TextInputEditText email = findViewById(R.id.Email);
        TextInputEditText country = findViewById(R.id.Where);
        TextInputEditText intro = findViewById(R.id.Intro);
        ImageView avatar = (ImageView) findViewById(R.id.UpdateImageView);
        ActionMenuItemView item = findViewById(R.id.SaveUpdate);
        SharedPreferences sh = getSharedPreferences("Info", Context.MODE_PRIVATE);
        name.setText(sh.getString("name", ""));
        email.setText(sh.getString("email", ""));
        country.setText(sh.getString("country", ""));
        intro.setText(sh.getString("intro", ""));
        url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/44341?id=" + AccountInfo.getAccountInfoHolder().getUserID();
        Log.d("@@@", url);
        new DownloadImageTask(avatar).execute(url);
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
        Log.d("@@@", url);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals( "Updated")) {
                    SharedPreferences sh = getSharedPreferences("Info", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    editor.putString("name", sName);
                    editor.putString("email", sEmail);
                    editor.putString("country", sCountry);
                    editor.putString("intro", sIntro);
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
        queue.add(stringRequest);
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
}