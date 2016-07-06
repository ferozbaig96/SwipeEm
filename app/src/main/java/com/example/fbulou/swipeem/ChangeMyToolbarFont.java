package com.example.fbulou.swipeem;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ChangeMyToolbarFont {

    public static void apply(Activity activity, AssetManager assetManager, Toolbar toolbar, String font) {

        for (int i = 0; i < toolbar.getChildCount(); i++) {

            View view = toolbar.getChildAt(i);

            if (view instanceof TextView) {
                TextView tv = (TextView) view;

                if (tv.getText().equals(activity.getTitle())) {
                    Typeface custom_font = Typeface.createFromAsset(assetManager, "fonts/" + font);
                    tv.setTypeface(custom_font);
                }
            }
        }
    }
}
