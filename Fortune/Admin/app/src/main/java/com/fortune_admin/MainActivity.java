package com.fortune_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    ImageView pending,luckydrawbatches,luckydrawwinner,customer,agents,accounts;
    ConnectionDetecter cd;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        cd=new ConnectionDetecter(this);
        pd=new ProgressDialog(this);
        pending=findViewById(R.id.pending);
        luckydrawbatches=findViewById(R.id.luckydrawbatches);
        luckydrawwinner=findViewById(R.id.luckydrawwinner);
        customer=findViewById(R.id.customer);
        agents=findViewById(R.id.agents);
        accounts=findViewById(R.id.accounts);

        try {
            if (udb.get_screenwidth().equalsIgnoreCase("")) {
                int width = getResources().getDisplayMetrics().widthPixels;
                udb.addscreenwidth(width+"");
            }
        } catch (Exception e) {
        }


        if (udb.get_userid().equalsIgnoreCase("")) {
            startActivity(new Intent(getApplicationContext(), Registration.class));
            finish();
            return;
        }

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Pending.class));
            }
        });

        luckydrawbatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Lucky_Draw_Types.class));
            }
        });

        luckydrawwinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),Batchlist_ForWinner.class));
            }
        });

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),Customer_Batches.class));
            }
        });

        agents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Agents.class));
            }
        });

        accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Accounts.class));
            }
        });

    }
}
