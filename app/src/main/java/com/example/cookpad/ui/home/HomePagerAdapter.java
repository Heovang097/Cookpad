package com.example.cookpad.ui.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class HomePagerAdapter extends FragmentStateAdapter {
    public HomePagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public HomePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new InspirationFragment();
        else
            return new NetworkFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
