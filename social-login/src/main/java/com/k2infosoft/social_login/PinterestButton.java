package com.k2infosoft.social_login;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by kaushal on 27-11-2016.
 */

public class PinterestButton extends BaseButton {

    public PinterestButton(Context context, AttributeSet attrs) {
        super(context,attrs,R.color.pinterest,R.drawable.pinterest);
    }

    public PinterestButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs,defStyle,R.color.pinterest,R.drawable.pinterest);
    }

    public PinterestButton(Context context) {
        super(context);
    }
}
