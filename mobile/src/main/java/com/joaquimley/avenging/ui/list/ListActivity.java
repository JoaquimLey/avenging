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

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.joaquimley.avenging.R;
import com.joaquimley.avenging.ui.base.BaseActivity;

public class ListActivity extends BaseActivity {

    protected static final String KEY_MARVEL_COPYRIGHT = "keyMarvelCopyRight";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme); // Branded launch
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.list_container, ListFragment.newInstance())
                    .commit();
        }
        showCopyRightSnackbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    protected void showCopyRightSnackbar() {

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean(KEY_MARVEL_COPYRIGHT, false)) {
            Log.i("BaseActivity", "User already knows");
            return;
        }

        final Snackbar copyrightSnackbar = Snackbar.make(findViewById(R.id.list_container),
                getString(R.string.marvel_copyright_notice), Snackbar.LENGTH_INDEFINITE);
        copyrightSnackbar.setAction(R.string.action_acknowledge, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putBoolean(KEY_MARVEL_COPYRIGHT, true).apply();
                copyrightSnackbar.dismiss();
            }
        });
        copyrightSnackbar.show();
    }

}
