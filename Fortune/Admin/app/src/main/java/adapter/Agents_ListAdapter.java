package adapter;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.fortune_admin.Add_Agents;
import com.fortune_admin.Agents;
import com.fortune_admin.ConnectionDetecter;
import com.fortune_admin.Lucky_Draw_Types;
import com.fortune_admin.MainActivity;
import com.fortune_admin.R;
import com.fortune_admin.Temp;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import data.AgentList_FeedItem;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Agents_ListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    private Context context;
    Typeface face;
    public List<AgentList_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String t_agentid="";
    public Agents_ListAdapter(Activity activity2, List<AgentList_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        face = Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
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
            convertView = inflater.inflate(R.layout.custom_agents, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView agentid = (TextView) convertView.findViewById(R.id.agentid);
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        ImageView edit = (ImageView) convertView.findViewById(R.id.edit);
        AgentList_FeedItem item = (AgentList_FeedItem) feedItems.get(position);
        name.setTypeface(face);
        agentid.setTypeface(face);

        name.setText(item.getName());
        agentid.setText("Agent Id : "+item.getAgentid());

        edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AgentList_FeedItem item = (AgentList_FeedItem) feedItems.get(position);
                Temp.agentedit=1;
                Temp.agent_sn=item.getSn();
                Temp.agent_name=item.getName();
                Temp.agent_mobile=item.getMobile();
                Temp.agent_agentid=item.getAgentid();
                Intent i = new Intent(context, Add_Agents.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });

        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AgentList_FeedItem item = (AgentList_FeedItem) feedItems.get(position);
                pos = position;
                t_agentid=item.getSn();
                showalert_delete("Are you sure want to delete ?");
            }
        });
        return convertView;
    }
    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void showalert_delete(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                     new delete_data().execute();
                } else {
                    Toast.makeText(context, Temp.nointernet, Toast.LENGTH_SHORT).show();
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

    public class delete_data extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {
                String link= Temp.weblink+"delete_agent.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(t_agentid, "UTF-8");
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
                    Toasty.info(context, "Deleted", Toast.LENGTH_SHORT).show();
                    ((Agents) activity).removeitem(pos);
                }
                else{
                    Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}
