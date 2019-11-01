package com.suhi_chintha;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import chintha_adapter.Whats_Adapter;
import chintha_data.Whats_Feed;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class wpstatus extends Fragment {
    private static String Save_Media = "/Status_WhatStatus/";
    private static final String W_Location = "/WhatsApp/Media/.Statuses";
    ImageView back;
    private Context context;
    public DataDB4 db;
    TextView emptydata;
    Typeface face;
    private List<Whats_Feed> feedItems;
    private Whats_Adapter listAdapter;
    ListView listview;
    ProgressBar pb;
    ProgressDialog pd;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wpstatus_fragment, container, false);
        try {
            context = getActivity();
            db = new DataDB4(context);
            face = Typeface.createFromAsset(context.getAssets(), "asset_fonts/font_rachana.ttf");
            listview = (ListView) rootView.findViewById(R.id.listview);
            emptydata = (TextView) rootView.findViewById(R.id.emptydownloads);
            pd = new ProgressDialog(context);
            back = (ImageView) rootView.findViewById(R.id.back);
            pb = (ProgressBar) rootView.findViewById(R.id.pb);
            feedItems = new ArrayList();
            listAdapter = new Whats_Adapter(getActivity(), feedItems);
            listview.setAdapter(listAdapter);
            emptydata.setText("താങ്കളുടെ വാട്‌സ്ആപ്പ് സുഹൃത്തിന്റെ ഒരു തവണ കണ്ട വീഡിയോ അല്ലെങ്കില്‍ ഇമേജ് സ്റ്റാറ്റസുകള്‍ ഇവിടെ നിന്നും ഡൗണ്‍ലോഡ് ചെയ്ത് എടുക്കാവുന്നതാണ്‌");
            File folder2 = new File(Environment.getExternalStorageDirectory()+"/"+Save_Media);
            if (!folder2.exists()) {
                folder2.mkdir();
            }
        } catch (Exception e) {
        }
        return rootView;
    }

    public void refresh() {
        try {
            if (db.get_wpstatusopen().equalsIgnoreCase("")) {
                notice_wp();
            }
        } catch (Exception e) {
        }
        data_loading();
    }

    public void data_loading() {
        try {
            listview. setVisibility(View.GONE);
            emptydata. setVisibility(View.GONE);
            feedItems.clear();
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().toString());
            sb.append(W_Location);
            File[] files = new File(sb.toString()).listFiles();
            Arrays.sort(files, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return Long.compare(f2.lastModified(), f1.lastModified());
                }
            });
            int j = 1;
            boolean z = false;
            for (File file : files) {
                if (!file.getName().endsWith(".jpg")) {
                    if (!file.getName().endsWith(".gif")) {
                        if (!file.getName().endsWith(".mp4")) {
                        }
                    }
                }
                z = true;
                Whats_Feed item = new Whats_Feed();
                item.set_fpath(file.getAbsolutePath());
                if (j % 2 == 0) {
                    item.set_Showads("1");
                } else {
                    item.set_Showads("0");
                }
                j++;
                feedItems.add(item);
            }
            if (z) {
                pb. setVisibility(View.GONE);
                listview.setVisibility(View.VISIBLE);
                listAdapter.notifyDataSetChanged();
                return;
            }
            pb. setVisibility(View.GONE);
            listview. setVisibility(View.GONE);
            listAdapter.notifyDataSetChanged();
            emptydata.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            listview. setVisibility(View.GONE);
            emptydata.setVisibility(View.VISIBLE);
        }
    }

    public void notice_wp() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.wpstatus_notice);
        TextView text = (TextView) dialog.findViewById(R.id.text);
        Button agree = (Button) dialog.findViewById(R.id.agree);
        text.setTypeface(face);
        text.setText("താങ്കളുടെ വാട്‌സ്ആപ്പ് സുഹൃത്തിന്റെ ഒരു തവണ കണ്ട വീഡിയോ അല്ലെങ്കില്‍ ഇമേജ് സ്റ്റാറ്റസുകള്‍ ഇവിടെ നിന്നും ഡൗണ്‍ലോഡ് ചെയ്ത് എടുക്കാവുന്നതാണ്‌ ");
        agree.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                db.add_wpstatusopen("ok");
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
