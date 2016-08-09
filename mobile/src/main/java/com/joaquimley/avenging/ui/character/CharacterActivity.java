package com.joaquimley.avenging.ui.character;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.joaquimley.avenging.R;
import com.joaquimley.avenging.ui.base.BaseActivity;
import com.joaquimley.core.data.model.CharacterMarvel;

public class CharacterActivity extends BaseActivity {

    private static final String EXTRA_CHARACTER_MARVEL = "characterMarvel";

    public static Intent newStartIntent(Context context, CharacterMarvel characterMarvel) {
        Intent intent = new Intent(context, CharacterActivity.class);
        intent.putExtra(EXTRA_CHARACTER_MARVEL, characterMarvel);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CharacterMarvel characterMarvel = getIntent().getParcelableExtra(EXTRA_CHARACTER_MARVEL);
        if (characterMarvel == null) {
            throw new IllegalArgumentException("CharacterActivity requires a characterMarvel instance!");
        }

        setContentView(R.layout.activity_character);
        supportPostponeEnterTransition();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.character_container, CharacterFragment.newInstance(characterMarvel))
                    .commit();
        }
        showCopyRightSnackbar();
    }

    @Override
    protected View getContainerView() {
        return findViewById(R.id.character_container);
    }
}
