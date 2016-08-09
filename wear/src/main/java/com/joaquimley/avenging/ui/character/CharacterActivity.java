package com.joaquimley.avenging.ui.character;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joaquimley.avenging.R;
import com.joaquimley.avenging.ui.base.BaseActivity;
import com.joaquimley.core.data.model.CharacterMarvel;
import com.joaquimley.core.ui.character.CharacterPresenter;
import com.joaquimley.core.ui.character.CharacterPresenterView;


public class CharacterActivity extends BaseActivity implements CharacterPresenterView {

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
        mCharacterPresenter = new CharacterPresenter();
        mCharacterPresenter.attachView(this);

        initViews();
        mCharacterPresenter.getCharacter(mCharacter.getId());
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
                mCharacterPresenter.getCharacter(mCharacter.getId());
            }
        });
    }

    @Override
    public void showCharacter(CharacterMarvel character) {
        mCharacter = character;
        // TODO: 04/08/16 see wear module
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
    }
}
