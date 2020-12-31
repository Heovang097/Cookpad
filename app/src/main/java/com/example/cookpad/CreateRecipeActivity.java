package com.example.cookpad;

import android.os.Bundle;

import com.example.cookpad.fragments.CreateRecipe.IngredientsAdapter;
import com.example.cookpad.fragments.CreateRecipe.ItemIngredients;
import com.example.cookpad.fragments.CreateRecipe.MethodAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CreateRecipeActivity extends AppCompatActivity {

    private RecyclerView recyclerIngredient;
    private IngredientsAdapter mIngredientAdapter;
    private RecyclerView recyclerViewMethod;
    private MethodAdapter mAdapterMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
//        toolBarLayout.setTitle("Phở bò");
        recyclerIngredient = (RecyclerView) findViewById(R.id.recyclerIngredients);

        mIngredientAdapter = new IngredientsAdapter(generateIngredients(),this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerIngredient.setLayoutManager(mLayoutManager);
        recyclerIngredient.setItemAnimator(new DefaultItemAnimator());
        recyclerIngredient.setAdapter(mIngredientAdapter);

//        recyclerViewMethod = (RecyclerView) findViewById(R.id.recyclerMethod);
//
//        mAdapterMethod = new MethodAdapter(getBaseContext(), generatePreparation(),this);
//        LinearLayoutManager mLayoutManagerPreparation = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerViewMethod.setLayoutManager(mLayoutManagerPreparation);
//        recyclerViewMethod.setItemAnimator(new DefaultItemAnimator());
//        recyclerViewMethod.setAdapter(mAdapterMethod);

    }


    public List<ItemIngredients> generateIngredients(){
        List<ItemIngredients> itemList = new ArrayList<>();
        String name[] = {"200g bơ", "100g thịt ba rọi", "2 quả trứng", "200g bột gạo"};
        for (int i = 0; i<name.length; i++){
            ItemIngredients itemIngredients = new ItemIngredients();
            itemIngredients.setName(name[i]);
            itemList.add(itemIngredients);
        }
        return itemList;
    }
}