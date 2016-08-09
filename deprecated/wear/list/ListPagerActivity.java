package com.joaquimley.avenging.ui.list;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joaquimley.avenging.R;
import com.joaquimley.avenging.ui.base.BaseActivity;
import com.joaquimley.avenging.ui.character.CharacterActivity;
import com.joaquimley.core.data.model.CharacterMarvel;
import com.joaquimley.core.ui.list.ListPresenter;
import com.joaquimley.core.ui.list.ListPresenterView;

import java.util.List;

/**
 * Deprecated, using {@link ListActivity} you can use this if you wish to use {@link ListPagerAdapter}
 */
public class ListPagerActivity extends BaseActivity implements ListPresenterView, ListPagerAdapter.InteractionListener {

    private ListPresenter mListPresenter;
    private ViewPager mCharacterViewPager;
    private ListPagerAdapter mPagerAdapter;

    private ProgressBar mContentLoadingProgress;

    private View mMessageLayout;
    private ImageView mMessageImage;
    private TextView mMessageText;
    private Button mMessageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_list);
        mListPresenter = new ListPresenter();
        mListPresenter.attachView(this);

        initViews();
        mListPresenter.getCharacters();
    }

    private void initViews() {
        mPagerAdapter = new ListPagerAdapter(this, this);
        mCharacterViewPager = (ViewPager) findViewById(R.id.vp_characters);
        mCharacterViewPager.setAdapter(mPagerAdapter);

        mContentLoadingProgress = (ProgressBar) findViewById(R.id.progress);
        mMessageLayout = findViewById(R.id.message_layout);
        mMessageImage = (ImageView) findViewById(R.id.iv_message);
        mMessageText = (TextView) findViewById(R.id.tv_message);
        mMessageButton = (Button) findViewById(R.id.btn_try_again);
        mMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPagerAdapter.removeAll();
                mListPresenter.getCharacters();
            }
        });
    }

    @Override
    public void onListClick(CharacterMarvel character) {
        startActivity(CharacterActivity.newStartIntent(this, character));
    }

    @Override
    public void showCharacters(List<CharacterMarvel> characterList) {
        mPagerAdapter.addItems(characterList);
    }

    @Override
    public void showSearchedCharacters(List<CharacterMarvel> characterList) {
        // TODO: 04/08/16
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
        mCharacterViewPager.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}
