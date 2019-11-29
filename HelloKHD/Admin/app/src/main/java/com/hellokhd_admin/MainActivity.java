package com.hellokhd_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    UserDatabaseHandler udb = new UserDatabaseHandler(this);
    final DatabaseHandler db=new DatabaseHandler(this);
    ImageView districresult,schoolresult,advts,stages,markadmin,veriadmin,shops,tourspot,room,docters,video,announcment,news,atm,pumb;
    ImageView hospital,accomedation,food,ambulance,bus,train,cinema,transporation,helpdesk;
    List<String> lst_itemtype= new ArrayList();
    List<String> lst_distric= new ArrayList();
    Typeface face;
    ImageView logout;
    ImageView insta;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        FirebaseApp.initializeApp(this);
        pd=new ProgressDialog(this);
        logout=findViewById(R.id.logout);
        helpdesk=findViewById(R.id.helpdesk);
        insta=findViewById(R.id.insta);
        districresult=findViewById(R.id.districresult);
        transporation=findViewById(R.id.transport);
        schoolresult=findViewById(R.id.schoolresult);
        advts=findViewById(R.id.advts);
        stages=findViewById(R.id.stages);
        markadmin=findViewById(R.id.markadmin);
        veriadmin=findViewById(R.id.veriadmin);
        shops=findViewById(R.id.shops);
        tourspot=findViewById(R.id.tourspot);
        room=findViewById(R.id.room);
        docters=findViewById(R.id.docters);
        video=findViewById(R.id.video);
        announcment=findViewById(R.id.announcment);
        news=findViewById(R.id.news);
        atm=findViewById(R.id.atm);
        pumb=findViewById(R.id.pumb);

        hospital=findViewById(R.id.hospital);
        accomedation=findViewById(R.id.accomedation);
        food=findViewById(R.id.food);

        ambulance=findViewById(R.id.ambulance);
        bus=findViewById(R.id.bus);
        train=findViewById(R.id.train);
        cinema=findViewById(R.id.cinema);


        if (db.getscreenwidth().equalsIgnoreCase("")) {
            int width = getResources().getDisplayMetrics().widthPixels;
            db.addscreenwidth(width+"");
        }

        if (udb.get_userid().equalsIgnoreCase("")) {
            startActivity(new Intent(getApplicationContext(), Registration.class));
            finish();
            return;
        }

        advts=findViewById(R.id.advts);
        stages=findViewById(R.id.stages);
        markadmin=findViewById(R.id.markadmin);
        veriadmin=findViewById(R.id.veriadmin);
        shops=findViewById(R.id.shops);
        tourspot=findViewById(R.id.tourspot);
        room=findViewById(R.id.room);
        docters=findViewById(R.id.docters);
        video=findViewById(R.id.video);
        announcment=findViewById(R.id.announcment);
        news=findViewById(R.id.news);

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),VideoList.class);
                startActivity(i);
            }
        });

        announcment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Anouncments.class);
                startActivity(i);
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),News_List.class);
                startActivity(i);
            }
        });
        advts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),Advt_List.class);
                startActivity(i);

            }
        });

        stages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Stage_List.class);
                startActivity(i);
            }
        });

        markadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),MarkAdminList.class);
                startActivity(i);
            }
        });

        veriadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Verification_Admin.class);
                startActivity(i);
            }
        });

        shops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypes="0";
                Intent i=new Intent(getApplicationContext(),Shop_List.class);
                startActivity(i);
            }
        });

        tourspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypes="1";
                Intent i=new Intent(getApplicationContext(),Shop_List.class);
                startActivity(i);
            }
        });

        room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypes="2";
                Intent i=new Intent(getApplicationContext(),Shop_List.class);
                startActivity(i);
            }
        });

        docters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypes="3";
                Intent i=new Intent(getApplicationContext(),Shop_List.class);
                startActivity(i);
            }
        });

       atm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Temp.shoptypes="4";
                Intent i=new Intent(getApplicationContext(),Shop_List.class);
                startActivity(i);
            }
        });

        pumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypes="5";
                Intent i=new Intent(getApplicationContext(),Shop_List.class);
                startActivity(i);
            }
        });

        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypes="6";
                Intent i=new Intent(getApplicationContext(),Shop_List.class);
                startActivity(i);
            }
        });
        accomedation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypes="7";
                Intent i=new Intent(getApplicationContext(),Shop_List.class);
                startActivity(i);
            }
        });
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypes="8";
                Intent i=new Intent(getApplicationContext(),Shop_List.class);
                startActivity(i);
            }
        });

        districresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Distric_Result.class);
                startActivity(i);
            }
        });

        schoolresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),School_Result.class);
                startActivity(i);
            }
        });

        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.is_amb_trans="1";
                Intent i=new Intent(getApplicationContext(),Ambulance_List.class);
                startActivity(i);
            }
        });
        helpdesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.is_amb_trans="3";
                Intent i=new Intent(getApplicationContext(),Ambulance_List.class);
                startActivity(i);
            }
        });
        transporation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.is_amb_trans="2";
                Intent i=new Intent(getApplicationContext(),Ambulance_List.class);
                startActivity(i);
            }
        });
        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.is_train_bus="1";
                Intent i=new Intent(getApplicationContext(),Bus_List.class);
                startActivity(i);
            }
        });
        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.is_train_bus="2";
                Intent i=new Intent(getApplicationContext(),Bus_List.class);
                startActivity(i);
            }
        });
        cinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),CinemaList.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                udb.deleteuserid();

                startActivity(new Intent(getApplicationContext(), Registration.class));
                finish();
                return;
            }
        });

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new getresult().execute();

            }
        });
    }

    public class getresult extends AsyncTask<String, Void, String> {


        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }

        public String doInBackground(String... arg0) {

            try {
                String link=Temp.weblink +"pagefeedupdate.php";
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
                Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
            }
        }
    }

    public void selectitemtype() {
        try {
            final Dialog dialog3 = new Dialog(this);
            dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog3.setContentView(R.layout.custom_itemtype);
            dialog3.setCancelable(true);
            final Spinner itemtype=dialog3.findViewById(R.id.itemtype);
            Button update=dialog3.findViewById(R.id.select);
            update.setTypeface(face);

            lst_itemtype.clear();
            lst_itemtype.add("Choose Type");
            lst_itemtype.add("HS");
            lst_itemtype.add("HSS");



            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, lst_itemtype) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    ((TextView) v).setTextSize(16.0f);
                    ((TextView) v).setTypeface(face);
                    return v;
                }

                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View v = super.getDropDownView(position, convertView, parent);
                    ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    ((TextView) v).setTextSize(16.0f);
                    ((TextView) v).setTypeface(face);
                    return v;
                }
            };
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            itemtype.setAdapter(dataAdapter2);
            itemtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {

                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });


            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(itemtype.getSelectedItemPosition()<=0)
                    {
                        Toast.makeText(getApplicationContext(),"Please select item type",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Temp.itemtype=itemtype.getSelectedItemPosition()+"";
                        startActivity(new Intent(getApplicationContext(),Programs.class));
                        dialog3.dismiss();
                    }

                }
            });
            dialog3.show();
        } catch (Exception e) {
        }
    }



    public void selectdistric() {
        try {
            final Dialog dialog3 = new Dialog(this);
            dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog3.setContentView(R.layout.custom_districs);
            dialog3.setCancelable(true);
            final Spinner distric=dialog3.findViewById(R.id.distric);
            Button update=dialog3.findViewById(R.id.select);
            update.setTypeface(face);

            lst_distric.clear();
            lst_distric.add("Choose Distric");
            lst_distric.add("Kasaragod"); //1
            lst_distric.add("Kannur"); //2
            lst_distric.add("Wayanad"); //3
            lst_distric.add("Kozhikode"); //4
            lst_distric.add("Malappuram"); //5
            lst_distric.add("Palakkad"); //6
            lst_distric.add("Thrissur"); //7
            lst_distric.add("Ernakulam"); //8
            lst_distric.add("Idukki"); //9
            lst_distric.add("Kottayam"); //10
            lst_distric.add("Alappuzha"); //11
            lst_distric.add("Pathanamthitta"); //12
            lst_distric.add("Kollam"); //13
            lst_distric.add("Thiruvananthapuram"); //14

            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,lst_distric) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    ((TextView) v).setTextSize(16.0f);
                    ((TextView) v).setTypeface(face);
                    return v;
                }

                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View v = super.getDropDownView(position, convertView, parent);
                    ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    ((TextView) v).setTextSize(16.0f);
                    ((TextView) v).setTypeface(face);
                    return v;
                }
            };
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            distric.setAdapter(dataAdapter2);
           distric.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {

                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });


            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(distric.getSelectedItemPosition()<=0)
                    {
                        Toast.makeText(getApplicationContext(),"Please select distric",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Temp.distric=distric.getSelectedItemPosition()+"";
                        startActivity(new Intent(getApplicationContext(),Programs.class));
                        dialog3.dismiss();
                    }

                }
            });
            dialog3.show();
        } catch (Exception e) {
        }
    }

}
