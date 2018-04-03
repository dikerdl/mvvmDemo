package com.icode.jiling.na517demo_mvvm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

public class AnimPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> viewLists;

    private ArrayList<String> listTitles;

    public AnimPagerAdapter(FragmentManager fm, ArrayList<Fragment> viewLists, ArrayList<String> listTitles) {
        super(fm);
        this.viewLists = viewLists;
        this.listTitles = listTitles;
    }

    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public Fragment getItem(int position) {
        return viewLists.get(position);
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitles.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //注释后始终不会销毁
        /*super.destroyItem(container, position, object);*/
    }
}