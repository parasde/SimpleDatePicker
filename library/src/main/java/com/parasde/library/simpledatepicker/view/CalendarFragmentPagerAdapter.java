package com.parasde.library.simpledatepicker.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CalendarFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragmentPagerArray = new ArrayList<>();
    private ArrayList<String> fragmentTag = new ArrayList<>();

    CalendarFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentPagerArray.get(position);
    }

    @Override
    public int getCount() {
        return fragmentPagerArray.size();
    }

    @Override
    public int getItemPosition(@NotNull Object obj) {
        return POSITION_NONE;
    }

    void addPrevItem(Fragment fragment, String tag) {
        fragmentPagerArray.add(0, fragment);
        fragmentTag.add(0, tag);
        notifyDataSetChanged();
    }

    void addItem(Fragment fragment, String tag) {
        fragmentPagerArray.add(fragment);
        fragmentTag.add(tag);
        notifyDataSetChanged();
    }
}
