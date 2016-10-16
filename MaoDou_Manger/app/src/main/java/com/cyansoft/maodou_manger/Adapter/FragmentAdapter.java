package com.cyansoft.maodou_manger.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cyansoft.maodou_manger.Activity.FirstActivity;
import com.cyansoft.maodou_manger.fragment.DetailFragment;
import com.cyansoft.maodou_manger.fragment.InfoFragment;
import com.cyansoft.maodou_manger.fragment.MeFragment;

/**
 * Created by author:StarryFei on 2016/8/19 0019.
 * Email:760646630fei@sina.com
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 3;
    private InfoFragment infoFragment =null;
    private DetailFragment mDetailFragment = null;
    private MeFragment meFragment =null;
//    private FindFragment mFindFragment = null;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        infoFragment = new InfoFragment();
        mDetailFragment = new DetailFragment();
//        updateFragment = new UpdateFragment();
        meFragment = new MeFragment();
//        mFindFragment = new FindFragment();

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case FirstActivity.PAGE_ONE:
                fragment = infoFragment;
                break;
//            case FirstActivity.PAGE_TWO:
//                fragment = mFindFragment;
//                break;
            case FirstActivity.PAGE_THREE:
                fragment = mDetailFragment;
                break;
            case FirstActivity.PAGE_FOUR:
                fragment = meFragment;
                break;
        }
        return fragment;

    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
}
