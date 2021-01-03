package com.example.cookpad.ui.you;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.cookpad.R;

/**
 * A simple {@link Fragment} subclass.
<<<<<<< HEAD:app/src/main/java/com/example/cookpad/ui/you/YourFragment.java
 * Use the {@link YourFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YourFragment extends Fragment {

    public YourFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_your, container, false);


    }
}