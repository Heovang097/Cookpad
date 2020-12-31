package com.example.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;

public class SignUpFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button btnSignUp = getActivity().findViewById(R.id.btnSignUpDone);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(v);
            }
        });
    }

    private void signUp(View view) {
        String username = ((TextInputLayout) getActivity().findViewById(R.id.signUpUsername)).getEditText().getText().toString();
        String password = ((TextInputLayout) getActivity().findViewById(R.id.signUpPassword)).getEditText().getText().toString();
        String name = ((TextInputLayout) getActivity().findViewById(R.id.signUpName)).getEditText().getText().toString();
        //Luu database
        Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);
    }
}
