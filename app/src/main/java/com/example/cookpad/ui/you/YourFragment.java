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
=======
 * Use the {@link com.example.cookpad.ui.you.MyRecipeFragment   #newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyRecipeFragment extends Fragment {
>>>>>>> ky:app/src/main/java/com/example/cookpad/ui/you/MyRecipeFragment.java

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

<<<<<<< HEAD:app/src/main/java/com/example/cookpad/ui/you/YourFragment.java
    public YourFragment() {
=======
    public MyRecipeFragment() {
>>>>>>> ky:app/src/main/java/com/example/cookpad/ui/you/MyRecipeFragment.java
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
<<<<<<< HEAD:app/src/main/java/com/example/cookpad/ui/you/YourFragment.java
     * @return A new instance of fragment YourFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YourFragment newInstance(String param1, String param2) {
        YourFragment fragment = new YourFragment();
=======
     * @return A new instance of fragment MyRecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static com.example.cookpad.ui.you.MyRecipeFragment newInstance(String param1, String param2) {
        com.example.cookpad.ui.you.MyRecipeFragment fragment = new com.example.cookpad.ui.you.MyRecipeFragment();
>>>>>>> ky:app/src/main/java/com/example/cookpad/ui/you/MyRecipeFragment.java
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
<<<<<<< HEAD:app/src/main/java/com/example/cookpad/ui/you/YourFragment.java
        return inflater.inflate(R.layout.fragment_your, container, false);
=======
        return inflater.inflate(R.layout.fragment_my_recipe, container, false);
>>>>>>> ky:app/src/main/java/com/example/cookpad/ui/you/MyRecipeFragment.java
    }
}