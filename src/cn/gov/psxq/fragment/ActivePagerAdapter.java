package cn.gov.psxq.fragment;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ActivePagerAdapter extends FragmentPagerAdapter {
    private List<Fragment>     fragments;
    private List<CharSequence> titles;
    private Context            context;

    public ActivePagerAdapter(FragmentManager fm, List<Fragment> fragments,
                              List<CharSequence> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    public Fragment getItem(int arg0) {
        return fragments.get(arg0);
    }

    public int getCount() {
        return fragments.size();
    }

    public CharSequence getPageTitle(int position) {
        return position < titles.size() ? titles.get(position) : "";
    }

}
