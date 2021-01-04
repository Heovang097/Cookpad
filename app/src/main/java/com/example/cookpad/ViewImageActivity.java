package com.example.cookpad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class ViewImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        Intent intent = getIntent();
        String imgUri = intent.getStringExtra("uri");

        getImageFromServer(imgUri);
    }
    public void getImageFromServer(String uri){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://"+R.string.serverSocket +"/43219?id="+ AccountInfo.getAccountInfoHolder().getUserID() +"&uri="+uri;
        Log.d("@@@", url);
        // Request a string response from the provided URL.
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ImageView imageView = (ImageView)findViewById(R.id.activity_view_image_image_view);
                imageView.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(ViewImageActivity.this, "Error loading image", Toast.LENGTH_LONG);
            }
        });
        // Add the request to the RequestQueue.
        queue.add(imageRequest);
    }

    public void onClickActivityImageViewBack(View view){
        finish();
    }
}