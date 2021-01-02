package com.example.cookpad.ui.you;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookpad.R;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateActivity extends AppCompatActivity {
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "avatar");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().hide();
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        EditText ed = findViewById(R.id.Name);
        String id = sharedPref.getString("ID", "DEFAULT" );
        ed.setText(id, TextView.BufferType.EDITABLE);
        String url ="http://192.168.1.9:8000/44341?id=" + id;
        Log.d("@@@", url);
        ImageView avatar = findViewById(R.id.UpdateImageView);
        avatar.setImageDrawable(LoadImageFromWebOperations(url));
        Picasso.with(getBaseContext())
                .load(url)
                .resize(150, 150)
                .centerCrop()
                .into(avatar);
    }
}