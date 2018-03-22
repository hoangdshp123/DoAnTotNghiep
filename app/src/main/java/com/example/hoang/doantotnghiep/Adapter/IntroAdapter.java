package com.example.hoang.doantotnghiep.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Admin on 12/19/2017.
 */

public class IntroAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> listFragments;

    public IntroAdapter(FragmentManager fm, ArrayList<Fragment> listFragments) {
        super(fm);
        this.listFragments = listFragments;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = listFragments.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return listFragments.size();
    }
}
