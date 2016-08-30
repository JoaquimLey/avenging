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

package com.joaquimley.avenging.ui.comic;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joaquimley.avenging.R;
import com.joaquimley.avenging.util.animation.Pulse;
import com.joaquimley.core.data.model.CharacterMarvel;
import com.joaquimley.core.data.model.Comic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} populated with {@link CharacterMarvel}
 * makes the call to the {@link ComicAdapter.InteractionListener}.
 */
public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder> {

    private static final String TRANSACTION_PREFIX_ = "transactionPrefix_";

    private final List<Comic> mComicList;
    private InteractionListener mListInteractionListener;

    public ComicAdapter(List<Comic> comicList, InteractionListener listener) {
        mComicList = new ArrayList<>();
        mListInteractionListener = listener;
        addItems(comicList);
    }

    @Override
    public int getItemCount() {
        return mComicList.size();
    }

    @Override
    public ComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comic, parent, false);
        return new ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComicViewHolder holder, int position) {
        holder.title.setText(mComicList.get(position).getName());

        String imageUrl = mComicList.get(position).getThumbnailUrl();
        if (!imageUrl.isEmpty()) {
            Picasso.with(holder.listItem.getContext())
                    .load(mComicList.get(position).getThumbnailUrl())
                    .fit()
                    .into(holder.imageView);

            holder.imageView.clearAnimation();
            holder.imageView.setBackgroundColor(ContextCompat
                    .getColor(holder.listItem.getContext(),
                            R.color.colorPrimary));
        } else {
            holder.imageView.setAnimation(new Pulse());
        }
    }

    public Comic getItem(int position) {
        return mComicList.get(position);
    }

    public void addItems(List<Comic> itemsList) {
        mComicList.addAll(itemsList);
        notifyItemRangeInserted(getItemCount(), mComicList.size() - 1);
    }

    /**
     * ViewHolders
     */
    public class ComicViewHolder extends RecyclerView.ViewHolder {

        public final View listItem;
        public final ImageView imageView;
        public final TextView title;

        public ComicViewHolder(View view) {
            super(view);
            listItem = view;
            title = (TextView) view.findViewById(R.id.tv_title);
            imageView = (ImageView) view.findViewById(R.id.iv_image);
            ViewCompat.setTransitionName(imageView, TRANSACTION_PREFIX_ + getAdapterPosition());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListInteractionListener != null) {
                        mListInteractionListener.onComicClick(mComicList, imageView, getAdapterPosition());
                    }
                }
            });
        }
    }

    /**
     * Interface for handling list interactions
     */
    public interface InteractionListener {
        void onComicClick(List<Comic> comicList, ImageView sharedImageView, int clickedPosition);
    }
}
