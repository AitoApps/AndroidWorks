package adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fishapp.user.Add_Address;
import com.fishapp.user.ConnectionDetecter;
import com.fishapp.user.DatabaseHandler;
import com.fishapp.user.R;
import com.fishapp.user.Temp;
import com.fishapp.user.User_Address;
import data.User_Address_Feeditem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class Useraddress_ListAdapter extends BaseAdapter {

    public Activity activity;
    public String addressid = "";
    public ConnectionDetecter cd;

    public Context context;
    DatabaseHandler db;
    Typeface face;
    Typeface face1;

    public List<User_Address_Feeditem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public Useraddress_ListAdapter(Activity activity2, List<User_Address_Feeditem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        db = new DatabaseHandler(context);
        face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(context.getAssets(), "font/proximanormal.ttf");
    }

    public int getCount() {
        return feedItems.size();
    }

    public Object getItem(int location) {
        return feedItems.get(location);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.useraddress_customdetails, null);
        }
        final RadioButton radio1 = (RadioButton) convertView.findViewById(R.id.radio1);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView address = (TextView) convertView.findViewById(R.id.address);
        TextView place = (TextView) convertView.findViewById(R.id.place);
        TextView pincode = (TextView) convertView.findViewById(R.id.pincode);
        TextView mobile1 = (TextView) convertView.findViewById(R.id.mobile1);
        ImageView edit = (ImageView) convertView.findViewById(R.id.edit);
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        final User_Address_Feeditem item = (User_Address_Feeditem) feedItems.get(position);
        name.setText(item.getuser_name());
        address.setText(item.getuser_address());
        place.setText(item.getuser_place());
        mobile1.setText(item.getuser_mobile1()+","+item.getuser_mobile2());
        name.setTypeface(face);
        address.setTypeface(face1);
        place.setTypeface(face1);
        mobile1.setTypeface(face1);
        pincode.setTypeface(face1);
        if (db.getselectaddress().equalsIgnoreCase(item.getsn())) {
            radio1.setChecked(true);
        }
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                User_Address_Feeditem user_Address_Feeditem = (User_Address_Feeditem) feedItems.get(position);
                User_Address_Feeditem item = (User_Address_Feeditem) feedItems.get(position);
                addressid = item.getsn();
                pos = position;
                showalert_delete("Are you sure want to delete ?");
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    User_Address_Feeditem user_Address_Feeditem = (User_Address_Feeditem) feedItems.get(position);
                    User_Address_Feeditem item = (User_Address_Feeditem) feedItems.get(position);
                    Temp.isaddressedit = 1;
                    Temp.user_sn = item.getsn();
                    Temp.user_name = item.getuser_name();
                    Temp.user_place = item.getuser_place();
                    Temp.user_mobile1 = item.getuser_mobile1();
                    Temp.user_mobile2 = item.getuser_mobile2();
                    Temp.user_address = item.getuser_address();
                    Temp.user_landmark = item.getuser_landmark();
                    Temp.user_pincode = item.getpincode();
                    Intent i = new Intent(context, Add_Address.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } catch (Exception e) {
                }
            }
        });
        radio1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((User_Address) activity).uncheckall();
                db.addselectaddress(item.getsn());
                radio1.setChecked(true);
            }
        });
        return convertView;
    }

    public void showalert_delete(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new delete_product().execute(new String[0]);
                } else {
                    Toasty.info(context, Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }
    public class delete_product extends AsyncTask<String, Void, String> {
        public delete_product() {
        }

        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {

            try {

                String link= Temp.weblink +"deleteuseraddress.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(addressid, "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }

        }
        public void onPostExecute(String result) {
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
                if (result.contains("ok")) {
                    db.deleteselectaddress();
                    ((User_Address) activity).removeitem(pos);
                    return;
                }
                Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
