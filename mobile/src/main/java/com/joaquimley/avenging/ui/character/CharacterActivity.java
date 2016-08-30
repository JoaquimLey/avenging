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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
    }
}
