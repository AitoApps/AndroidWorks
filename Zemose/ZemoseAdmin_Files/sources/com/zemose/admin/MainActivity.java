package com.zemose.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button chats;
    Button customers;
    Button onlinepayment;
    Button orders;
    Button product;
    Button regionmanager;
    Button requets;
    Button supplier;
    Button traferdorder;
    UserDB udb = new UserDB(this);
    Button walletreport;
    Button wallets;
    Button walletupdate;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        this.onlinepayment = (Button) findViewById(R.id.onlinepayment);
        this.walletupdate = (Button) findViewById(R.id.walletupdate);
        this.chats = (Button) findViewById(R.id.chats);
        this.wallets = (Button) findViewById(R.id.wallets);
        this.orders = (Button) findViewById(R.id.orders);
        this.traferdorder = (Button) findViewById(R.id.traferdorder);
        this.product = (Button) findViewById(R.id.product);
        this.requets = (Button) findViewById(R.id.requets);
        this.supplier = (Button) findViewById(R.id.supplier);
        this.customers = (Button) findViewById(R.id.customers);
        this.walletreport = (Button) findViewById(R.id.walletreport);
        this.regionmanager = (Button) findViewById(R.id.regionmanager);
        this.supplier.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), Suppliers_List.class));
            }
        });
        this.customers.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), Customers_List.class));
            }
        });
        this.onlinepayment.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), Do_Payment.class));
            }
        });
        this.requets.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), Requests.class));
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
        this.regionmanager.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), Region_Management.class));
            }
        });
        if (this.udb.getuserid().equalsIgnoreCase("")) {
            startActivity(new Intent(getApplicationContext(), Registration.class));
            finish();
        }
    }
}
