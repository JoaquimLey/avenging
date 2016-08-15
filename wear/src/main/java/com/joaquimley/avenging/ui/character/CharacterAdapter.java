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
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joaquimley.avenging.R;
import com.joaquimley.core.data.model.CharacterMarvel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends PagerAdapter {

    private Context mContext;
    private final InteractionListener mInteractionListener;
    private final List<CharacterMarvel> mCharacterList;

    public CharacterAdapter(Context context, InteractionListener listener) {
        mContext = context;
        mInteractionListener = listener;
        mCharacterList = new ArrayList<>();
    }

    public void addItems(List<CharacterMarvel> characterList) {
        mCharacterList.addAll(characterList);
    }


    @Override
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        CharacterMarvel characterMarvel = mCharacterList.get(i);

        ViewHolder viewHolder;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_character, viewGroup, false);
        viewHolder = new ViewHolder(view);
        viewHolder.bind(characterMarvel);
        view.setTag(viewHolder);
        viewGroup.addView(view);
        return viewHolder.listItem;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }


    @Override
    public int getCount() {
        return mCharacterList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view.equals(o);
    }

    class ViewHolder {
        View listItem;
        ImageView image;
        TextView name;

        public ViewHolder(View view) {
            listItem = view;
            name = (TextView) listItem.findViewById(R.id.tv_name);
            image = (ImageView) listItem.findViewById(R.id.iv_character);
        }

        void bind(final CharacterMarvel character) {
            name.setText(character.getName());
            Picasso.with(mContext).load(character.getImageUrl()).into(image);
            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mInteractionListener.onListClick(character);
                }
            });
        }
    }


    /**
     * Interface for handling list interactions
     */
    public interface InteractionListener {
        void onListClick(CharacterMarvel character);
    }
}