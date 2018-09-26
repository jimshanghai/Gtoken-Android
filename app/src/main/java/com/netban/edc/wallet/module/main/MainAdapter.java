package com.netban.edc.wallet.module.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netban.edc.wallet.R;

import java.util.List;

/**
 * Created by Evan on 2018/8/3.
 */

public class MainAdapter extends FragmentPagerAdapter {
    private List<Fragment> mlist;
    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setMlist(List<Fragment> mlist) {
        this.mlist = mlist;
    }

    @Override
    public Fragment getItem(int position) {

        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist==null?0:mlist.size();
    }

}
