package com.k2infosoft.k2veticalintro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class K2VerticalIntroFragment extends Fragment {

    private static final String VERTICAL_INTRO_ITEM_BUNDLE_KEY = "verticalIntroItemBundleKey";

    public static K2VerticalIntroFragment newInstance(K2VerticalIntroItem verticalIntroItem) {
        Bundle args = new Bundle();
        args.putParcelable(VERTICAL_INTRO_ITEM_BUNDLE_KEY, verticalIntroItem);
        K2VerticalIntroFragment fragment = new K2VerticalIntroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vertical_intro_base_layout, container, false);
        K2VerticalIntroItem verticalIntroItem = getArguments().getParcelable(VERTICAL_INTRO_ITEM_BUNDLE_KEY);
        if (verticalIntroItem != null) {
            TextView text = (TextView) view.findViewById(R.id.text);
            TextView title = (TextView) view.findViewById(R.id.title);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            text.setText(verticalIntroItem.getText());
            title.setText(verticalIntroItem.getTitle());
            image.setImageResource(verticalIntroItem.getImage());
            view.setBackgroundColor(ContextCompat.getColor(getActivity(), verticalIntroItem.getBackgroundColor()));

            if (verticalIntroItem.getCustomTypeFace() != null) {
                text.setTypeface(verticalIntroItem.getCustomTypeFace());
                title.setTypeface(verticalIntroItem.getCustomTypeFace());
            }
        } else {
            Log.e(K2VerticalIntro.TAG, "Something went wrong");
        }
        return view;
    }
}
