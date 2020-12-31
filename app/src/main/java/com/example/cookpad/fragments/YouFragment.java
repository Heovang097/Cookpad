package com.example.cookpad.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cookpad.R;
import com.example.cookpad.fragments.you.MyImageFragment;
import com.example.cookpad.fragments.you.MyRecipeFragment;
import com.example.cookpad.fragments.you.SavedRecipeFragment;
import com.example.cookpad.fragments.you.UpdateActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class YouFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_you, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbarYou);
        toolbar.setLogo(R.drawable.avatar);
        toolbar.setTitle(" Đoàn Văn Thanh An");
        toolbar.inflateMenu(R.menu.menu_you);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.YourPage:
                        Toast.makeText(getActivity(), "Your page", Toast.LENGTH_SHORT).show();
                        YouFragment your = new YouFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.YouF, your);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.UpdateInfo:
                        Toast.makeText(getActivity(), "Update", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), UpdateActivity.class);
                        startActivity(intent);
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
