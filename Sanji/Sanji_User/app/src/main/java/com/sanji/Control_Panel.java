package com.sanji;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

public class Control_Panel extends AppCompatActivity implements OnNavigationItemSelectedListener {
    final DatabaseHandler db = new DatabaseHandler(this);
    FrameLayout fragment_container;
    BottomNavigationView navigation;
    TextView tv;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    public void onCreate(Bundle savedInstanceState) {
        String str = "";
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_control__panel);
        fragment_container = (FrameLayout) findViewById(R.id.fragment_container);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) bottomNavigationMenuView.getChildAt(2);
        View badge = LayoutInflater.from(this).inflate(R.layout.custom_badge, bottomNavigationMenuView, false);
        tv = (TextView) badge.findViewById(R.id.notification_badge);
        itemView.addView(badge);
        try {
            if (db.getscreenwidth().equalsIgnoreCase(str)) {
                int width = getResources().getDisplayMetrics().widthPixels;
                DatabaseHandler databaseHandler = db;
                StringBuilder sb = new StringBuilder();
                sb.append(width);
                sb.append(str);
                databaseHandler.addscreenwidth(sb.toString());
            }
        } catch (Exception e) {
        }
        if (db.get_locationaddress().equalsIgnoreCase(str)) {
            startActivity(new Intent(getApplicationContext(), Location_Picker.class));
        } else {
            loadFragment(new FragmentHome(), 0);
        }
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int frg = 0;
        switch (item.getItemId()) {
            case R.id.cart /*2131296306*/:
                frg = 2;
                fragment = new FragmentCart();
                break;
            case R.id.home /*2131296390*/:
                frg = 0;
                fragment = new FragmentHome();
                break;
            case R.id.orderhistory /*2131296484*/:
                frg = 1;
                fragment = new Fragment_MyOrder();
                break;
            case R.id.searchicon /*2131296571*/:
                frg = 3;
                fragment = new Fragment_Search();
                break;
        }
        return loadFragment(fragment, frg);
    }

    private boolean loadFragment(Fragment fragment, int frag) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        if (frag == 0) {
            ((FragmentHome) getSupportFragmentManager().findFragmentById(R.id.fragment_container)).change_location(db.get_locationaddress());
        }
        return false;
    }
    public void onResume() {
        super.onResume();
        try {
            ((Fragment_Search) getSupportFragmentManager().findFragmentById(R.id.fragment_container)).refreshcart();
        } catch (Exception e) {
        }
        try {
            FragmentCart fragment1 = (FragmentCart) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            fragment1.loadmaindata();
            fragment1.calculatetotal();
        } catch (Exception e2) {
        }
        setcartcount();
    }

    public void setcartcount() {
        String str = "";
        if (db.get_totalqty() > 0) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(str);
            TextView textView = tv;
            StringBuilder sb = new StringBuilder();
            sb.append(db.get_totalqty());
            sb.append(str);
            textView.setText(sb.toString());
            return;
        }
        tv.setVisibility(View.GONE);
        tv.setText(str);
    }
}
