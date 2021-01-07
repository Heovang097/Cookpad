package com.example.cookpad.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cookpad.AccountInfo;
import com.example.cookpad.NetWork;
import com.example.cookpad.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FActivity extends AppCompatActivity {
    ArrayList<People> listPeople;
    PeopleListViewAdapter peopleListViewAdapter;
    ListView listViewPeople;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f);
        getSupportActionBar().hide();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarF);
        listPeople = new ArrayList<>();
        String url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/" + getIntent().getExtras().getString("name") + "?id=" + AccountInfo.getAccountInfoHolder().getUserID();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Integer num = response.getInt("amount");
                    toolbar.setTitle(num.toString() + getIntent().getExtras().getString("tittle"));
                    for (Integer i = 0; i < num; i++) {
                        JSONObject people = response.getJSONObject(i.toString());
                        listPeople.add(new People(people.getString("id"),
                                people.getString("name"),
                                people.getInt("nRecipe"),
                                people.getString("isFriend")));
                    }
                    Log.d("@@@", listPeople.toString());
                    peopleListViewAdapter = new PeopleListViewAdapter(listPeople);
                    listViewPeople = findViewById(R.id.Flist);
                    listViewPeople.setAdapter(peopleListViewAdapter);
                    listViewPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            People people = (People) peopleListViewAdapter.getItem(position);
                            Intent intent = new Intent(FActivity.this, PeopleActivity.class);
                            intent.putExtra("id", people.sId);
                            startActivity(intent);
                        }
                    });
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

    class People {
        Integer id;
        String sId;
        String name;
        Integer amountRecipe;
        Boolean isFriend;
        public People(String id, String Name, Integer amountRecipe, String isFriend){
            this.id = Integer.parseInt(id);
            this.sId = id;
            this.name = Name;
            this.amountRecipe = amountRecipe;
            if (isFriend.equals("0"))
                this.isFriend = Boolean.FALSE;
            else
                this.isFriend = Boolean.TRUE;
        }
    }

    class PeopleListViewAdapter extends BaseAdapter {
        final ArrayList<People> listPeople;
        PeopleListViewAdapter(ArrayList<People> listPeople) {
            this.listPeople = listPeople;
        }
        @Override
        public int getCount() {
            return listPeople.size();
        }
        @Override
        public Object getItem(int position) {
            return listPeople.get(position);
        }
        @Override
        public long getItemId(int position) {
            return listPeople.get(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View viewPeople;
            if (convertView == null) {
                viewPeople = View.inflate(parent.getContext(), R.layout.layout_people, null);
            } else viewPeople = convertView;
            People people = (People) getItem(position);
            ((TextView) viewPeople.findViewById(R.id.layoutPeopleName)).setText(people.name);
            ((TextView) viewPeople.findViewById(R.id.layoutPeopleNum)).setText(" " + people.amountRecipe.toString());
            TextView friend = viewPeople.findViewById(R.id.layoutPeopleFriend);
            if (people.isFriend.booleanValue() == true) {
                friend.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_confirm,0,0,0);
                friend.setText("BẠN BẾP");
            }
            else {
                friend.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                friend.setText("KẾT BẠN BẾP");
            }
            CircleImageView avatar = viewPeople.findViewById(R.id.layoutPeopleAvatar);
            String url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/" + "44341?id=" + people.sId;
            Picasso.get().load(url).into(avatar);
            return viewPeople;
        }
    }
}