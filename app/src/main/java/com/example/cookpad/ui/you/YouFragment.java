package com.example.cookpad.ui.you;

import android.content.Intent;
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
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.example.cookpad.R;
import com.example.cookpad.ui.you.MyImageFragment;
import com.example.cookpad.ui.you.MyRecipeFragment;
import com.example.cookpad.ui.you.SavedRecipeFragment;
import com.google.android.material.tabs.TabLayout;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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