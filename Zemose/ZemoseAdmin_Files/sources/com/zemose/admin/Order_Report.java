package com.zemose.admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.design.widget.TabLayout.ViewPagerOnTabSelectedListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class Order_Report extends AppCompatActivity {

    /* renamed from: adapter reason: collision with root package name */
    ViewPagerAdapter f17adapter;
    String mobile = "";
    /* access modifiers changed from: private */
    public TabLayout tabLayout;
    private ViewPager viewPager;

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public Fragment getItem(int position) {
            return (Fragment) this.mFragmentList.get(position);
        }

        public int getCount() {
            return this.mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(title);
        }

        public CharSequence getPageTitle(int position) {
            return (CharSequence) this.mFragmentTitleList.get(position);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_order__report);
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(this.viewPager);
        this.tabLayout = (TabLayout) findViewById(R.id.tabs);
        this.tabLayout.setupWithViewPager(this.viewPager);
        StrictMode.setThreadPolicy(new Builder().permitAll().build());
        this.tabLayout.addOnTabSelectedListener(new ViewPagerOnTabSelectedListener(this.viewPager) {
            public void onTabSelected(Tab tab) {
                super.onTabSelected(tab);
                int position = Order_Report.this.tabLayout.getSelectedTabPosition();
                Fragment fragment = Order_Report.this.f17adapter.getItem(tab.getPosition());
                if (fragment != null) {
                    switch (position) {
                        case 0:
                            ((sna_fragment) fragment).refresh();
                            return;
                        case 1:
                            ((cna_fragment) fragment).refresh();
                            return;
                        default:
                            return;
                    }
                }
            }

            public void onTabUnselected(Tab tab) {
                super.onTabUnselected(tab);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager2) {
        try {
            this.f17adapter = new ViewPagerAdapter(getSupportFragmentManager());
            this.f17adapter.addFragment(new sna_fragment(), "SNA");
            this.f17adapter.addFragment(new cna_fragment(), "CNC");
            viewPager2.setAdapter(this.f17adapter);
        } catch (Exception e) {
        }
    }

    public void call(String mobile1) {
        try {
            this.mobile = mobile1;
            if (ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.CALL_PHONE"}, 1);
                return;
            }
            Intent callIntent = new Intent("android.intent.action.CALL");
            StringBuilder sb = new StringBuilder();
            sb.append("tel:");
            sb.append(this.mobile);
            callIntent.setData(Uri.parse(sb.toString()));
            startActivity(callIntent);
        } catch (Exception e) {
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == 0) {
            call(this.mobile);
        }
    }
}
