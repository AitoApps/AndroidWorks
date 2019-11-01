package com.daydeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    final DatabaseHandler db = new DatabaseHandler(this);
    FrameLayout fragment_container;
    BottomNavigationView navigation;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        String str = "";
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        fragment_container = (FrameLayout) findViewById(R.id.fragment_container);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        try {
            if (db.getscreenwidth().equalsIgnoreCase(str)) {
                int width = getResources().getDisplayMetrics().widthPixels;
                db.addscreenwidth(width+"");
            }
        } catch (Exception e) {
        }
        loadFragment(new FragmentHome(), 0);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int frg = 0;
        int itemId = item.getItemId();
        if (itemId == R.id.home) {
            frg = 0;
            fragment = new FragmentHome();
        } else if (itemId == R.id.mycard) {
            frg = 1;
            fragment = new FragmentCard();
        }
        if (frg != 1) {
            return loadFragment(fragment, frg);
        }
        if (!udb.get_userid().equalsIgnoreCase("")) {
            return loadFragment(fragment, frg);
        }
        startActivity(new Intent(getApplicationContext(), Registration.class));
        return false;
    }

    private boolean loadFragment(Fragment fragment, int frag) {
        if (fragment == null) {
            return false;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        return true;
    }
    public void onResume() {
        super.onResume();
        if (db.getlatitude().equalsIgnoreCase("") || db.getlatitude().equalsIgnoreCase("0") || db.getlatitude().equalsIgnoreCase("0.0")) {
            startActivity(new Intent(getApplicationContext(), Pick_Location.class));
            return;
        }
        try {
            ((FragmentHome) getSupportFragmentManager().findFragmentById(R.id.fragment_container)).setlocation();
        } catch (Exception e) {
        }
    }
}
