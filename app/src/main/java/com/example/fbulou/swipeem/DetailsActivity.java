package com.example.fbulou.swipeem;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    static DetailsActivity Instance;
    Menu mMenu;

    //For SharedTransition
    int top, left, width, height;
    int leftDelta, topDelta;
    float widthScale, heightScale;

    int currentWishlistPosition;
    ImageView image;
    TextView description, mrp, productName, shippingCharges, cashOnDelivery, brand, material, gender, estimatedArrival;

    /*TextView qty;
    Button qtyAdd, qtySub;*/

    Button btn_buy;
    int price_int;

    public static DetailsActivity getInstance() {
        return Instance;
    }

    //TODO get Bundle (or getExtras) from WishlistActivity to get details
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // ActivityTransition.with(getIntent()).to(findViewById(R.id.image_detail)).duration(400).start(savedInstanceState);

        setupTitleWhenCollapsed();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Instance = this;

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });*/

        image = (ImageView) findViewById(R.id.image_detail);

        description = (TextView) findViewById(R.id.description_detail);
        //  qty = (TextView) findViewById(R.id.qty);
        mrp = (TextView) findViewById(R.id.mrp);
        productName = (TextView) findViewById(R.id.product_name);

        // qtyAdd = (Button) findViewById(R.id.qty_add);
        // qtySub = (Button) findViewById(R.id.qty_sub);
        btn_buy = (Button) findViewById(R.id.btn_buy);

        shippingCharges = (TextView) findViewById(R.id.shipping_charges);
        cashOnDelivery = (TextView) findViewById(R.id.cash_on_delivery);
        brand = (TextView) findViewById(R.id.brand);
        material = (TextView) findViewById(R.id.material);
        gender = (TextView) findViewById(R.id.gender);
        estimatedArrival = (TextView) findViewById(R.id.estimated_arrival);

        price_int = 999;    //DEMO

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Instance, ScrollFling.class);
                //TODO get full size image
                intent.putExtra("imagePath", getIntent().getStringExtra("imagePath"));
                startActivity(intent);
            }
        });

       /* qtyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.valueOf(qty.getText().toString());
                String temp = String.valueOf(q + 1);
                qty.setText(temp);

                btn_buy.setText("Buy Now for ₹ " + (q + 1) * price_int);
            }
        });

        qtySub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.valueOf(qty.getText().toString());

                if (q > 1) {
                    String temp = String.valueOf(q - 1);
                    qty.setText(temp);

                    btn_buy.setText("Buy Now for ₹ " + (q - 1) * price_int);
                }
            }
        });*/

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Bundle (or put Extras) for appropriate product info

                Intent intent = new Intent(Instance, ShippingActivity.class);
                startActivity(intent);
            }
        });

        makeImageSquare();
        receiveSharedTransition();
        assignProductValues();
    }

    void makeImageSquare() {
        ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
        layoutParams.height = MainActivity.Instance.getEffectiveWidth();
        image.setLayoutParams(layoutParams);
    }

    private void receiveSharedTransition() {
        Intent intent = getIntent();
        top = intent.getIntExtra("top", 0);
        left = intent.getIntExtra("left", 0);
        width = intent.getIntExtra("width", 0);
        height = intent.getIntExtra("height", 0);
        onUiReady(image);
    }

    public void onUiReady(final View view) {
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                view.getViewTreeObserver().removeOnPreDrawListener(this);
                prepareScene(view);
                runEnterAnimation(view);
                return true;
            }
        });
    }

    private void prepareScene(View view) {
        //Scale the destination view to be the same size as the original view
        widthScale = (float) width / view.getWidth();
        heightScale = (float) height / view.getHeight();
        view.setScaleX(widthScale);
        view.setScaleY(heightScale);

        //Position the destination view where the original view was
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);

        leftDelta = left - screenLocation[0];
        topDelta = top - screenLocation[1];
        view.setTranslationX(leftDelta);
        view.setTranslationY(topDelta);
    }

    private void runEnterAnimation(View view) {
        //Now simply animate to the default positions

        view.animate()
                .setDuration(250)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .scaleX(1)
                .scaleY(1)
                .translationX(0)
                .translationY(0)
                .start();

    }

    private void setupTitleWhenCollapsed() {    //sets CollapsingToolbarLayout title visible only when it is collapsed.
        //Here it is visible when it scrim starts (and fab goes away)

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/" + "Courgette-Regular.otf");
        collapsingToolbarLayout.setCollapsedTitleTypeface(custom_font);
        collapsingToolbarLayout.setExpandedTitleTypeface(custom_font);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int bottomPx = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (bottomPx == -1) {
                    bottomPx = appBarLayout.getBottom();
                }
                if (bottomPx + verticalOffset <= collapsingToolbarLayout.getScrimVisibleHeightTrigger()) {
                    collapsingToolbarLayout.setTitle("Product Details");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });
    }

   /* @Override
    public void onBackPressed() {

        runExitAnimation(image);
    }
*/
    //todo remove
    private void runExitAnimation(View view) {
        view.animate()
                .setDuration(250)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .scaleX(widthScale)
                .scaleY(heightScale)
                .translationX(leftDelta)
                .translationY(topDelta)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        //now we can finish the actvity
                        finish();
                        overridePendingTransition(0, 0);
                    }
                })
                .start();

    }

    void assignProductValues() {
        Intent intent = getIntent();

        currentWishlistPosition = intent.getIntExtra("currentWishlistPosition", -1);

        Glide.with(this)
                .load(intent.getStringExtra("imagePath"))
                .placeholder(R.drawable.default_img)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                //  .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .centerCrop()
                .into(image);

        productName.setText(intent.getStringExtra("title"));

    }

    //MENUS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Delete this item from the Wishlist
        if (id == R.id.action_delete_from_wishlist) {

            //Deleting an existing item. Hence myWishlist is non-empty
            List<Information> myWishlist = WishlistActivity.getInstance().loadWishlistPref();
            //Deleting item at [position] from the list
            myWishlist.remove(currentWishlistPosition);
            //Saving back to the WishlistPrefs
            WishlistActivity.getInstance().saveWishlistPref(myWishlist);

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

