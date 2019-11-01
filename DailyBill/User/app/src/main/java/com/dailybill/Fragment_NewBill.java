package com.dailybill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_NewBill extends Fragment {
    ConnectionDetecter cd;
    Context context;
    public DatabaseHandler db;
    ImageView groccery;
    ImageView specialoffer;
    ImageView vegitables;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newbill, container, false);
        context = getContext();
        db = new DatabaseHandler(context);
        cd = new ConnectionDetecter(context);
        groccery = (ImageView) view.findViewById(R.id.groccery);
        vegitables = (ImageView) view.findViewById(R.id.vegitables);
        specialoffer = (ImageView) view.findViewById(R.id.specialoffer);
        groccery.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.catogerid = "1";
                startActivity(new Intent(context, ProductsByCatogery.class));
            }
        });
        vegitables.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.catogerid = "2";
                startActivity(new Intent(context, ProductsByCatogery.class));
            }
        });
        specialoffer.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.catogerid = "3";
                startActivity(new Intent(context, ProductsByCatogery.class));
            }
        });
        return view;
    }
}
