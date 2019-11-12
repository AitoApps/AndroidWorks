package com.hellokhd_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    UserDatabaseHandler udb = new UserDatabaseHandler(this);
    final DatabaseHandler db=new DatabaseHandler(this);
    ImageView advts,stages,markadmin,veriadmin,shops,tourspot,room,docters,video,announcment,news,atm,pumb;
    List<String> lst_itemtype= new ArrayList();
    List<String> lst_distric= new ArrayList();
    Typeface face;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        FirebaseApp.initializeApp(this);
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
