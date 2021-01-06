package com.example.cookpad.ui.login;

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
import com.example.cookpad.NetWork;
import com.example.cookpad.R;

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
        Button btnBackLogin = getActivity().findViewById(R.id.btnBackLogin);
        btnBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);
            }
        });
    }

    private void signUp(final View view) {
        String username = ((EditText) getActivity().findViewById(R.id.signUpUsername)).getText().toString();
        String password = ((EditText) getActivity().findViewById(R.id.signUpPassword)).getText().toString();
        String name = ((EditText) getActivity().findViewById(R.id.signUpName)).getText().toString();
        if(username.equals(""))
        {
            Toast toast = Toast.makeText(getActivity(), "Bạn chưa nhập username!!!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(password.equals(""))
        {
            Toast toast = Toast.makeText(getActivity(), "Bạn chưa nhập password!!!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(name.equals(""))
        {
            Toast toast = Toast.makeText(getActivity(), "Bạn chưa nhập name!!!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        //Luu database
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        String url ="http://"+ getResources().getString(R.string.serverSocket) +"/44326?username="+ username +"&password="+password+"&name="+name;
        Log.d("@@@", url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("@@@", response);
                        if (response.equals("Sign up success")) {
                            Toast toast = Toast.makeText(getActivity(), "Đăng ký thành công", Toast.LENGTH_LONG);
                            toast.show();
                            Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);
                        }
                        else if (response.equals("Tai Khoan da ton tai")){
                            Toast toast = Toast.makeText(getActivity(), "Tài khoản đăng ký đã tồn tại", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else{
                            Toast toast = Toast.makeText(getActivity(), "Đăng ký thất bại", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("@@@", "error");
                Toast toast = Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG);
                toast.show();
            }
        });
        queue.add(stringRequest);

    }
}
