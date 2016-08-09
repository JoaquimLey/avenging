package com.joaquimley.avenging.util.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joaquimley.avenging.R;
import com.joaquimley.avenging.ui.comic.ComicAdapter;
import com.joaquimley.core.data.model.Comic;

import java.util.List;

public class ComicFrameWrapper extends LinearLayout {

    private TextView mTitle;
    private ComicAdapter mComicAdapter;

    public ComicFrameWrapper(Context context) {
        super(context);
    }

    public ComicFrameWrapper(Context context, @Nullable String title, List<Comic> comicList,
                             ComicAdapter.InteractionListener listener) {
        super(context);
        mComicAdapter = new ComicAdapter(comicList, listener);
        init(context);
        if (mTitle == null) {
            return;
        }

        if (title != null && !title.isEmpty()) {
            mTitle.setText(title);
        }
    }

    private void init(Context context) {
        inflate(context, R.layout.frame_wrapper_comic, this);

        mTitle = (TextView) findViewById(R.id.tv_title);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_comic_list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setMotionEventSplittingEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mComicAdapter);
    }

    public void loadImages(List<Comic> comicList) {
        for (int i = 0; i < comicList.size(); i++) {
            mComicAdapter.getItem(i).setThumbnail(comicList.get(i).getThumbnail());
            mComicAdapter.notifyItemChanged(i);
        }
    }
}
