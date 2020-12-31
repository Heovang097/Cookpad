package com.example.cookpad.fragments.RecipeDetail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cookpad.R;

public class RecipeDetailFragment extends Fragment {
    View mFragmentView = null;
    Context mContext = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        return mFragmentView;
    }
}