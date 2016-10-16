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

package com.joaquimley.avenging.ui.character;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joaquimley.avenging.R;
import com.joaquimley.avenging.ui.comic.ComicAdapter;
import com.joaquimley.avenging.ui.comic.ComicFragment;
import com.joaquimley.avenging.util.widgets.ComicFrameWrapper;
import com.joaquimley.avenging.util.widgets.DescriptionFrameWrapper;
import com.joaquimley.avenging.util.widgets.UrlFrameWrapper;
import com.joaquimley.core.data.DataManager;
import com.joaquimley.core.data.model.CharacterMarvel;
import com.joaquimley.core.data.model.Comic;
import com.joaquimley.core.ui.character.CharacterContract;
import com.joaquimley.core.ui.character.CharacterPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CharacterFragment extends Fragment implements CharacterContract.CharacterView,
        ComicAdapter.InteractionListener {

    private static final String ARG_CHARACTER = "argCharacter";

    private CharacterPresenter mCharacterPresenter;
    private CharacterMarvel mCharacterMarvel;

    private AppCompatActivity mActivity;
    private LinearLayout mContentFrame;
    private ProgressBar mContentLoadingProgress;

    private DescriptionFrameWrapper mDescriptionWrapper;
    private ComicFrameWrapper mComicWrapper;
    private ComicFrameWrapper mSeriesWrapper;
    private ComicFrameWrapper mStoriesWrapper;
    private ComicFrameWrapper mEventsWrapper;

    private View mMessageLayout;
    private ImageView mMessageImage;
    private TextView mMessageText;
    private Button mMessageButton;

    public static CharacterFragment newInstance(CharacterMarvel characterMarvel) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_CHARACTER, characterMarvel);
        CharacterFragment fragment = new CharacterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle onSavedInstanceState) {
        super.onActivityCreated(onSavedInstanceState);
        getActivity().supportStartPostponedEnterTransition();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mCharacterPresenter = new CharacterPresenter(DataManager.getInstance());
        if (savedInstanceState != null) {
            mCharacterMarvel = savedInstanceState.getParcelable(ARG_CHARACTER);
        } else if (getArguments() != null) {
            mCharacterMarvel = getArguments().getParcelable(ARG_CHARACTER);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(ARG_CHARACTER, mCharacterMarvel);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character, container, false);

        mCharacterPresenter.attachView(this);
        initViews(view);
        mCharacterPresenter.onCharacterRequested(mCharacterMarvel.getId());
        return view;
    }

    private void initViews(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(mCharacterMarvel != null ? mCharacterMarvel.getName()
                : mActivity.getString(R.string.character_details));

        mActivity = (AppCompatActivity) getActivity();
        mActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Picasso.with(mActivity)
                .load(mCharacterMarvel.getImageUrl())
                .centerCrop()
                .fit()
                .into((ImageView) view.findViewById(R.id.iv_header));

        mContentFrame = (LinearLayout) view.findViewById(R.id.details_content_frame);
        if (!mCharacterMarvel.getDescription().isEmpty()) {
            mDescriptionWrapper = new DescriptionFrameWrapper(mActivity,
                    mActivity.getResources().getString(R.string.description),
                    mCharacterMarvel.getDescription());
            mContentFrame.addView(mDescriptionWrapper);
        }

        mContentLoadingProgress = (ProgressBar) view.findViewById(R.id.progress);
        mMessageLayout = view.findViewById(R.id.message_layout);
        mMessageImage = (ImageView) view.findViewById(R.id.iv_message);
        mMessageText = (TextView) view.findViewById(R.id.tv_message);
        mMessageButton = (Button) view.findViewById(R.id.btn_try_again);
        mMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCharacterPresenter.onCharacterRequested(mCharacterMarvel.getId());
            }
        });
    }

    @Override
    public void showCharacter(CharacterMarvel character) {

        mCharacterMarvel = character;
        if (mDescriptionWrapper == null && !mCharacterMarvel.getDescription().isEmpty()) {
            mDescriptionWrapper = new DescriptionFrameWrapper(mActivity,
                    mActivity.getResources().getString(R.string.description),
                    mCharacterMarvel.getDescription());
            mContentFrame.addView(mDescriptionWrapper);
        }

        List<Comic> characterComics = character.getComics().getItems();
        if (!characterComics.isEmpty()) {
            mComicWrapper = new ComicFrameWrapper(mActivity, getString(R.string.comics), characterComics, this);
            mContentFrame.addView(mComicWrapper);
            mCharacterPresenter.onCharacterComicsRequested(character.getId(), characterComics.size());
        }

        List<Comic> characterSeries = character.getSeries().getItems();
        if (!characterSeries.isEmpty()) {
            mSeriesWrapper = new ComicFrameWrapper(mActivity, getString(R.string.series), characterSeries, this);
            mContentFrame.addView(mSeriesWrapper);
            mCharacterPresenter.onCharacterSeriesRequested(character.getId(), characterSeries.size());
        }

        List<Comic> characterStories = character.getStories().getItems();
        if (!characterStories.isEmpty()) {
            mStoriesWrapper = new ComicFrameWrapper(mActivity, getString(R.string.stories), characterStories, this);
            mContentFrame.addView(mStoriesWrapper);
            mCharacterPresenter.onCharacterStoriesRequested(character.getId(), characterStories.size());
        }

        List<Comic> characterEvents = character.getEvents().getItems();
        if (!characterEvents.isEmpty()) {
            mEventsWrapper = new ComicFrameWrapper(mActivity, getString(R.string.events), characterEvents, this);
            mContentFrame.addView(mEventsWrapper);
            mCharacterPresenter.onCharacterEventsRequested(character.getId(), characterEvents.size());
        }

        if (!character.getUrls().isEmpty()) {
            mContentFrame.addView(new UrlFrameWrapper(mActivity,
                    mActivity.getResources().getString(R.string.related_links), character.getUrls()));
        }
        TextView copyRightTextView = new TextView(mActivity);
        copyRightTextView.setText(getString(R.string.marvel_copyright_notice));
        mContentFrame.addView(copyRightTextView);
    }

    @Override
    public void onComicClick(List<Comic> comicList, ImageView sharedImageView, int clickedPosition) {
        if (mActivity.getSupportFragmentManager().findFragmentByTag(ComicFragment.TAG) == null) {
            mActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.character_container, ComicFragment.newInstance(comicList, clickedPosition))
                    .addSharedElement(sharedImageView, ViewCompat.getTransitionName(sharedImageView))
                    .addToBackStack(ComicFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void showProgress() {
        if (mContentLoadingProgress.getVisibility() != View.VISIBLE) {
            mContentLoadingProgress.setVisibility(View.VISIBLE);
        }
        mContentFrame.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mContentLoadingProgress.setVisibility(View.GONE);
        mContentFrame.setVisibility(View.VISIBLE);
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
        mMessageText.setText(getString(R.string.error_no_char_info_to_display));
        mMessageButton.setText(getString(R.string.action_check_again));
        showMessageLayout(true);
    }

    @Override
    public void showMessageLayout(boolean show) {
        mMessageLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        mContentFrame.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showComicList(final List<Comic> comicList) {
        mComicWrapper.loadImages(comicList);
    }

    @Override
    public void showSeriesList(List<Comic> seriesList) {
        mSeriesWrapper.loadImages(seriesList);
    }

    @Override
    public void showStoriesList(List<Comic> storiesList) {
        mStoriesWrapper.loadImages(storiesList);
    }

    @Override
    public void showEventsList(List<Comic> eventsList) {
        mEventsWrapper.loadImages(eventsList);
    }

    @Override
    public void onDestroy() {
        mCharacterPresenter.detachView();
        super.onDestroy();
    }
}
