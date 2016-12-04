package com.k2infosoft.social_login;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by kaushal on 11/27/2016.
 */

public class InstagramButton extends BaseButton {

    public InstagramButton(Context context, AttributeSet attrs) {
        super(context,attrs,R.color.instagram,R.drawable.instagram);
    }

    public InstagramButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs,defStyle,R.color.instagram,R.drawable.instagram);
    }

    public InstagramButton(Context context) {
        super(context);
    }
}
