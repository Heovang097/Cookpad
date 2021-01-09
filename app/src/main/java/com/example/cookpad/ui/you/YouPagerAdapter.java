package com.example.cookpad.ui.you;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class YouPagerAdapter extends FragmentStateAdapter {
    public YouPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public YouPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new SavedRecipeFragment();
        else if (position == 1)
            return new MyRecipeFragment();
        else
            return new MyImageFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
