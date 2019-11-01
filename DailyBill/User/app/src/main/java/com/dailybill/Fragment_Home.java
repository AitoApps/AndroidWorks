package com.dailybill;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_Home extends Fragment {
    ConnectionDetecter cd;
    Context context;
    public DatabaseHandler db;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getContext();
        db = new DatabaseHandler(context);
        cd = new ConnectionDetecter(context);
        return view;
    }
}
