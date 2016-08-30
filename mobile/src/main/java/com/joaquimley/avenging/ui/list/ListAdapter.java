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

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joaquimley.avenging.R;
import com.joaquimley.core.data.model.CharacterMarvel;
import com.squareup.picasso.Picasso;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} populated with {@link CharacterMarvel}
 * makes the call to the {@link ListAdapter.InteractionListener}.
 */
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = ListAdapter.class.getSimpleName();

    private InteractionListener mListInteractionListener;
    private final List<CharacterMarvel> mCharacterList;

    /**
     * ViewTypes serve as a mapping point to which layout should be inflated
     */
    public static final int VIEW_TYPE_GALLERY = 0;
    public static final int VIEW_TYPE_LIST = 1;
    public static final int VIEW_TYPE_LOADING = 2;

    @IntDef({VIEW_TYPE_LOADING, VIEW_TYPE_GALLERY, VIEW_TYPE_LIST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
    }

    @ViewType
    private int mViewType;

    public ListAdapter() {
        mCharacterList = new ArrayList<>();
        mViewType = VIEW_TYPE_GALLERY;
        mListInteractionListener = null;
    }

    @Override
    public int getItemViewType(int position) {
        return mCharacterList.get(position) == null ? VIEW_TYPE_LOADING : mViewType;
    }

    @Override
    public int getItemCount() {
        return mCharacterList.size();
    }

    @Override
    public long getItemId(int position) {
        return mCharacterList.size() >= position ? mCharacterList.get(position).getId() : -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return onIndicationViewHolder(parent);
        }
        return onGenericItemViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder.getItemViewType() == VIEW_TYPE_LOADING) {
            return; // no-op
        }
        onBindGenericItemViewHolder((CharacterViewHolder) holder, position);
    }

    private RecyclerView.ViewHolder onIndicationViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar, parent, false);
        return new ProgressBarViewHolder(view);
    }

    private RecyclerView.ViewHolder onGenericItemViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case VIEW_TYPE_GALLERY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character_gallery, parent, false);
                break;

            case VIEW_TYPE_LIST:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character_list, parent, false);
                break;
        }
        return new CharacterViewHolder(view);
    }

    private void onBindGenericItemViewHolder(final CharacterViewHolder holder, int position) {
        holder.name.setText(mCharacterList.get(position).getName());

        String characterImageUrl = mCharacterList.get(position).getImageUrl();
        if (!TextUtils.isEmpty(characterImageUrl)) {
            Picasso.with(holder.listItem.getContext())
                    .load(characterImageUrl)
                    .centerCrop()
                    .fit()
                    .into(holder.image);
        }
    }

    public void add(CharacterMarvel item) {
        add(null, item);
    }

    public void add(@Nullable Integer position, CharacterMarvel item) {
        if (position != null) {
            mCharacterList.add(position, item);
            notifyItemInserted(position);
        } else {
            mCharacterList.add(item);
            notifyItemInserted(mCharacterList.size() - 1);
        }
    }

    public void addItems(List<CharacterMarvel> itemsList) {
        mCharacterList.addAll(itemsList);
        notifyItemRangeInserted(getItemCount(), mCharacterList.size() - 1);
    }

    public void remove(int position) {
        if (mCharacterList.size() < position) {
            Log.w(TAG, "The item at position: " + position + " doesn't exist");
            return;
        }
        mCharacterList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAll() {
        mCharacterList.clear();
        notifyDataSetChanged();
    }

    public boolean addLoadingView() {
        if (getItemViewType(mCharacterList.size() - 1) != VIEW_TYPE_LOADING) {
            add(null);
            return true;
        }
        return false;
    }

    public boolean removeLoadingView() {
        if (mCharacterList.size() > 1) {
            int loadingViewPosition = mCharacterList.size() - 1;
            if (getItemViewType(loadingViewPosition) == VIEW_TYPE_LOADING) {
                remove(loadingViewPosition);
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public int getViewType() {
        return mViewType;
    }

    public void setViewType(@ViewType int viewType) {
        mViewType = viewType;
    }


    /**
     * ViewHolders
     */
    public class ProgressBarViewHolder extends RecyclerView.ViewHolder {

        public final ProgressBar progressBar;

        public ProgressBarViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        }
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder {

        public final View listItem;
        public final TextView name;
        public final AppCompatImageView image;

        public CharacterViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            image = (AppCompatImageView) view.findViewById(R.id.image);
            listItem = view;
            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListInteractionListener != null) {
                        mListInteractionListener.onListClick(mCharacterList.get(getAdapterPosition()),
                                image, getAdapterPosition());
                    }
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + name.getText() + "'";
        }
    }

    /**
     * Interface for handling list interactions
     */
    public interface InteractionListener {
        void onListClick(CharacterMarvel character, View sharedElementView, int adapterPosition);
    }

    public void setListInteractionListener(InteractionListener listInteractionListener) {
        mListInteractionListener = listInteractionListener;
    }
}
