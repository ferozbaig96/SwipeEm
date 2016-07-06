package com.example.fbulou.swipeem;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class ShippingActivity extends AppCompatActivity {

    EditText fullname, phoneno, email, shippingaddress, pincode, city;
    AutoCompleteTextView state;
    Button btn_proceed;

    static ShippingActivity Instance;

    public static ShippingActivity getInstance() {
        return Instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Adding font
        ChangeMyToolbarFont.apply(this, getAssets(), toolbar, "Courgette-Regular.otf");

        Instance = this;

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Shipping Details");

        //  this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        fullname = (EditText) findViewById(R.id.full_name);
        phoneno = (EditText) findViewById(R.id.phone_no);
        email = (EditText) findViewById(R.id.email);
        shippingaddress = (EditText) findViewById(R.id.shipping_address);
        pincode = (EditText) findViewById(R.id.pincode);
        city = (EditText) findViewById(R.id.city);

        state = (AutoCompleteTextView) findViewById(R.id.state);

        btn_proceed = (Button) findViewById(R.id.btn_proceed);

        String[] india_states = getResources().getStringArray(R.array.india_states);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, india_states);
        state.setAdapter(adapter);

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm();
            }
        });


    }

    private void validateForm() {
        Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_error_outline, null);
        assert d != null;
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());


        if (fullname.getText().length() == 0) {
            fullname.setError("Name cannot be empty", d);
            fullname.requestFocus();

        } else if (phoneno.getText().length() < 10) {
            phoneno.setError("Valid Phone no is required", d);
            phoneno.requestFocus();

        } else if (!isValidEmail(email.getText()) || email.getText().length() == 0) {
            email.setError("Valid Email is required", d);
            email.requestFocus();

        } else if (shippingaddress.getText().length() ==0) {
            shippingaddress.setError("Shipping address cannot be empty", d);
            shippingaddress.requestFocus();

        } else if (city.getText().length() ==0) {
            city.setError("City cannot be empty", d);
            city.requestFocus();

        } else if (state.getText().length() ==0) {
            state.setError("State cannot be empty", d);
            state.requestFocus();

        } else if (pincode.getText().length() < 6) {
            pincode.setError("Valid Pincode is required", d);
            pincode.requestFocus();

        } else
            startActivity(new Intent(Instance, InvoiceActivity.class));

    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}