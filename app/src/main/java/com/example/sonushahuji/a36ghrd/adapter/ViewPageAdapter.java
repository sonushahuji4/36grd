package com.example.sonushahuji.a36ghrd.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapter extends FragmentPagerAdapter
{
    private final List<Fragment> lstFragment = new ArrayList<>();// for adding the different fragment 1st declare
    private  final List<String> lstTitle = new ArrayList<>(); // for adding the title(tab) for fragment

    public ViewPageAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {

        return lstFragment.get(position);// takes the fragment index
    }

    @Override
    public int getCount()
    {
        return lstTitle.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {

        return lstTitle.get(position);
    }

    //this method is to add different fragments and title to the tabs and calling this method inside the main method and passing the fragment and title or icon
    public void AddFragment(Fragment fragment, String title)
    {
        lstFragment.add(fragment);
        lstTitle.add(title);
    }
}
