package com.joaquimley.avenging.ui.comic;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joaquimley.avenging.R;
import com.joaquimley.core.data.model.Comic;
import com.squareup.picasso.Picasso;

public class ComicViewPagerFragment extends Fragment {

    public static final String ARG_COMIC = "argComic";

    private Comic mComic;

    public static ComicViewPagerFragment newInstance(Comic comic) {
        ComicViewPagerFragment comicViewPagerFragment = new ComicViewPagerFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_COMIC, comic);
        comicViewPagerFragment.setArguments(args);
        setupTransactions(comicViewPagerFragment);
        return comicViewPagerFragment;
    }

    private static void setupTransactions(Fragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setEnterTransition(new Fade());
            fragment.setExitTransition(new Fade());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mComic = getArguments().getParcelable(ARG_COMIC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_comic_view_pager, container, false);
        ((TextView) view.findViewById(R.id.tv_title)).setText(mComic.getName());

        String imageUrl = mComic.getThumbnailUrl();
        if (imageUrl.isEmpty()) {
            Picasso.with(getContext())
                    .load(R.drawable.ic_error_list)
                    .into(((ImageView) view.findViewById(R.id.iv_image)));
        } else {
            // TODO: 24/07/16 Get a more appropriate error resource
            Picasso.with(getContext())
                    .load(imageUrl)
                    .into(((ImageView) view.findViewById(R.id.iv_image)));
        }
        return view;
    }
}