package com.example.cookpad.ui.create.RecipeDetail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cookpad.AccountInfo;
import com.example.cookpad.NetWork;
import com.example.cookpad.R;
import com.example.cookpad.ViewImageActivity;
import com.example.cookpad.ui.create.AdapterItem.ItemIngredients;
import com.example.cookpad.ui.create.AdapterItem.ItemMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RecipeDetailFragment extends Fragment {

    private RecyclerView recyclerIngredient;
    private IngredientsDetailAdapter mIngredientAdapter;
    private RecyclerView recyclerViewMethod;
    private MethodDetailAdapter mMethodAdapter;
    private DecimalFormat decimalFormat;
    private ImageView thumbnail;
    TextView tv_recipe_name,tv_mo_ta,tv_khau_phan,tv_thoi_gian;
    String recipeId;
    public RecipeDetailFragment(){
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        recipeId = getArguments() != null ? getArguments().getString("id") : "";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        tv_khau_phan = v.findViewById(R.id.tv_khau_phan);
        tv_mo_ta = v.findViewById(R.id.tv_mo_ta);
        tv_thoi_gian = v.findViewById(R.id.tv_thoi_gian);
        tv_recipe_name = v.findViewById(R.id.tv_recipe_name);
        thumbnail = v.findViewById(R.id.imageThumbnailRecipeDetail);

        recyclerIngredient = (RecyclerView) v.findViewById(R.id.recyclerIngredients);
        recyclerViewMethod = (RecyclerView)v.findViewById(R.id.recyclerMethod);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerIngredient.setLayoutManager(mLayoutManager);
        recyclerIngredient.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager mLayoutManagerPreparation = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewMethod.setLayoutManager(mLayoutManagerPreparation);
        recyclerViewMethod.setItemAnimator(new DefaultItemAnimator());

        decimalFormat=new DecimalFormat("#.0");
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    private void fetchData() {
        String url = "files/recipes/" + recipeId+"/thumbnail.jpg";
        ViewImageActivity.getImageFromServer(url, thumbnail, getContext());

        url = "http://"+ NetWork.getNetworkInfoHolder().getSERVER() +"/44439?id="+recipeId;
        final ArrayList<ItemIngredients> ingList = new ArrayList<>();
        final ArrayList<ItemMethod> mtdList = new ArrayList<>();
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RecipeDetail",response.toString());
                        try {
                            tv_recipe_name.setText(response.getString("name"));
                            tv_khau_phan.setText(response.getString("nump"));
                            tv_mo_ta.setText(response.getString("desc"));
                            String tg = decimalFormat.format((float)Integer.valueOf(response.getString("time"))/3600);
                            tv_thoi_gian.setText(tg);
                            JSONArray ings = response.getJSONArray("ings");
                            JSONArray steps = response.getJSONArray("step");
                            for (int i =0;i<ings.length();i++)
                            {
                                ingList.add(new ItemIngredients(ings.getString(i)));
                            }
                            mIngredientAdapter = new IngredientsDetailAdapter(ingList,getContext());
                            mMethodAdapter = new MethodDetailAdapter(mtdList,getContext());
                            recyclerIngredient.setAdapter(mIngredientAdapter);

                            for (int i =0;i<steps.length();i++)
                            {
                                //String number = String.valueOf(mMethodAdapter.getItemCount());
                                String number = String.valueOf(i);
                                String desc = steps.getJSONObject(i).getString("desc");
                                ArrayList<String> imagePaths = new ArrayList<>();
                                for (int j =0 ;j<3;j++)
                                {
                                    String path = steps.getJSONObject(i).getString("img" + String.valueOf(j+1));
                                    if(path != null)
                                    {
                                        path =  "http://"+ NetWork.getNetworkInfoHolder().getSERVER() +"/44429?path=" + path.replace("/","%2F");
                                        imagePaths.add(path);
                                    }
                                    else
                                    {
                                        break;
                                    }
                                }
                                mtdList.add(new ItemMethod(desc,number,imagePaths));
                            }
                            recyclerViewMethod.setAdapter(mMethodAdapter);
                            mMethodAdapter.notifyDataSetChanged();
                            mIngredientAdapter.notifyDataSetChanged();
                        }catch (Exception e)
                        {

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

//    private List<ItemMethod> generateMehodDummies() {
//        List<ItemMethod> itemList = new ArrayList<>();
//        String name[] = {"Đập trứng", "Nấu cơm"};
//        ArrayList<Bitmap> bitmaps = new ArrayList<>();
//        for (int i = 0; i<name.length; i++){
//            for(int j=0;j<3;j++)
//                bitmaps.add(BitmapFactory.decodeResource(getResources(),R.drawable.nau_pho_0));
//            ItemMethod itemMethod =new ItemMethod(name[i],String.valueOf(i),bitmaps);
//            itemList.add(itemMethod);
//        }
//        return itemList;
//    }

    /*public List<ItemIngredients> generateIngredientsDummies(){
        List<ItemIngredients> itemList = new ArrayList<>();
        String name[] = {"200g bơ", "100g thịt ba rọi"};
        for (int i = 0; i<name.length; i++){
            ItemIngredients itemIngredients = new ItemIngredients(name[i]);
            itemList.add(itemIngredients);
        }
        return itemList;
    }*/

}