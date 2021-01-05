package com.example.cookpad.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.cookpad.AccountInfo;
import com.example.cookpad.NetWork;
import com.example.cookpad.ui.activity.MainPageActivity;
import com.example.cookpad.R;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {
    private static final String SHARED_PREFS = "SHARED_PREFS";
    private static final String USER_ID = "user_ID";

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

        /*getPref();
        if(!AccountInfo.getAccountInfoHolder().getUserID().equals("")){
            checkStillLogin(AccountInfo.getAccountInfoHolder().getUserID());
        }*/
    }

    private void login(View view)
    {
        String username = ((EditText)getActivity().findViewById(R.id.loginUsername)).getText().toString();
        String password = ((EditText)getActivity().findViewById(R.id.loginPassword)).getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        String url ="http://"+ getResources().getString(R.string.serverSocket) +"/44325?n="+ username +"&p="+password + "&c=9";

        Log.d("@@@", url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("@@@", response);
                        if (response.equals( "Login success")) {
                            getUserIDtoAccountInfo(username);

                            Toast toast = Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_LONG);
                            toast.show();
                            Intent intent = new Intent(getContext(), MainPageActivity.class);
                            startActivity(intent);
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
                Toast toast = Toast.makeText(getActivity(), "Không thể kết nối tới server", Toast.LENGTH_LONG);
                toast.show();

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void getUserIDtoAccountInfo(String username){
        String url = "http://"+ getResources().getString(R.string.serverSocket) +"/41111?n="+username;

        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AccountInfo.getAccountInfoHolder().setupInfo(response);
                setPref();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Không thể kết nối tới server", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }

    private void setPref(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, AccountInfo.getAccountInfoHolder().getUserID());
        editor.apply();
    }
}
