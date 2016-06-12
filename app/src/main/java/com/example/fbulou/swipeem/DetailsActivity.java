package com.example.fbulou.swipeem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    ImageView image;
    TextView description, qty, mrp, productName, shippingCharges, cashOnDelivery, brand, material, estimatedArrival;
    Button qtyAdd, qtySub, btn_buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Product Details");

        image = (ImageView) findViewById(R.id.image_detail);

        description = (TextView) findViewById(R.id.description_detail);
        qty = (TextView) findViewById(R.id.qty);
        mrp = (TextView) findViewById(R.id.mrp);
        productName = (TextView) findViewById(R.id.product_name);

        qtyAdd = (Button) findViewById(R.id.qty_add);
        qtySub = (Button) findViewById(R.id.qty_sub);
        btn_buy = (Button) findViewById(R.id.btn_buy);

        shippingCharges = (TextView) findViewById(R.id.shipping_charges);
        cashOnDelivery = (TextView) findViewById(R.id.cash_on_delivery);
        brand = (TextView) findViewById(R.id.brand);
        material = (TextView) findViewById(R.id.material);
        estimatedArrival = (TextView) findViewById(R.id.estimated_arrival);


        qtyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.valueOf(qty.getText().toString());
                String temp = String.valueOf(q + 1);
                qty.setText(temp);
            }
        });

        qtySub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.valueOf(qty.getText().toString());

                if (q > 1) {
                    String temp = String.valueOf(q - 1);
                    qty.setText(temp);
                }
            }
        });

        //Adding font
       /* Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        productName.setTypeface(custom_font);
*/
    }

}
