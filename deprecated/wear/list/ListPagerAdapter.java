package com.joaquimley.avenging.ui.list;


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

/**
 * Deprecated, using {@link ListAdapter} you can use this but may need some tweaking
 */
public class ListPagerAdapter extends PagerAdapter {

    private Context mContext;
    private final InteractionListener mInteractionListener;
    private final List<CharacterMarvel> mCharacterList;

    public ListPagerAdapter(Context context, InteractionListener listener) {
        mContext = context;
        mInteractionListener = listener;
        mCharacterList = new ArrayList<>();
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        CharacterMarvel characterMarvel = mCharacterList.get(i);

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_character, viewGroup, false);
        CharacterViewHolder characterViewHolder = new CharacterViewHolder(view);
        characterViewHolder.bind(characterMarvel);
        view.setTag(characterViewHolder);
        viewGroup.addView(view);
        return characterViewHolder.listItem;
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

    public void addItems(List<CharacterMarvel> characterList) {
        mCharacterList.addAll(characterList);
        notifyDataSetChanged();
    }

    public void removeAll() {
        mCharacterList.clear();
        notifyDataSetChanged();
    }

    class CharacterViewHolder {
        View listItem;
        ImageView image;
        TextView name;

        public CharacterViewHolder(View view) {
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