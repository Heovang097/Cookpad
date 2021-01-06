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
import com.example.cookpad.R;
import com.example.cookpad.ui.FActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class YourFragment extends Fragment {
    Bundle follow = new Bundle();
    Bundle friend = new Bundle();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_your, container, false);
        //Circle View
        CircleImageView avatar = (CircleImageView) view.findViewById(R.id.YourAvatar);
        String url = getResources().getString(R.string.Url) + "44341?id=" + AccountInfo.getAccountInfoHolder().getUserID();
        Picasso.get().load(url).into(avatar);
        //Name
        TextView tv = view.findViewById(R.id.YourName);
        SharedPreferences sh = getActivity().getSharedPreferences("Info", MODE_PRIVATE);
        tv.setText(sh.getString("name", ""));
        //Follow and Friend
        TextView tvNumberFollow = view.findViewById(R.id.YourNumberFollow);
        TextView tvNumberFriend = view.findViewById(R.id.YourNumberFriend);
        url = getResources().getString(R.string.Url) + "ff?id=" + AccountInfo.getAccountInfoHolder().getUserID();
        Log.d("@@@", url);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        follow.putString("name", "follow");
        follow.putString("tittle", "Người quan tâm");
        friend.putString("name", "friend");
        friend.putString("tittle", "Bạn bếp");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Follow
                    JSONObject jsonFollow = response.getJSONObject("follow");
                    Integer num = (Integer) jsonFollow.getInt("amount");
                    follow.putInt("amount", num);
                    ArrayList<Integer> arrayList = new ArrayList<Integer>();
                    for (Integer i=0; i<num; i++)
                        arrayList.add((Integer) jsonFollow.getInt(i.toString()));
                    follow.putIntegerArrayList("array", arrayList);
                    tvNumberFollow.setText(num.toString());
                    JSONObject jsonFriend = response.getJSONObject("friend");
                    //Friend
                    num = (Integer) jsonFriend.getInt("amount");
                    friend.putInt("amount", num);
                    arrayList.clear();
                    for (Integer i=0; i<num; i++)
                        arrayList.add((Integer) jsonFriend.getInt(i.toString()));
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
                openF(follow);
            }
        });
        TextView tvFollow = view.findViewById(R.id.YourFollow);
        tvFollow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openF(follow);
            }
        });
        tvNumberFriend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openF(friend);
            }
        });
        TextView tvFriend = view.findViewById(R.id.YourFriend);
        tvFriend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openF(friend);
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
    private void openF(Bundle b){
        Intent intent = new Intent(getActivity(), FActivity.class);
        intent.putExtra("name", b.getString("name"));
        intent.putExtra("amount", b.getInt("amount"));
        intent.putExtra("array", b.getIntegerArrayList("array"));
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
    }
}