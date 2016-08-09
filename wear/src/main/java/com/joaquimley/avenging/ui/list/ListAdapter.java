package com.joaquimley.avenging.ui.list;

import android.content.Context;
import android.support.wearable.view.GridPagerAdapter;
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

public class ListAdapter extends GridPagerAdapter {

    private final Context mContext;
    private final List<CharacterMarvel> mCharacterList = new ArrayList<>();
    private InteractionListener mListInteractionListener;

    public ListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getRowCount() {
        return mCharacterList.size();
    }

    @Override
    public int getColumnCount(int i) {
        return 1;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int i, int i1) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_character, viewGroup, false);
        CharacterViewHolder characterViewHolder = new CharacterViewHolder(view);
        characterViewHolder.bind(mCharacterList.get(i));
        view.setTag(characterViewHolder);
        viewGroup.addView(view);
        return characterViewHolder.listItem;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int i, int i1, Object o) {
        viewGroup.removeView((View) o);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view.equals(o);
    }

    public void setListener(InteractionListener listener) {
        mListInteractionListener = listener;
    }

    public void addItems(List<CharacterMarvel> characterList) {
        mCharacterList.addAll(characterList);
        notifyDataSetChanged();
    }

    public void removeAll() {
        mCharacterList.clear();
        notifyDataSetChanged();
    }

    private class CharacterViewHolder {

        public View listItem;
        public ImageView image;
        public TextView name;

        public CharacterViewHolder(View view) {
            listItem = view;
            image = (ImageView) listItem.findViewById(R.id.iv_character);
            name = (TextView) listItem.findViewById(R.id.tv_name);
        }

        void bind(final CharacterMarvel character) {
            name.setText(character.getName());
            Picasso.with(mContext).load(character.getImageUrl()).into(image);
            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListInteractionListener != null) {
                        mListInteractionListener.onListClick(character);
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