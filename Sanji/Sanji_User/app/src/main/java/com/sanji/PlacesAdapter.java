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
import com.google.android.gms.location.places.AutocompleteFilter.Builder;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.PlacesOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlacesAdapter extends ArrayAdapter {

    public static final LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(6.4626999d, 68.1097d), new LatLng(35.513327d, 97.39535869999999d));
    Context context;
    PlacesAutoCompleteFilter filter = new PlacesAutoCompleteFilter();
    GeoDataClient geoDataClient;
    List<String> placesList = new ArrayList();

    class PlacesAutoCompleteFilter extends Filter {
        private Object lock = new Object();

        public Object lockTwo = new Object();

        public boolean placeResults = false;

        PlacesAutoCompleteFilter() {
        }
        public FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            placeResults = false;
            final List<String> predictedPlacesList = new ArrayList<>();
            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = new ArrayList();
                    results.count = 0;
                }
            } else {
                getAutoCompletePlaces(prefix.toString().toLowerCase()).addOnCompleteListener(new OnCompleteListener<AutocompletePredictionBufferResponse>() {
                    public void onComplete(@NonNull Task<AutocompletePredictionBufferResponse> task) {
                        if (task.isSuccessful()) {
                            AutocompletePredictionBufferResponse predictions = (AutocompletePredictionBufferResponse) task.getResult();
                            Iterator it = predictions.iterator();
                            while (it.hasNext()) {
                                AutocompletePrediction prediction = (AutocompletePrediction) it.next();
                                List list = predictedPlacesList;
                                StringBuilder sb = new StringBuilder();
                                sb.append(prediction.getFullText(null));
                                sb.append("@");
                                sb.append(prediction.getPlaceId());
                                list.add(sb.toString().toString());
                            }
                            predictions.release();
                        } else {
                            Log.d("Podsd", task.getException().getMessage());
                        }
                        PlacesAutoCompleteFilter.placeResults = true;
                        synchronized (PlacesAutoCompleteFilter.lockTwo) {
                            PlacesAutoCompleteFilter.lockTwo.notifyAll();
                        }
                    }
                });
                while (!placeResults) {
                    synchronized (lockTwo) {
                        try {
                            lockTwo.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                }
                results.values = predictedPlacesList;
                results.count = predictedPlacesList.size();
                StringBuilder sb = new StringBuilder();
                sb.append("Autocomplete predictions size after wait");
                sb.append(results.count);
                Log.d("Pikas", sb.toString());
            }
            return results;
        }
        public void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                PlacesAdapter.placesList = (ArrayList) results.values;
            } else {
                PlacesAdapter.placesList = null;
            }
            for (int i = 0; i < PlacesAdapter.placesList.size(); i = i + 1 + 1) {
                PlacesAdapter.placesList.set(i, PlacesAdapter.placesList.get(i));
            }
            if (results.count > 0) {
                PlacesAdapter.notifyDataSetChanged();
            } else {
                PlacesAdapter.notifyDataSetInvalidated();
            }
        }

        private Task<AutocompletePredictionBufferResponse> getAutoCompletePlaces(String query) {
            return PlacesAdapter.geoDataClient.getAutocompletePredictions(query, PlacesAdapter.BOUNDS_INDIA, new Builder().build());
        }
    }

    public PlacesAdapter(Context context2) {
        super(context2, 17367050, new ArrayList());
        context = context2;
        geoDataClient = Places.getGeoDataClient(context2, (PlacesOptions) null);
    }

    public int getCount() {
        return placesList.size();
    }

    public String getItem(int position) {
        return (String) placesList.get(position);
    }

    public Filter getFilter() {
        return filter;
    }

    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_search_layout, parent, false);
        }
        ((TextView) view.findViewById(R.id.placename)).setText(((String) placesList.get(position)).split("@")[0]);
        return view;
    }
}
