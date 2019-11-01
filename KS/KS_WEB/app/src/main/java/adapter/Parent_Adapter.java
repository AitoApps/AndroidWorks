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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.suthra_malayalam_web.Act_Pos_Subcat;
import com.suthra_malayalam_web.Album_Pos;
import com.suthra_malayalam_web.DataBase;
import com.suthra_malayalam_web.DataBase_MobileNumber;
import com.suthra_malayalam_web.DataBase_POS;
import com.suthra_malayalam_web.NetConnect;
import com.suthra_malayalam_web.R;
import com.suthra_malayalam_web.ReadView_Subcatogery;
import com.suthra_malayalam_web.Static_Veriable;
import es.dmoral.toasty.Toasty;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class Parent_Adapter extends RecyclerView.Adapter<Parent_Adapter.Myviewholder> {
    Activity activity;

    public Context context;
    DataBase_POS da;
    final DataBase dataBase;
    public Dialog dialog;
    Typeface face;
    private List<Parent_Icons> items;
    final DataBase_MobileNumber mdb;
    NetConnect nc;
    ProgressDialog pd;
    public TextView pics;

    private class File_Download extends AsyncTask<String, Integer, String> {
        private File_Download() {
        }


        public void onPreExecute() {
            super.onPreExecute();
        }


        public String doInBackground(String... Url) {
            try {

                URL url = new URL(Url[0]);
                URLConnection connection = url.openConnection();
                connection.setRequestProperty("Accept-Encoding", "identity");
                connection.connect();
                int fileLength = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(context.getFilesDir()+"/"+Static_Veriable.foldername+"/chithram.sqlite");
                byte[] data2 = new byte[1024];
                long total = 0;
                while (true) {
                    int read = input.read(data2);
                    int count = read;
                    if (read == -1) {
                        output.flush();
                        output.close();
                        input.close();
                        break;
                    } else if (!dialog.isShowing()) {
                        return "stoped";
                    } else {
                        total += (long) count;
                        publishProgress(new Integer[]{Integer.valueOf((int) ((100 * total) / ((long) fileLength)))});
                        output.write(data2, 0, count);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "ok";
        }


        public void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            pics.setText(progress[0]+"%");
        }


        public void onPostExecute(String result) {
            try {
                if (!result.equalsIgnoreCase("stoped")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(context.getFilesDir());
                    sb.append("/");
                    sb.append(Static_Veriable.foldername);
                    sb.append("/chithram.sqlite");
                    if (new File(sb.toString()).exists()) {
                        new Handler().post(new Runnable() {
                            public void run() {
                                try {
                                    new DataBase_POS(context, "chithram.sqlite").importIfNotExist();
                                    Toasty.info(context, "ഡൗണ്‍ലോഡ് പൂര്‍ത്തിയായിരിക്കുന്നു ", 0).show();
                                } catch (IOException e) {
                                }
                            }
                        });
                    }
                }
                dialog.dismiss();
            } catch (Exception e) {
            }
        }
    }

    class Myviewholder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public Myviewholder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }

    public class check_pymntstatus extends AsyncTask<String, Void, String> {
        public check_pymntstatus() {
        }


        public void onPreExecute() {
            pd.setMessage(Static_Veriable.checkec);
            pd.setCancelable(false);
            pd.show();
        }


        public String doInBackground(String... arg0) {

            try {

                String link= Static_Veriable.weblink +"checkactive.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(dataBase.get_veriid(), "UTF-8");
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
            try {
                pd.dismiss();
                if (result.contains("waiting")) {
                    alert_show(Static_Veriable.waintingec);
                } else if (result.contains("okeda")) {
                    new pymnt_success().execute(new String[0]);
                } else if (result.contains("deactive")) {
                    dataBase.drop_veriid();
                    alert_show(Static_Veriable.disagreeec);
                } else if (result.contains("error")) {
                    dataBase.drop_veriid();
                    alert_show(Static_Veriable.tmpproblem);
                } else {
                    Toasty.error(context, Static_Veriable.tmpproblem, 1).show();
                }
            } catch (Exception e) {
            }
        }
    }

    public class checkposactive1 extends AsyncTask<String, Void, String> {
       
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            Parent_Adapter parent_Adapter = Parent_Adapter.this;
            parent_Adapter.timer_dialog(30000, parent_Adapter.pd);
        }
        
        public String doInBackground(String... arg0) {


            try {

                String link= Static_Veriable.weblink +"checkposactive1.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode("", "UTF-8");
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
            try {
                pd.dismiss();
                if (result.contains("yes")) {
                    String[] k = result.split(",");
                    if (k.length >= 2) {
                        dataBase.add_downsql(k[1]);
                        dataBase.add_firstdown("ok");
                        show_download_view();
                    } else {
                        Toasty.error(context, Static_Veriable.tmpproblem, 0).show();
                    }
                } else if (result.contains("stopped")) {
                    Toasty.error(context, "ക്ഷമിക്കണം ! ഈ ആപ്പ് ഇനി വര്‍ക്ക് ചെയ്യില്ല", 0).show();
                }
            } catch (Exception e) {
            }
        }
    }

    public class pymnt_success extends AsyncTask<String, Void, String> {
      
        public void onPreExecute() {
            pd.setMessage("Updating your payment...Please wait");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {


            try {

                String link= Static_Veriable.weblink +"afterpaymentsucuss.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(mdb.get_mob(), "UTF-8");
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
            try {
                pd.dismiss();
                dataBase.drop_veriid();
                dataBase.add_purchase("1");
                alert_show(Static_Veriable.aagreeec);
            } catch (Exception e) {
            }
        }
    }

    public Parent_Adapter(Activity activity2, List<Parent_Icons> items2, Context context2) {
        items = items2;
        context = context2;
        activity = activity2;
        dataBase = new DataBase(context2);
        da = new DataBase_POS(context2, "chithram.sqlite");
        nc = new NetConnect(context2);
        pd = new ProgressDialog(context2);
        mdb = new DataBase_MobileNumber(context2);
        face = Typeface.createFromAsset(context2.getAssets(), "app_fonts/malfont.ttf");
    }

    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_layout, parent, false));
    }

    public void onBindViewHolder(final Myviewholder holder, int position) {
        holder.imageView.setImageResource(((Parent_Icons) items.get(position)).image);
        holder.imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (holder.getAdapterPosition() != -1) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition == 0) {
                        pos_pics();
                    } else if (adapterPosition == 1) {
                        album_pics();
                    } else if (adapterPosition == 2) {
                        mthr_hoods();
                    } else if (adapterPosition == 3) {
                        knwldge();
                    } else if (adapterPosition == 4) {
                        foods();
                    } else if (adapterPosition == 5) {
                        issues();
                    }
                }
            }
        });
    }

    public int getItemCount() {
        return items.size();
    }

    public void mthr_hoods() {
        if (nc.isConnectingToInternet()) {
            dataBase.add_cat("1");
            context.startActivity(new Intent(context, ReadView_Subcatogery.class));
            return;
        }
        Toasty.info(context, Static_Veriable.nonet, 0).show();
    }

    public void knwldge() {
        if (nc.isConnectingToInternet()) {
            dataBase.add_cat("2");
            context.startActivity(new Intent(context, ReadView_Subcatogery.class));
            return;
        }
        Toasty.info(context, Static_Veriable.nonet, 0).show();
    }

    public void foods() {
        if (nc.isConnectingToInternet()) {
            dataBase.add_cat("4");
            context.startActivity(new Intent(context, ReadView_Subcatogery.class));
            return;
        }
        Toasty.info(context, Static_Veriable.nonet, 0).show();
    }

    public void issues() {
        if (nc.isConnectingToInternet()) {
            dataBase.add_cat("3");
            context.startActivity(new Intent(context, ReadView_Subcatogery.class));
            return;
        }
        Toasty.info(context, Static_Veriable.nonet, 0).show();
    }

    public void pos_pics() {
        String str = "android.permission.READ_EXTERNAL_STORAGE";
        String str2 = "android.permission.WRITE_EXTERNAL_STORAGE";
        String str3 = "";
        try {
            if (ContextCompat.checkSelfPermission(context, str2) != 0) {
                ActivityCompat.requestPermissions(activity, new String[]{str2}, 1);
            } else if (ContextCompat.checkSelfPermission(context, str) != 0) {
                ActivityCompat.requestPermissions(activity, new String[]{str}, 1);
            } else if (!dataBase.get_firstdown().equalsIgnoreCase(str3)) {
                try {
                    if (da.getpic("815").equalsIgnoreCase(str3)) {
                        if (nc.isConnectingToInternet()) {
                            show_download_view();
                        } else {
                            Toasty.info(context, Static_Veriable.nonet, 0).show();
                        }
                    } else if (dataBase.get_veriid().equalsIgnoreCase(str3)) {
                        context.startActivity(new Intent(context, Act_Pos_Subcat.class));
                    } else if (nc.isConnectingToInternet()) {
                        new check_pymntstatus().execute(new String[0]);
                    } else {
                        Toasty.info(context, Static_Veriable.nonet, 0).show();
                    }
                } catch (Exception e) {
                    if (nc.isConnectingToInternet()) {
                        show_download_view();
                    } else {
                        Toasty.info(context, Static_Veriable.nonet, 0).show();
                    }
                }
            } else if (nc.isConnectingToInternet()) {
                new checkposactive1().execute(new String[0]);
            } else {
                Toasty.info(context, Static_Veriable.nonet, 0).show();
            }
        } catch (Exception e2) {
        }
    }

    public void album_pics() {
        String str = "android.permission.WRITE_EXTERNAL_STORAGE";
        if (ContextCompat.checkSelfPermission(context, str) != 0) {
            ActivityCompat.requestPermissions(activity, new String[]{str}, 1);
            return;
        }
        String str2 = "android.permission.READ_EXTERNAL_STORAGE";
        if (ContextCompat.checkSelfPermission(context, str2) != 0) {
            ActivityCompat.requestPermissions(activity, new String[]{str2}, 1);
            return;
        }
        String str3 = "";
        if (!dataBase.get_firstdown().equalsIgnoreCase(str3)) {
            try {
                if (da.getpic("815").equalsIgnoreCase(str3)) {
                    if (nc.isConnectingToInternet()) {
                        show_download_view();
                    } else {
                        Toasty.info(context, Static_Veriable.nonet, 0).show();
                    }
                } else if (dataBase.get_veriid().equalsIgnoreCase(str3)) {
                    context.startActivity(new Intent(context, Album_Pos.class));
                } else if (nc.isConnectingToInternet()) {
                    new check_pymntstatus().execute(new String[0]);
                } else {
                    Toasty.info(context, Static_Veriable.nonet, 0).show();
                }
            } catch (Exception e) {
                if (nc.isConnectingToInternet()) {
                    show_download_view();
                } else {
                    Toasty.info(context, Static_Veriable.nonet, 0).show();
                }
            }
        } else if (nc.isConnectingToInternet()) {
            new checkposactive1().execute(new String[0]);
        } else {
            Toasty.info(context, Static_Veriable.nonet, 0).show();
        }
    }

    public void show_download_view() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.download_custom);
        dialog.setTitle("Download");
        dialog.setCancelable(false);
        TextView txtmsg = (TextView) dialog.findViewById(R.id.txtmsg);
        pics = (TextView) dialog.findViewById(R.id.pics);
        Button close = (Button) dialog.findViewById(R.id.close);
        txtmsg.setTypeface(face);
        txtmsg.setText(Static_Veriable.pleasewaitdown);
        pics.setTypeface(face);
        close.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        if (!dialog.isShowing()) {
            dialog.show();
        }
        if (dataBase.get_downsql().equalsIgnoreCase("")) {
            dataBase.delete_firstdown();
            Toasty.error(context, Static_Veriable.tmpproblem, 0).show();
            return;
        }
        new File_Download().execute(new String[]{dataBase.get_downsql()});
    }

    public void timer_dialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void alert_show(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage((CharSequence) message).setCancelable(false).setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
