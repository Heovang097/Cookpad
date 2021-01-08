package com.example.cookpad.ui.create;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookpad.R;
import com.example.cookpad.ui.create.CreateRecipe.IngredientsAdapter;
import com.example.cookpad.ui.create.AdapterItem.ItemIngredients;
import com.example.cookpad.ui.create.AdapterItem.ItemMethod;
import com.example.cookpad.ui.create.CreateRecipe.MethodAdapter;

import java.util.ArrayList;
import java.util.List;

public class CreateRecipeActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerIngredient;
    private IngredientsAdapter mIngredientAdapter;
    private RecyclerView recyclerViewMethod;
    private MethodAdapter mMethodAdapter;
    private Button addIngredientButton;
    private Button addMethodButton;
    private Button publishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
//        toolBarLayout.setTitle("Phở bò");
        recyclerIngredient = (RecyclerView) findViewById(R.id.recyclerIngredients);

        mIngredientAdapter = new IngredientsAdapter(generateIngredientsDummies(),this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerIngredient.setLayoutManager(mLayoutManager);
        recyclerIngredient.setItemAnimator(new DefaultItemAnimator());
        recyclerIngredient.setAdapter(mIngredientAdapter);


        recyclerViewMethod = (RecyclerView) findViewById(R.id.recyclerMethod);

        mMethodAdapter = new MethodAdapter(generateMehodDummies(),this);
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

    private List<ItemMethod> generateMehodDummies() {
        List<ItemMethod> itemList = new ArrayList<>();
        String name[] = {"Đập trứng", "Nấu cơm"};
        int imageID[] = {R.drawable.nau_pho_0, R.drawable.nau_pho_1};
        for (int i = 0; i<name.length; i++){
            ItemMethod itemMethod =new ItemMethod(name[i],String.valueOf(i),imageID[i]);
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

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.publishRecipeButton:
                break;
            case R.id.addIngredientButton:
                mIngredientAdapter.add(new ItemIngredients(""));
                break;
            case R.id.addStepButton:
                mMethodAdapter.add(new ItemMethod("",String.valueOf(mMethodAdapter.getItemCount()), R.drawable.nau_pho_3));
                break;
        }
    }
}