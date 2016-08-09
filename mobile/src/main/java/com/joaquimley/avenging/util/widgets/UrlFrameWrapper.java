package com.joaquimley.avenging.util.widgets;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joaquimley.avenging.R;
import com.joaquimley.core.data.model.Url;

import java.util.List;

public class UrlFrameWrapper extends LinearLayout {

    private TextView mTitle;
    private LinearLayout mLinksContentFrame;

    public UrlFrameWrapper(Context context) {
        super(context);
        init(context);
        mTitle.setText(context.getResources().getString(R.string.related_links));
    }

    public UrlFrameWrapper(Context context, @Nullable String title, @Nullable List<Url> urlItemList) {
        super(context);
        init(context);

        if (title != null && !title.isEmpty()) {
            mTitle.setText(title);
        }

        if (urlItemList != null && !urlItemList.isEmpty()) {
            addLinks(context, urlItemList);
        }
    }

    private void init(Context context) {
        inflate(context, R.layout.frame_wrapper_url, this);
        mTitle = (TextView) findViewById(R.id.tv_title_links);
        mLinksContentFrame = (LinearLayout) findViewById(R.id.url_content_frame);
    }

    public void addLinks(final Context context, List<Url> urlItemList) {

        UrlFrame subItemUrl;
        for (int i = 0; i < urlItemList.size(); i++) {
            Url urlItem = urlItemList.get(i);
            subItemUrl = new UrlFrame(context, urlItem.getType(), i == urlItemList.size() - 1);

            final String currentLinkUrl = urlItem.getUrl();
            subItemUrl.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentLinkUrl));
                    context.startActivity(browserIntent);
                }
            });
            mLinksContentFrame.addView(subItemUrl);
        }
    }

    /**
     * Inner UrlFrame class
     */
    private class UrlFrame extends LinearLayout {

        private AppCompatTextView mLink;

        public UrlFrame(Context context, String linkName, boolean isLastLink) {
            super(context);
            init(context, linkName, isLastLink);
        }

        private void init(Context context, String linkName, boolean isLastLink) {
            inflate(context, R.layout.frame_url, this);
            mLink = (AppCompatTextView) findViewById(R.id.tv_link);
            mLink.setText(linkName);
            if (isLastLink) {
                findViewById(R.id.line).setVisibility(View.GONE);
            }
        }

        public void setOnClickListener(OnClickListener onClickListener) {
            mLink.setOnClickListener(onClickListener);
        }
    }
}
