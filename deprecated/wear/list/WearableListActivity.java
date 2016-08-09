package com.joaquimley.avenging.ui.list;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.wearable.view.WearableListView;
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
 * Deprecated, using {@link ListActivity} you can use this if you wish to accomplish
 * the '3 items per screen' (see README.MD for example).
 */
public class WearableListActivity extends BaseActivity implements ListPresenterView, WearableListAdapter.InteractionListener, WearableListView.OnScrollListener{

    private ListPresenter mListPresenter;
    private WearableListView mWearableListView;
    private WearableListAdapter mListCharacterAdapter;

    private ProgressBar mContentLoadingProgress;

    private View mMessageLayout;
    private ImageView mMessageImage;
    private TextView mMessageText;
    private Button mMessageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mListPresenter = new ListPresenter();
        mListPresenter.attachView(this);
        initViews();
        mListPresenter.getCharacters();
    }

    private void initViews() {
        mWearableListView = (WearableListView) findViewById(R.id.wl_characters);
        mWearableListView.setItemAnimator(new DefaultItemAnimator());
        mListCharacterAdapter = new WearableListAdapter(this);
        mWearableListView.setAdapter(mListCharacterAdapter);
        mWearableListView.addOnScrollListener(this);

        mContentLoadingProgress = (ProgressBar) findViewById(R.id.progress);
        mMessageLayout = findViewById(R.id.message_layout);
        mMessageImage = (ImageView) findViewById(R.id.iv_message);
        mMessageText = (TextView) findViewById(R.id.tv_message);
        mMessageButton = (Button) findViewById(R.id.btn_try_again);
        mMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListCharacterAdapter.removeAll();
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
        mListCharacterAdapter.addItems(characterList);
    }

    @Override
    public void showSearchedCharacters(List<CharacterMarvel> characterList) {
        // no-op
    }

    @Override
    public void showProgress() {
        mContentLoadingProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mContentLoadingProgress.setVisibility(View.GONE);
        mListCharacterAdapter.removeLoadingView();
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
        mWearableListView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onScroll(int i) {

    }

    @Override
    public void onAbsoluteScrollChange(int i) {

    }

    @Override
    public void onScrollStateChanged(int i) {

    }

    @Override
    public void onCentralPositionChanged(int i) {
        // Pear WearableListView source code the list (should) always displays 3 items per screen
        if(i + 1 == mListCharacterAdapter.getItemCount()) {
            // End reached
            mListPresenter.getCharacters(mListCharacterAdapter.getItemCount(), true);
        }
    }
}
