package com.del.stefan.customtablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by stefan on 3/30/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> mFragments = new ArrayList<>();
    ArrayList<String> mTabTitles = new ArrayList<>();

    public void addFragment(Fragment fragments, String tabTitles) {
        this.mFragments.add(fragments);
        this.mTabTitles.add(tabTitles);
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
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
        return mTabTitles.get(position);
    }
}
