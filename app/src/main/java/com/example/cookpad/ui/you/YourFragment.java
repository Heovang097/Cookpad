package com.example.cookpad.ui.you;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cookpad.AccountInfo;
import com.example.cookpad.NetWork;
import com.example.cookpad.R;
import com.example.cookpad.ui.FActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class YourFragment extends Fragment {
    TextView tvNumberFollow;
    TextView tvNumberFriend;
    CircleImageView avatar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_your, container, false);
        //Circle View
        avatar = (CircleImageView) view.findViewById(R.id.YourAvatar);
        String url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/44341?id=" + AccountInfo.getAccountInfoHolder().getUserID();
        //Name
        TextView tv = view.findViewById(R.id.YourName);
        SharedPreferences sh = getActivity().getSharedPreferences("Info", MODE_PRIVATE);
        tv.setText(sh.getString("name", ""));
        //Follow and Friend
        tvNumberFollow = view.findViewById(R.id.YourNumberFollow);
        tvNumberFriend = view.findViewById(R.id.YourNumberFriend);
        url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/fnum?id=" + AccountInfo.getAccountInfoHolder().getUserID();
        RequestQueue queue = Volley.newRequestQueue(getContext());
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
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
        tvNumberFollow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startFollow();
            }
        });
        TextView tvFollow = view.findViewById(R.id.YourFollow);
        tvFollow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startFollow();
            }
        });
        tvNumberFriend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startFriend();
            }
        });
        TextView tvFriend = view.findViewById(R.id.YourFriend);
        tvFriend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startFriend();
            }
        });
        Toolbar toolbar = view.findViewById(R.id.toolbarYour);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.YourUpdate:
                        Toast.makeText(getActivity(), "Update", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), UpdateActivity.class);
                        getActivity().startActivityForResult(intent, 10001);
                        //startActivity(intent);
                        return true;
                }
                return false;
            }
        });
        return view;
    }

    private void startFollow(){
        Intent intent = new Intent(getActivity(), FActivity.class);
        intent.putExtra("name", "follow");
        intent.putExtra("tittle", " Followers");
        intent.putExtra("id", AccountInfo.getAccountInfoHolder().getUserID());
        startActivity(intent);
    }

    private void startFriend(){
        Intent intent = new Intent(getActivity(), FActivity.class);
        intent.putExtra("name", "friend");
        intent.putExtra("tittle", " Following");
        intent.putExtra("id", AccountInfo.getAccountInfoHolder().getUserID());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView tv = (TextView) getActivity().findViewById(R.id.YourName);
        SharedPreferences sh = getActivity().getSharedPreferences("Info", MODE_PRIVATE);
        SharedPreferences.OnSharedPreferenceChangeListener prefListener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences prefs,
                                                          String key) {
                        if (key.equals("name")) {
                            //tv.setText(sh.getString("name", ""));
                            //Log.d("@@@", sh.getString("name", ""));
                        }
                    }
                };
        sh.registerOnSharedPreferenceChangeListener(prefListener);
        //tv.setText(sh.getString("name", ""));
        tv.setText("  " + sh.getString("name", ""));
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/fnum?id=" + AccountInfo.getAccountInfoHolder().getUserID();
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
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
        url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/44341?id=" + AccountInfo.getAccountInfoHolder().getUserID();
        Picasso.get().load(url).into(avatar);
    }
}