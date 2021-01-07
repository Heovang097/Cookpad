package com.example.cookpad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.cookpad.NetWork;
import com.example.cookpad.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeopleActivity extends AppCompatActivity {

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
    }
}