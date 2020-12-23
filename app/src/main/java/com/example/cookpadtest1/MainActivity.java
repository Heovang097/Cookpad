package com.example.cookpadtest1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.os.Bundle;
import com.mypackage.utils.RecyclerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList recipes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        addRecipes();
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, recipes);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void addRecipes() {
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "Pumpkin Spice Pancakes"));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "Red Velvet Marbled New York Cheesecake"));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "Raw mango chutney"));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "Purple rice"));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "Mother's Cassava Cake"));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "10 min pasta"));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "White Gingerbread Cookies"));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "Salamon in oven"));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "Duck pot pie"));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "Homemade cappuccino"));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "Budget Carbonara"));
    }
}