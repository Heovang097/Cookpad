package com.example.cookpad.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cookpad.NetWork;
import com.example.cookpad.R;
import com.example.cookpad.ui.home.InspirationFragment;
import com.example.cookpad.ui.home.RecipeCard;
import com.mypackage.utils.RecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter = null;
    ArrayList recipes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = view.findViewById(R.id.search_pager);
        final EditText edtSearch = view.findViewById(R.id.edtsearch);
        Button btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipes.clear();
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
                Log.d("@@@", edtSearch.getText().toString());
                String url = "http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/44336?search=" + edtSearch.getText();
                Log.d("@@@", url);
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
                                RecipeCard recipe = new RecipeCard(username, recipeName, likeCount, idUser, idRecipe);
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
        });
    }
}