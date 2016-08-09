package com.joaquimley.avenging.ui.list;

import android.os.Bundle;
import android.support.wearable.view.GridViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joaquimley.avenging.R;
import com.joaquimley.avenging.ui.base.BaseActivity;
import com.joaquimley.core.data.model.CharacterMarvel;
import com.joaquimley.core.ui.list.ListPresenter;
import com.joaquimley.core.ui.list.ListPresenterView;

import java.util.List;

/**
 * You can view more implementations of a list (as viewable on README.MD), this can be found
 * on the "deprecated/wear/list" folder.
 */
public class ListActivity extends BaseActivity implements ListPresenterView,
        ListAdapter.InteractionListener, GridViewPager.OnPageChangeListener {

    private ListPresenter mListPresenter;

    private GridViewPager mCharacterGridPager;
    private ListAdapter mGridPagerAdapter;

    private ProgressBar mContentLoadingProgress;
    private View mMessageLayout;
    private ImageView mMessageImage;
    private TextView mMessageText;
    private Button mMessageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_pager_list);
        mListPresenter = (ListPresenter) getLastNonConfigurationInstance();
        if(mListPresenter == null) {
            mListPresenter = new ListPresenter();
        }
        mListPresenter.attachView(this);

        initViews();
        mListPresenter.getCharacters();
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        return mListPresenter;
    }

    private void initViews() {
        mCharacterGridPager = (GridViewPager) findViewById(R.id.gvp_characters);
        mGridPagerAdapter = new ListAdapter(this);
        mGridPagerAdapter.setListener(this);
        mCharacterGridPager.setAdapter(mGridPagerAdapter);
        mCharacterGridPager.setConsumeWindowInsets(false);
        mCharacterGridPager.setOnPageChangeListener(this);

        mContentLoadingProgress = (ProgressBar) findViewById(R.id.progress);
        mMessageLayout = findViewById(R.id.message_layout);
        mMessageImage = (ImageView) findViewById(R.id.iv_message);
        mMessageText = (TextView) findViewById(R.id.tv_message);
        mMessageButton = (Button) findViewById(R.id.btn_try_again);
        mMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGridPagerAdapter.removeAll();
                mListPresenter.getCharacters();
            }
        });
    }

    @Override
    public void onListClick(CharacterMarvel character) {
        //TODO: startActivity(CharacterActivity.newStartIntent(this, character)); on 1.1
    }

    @Override
    public void showCharacters(List<CharacterMarvel> characterList) {
        mGridPagerAdapter.addItems(characterList);
        mGridPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSearchedCharacters(List<CharacterMarvel> characterList) {
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
        mCharacterGridPager.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onPageScrolled(int i, int i1, float v, float v1, int i2, int i3) {

    }

    @Override
    public void onPageSelected(int i, int i1) {
        if(mGridPagerAdapter.getRowCount() == i + 2) {
            // onLoadMore()
            mListPresenter.getCharacters(mGridPagerAdapter.getRowCount(), true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
