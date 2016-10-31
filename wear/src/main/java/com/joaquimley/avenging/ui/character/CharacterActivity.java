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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joaquimley.avenging.R;
import com.joaquimley.core.data.DataManager;
import com.joaquimley.core.data.model.CharacterMarvel;
import com.joaquimley.core.data.model.Comic;
import com.joaquimley.core.ui.character.CharacterContract;
import com.joaquimley.core.ui.character.CharacterPresenter;

import java.util.List;


public class CharacterActivity extends Activity implements CharacterContract.CharacterView {

    private static final String EXTRA_CHARACTER = "extraCharacter";

    private CharacterPresenter mCharacterPresenter;
    private CharacterMarvel mCharacter;

    private ProgressBar mContentLoadingProgress;
    private View mMessageLayout;
    private ImageView mMessageImage;
    private TextView mMessageText;
    private Button mMessageButton;


    public static Intent newStartIntent(Context context, CharacterMarvel characterMarvel) {
        Intent intent = new Intent(context, CharacterActivity.class);
        intent.putExtra(EXTRA_CHARACTER, characterMarvel);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mCharacter = getIntent().getExtras().getParcelable(EXTRA_CHARACTER);
        mCharacterPresenter = new CharacterPresenter(DataManager.getInstance());
        mCharacterPresenter.attachView(this);

        initViews();
        mCharacterPresenter.onCharacterRequested(mCharacter.getId());
    }

    private void initViews() {
        mContentLoadingProgress = (ProgressBar) findViewById(R.id.progress);
        mMessageLayout = findViewById(R.id.message_layout);
        mMessageImage = (ImageView) findViewById(R.id.iv_message);
        mMessageText = (TextView) findViewById(R.id.tv_message);
        mMessageButton = (Button) findViewById(R.id.btn_try_again);
        mMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCharacterPresenter.onCharacterRequested(mCharacter.getId());
            }
        });
    }

    @Override
    public void showCharacter(CharacterMarvel character) {
        mCharacter = character;
//        if (mDescriptionWrapper == null && !mCharacter.getDescription().isEmpty()) {
//            mDescriptionWrapper = new DescriptionFrameWrapper(mActivity,
//                    mActivity.getResources().getString(R.string.description),
//                    mCharacter.getDescription());
//            mContentFrame.addView(mDescriptionWrapper);
//        }
//
//        List<Comic> characterComics = character.getComics().getItems();
//        if (!characterComics.isEmpty()) {
//            mComicWrapper = new ComicFrameWrapper(mActivity, getString(R.string.comics), characterComics, this);
//            mContentFrame.addView(mComicWrapper);
//            mCharacterPresenter.onCharacterComicsRequested(character.getId(), characterComics.size());
//        }
//
//        List<Comic> characterSeries = character.getSeries().getItems();
//        if (!characterSeries.isEmpty()) {
//            mSeriesWrapper = new ComicFrameWrapper(mActivity, getString(R.string.series), characterSeries, this);
//            mContentFrame.addView(mSeriesWrapper);
//            mCharacterPresenter.onCharacterSeriesRequested(character.getId(), characterSeries.size());
//        }
//
//        List<Comic> characterStories = character.getStories().getItems();
//        if (!characterStories.isEmpty()) {
//            mStoriesWrapper = new ComicFrameWrapper(mActivity, getString(R.string.stories), characterStories, this);
//            mContentFrame.addView(mStoriesWrapper);
//            mCharacterPresenter.onCharacterStoriesRequested(character.getId(), characterStories.size());
//        }
//
//        List<Comic> characterEvents = character.getEvents().getItems();
//        if (!characterEvents.isEmpty()) {
//            mEventsWrapper = new ComicFrameWrapper(mActivity, getString(R.string.events), characterEvents, this);
//            mContentFrame.addView(mEventsWrapper);
//            mCharacterPresenter.onCharacterEventsRequested(character.getId(), characterEvents.size());
//        }
//
//        if (!character.getUrls().isEmpty()) {
//            mContentFrame.addView(new UrlFrameWrapper(mActivity,
//                    mActivity.getResources().getString(R.string.related_links), character.getUrls()));
//        }
        // TODO: 04/08/16 see wear module
    }

    @Override
    public void showComicList(List<Comic> comicList) {

    }

    @Override
    public void showSeriesList(List<Comic> seriesList) {

    }

    @Override
    public void showStoriesList(List<Comic> storiesList) {

    }

    @Override
    public void showEventsList(List<Comic> eventsList) {

    }

    @Override
    public void showProgress() {
        mContentLoadingProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mContentLoadingProgress.setVisibility(View.GONE);
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
    }
}
