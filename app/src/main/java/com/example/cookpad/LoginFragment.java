package com.example.cookpad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btnSignUp = getActivity().findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });

        Button btnLogin = getActivity().findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
    }

    private void login(View view)
    {
        String username = ((EditText)getActivity().findViewById(R.id.loginUsername)).getText().toString();
        String password = ((EditText)getActivity().findViewById(R.id.loginPassword)).getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        String url ="http://192.168.1.3:8000/login?username="+ username +"&password="+password;
        Log.d("@@@", url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("@@@", response);
                        if (response == "TRUE") {
                            Toast toast = Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else {
                            Toast toast = Toast.makeText(getActivity(), "Đăng nhập thất bại", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("@@@", "error");
                Toast toast = Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(getContext(), MenuActivity.class);
                startActivity(intent);
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
