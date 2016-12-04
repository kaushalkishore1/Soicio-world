package com.coolacharya.soicio_world;

import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;

import com.k2infosoft.k2veticalintro.K2VerticalIntro;
import com.k2infosoft.k2veticalintro.K2VerticalIntroItem;

/**
 * Created by kaushal on 27/11/2016.
 */
public class IntroScreen extends K2VerticalIntro {

    @Override
    protected void init() {

        addIntroItem(new K2VerticalIntroItem.Builder()
                .backgroundColor(R.color.splash_color)
                .image(R.drawable.intro1)
                .title("Welcome to Socio-app")
                .text("Amazing android app with cool features.")
                .build());

        addIntroItem(new K2VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorPrimary)
                .image(R.drawable.new_intro)
                .title("Amazing Features")
                .text("50+ amazing features and it's 100% free to use.")
                .build());

        setSkipEnabled(true);
        setVibrateEnabled(true);
        setVibrateIntensity(20);
        setCustomTypeFace(Typeface.createFromAsset(getAssets(), "fonts/NotoSans-Regular.ttf"));
    }

    @Override
    protected Integer setLastItemBottomViewColor() {
        return R.color.color2;
    }

    @Override
    protected void onSkipPressed(View view) {
        Log.e("onSkipPressed ", "onSkipPressed");
    }

    @Override
    protected void onFragmentChanged(int position) {
        Log.e("onFragmentChanged ", "" + position);
    }

    @Override
    protected void onDonePressed() {
        startActivity(new Intent(IntroScreen.this, LoginActivity.class));
        finish();
    }
}
