package com.example.fbulou.swipeem;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ScrollFling extends AppCompatActivity {

    ImageView image;
    private PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_fling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        image = (ImageView) findViewById(R.id.image);
        Drawable drawable = DetailsActivity.getInstance().image.getDrawable();
        image.setImageDrawable(drawable);

        mAttacher = new PhotoViewAttacher(image);

    }

}
