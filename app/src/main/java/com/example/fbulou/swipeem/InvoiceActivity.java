package com.example.fbulou.swipeem;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InvoiceActivity extends AppCompatActivity {

    ImageView image;
    TextView productName, qty, mrp, shippingCharges, tax, amount;
    TextView fullname, phoneno, email, shippingaddress, pincode, city, state;
    Button btn_checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Adding font
        ChangeMyToolbarFont.apply(this, getAssets(), toolbar, "Courgette-Regular.otf");

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Invoice Details");

        image = (ImageView) findViewById(R.id.image_invoice);

        productName = (TextView) findViewById(R.id.product_name_invoice);
        qty = (TextView) findViewById(R.id.qty_invoice);
        mrp = (TextView) findViewById(R.id.mrp_invoice);
        shippingCharges = (TextView) findViewById(R.id.shipping_charges_invoice);
        tax = (TextView) findViewById(R.id.tax);
        amount = (TextView) findViewById(R.id.amount);
        fullname = (TextView) findViewById(R.id.full_name_invoice);
        phoneno = (TextView) findViewById(R.id.phone_no_invoice);
        email = (TextView) findViewById(R.id.email_invoice);
        shippingaddress = (TextView) findViewById(R.id.shipping_address_invoice);
        pincode = (TextView) findViewById(R.id.pincode_invoice);
        city = (TextView) findViewById(R.id.city_invoice);
        state = (TextView) findViewById(R.id.state_invoice);

        btn_checkout = (Button) findViewById(R.id.btn_checkout);

        DetailsActivity detailsActivity = DetailsActivity.getInstance();

        Drawable drawable = detailsActivity.image.getDrawable();
        image.setImageDrawable(drawable);

        productName.setText(detailsActivity.productName.getText());
        // qty.setText(detailsActivity.qty.getText());
        mrp.setText(detailsActivity.mrp.getText());
        shippingCharges.setText(detailsActivity.shippingCharges.getText());

        ShippingActivity shippingActivity = ShippingActivity.getInstance();

        fullname.setText(shippingActivity.fullname.getText());
        phoneno.setText(shippingActivity.phoneno.getText());
        email.setText(shippingActivity.email.getText());
        shippingaddress.setText(shippingActivity.shippingaddress.getText());
        pincode.setText(shippingActivity.pincode.getText());
        city.setText(shippingActivity.city.getText());
        state.setText(shippingActivity.state.getText());

    }

}
