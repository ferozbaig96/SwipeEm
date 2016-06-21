package com.example.fbulou.swipeem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    static DetailsActivity Instance;
    Menu mMenu;

    int currentWishlistPosition;
    ImageView image;
    TextView description, mrp, productName, shippingCharges, cashOnDelivery, brand, material, estimatedArrival;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Instance = this;

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                *//*Glide.with(Instance)
                        .load("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg")
                        .centerCrop()
                        .into(image);*//*

            }
        });*/

        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Product Details");

        //TODO get and set image via url using Glide
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
        estimatedArrival = (TextView) findViewById(R.id.estimated_arrival);

        price_int = 999;    //DEMO

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Instance, ScrollFling.class);
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

        //Adding font
       /* Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        productName.setTypeface(custom_font);
*/

        assignProductValues();
    }

    void assignProductValues() {
        Intent intent = getIntent();

        currentWishlistPosition = intent.getIntExtra("currentWishlistPosition", -1);

        Glide.with(this)
                .load(intent.getStringExtra("imagePath"))
                .placeholder(R.drawable.default_img)
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

