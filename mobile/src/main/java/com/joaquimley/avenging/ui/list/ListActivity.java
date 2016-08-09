package com.joaquimley.avenging.ui.list;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.joaquimley.avenging.R;
import com.joaquimley.avenging.ui.base.BaseActivity;

public class ListActivity extends BaseActivity {

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
    protected View getContainerView() {
        return findViewById(R.id.list_container);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
