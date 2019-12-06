package com.zemose.regionadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {
    Button chats;
    Button onlinepayment;
    Button orders;
    Button product;
    Button supplier;
    TextView text;
    Button traferdorder;
    UserDB udb = new UserDB(this);
    Button walletreport;
    Button wallets;
    Button walletupdate;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        this.text = (TextView) findViewById(R.id.text);
        this.onlinepayment = (Button) findViewById(R.id.onlinepayment);
        this.walletupdate = (Button) findViewById(R.id.walletupdate);
        this.chats = (Button) findViewById(R.id.chats);
        this.wallets = (Button) findViewById(R.id.wallets);
        this.orders = (Button) findViewById(R.id.orders);
        this.traferdorder = (Button) findViewById(R.id.traferdorder);
        this.product = (Button) findViewById(R.id.product);
        this.supplier = (Button) findViewById(R.id.supplier);
        this.walletreport = (Button) findViewById(R.id.walletreport);
        this.text.setText(this.udb.getheadname());
        this.supplier.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), Suppliers_List.class));
            }
        });
        this.onlinepayment.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), Do_Payment.class));
            }
        });
        this.walletupdate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), Wallet_Update.class));
            }
        });
        this.chats.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), Chatting.class));
            }
        });
        this.wallets.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), Wallets_Report.class));
            }
        });
        this.orders.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), Order_Report.class));
            }
        });
        this.traferdorder.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), Transfered_Report.class));
            }
        });
        this.product.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), Search_Suppliers.class));
            }
        });
        this.walletreport.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), Wallet_History.class));
            }
        });
        if (this.udb.getRegionId().equalsIgnoreCase("")) {
            startActivity(new Intent(getApplicationContext(), User_registration.class));
            finish();
        }
    }
}
