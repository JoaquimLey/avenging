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

package com.joaquimley.avenging.ui.list;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joaquimley.avenging.R;
import com.joaquimley.avenging.ui.character.CharacterActivity;
import com.joaquimley.avenging.util.DisplayMetricsUtil;
import com.joaquimley.core.data.DataManager;
import com.joaquimley.core.data.model.CharacterMarvel;
import com.joaquimley.core.ui.list.ListContract;
import com.joaquimley.core.ui.list.ListPresenter;

import java.util.List;

public class ListFragment extends Fragment implements ListContract.ListView,
        ListAdapter.InteractionListener, SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener {

    private static final int TAB_LAYOUT_SPAN_SIZE = 2;
    private static final int TAB_LAYOUT_ITEM_SPAN_SIZE = 1;
    private static final int SCREEN_TABLET_DP_WIDTH = 600;

    private AppCompatActivity mActivity;
    private ListPresenter mListPresenter;
    private ListAdapter mListCharacterAdapter;
    private String mSearchQuery;

    private RecyclerView mCharactersRecycler;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mContentLoadingProgress;

    private View mMessageLayout;
    private ImageView mMessageImage;
    private TextView mMessageText;
    private Button mMessageButton;

    public ListFragment() {
    }

    public static ListFragment newInstance() {
        return newInstance(null);
    }

    public static ListFragment newInstance(@Nullable Bundle arguments) {
        ListFragment fragment = new ListFragment();
        if (arguments != null) {
            fragment.setArguments(arguments);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setEnterTransition(new Slide());
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        mListPresenter = new ListPresenter(DataManager.getInstance());
        mListCharacterAdapter = new ListAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        initViews(view);
        mListPresenter.attachView(this);
        mListCharacterAdapter.setListInteractionListener(this);
        if (mListCharacterAdapter.isEmpty()) {
            mListPresenter.onInitialListRequested();
        }
        return view;
    }

    private void initViews(View view) {
        mActivity = (AppCompatActivity) getActivity();
        mActivity.setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));

        mCharactersRecycler = (RecyclerView) view.findViewById(R.id.recycler_characters);
        mCharactersRecycler.setHasFixedSize(true);
        mCharactersRecycler.setMotionEventSplittingEnabled(false);
        mCharactersRecycler.setItemAnimator(new DefaultItemAnimator());
        mCharactersRecycler.setAdapter(mListCharacterAdapter);

        boolean isTabletLayout = DisplayMetricsUtil.isScreenW(SCREEN_TABLET_DP_WIDTH);
        mCharactersRecycler.setLayoutManager(setUpLayoutManager(isTabletLayout));
        mCharactersRecycler.addOnScrollListener(setupScrollListener(isTabletLayout,
                mCharactersRecycler.getLayoutManager()));

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mContentLoadingProgress = (ProgressBar) view.findViewById(R.id.progress);
        mMessageLayout = view.findViewById(R.id.message_layout);
        mMessageImage = (ImageView) view.findViewById(R.id.iv_message);
        mMessageText = (TextView) view.findViewById(R.id.tv_message);
        mMessageButton = (Button) view.findViewById(R.id.btn_try_again);
        mMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });
    }

    private RecyclerView.LayoutManager setUpLayoutManager(boolean isTabletLayout) {
        RecyclerView.LayoutManager layoutManager;
        if (!isTabletLayout) {
            layoutManager = new LinearLayoutManager(mActivity);
        } else {
            layoutManager = initGridLayoutManager(TAB_LAYOUT_SPAN_SIZE, TAB_LAYOUT_ITEM_SPAN_SIZE);
        }
        return layoutManager;
    }

    private RecyclerView.LayoutManager initGridLayoutManager(final int spanCount, final int itemSpanCount) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, spanCount);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mListCharacterAdapter.getItemViewType(position)) {
                    case ListAdapter.VIEW_TYPE_LOADING:
                        // If it is a loading view we wish to accomplish a single item per row
                        return spanCount;
                    default:
                        // Else, define the number of items per row (considering TAB_LAYOUT_SPAN_SIZE).
                        return itemSpanCount;
                }
            }
        });
        return gridLayoutManager;
    }

    private EndlessRecyclerViewOnScrollListener setupScrollListener(boolean isTabletLayout,
                                                                    RecyclerView.LayoutManager layoutManager) {
        return new EndlessRecyclerViewOnScrollListener(isTabletLayout ?
                (GridLayoutManager) layoutManager : (LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (mListCharacterAdapter.addLoadingView()) {
                    mListPresenter.onListEndReached(totalItemsCount, null, mSearchQuery);
                }
            }
        };
    }

    @Override
    public void onRefresh() {
        mListCharacterAdapter.removeAll();
        mListPresenter.onInitialListRequested();
    }


    @Override
    public void showCharacters(List<CharacterMarvel> characterList) {
        if (mListCharacterAdapter.getViewType() != ListAdapter.VIEW_TYPE_GALLERY) {
            mListCharacterAdapter.removeAll();
            mListCharacterAdapter.setViewType(ListAdapter.VIEW_TYPE_GALLERY);
        }

        if (!mSwipeRefreshLayout.isActivated()) {
            mSwipeRefreshLayout.setEnabled(true);
        }
        mListCharacterAdapter.addItems(characterList);
    }

    @Override
    public void showSearchedCharacters(List<CharacterMarvel> searchResults) {
        if (mListCharacterAdapter.getViewType() != ListAdapter.VIEW_TYPE_LIST) {
            mListCharacterAdapter.removeAll();
            mListCharacterAdapter.setViewType(ListAdapter.VIEW_TYPE_LIST);

        }
        if (mSwipeRefreshLayout.isActivated()) {
            mSwipeRefreshLayout.setEnabled(false);
        }
        mListCharacterAdapter.addItems(searchResults);
    }

    @Override
    public void showProgress() {
        if (mListCharacterAdapter.isEmpty() && !mSwipeRefreshLayout.isRefreshing()) {
            mContentLoadingProgress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
        mContentLoadingProgress.setVisibility(View.GONE);
        mListCharacterAdapter.removeLoadingView();
    }

    @Override
    public void showUnauthorizedError() {
        mMessageImage.setImageResource(R.drawable.ic_error_list);
        mMessageText.setText(getString(R.string.error_generic_server_error, "Unauthorized"));
        mMessageButton.setText(getString(R.string.action_try_again));
        showMessageLayout(true);
    }

    @Override
    public void showError(String errorMessage) {
        mMessageImage.setImageResource(R.drawable.ic_error_list);
        mMessageText.setText(getString(R.string.error_generic_server_error, errorMessage));
        mMessageButton.setText(getString(R.string.action_try_again));
        showMessageLayout(true);
    }

    @Override
    public void showEmpty() {
        mMessageImage.setImageResource(R.drawable.ic_clear);
        mMessageText.setText(getString(R.string.error_no_items_to_display));
        mMessageButton.setText(getString(R.string.action_check_again));
        showMessageLayout(true);
    }

    @Override
    public void showMessageLayout(boolean show) {
        mMessageLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        mCharactersRecycler.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onListClick(CharacterMarvel character, View sharedElementView, int adapterPosition) {
        startActivity(CharacterActivity.newStartIntent(mActivity, character),
                makeTransitionBundle(sharedElementView));
    }

    private Bundle makeTransitionBundle(View sharedElementView) {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity,
                sharedElementView, ViewCompat.getTransitionName(sharedElementView)).toBundle();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setQueryHint(getString(R.string.action_search));
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.action_search),
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        mSearchQuery = "";
                        mListCharacterAdapter.setViewType(ListAdapter.VIEW_TYPE_LIST);
                        return true;
                    }
                });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String queryText) {
        mSearchQuery = queryText;
        if (!TextUtils.isEmpty(mSearchQuery)) {
            mListPresenter.onCharacterSearched(mSearchQuery);
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        mCharactersRecycler.setAdapter(null);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mListPresenter.detachView();
        super.onDestroy();
    }
}