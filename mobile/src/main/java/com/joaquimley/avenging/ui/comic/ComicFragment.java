/*
 * Copyright (c) Joaquim Ley 2016. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.joaquimley.avenging.ui.comic;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joaquimley.avenging.R;
import com.joaquimley.core.data.model.Comic;

import java.util.ArrayList;
import java.util.List;

/**
 * Use the {@link ComicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComicFragment extends Fragment implements ViewPager.OnPageChangeListener {

    public static final String TAG = "ComicFragment";

    private static final String ARG_COMIC_LIST = "argComicList";
    private static final String ARG_CLICKED_POSITION = "argClickedPosition";
    private static final String ARG_TRANSACTION_NAME = "argTransactionName";
    private static final int VIEW_PAGER_OFF_SCREEN_LIMIT = 2;

    private int mClickedPosition;
    private String mTransactionName;
    private List<Comic> mComicList;
    private ComicViewPagerAdapter mViewPagerAdapter;

    private AppCompatActivity mActivity;
    private TextView mPageCounter;

    public static ComicFragment newInstance(List<Comic> comicList, int clickedPosition) {
        return newInstance(comicList, null, clickedPosition);
    }

    public static ComicFragment newInstance(List<Comic> comicList, @Nullable String transactionName, int clickedPosition) {
        ComicFragment fragment = new ComicFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CLICKED_POSITION, clickedPosition);
        if (transactionName != null) {
            args.putString(ARG_TRANSACTION_NAME, transactionName);

        }
        args.putParcelableArrayList(ARG_COMIC_LIST, (ArrayList<Comic>) comicList);
        fragment.setArguments(args);
        setupTransactions(fragment);
        return fragment;
    }

    private static void setupTransactions(Fragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementEnterTransition(new AutoTransition());
            fragment.setSharedElementReturnTransition(new AutoTransition());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mComicList = getArguments().getParcelableArrayList(ARG_COMIC_LIST);
            mClickedPosition = getArguments().getInt(ARG_CLICKED_POSITION);
            mTransactionName = getArguments().getString(ARG_TRANSACTION_NAME);
        }
        mActivity = (AppCompatActivity) getActivity();
        mViewPagerAdapter = new ComicViewPagerAdapter(mActivity.getSupportFragmentManager(), mComicList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comic, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        mPageCounter = (TextView) view.findViewById(R.id.tv_page_count);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.vp_comic_fragments);
        viewPager.setOffscreenPageLimit(VIEW_PAGER_OFF_SCREEN_LIMIT);
        viewPager.addOnPageChangeListener(this);
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                final float normalizedPosition = Math.abs(Math.abs(position) - 1);
                page.setScaleX(normalizedPosition / 2 + 0.5f);
                page.setScaleY(normalizedPosition / 2 + 0.5f);
            }
        });

        if (mViewPagerAdapter != null) {
            viewPager.setAdapter(mViewPagerAdapter);
            viewPager.setCurrentItem(mClickedPosition, true);
            ViewCompat.setTransitionName(viewPager, mTransactionName);
        }

        (view.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mPageCounter.setText(mActivity.getString(R.string.page_count_placeholder,
                position + 1, mComicList.size()));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onDetach() {
        mViewPagerAdapter = null;
        mComicList = null;
        super.onDetach();
    }
}
