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

package com.joaquimley.avenging.util.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joaquimley.avenging.R;


public class DescriptionFrameWrapper extends LinearLayout {

    private TextView mTitle;
    private TextView mDescription;


    public DescriptionFrameWrapper(Context context) {
        super(context);
        init(context);
    }

    public DescriptionFrameWrapper(Context context, @Nullable String title, @Nullable String description) {
        super(context);
        init(context);

        if (title == null || title.isEmpty()) {
            mTitle.setText(context.getResources().getString(R.string.description));
        } else {
            mTitle.setText(title);
        }

        if (description != null && !description.isEmpty()) {
            mDescription.setText(description);
        }
    }

    private void init(Context context) {
        inflate(context, R.layout.frame_character_description, this);
        mTitle = (AppCompatTextView) findViewById(R.id.tv_title_description);
        mDescription = (AppCompatTextView) findViewById(R.id.tv_description);
    }
}
