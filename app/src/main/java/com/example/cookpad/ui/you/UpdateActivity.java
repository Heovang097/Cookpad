package com.example.cookpad.ui.you;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.example.cookpad.R;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().hide();
        //Toolbar toolbar = (Toolbar) (Toolbar) findViewById(R.id.toolbarUpdate);
        //setSupportActionBar(toolbar);
    }
}