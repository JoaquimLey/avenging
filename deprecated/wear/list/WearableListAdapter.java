package com.joaquimley.avenging.ui.list;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
 * Deprecated, using {@link ListAdapter} you can use this if you wish to accomplish
 * the '3 items per screen' (see README.MD for example).
 */
public class WearableListAdapter extends WearableListView.Adapter {

    private final List<CharacterMarvel> mCharacterList;
    private InteractionListener mListInteractionListener;

    /**
     * ViewTypes serve as a mapping point to which layout should be inflated
     */
    public static final int VIEW_TYPE_LIST = 1;
    public static final int VIEW_TYPE_LOADING = 2;

    @IntDef({VIEW_TYPE_LOADING, VIEW_TYPE_LIST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {}

    public WearableListAdapter(InteractionListener listener) {
        mListInteractionListener = listener;
        mCharacterList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return mCharacterList.size();
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == VIEW_TYPE_LOADING) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new ProgressBarViewHolder(view);
        }
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_LOADING) {
            return; // no-op
        }
        CharacterViewHolder characterViewHolder = (CharacterViewHolder) holder;
        characterViewHolder.name.setText(mCharacterList.get(position).getName());
        Picasso.with(characterViewHolder.listItem.getContext())
                .load(mCharacterList.get(position).getImageUrl())
                .centerCrop()
                .fit()
                .into(characterViewHolder.image);
    }

    @Override
    public int getItemViewType(int position) {
        return mCharacterList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_LIST;
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

    public void addItems(List<CharacterMarvel> characterList) {
        mCharacterList.addAll(characterList);
        notifyItemRangeInserted(getItemCount(), mCharacterList.size() - 1);
    }

    public void remove(int position) {
        if (mCharacterList.size() < position) {
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

    public class ProgressBarViewHolder extends WearableListView.ViewHolder {

        public final ProgressBar progressBar;

        public ProgressBarViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        }
    }


    private class CharacterViewHolder extends WearableListView.ViewHolder {

        public View listItem;
        public ImageView image;
        public TextView name;

        public CharacterViewHolder(View view) {
            super(view);
            listItem = view;
            image = (ImageView) listItem.findViewById(R.id.iv_character);
            name = (TextView) listItem.findViewById(R.id.tv_name);
            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListInteractionListener != null) {
                        mListInteractionListener.onListClick(mCharacterList.get(getAdapterPosition()));
                    }
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