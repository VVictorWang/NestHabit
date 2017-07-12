package com.victor.nesthabit.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.victor.nesthabit.fragments.BirdCageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 7/2/17.
 * email: chengyiwang@hustunique.com
 * blog:  www.victorwang.science
 */

public class MyFragPageAdapter extends FragmentPagerAdapter{
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();

    public MyFragPageAdapter(FragmentManager fm) {
        super(fm);
        mFragments.add(new BirdCageFragment());
        mTitles.add("鸟窝");
    }

    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
