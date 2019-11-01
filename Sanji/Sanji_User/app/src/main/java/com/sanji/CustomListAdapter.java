package com.sanji;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter {

    public List<String> dataList;

    public List<String> dataListAllItems;
    private int itemLayout;
    private ListFilter listFilter = new ListFilter();
    private Context mContext;

    public class ListFilter extends Filter {
        private Object lock = new Object();

        public ListFilter() {
        }
        public FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (CustomListAdapter.dataListAllItems == null) {
                synchronized (lock) {
                    CustomListAdapter.dataListAllItems = new ArrayList(CustomListAdapter.dataList);
                }
            }
            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = CustomListAdapter.dataListAllItems;
                    results.count = CustomListAdapter.dataListAllItems.size();
                }
            } else {
                String searchStrLowerCase = prefix.toString().toLowerCase();
                ArrayList<String> matchValues = new ArrayList<>();
                for (String dataItem : CustomListAdapter.dataListAllItems) {
                    if (dataItem.toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                    }
                }
                results.values = matchValues;
                results.count = matchValues.size();
            }
            return results;
        }
        public void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                CustomListAdapter.dataList = (ArrayList) results.values;
            } else {
                CustomListAdapter.dataList = null;
            }
            if (results.count > 0) {
                CustomListAdapter.notifyDataSetChanged();
            } else {
                CustomListAdapter.notifyDataSetInvalidated();
            }
        }
    }

    public CustomListAdapter(Context context, int resource, List<String> storeDataLst) {
        super(context, resource, storeDataLst);
        dataList = storeDataLst;
        mContext = context;
        itemLayout = resource;
    }

    public int getCount() {
        return dataList.size();
    }

    public String getItem(int position) {
        Log.d("CustomListAdapter", (String) dataList.get(position));
        return (String) dataList.get(position);
    }

    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        }
        ((TextView) view.findViewById(R.id.placename)).setText(getItem(position));
        return view;
    }

    @NonNull
    public Filter getFilter() {
        return listFilter;
    }
}
