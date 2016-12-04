package com.k2infosoft.k2veticalintro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Chatikyan on 18.10.2016.
 */

class K2VerticalIntroPagerAdapter extends FragmentPagerAdapter {

    private List<K2VerticalIntroItem> verticalIntroItemList;

    K2VerticalIntroPagerAdapter(FragmentManager fm, List<K2VerticalIntroItem> verticalIntroItemList) {
        super(fm);
        this.verticalIntroItemList = verticalIntroItemList;
    }

    @Override
    public Fragment getItem(int position) {
        K2VerticalIntroItem verticalIntroItem = verticalIntroItemList.get(position);
        return K2VerticalIntroFragment.newInstance(verticalIntroItem);
    }

    @Override
    public int getCount() {
        return verticalIntroItemList.size();
    }
}
