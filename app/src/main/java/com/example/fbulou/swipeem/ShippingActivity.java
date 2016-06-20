package com.example.fbulou.swipeem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class ShippingActivity extends AppCompatActivity {

    EditText fullname, phoneno, shippingaddress, pincode, city;
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

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        fullname = (EditText) findViewById(R.id.full_name);
        phoneno = (EditText) findViewById(R.id.phone_no);
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
                startActivity(new Intent(Instance, InvoiceActivity.class));
            }
        });


    }
}