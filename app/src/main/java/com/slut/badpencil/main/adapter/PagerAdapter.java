package com.slut.badpencil.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/28.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private String[] titleArr;

    public PagerAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titleArr) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleArr = titleArr;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragmentList != null && !fragmentList.isEmpty() && position < fragmentList.size()) {
            return fragmentList.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (fragmentList != null && !fragmentList.isEmpty()) {
            return fragmentList.size();
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titleArr != null && titleArr.length != 0 && position < titleArr.length) {
            return titleArr[position];
        }
        return super.getPageTitle(position);
    }
}
