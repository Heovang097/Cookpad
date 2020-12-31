package com.example.cookpadtest1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.mypackage.utils.RecyclerAdapter;

import java.util.ArrayList;

public class NetworkFragment extends Fragment {
    private RecyclerView recyclerView;
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
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(view.getContext(), addRecipes());
        recyclerView.setAdapter(recyclerAdapter);
    }

    private ArrayList addRecipes() {
        ArrayList recipes = new ArrayList<>();
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "Pumpkin Spice Pancakes", R.drawable.daljin));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "Red Velvet Marbled New York Cheesecake", R.drawable.daljin));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "Raw mango chutney", R.drawable.daljin));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "Purple rice", R.drawable.daljin));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "Mother's Cassava Cake", R.drawable.daljin));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "10 min pasta", R.drawable.daljin));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "White Gingerbread Cookies", R.drawable.daljin));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "Salmon in oven", R.drawable.daljin));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "Duck pot pie", R.drawable.daljin));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "Homemade cappuccino", R.drawable.daljin));
        recipes.add(new RecipeCard("cookie", R.drawable._55_jura, "Budget Carbonara", R.drawable.daljin));
        return recipes;
    }
}
