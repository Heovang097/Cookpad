package com.example.cookpad.ui.home;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cookpad.AccountInfo;
import com.example.cookpad.NetWork;
import com.example.cookpad.R;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.mypackage.utils.RecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NetworkFragment extends Fragment {
    private RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter = null;
    ArrayList recipes = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        String userID = AccountInfo.getAccountInfoHolder().getUserID();
        String url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/44335?id=" + userID;
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("debug", response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("result");
                    ArrayList inspirations = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String username = obj.getString("nameUser");
                        String recipeName = obj.getString("nameRecipe");
                        int likeCount = obj.getInt("likeCount");
                        String idUser = obj.getString("idUser");
                        String idRecipe = obj.getString("idRecipe");
                        Boolean liked = obj.getBoolean("liked");
                        RecipeCard recipe = new RecipeCard(username, recipeName, likeCount, idUser, idRecipe, liked);
                        recipes.add(recipe);
                        if (recyclerAdapter != null)
                            recyclerAdapter.notifyItemInserted(recyclerAdapter.getItemCount() - 1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonObjectRequest);
        recyclerAdapter = new RecyclerAdapter(view.getContext(), recipes);
        recyclerView.setAdapter(recyclerAdapter);
    }
}
