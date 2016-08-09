package com.joaquimley.avenging.ui.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.joaquimley.avenging.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected static final String KEY_MARVEL_COPYRIGHT = "keyMarvelCopyRight";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    protected abstract View getContainerView();

    protected void showCopyRightSnackbar() {

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean(KEY_MARVEL_COPYRIGHT, false)) {
            Log.i("BaseActivity", "User already knows");
            return;
        }

        final Snackbar copyrightSnackbar = Snackbar.make(getContainerView(),
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
