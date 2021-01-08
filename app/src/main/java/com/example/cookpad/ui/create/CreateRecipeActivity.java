package com.example.cookpad.ui.create;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cookpad.R;
import com.example.cookpad.ui.create.CreateRecipe.IngredientsAdapter;
import com.example.cookpad.ui.create.AdapterItem.ItemIngredients;
import com.example.cookpad.ui.create.AdapterItem.ItemMethod;
import com.example.cookpad.ui.create.CreateRecipe.MethodAdapter;
import com.example.cookpad.utils.Volley.AppHelper;
import com.example.cookpad.utils.Volley.VolleyMultipartRequest;
import com.example.cookpad.utils.Volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class CreateRecipeActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextTitle;
    EditText editTextDesc;
    EditText editTextNump;
    EditText editTextTime;
    ImageView imageViewThumbnail;

    private RecyclerView recyclerIngredient;
    private IngredientsAdapter mIngredientAdapter;
    private RecyclerView recyclerViewMethod;
    private MethodAdapter mMethodAdapter;
    private Button addIngredientButton;
    private Button addMethodButton;
    private Button publishButton;

    Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
//        toolBarLayout.setTitle("Phở bò");


        editTextTitle = findViewById(R.id.et_title);
        editTextDesc = findViewById(R.id.et_desc);
        editTextNump = findViewById(R.id.et_nump);
        editTextTime = findViewById(R.id.et_time);
        imageViewThumbnail = findViewById(R.id.image);
        recyclerIngredient = (RecyclerView) findViewById(R.id.recyclerIngredients);

        mIngredientAdapter = new IngredientsAdapter(generateIngredientsDummies(),this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerIngredient.setLayoutManager(mLayoutManager);
        recyclerIngredient.setItemAnimator(new DefaultItemAnimator());
        recyclerIngredient.setAdapter(mIngredientAdapter);


        recyclerViewMethod = (RecyclerView) findViewById(R.id.recyclerMethod);

        mMethodAdapter = new MethodAdapter(generateMethodDummies(),this);
        LinearLayoutManager mLayoutManagerPreparation = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewMethod.setLayoutManager(mLayoutManagerPreparation);
        recyclerViewMethod.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMethod.setAdapter(mMethodAdapter);

        addMethodButton = findViewById(R.id.addStepButton);
        addIngredientButton = findViewById(R.id.addIngredientButton);
        publishButton = findViewById(R.id.publishRecipeButton);
        addMethodButton.setOnClickListener(this);
        addIngredientButton.setOnClickListener(this);
        publishButton.setOnClickListener(this);
    }

    List<Bitmap> bitmaps = new ArrayList();
    private List<ItemMethod> generateMethodDummies() {
        List<ItemMethod> itemList = new ArrayList<>();
        String name[] = {"Đập trứng", "Nấu cơm"};
        bitmaps.add(BitmapFactory.decodeResource(getResources(),R.drawable.nau_pho_0));
        bitmaps.add(BitmapFactory.decodeResource(getResources(),R.drawable.nau_pho_1));
        for (int i = 0; i<name.length; i++){
            ItemMethod itemMethod =new ItemMethod(name[i],String.valueOf(i));
            itemMethod.setBitmaps(bitmaps);
            itemList.add(itemMethod);
        }
        return itemList;
    }

    public List<ItemIngredients> generateIngredientsDummies(){
        List<ItemIngredients> itemList = new ArrayList<>();
        String name[] = {"200g bơ", "100g thịt ba rọi"};
        for (int i = 0; i<name.length; i++){
            ItemIngredients itemIngredients = new ItemIngredients(name[i]);
            itemList.add(itemIngredients);
        }
        return itemList;
    }


    private void uploadData(String url)
    {
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    String status = result.getString("status");
                    String message = result.getString("message");

                    if (status.equals("404")) {
                        // tell everybody you have succed upload image and post strings
                        Log.i("Messsage", message);
                    } else {
                        Log.i("Unexpected", message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message+" Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message+ " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message+" Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();;
                params.put("api_token", "gh659gjhvdyudo973823tt9gvjf7i6ric75r76");
                params.put("recipe_name", editTextTitle.getText().toString());
                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                JSONObject jsonObject = new JSONObject();
                JSONArray ings = mIngredientAdapter.getIngredientsName();
                JSONArray step = mMethodAdapter.getStepList();

                try{
                    jsonObject.put("name", editTextTitle.getText().toString());
                    jsonObject.put("desc", editTextDesc.getText().toString());
                    jsonObject.put("nump", editTextNump.getText().toString());
                    jsonObject.put("time", editTextTime.getText().toString());
                    jsonObject.put("ings",ings);
                    jsonObject.put("step",step);

                }
                catch (Exception e)
                {
                    Toast.makeText(mContext, "JSON Error", Toast.LENGTH_SHORT).show();
                }
                params.put("json",new DataPart("metadata.json",jsonObject.toString().getBytes()));
                params.put("thumbnail", new DataPart("thumbnail.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), getDrawable(R.drawable.pho_bo)), "image/jpeg"));

                //params.put("cover", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), mCoverImage.getDrawable()), "image/jpeg"));
                return params;
            }
        };

        VolleySingleton.getInstance(mContext).addToRequestQueue(multipartRequest);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.publishRecipeButton:
                uploadData("http://192.168.1.85:8999/44419");
                break;
            case R.id.addIngredientButton:
                mIngredientAdapter.add(new ItemIngredients(""));
                break;
            case R.id.addStepButton:
                ItemMethod itemMethod = new ItemMethod("",String.valueOf(mMethodAdapter.getItemCount()));
                itemMethod.setBitmaps(bitmaps);
                mMethodAdapter.add(itemMethod);

                break;
        }
    }
}