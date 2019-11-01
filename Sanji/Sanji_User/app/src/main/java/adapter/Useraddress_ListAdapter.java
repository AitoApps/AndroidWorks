package adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.sanji.ConnectionDetecter;
import com.sanji.DatabaseHandler;
import com.sanji.Temp;
import com.sanji.UserDatabaseHandler;
import com.sanji.User_Address;
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
    UserDatabaseHandler udb;

    public class delete_product extends AsyncTask<String, Void, String> {
        public delete_product() {
        }
        public void onPreExecute() {
            Useraddress_ListAdapter.pd.setMessage("Please wait...");
            Useraddress_ListAdapter.pd.setCancelable(false);
            Useraddress_ListAdapter.pd.show();
            Useraddress_ListAdapter useraddress_ListAdapter = Useraddress_ListAdapter.this;
            useraddress_ListAdapter.timerDelayRemoveDialog(50000, useraddress_ListAdapter.pd);
        }
        public String doInBackground(String... arg0) {
            String str = "UTF-8";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("deleteuseraddress.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Useraddress_ListAdapter.addressid);
                sb3.append("");
                sb2.append(URLEncoder.encode(sb3.toString(), str));
                String data2 = sb2.toString();
                URLConnection conn = new URL(link).openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data2);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb4 = new StringBuilder();
                while (true) {
                    String readLine = reader.readLine();
                    String line = readLine;
                    if (readLine == null) {
                        return sb4.toString();
                    }
                    sb4.append(line);
                }
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            if (Useraddress_ListAdapter.pd != null || Useraddress_ListAdapter.pd.isShowing()) {
                Useraddress_ListAdapter.pd.dismiss();
                if (result.contains("ok")) {
                    Useraddress_ListAdapter.db.deleteselectaddress();
                    ((User_Address) Useraddress_ListAdapter.activity).removeitem(Useraddress_ListAdapter.pos);
                    return;
                }
                Toasty.info(Useraddress_ListAdapter.context, Temp.tempproblem, 0).show();
            }
        }
    }

    public Useraddress_ListAdapter(Activity activity2, List<User_Address_Feeditem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        db = new DatabaseHandler(context);
        udb = new UserDatabaseHandler(context);
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
            inflater = (LayoutInflater) activity.getSystemService("layout_inflater");
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
        StringBuilder sb = new StringBuilder();
        sb.append(", ");
        sb.append(item.getpincode());
        pincode.setText(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(item.getuser_mobile1());
        sb2.append(" , ");
        sb2.append(item.getuser_mobile2());
        mobile1.setText(sb2.toString());
        name.setTypeface(face);
        address.setTypeface(face1);
        place.setTypeface(face1);
        mobile1.setTypeface(face1);
        pincode.setTypeface(face1);
        Log.w("possssssss", "pofd");
        if (db.getselectaddress().equalsIgnoreCase(item.getsn())) {
            radio1.setChecked(true);
        }
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                User_Address_Feeditem user_Address_Feeditem = (User_Address_Feeditem) Useraddress_ListAdapter.feedItems.get(position);
                User_Address_Feeditem item = (User_Address_Feeditem) Useraddress_ListAdapter.feedItems.get(position);
                Useraddress_ListAdapter.addressid = item.getsn();
                Useraddress_ListAdapter useraddress_ListAdapter = Useraddress_ListAdapter.this;
                useraddress_ListAdapter.pos = position;
                useraddress_ListAdapter.showalert_delete("Are you sure want to delete ?");
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    User_Address_Feeditem user_Address_Feeditem = (User_Address_Feeditem) Useraddress_ListAdapter.feedItems.get(position);
                    User_Address_Feeditem item = (User_Address_Feeditem) Useraddress_ListAdapter.feedItems.get(position);
                    Temp.isaddressedit = 1;
                    Temp.user_sn = item.getsn();
                    Temp.user_name = item.getuser_name();
                    Temp.user_place = item.getuser_place();
                    Temp.user_mobile1 = item.getuser_mobile1();
                    Temp.user_mobile2 = item.getuser_mobile2();
                    Temp.user_address = item.getuser_address();
                    Temp.user_landmark = item.getuser_landmark();
                    Temp.user_pincode = item.getpincode();
                    ((User_Address) Useraddress_ListAdapter.activity).showuserdetails(Temp.user_sn, Temp.user_name, Temp.user_place, Temp.user_mobile1, Temp.user_mobile2, Temp.user_address, Temp.user_landmark, Temp.user_pincode);
                } catch (Exception e) {
                }
            }
        });
        radio1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((User_Address) Useraddress_ListAdapter.activity).uncheckall();
                Useraddress_ListAdapter.db.addselectaddress(item.getsn());
                radio1.setChecked(true);
            }
        });
        return convertView;
    }

    public void showalert_delete(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (Useraddress_ListAdapter.cd.isConnectingToInternet()) {
                    new delete_product().execute(new String[0]);
                } else {
                    Toasty.info(Useraddress_ListAdapter.context, Temp.nointernet, 0).show();
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
}
