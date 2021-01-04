package com.example.cookpad.ui.you;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cookpad.AccountInfo;
import com.example.cookpad.MainActivity;
import com.example.cookpad.NetWork;
import com.example.cookpad.R;
import com.example.cookpad.ui.activity.MainPageActivity;
import com.example.cookpad.ui.you.MyImageFragment;
import com.example.cookpad.ui.you.MyRecipeFragment;
import com.example.cookpad.ui.you.SavedRecipeFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class YouFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_you, container, false);
        Toolbar toolbar = (Toolbar) (Toolbar) view.findViewById(R.id.toolbarYou);
        toolbar.setLogo(R.drawable.avatar);
        toolbar.setTitle(" Đoàn Văn Thanh An");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.YourPage:
                        Toast.makeText(getActivity(), "YourPage!", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(getView()).navigate(R.id.navigation_your);
                        return true;
                    case R.id.UpdateInfo:
                        Toast.makeText(getActivity(), "Update", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), UpdateActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.Logout:

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
                        sharedPreferences.edit().remove("user_ID").commit();
                        String url = "http://"+ NetWork.getNetworkInfoHolder().getSERVER()+"/42532?id="+ AccountInfo.getAccountInfoHolder().getUserID();

                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("Yes")){
                                    Toast.makeText(getActivity(), "Log out success", Toast.LENGTH_SHORT).show();
                                    Intent intent1 = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent1);
                                }
                                else{

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                            }
                        });
                        queue.add(stringRequest);
                        return true;
                }
                Toast.makeText(getActivity(), "This is my Toast message!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pagerYou);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.tabsYou);
        tabs.setupWithViewPager(viewPager);
        return view;
    }
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new SavedRecipeFragment(), "Món đã lưu");
        adapter.addFragment(new MyRecipeFragment(), "Món của tôi");
        adapter.addFragment(new MyImageFragment(), "Hình thực hành");
        viewPager.setAdapter(adapter);
    }
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}