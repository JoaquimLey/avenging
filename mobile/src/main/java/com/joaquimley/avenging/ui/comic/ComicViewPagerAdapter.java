package com.joaquimley.avenging.ui.comic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.joaquimley.core.data.model.Comic;

import java.util.ArrayList;
import java.util.List;

public class ComicViewPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Comic> mComicList;

    public ComicViewPagerAdapter(FragmentManager fragmentManager, List<Comic> comicList) {
        super(fragmentManager);
        mComicList = new ArrayList<>();
        mComicList.addAll(comicList);
    }

    @Override
    public Fragment getItem(int position) {
        return ComicViewPagerFragment.newInstance(mComicList.get(position));
    }

    @Override
    public int getCount() {
        return mComicList.size();
    }
}