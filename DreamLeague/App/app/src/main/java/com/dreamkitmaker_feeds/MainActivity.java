package com.dreamkitmaker_feeds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.ColorsList_Adapter;
import adapter.FiltersList_Adapter;
import adapter.TextColorsList_Adapter;
import data.Colors_FeedItem;
import data.Filters_FeedItem;
import data.TextColors_FeedItem;
import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;
import ja.burhanrashid52.photoeditor.ViewType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class MainActivity extends AppCompatActivity {

    PhotoEditorView photo;
    PhotoEditor mPhotoEditor;
    RelativeLayout brushlyt;
    String[] PERMISSIONS = {
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.ACCESS_WIFI_STATE
    };
    int PERMISSION_ALL = 1;
    RelativeLayout brushproperty;
    SeekBar brushwidth,brushopacity;

    public ColorsList_Adapter coloradapter;
    public TextColorsList_Adapter textcoloradapter;
    public FiltersList_Adapter filteradapter;

    public List<Colors_FeedItem> colorfeeditem;
    public List<TextColors_FeedItem> textcolorfeeditem;
    public List<Filters_FeedItem> filterfeeditem;

    RecyclerView colorlayout;
    Bitmap img;
    RelativeLayout eraserlyt;

    RelativeLayout textproperty;
    EditText edittext;
    RecyclerView textscolorlayout;
    TextView close,apply;
    RelativeLayout textslyt;
    View textview;
    public int istextedit=0;

    RelativeLayout imagelyt,filterlyt;

    RelativeLayout filterproperty;
    RecyclerView filterlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photo=findViewById(R.id.photo);
        brushlyt=findViewById(R.id.brushlyt);
        brushproperty=findViewById(R.id.brushproperty);
        brushwidth=findViewById(R.id.brushwidth);
        brushopacity=findViewById(R.id.brushopacity);
        eraserlyt=findViewById(R.id.eraserlyt);
        textslyt=findViewById(R.id.textslyt);
        textscolorlayout=findViewById(R.id.textscolorlayout);
        textproperty=findViewById(R.id.textproperty);
        filterlayout=findViewById(R.id.filterlayout);
        edittext=findViewById(R.id.edittext);
        close=findViewById(R.id.close);
        apply=findViewById(R.id.apply);
        imagelyt=findViewById(R.id.imagelyt);
        filterlyt=findViewById(R.id.filterlyt);
        filterproperty=findViewById(R.id.filterproperty);
        filterlayout=findViewById(R.id.filterlayout);

        photo.getSource().setImageResource(R.drawable.demo);
        colorlayout=findViewById(R.id.colorlayout);
        mPhotoEditor = new PhotoEditor.Builder(this, photo)
                .setPinchTextScalable(true)
                //.setDefaultTextTypeface(mTextRobotoTf)
               // .setDefaultEmojiTypeface(mEmojiTypeFace)
                .build();

        textslyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadtextcolor();
                istextedit=0;
                brushproperty.setVisibility(View.GONE);
                filterproperty.setVisibility(View.GONE);
                mPhotoEditor.setBrushDrawingMode(false);
                textproperty.setVisibility(View.VISIBLE);
                edittext.setText("");
            }
        });


        eraserlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brushproperty.setVisibility(View.GONE);
                textproperty.setVisibility(View.GONE);
                filterproperty.setVisibility(View.GONE);
                mPhotoEditor.setBrushDrawingMode(false);
                mPhotoEditor.brushEraser();
            }
        });

        brushlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadcolor();

                brushproperty.setVisibility(View.VISIBLE);
                textproperty.setVisibility(View.GONE);
                filterproperty.setVisibility(View.GONE);
                mPhotoEditor.setBrushDrawingMode(true);
                mPhotoEditor.setBrushSize(brushwidth.getProgress());
                mPhotoEditor.setOpacity(brushopacity.getProgress());
                mPhotoEditor.setBrushColor(Color.parseColor(Temp.color));
            }
        });




        imagelyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brushproperty.setVisibility(View.GONE);
                textproperty.setVisibility(View.GONE);
                filterproperty.setVisibility(View.GONE);

                if (!hasPermissions(MainActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    File folder = new File(Environment.getExternalStorageDirectory() + "/" + Temp.foldername);
                    if (!folder.exists()) {
                        folder.mkdir();
                        try {
                            new File(Environment.getExternalStorageDirectory() + "/" + Temp.foldername + "/.nomedia").createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    selectImage();
                }
            }
        });

        filterlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                brushproperty.setVisibility(View.GONE);
                textproperty.setVisibility(View.GONE);
                filterproperty.setVisibility(View.VISIBLE);
                laodfilter();

            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edittext.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getApplicationContext(),"Please add text",Toast.LENGTH_SHORT).show();
                    edittext.requestFocus();
                }
                else
                {
                    if(istextedit==1)
                    {
                        istextedit=0;
                        mPhotoEditor.editText(textview, edittext.getText().toString(), Color.parseColor(Temp.textcolor));
                        textproperty.setVisibility(View.GONE);
                    }
                    else
                    {
                        mPhotoEditor.addText(edittext.getText().toString(), Color.parseColor(Temp.textcolor));
                        textproperty.setVisibility(View.GONE);
                    }

                }

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textproperty.setVisibility(View.GONE);
            }
        });

        colorfeeditem = new ArrayList();
        textcolorfeeditem=new ArrayList();
        filterfeeditem=new ArrayList();

        coloradapter = new ColorsList_Adapter(this, colorfeeditem);
        textcoloradapter = new TextColorsList_Adapter(this, textcolorfeeditem);
        filteradapter = new FiltersList_Adapter(this, filterfeeditem);


        colorlayout.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        colorlayout.setAdapter(coloradapter);
        coloradapter.notifyDataSetChanged();


        textscolorlayout.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        textscolorlayout.setAdapter(textcoloradapter);
        textcoloradapter.notifyDataSetChanged();


        filterlayout.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        filterlayout.setAdapter(filteradapter);
        filteradapter.notifyDataSetChanged();

        mPhotoEditor.setOnPhotoEditorListener(new OnPhotoEditorListener() {
            @Override
            public void onEditTextChangeListener(View rootView, String text, int colorCode) {

                istextedit=1;
                textview=rootView;
                textproperty.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {

            }

            @Override
            public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {

            }

            @Override
            public void onStartViewChangeListener(ViewType viewType) {

                brushproperty.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onStopViewChangeListener(ViewType viewType) {

            }
        });


        brushwidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mPhotoEditor.setBrushSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        brushopacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mPhotoEditor.setOpacity(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }


    public void laodfilter()
    {
        PhotoFilter[] k={PhotoFilter.NONE,PhotoFilter.AUTO_FIX,PhotoFilter.BRIGHTNESS,PhotoFilter.CONTRAST,PhotoFilter.DOCUMENTARY,PhotoFilter.DUE_TONE,PhotoFilter.FILL_LIGHT,PhotoFilter.FISH_EYE,PhotoFilter.GRAIN,PhotoFilter.GRAY_SCALE,PhotoFilter.LOMISH,PhotoFilter.NEGATIVE,PhotoFilter.POSTERIZE,PhotoFilter.SATURATE,PhotoFilter.SEPIA,PhotoFilter.SHARPEN,PhotoFilter.TEMPERATURE,PhotoFilter.TINT,PhotoFilter.VIGNETTE,PhotoFilter.CROSS_PROCESS,PhotoFilter.BLACK_WHITE};
        String[] k1={"None","Auto Fix","Brightness","Contrast","Documentary","Due Tone","Fill Light","Fish Eye","Grain","Gray Scale","Lomish","Negative","Posterize","Saturate","Sepia","Sharpen","Temperature","Tint","Vignette","Cross Process","Black White"};
        int[] myImageList = new int[]{R.drawable.original,R.drawable.auto_fix,R.drawable.brightness,R.drawable.contrast,R.drawable.documentary,R.drawable.dual_tone,R.drawable.fill_light,R.drawable.fish_eye,R.drawable.grain,R.drawable.gray_scale,R.drawable.lomish,R.drawable.negative,R.drawable.posterize,R.drawable.saturate,R.drawable.sepia,R.drawable.sharpen,R.drawable.temprature,R.drawable.tint,R.drawable.vignette,R.drawable.cross_process,R.drawable.b_n_w};
        filterfeeditem.clear();
        for(int i=0;i<k.length;i++)
        {
            Filters_FeedItem cf=new Filters_FeedItem();
            cf.setStyleid(k[i]);
            cf.setStylename(k1[i]);
            cf.setDrawid(myImageList[i]);
            filterfeeditem.add(cf);
            filteradapter.notifyDataSetChanged();
        }
    }

    public void loadcolor()
    {
        String[] k={"#FFFFFF","#C0C0C0","#808080","#000000","#FF0000","#800000","#FFFF00","#808000","#00FF00","#008000","#00FFFF","#008080","#0000FF","#000080","#FF00FF","#800080"};
        colorfeeditem.clear();
        for(int i=0;i<k.length;i++)
        {
            Colors_FeedItem cf=new Colors_FeedItem();
            cf.setColorcode(k[i]);
            if(Temp.color.equalsIgnoreCase(k[i]))
            {
              cf.setIsselected("1");
            }
            else
            {
                cf.setIsselected("0");
            }
            colorfeeditem.add(cf);
            coloradapter.notifyDataSetChanged();
        }
    }

    public void loadtextcolor()
    {
        String[] k={"#FFFFFF","#C0C0C0","#808080","#000000","#FF0000","#800000","#FFFF00","#808000","#00FF00","#008000","#00FFFF","#008080","#0000FF","#000080","#FF00FF","#800080"};
        textcolorfeeditem.clear();
        for(int i=0;i<k.length;i++)
        {
            TextColors_FeedItem cf=new TextColors_FeedItem();
            cf.setColorcode(k[i]);
            if(Temp.textcolor.equalsIgnoreCase(k[i]))
            {
                cf.setIsselected("1");
            }
            else
            {
                cf.setIsselected("0");
            }
            textcolorfeeditem.add(cf);
            textcoloradapter.notifyDataSetChanged();
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (!(context == null || permissions == null)) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setbrushstyle()
    {

        mPhotoEditor.setBrushColor(Color.parseColor(Temp.color));
        mPhotoEditor.setOpacity(brushopacity.getProgress());
        mPhotoEditor.setBrushSize(brushwidth.getProgress());

        loadcolor();
    }

    public void setfilter(PhotoFilter f)
    {
        mPhotoEditor.setFilterEffect(f);
    }
    public void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    EasyImage.openCamera(MainActivity.this, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    EasyImage.openGallery(MainActivity.this, 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data2) {
        super.onActivityResult(requestCode, resultCode, data2);
        EasyImage.handleActivityResult(requestCode, resultCode, data2, this, new DefaultCallback() {
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
            }

            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                File f = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/productpic1.jpg");
                try {
                    f.createNewFile();
                } catch (IOException e) {
                }
                try {
                    Uri uri = Uri.fromFile(f);
                    UCrop.Options options = new UCrop.Options();
                    options.setFreeStyleCropEnabled(true);
                    options.setToolbarColor(Color.parseColor("#205c14"));
                    options.setStatusBarColor(Color.parseColor("#2E7D32"));
                    options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                    options.setCompressionQuality(80);
                    options.setToolbarTitle("Crop Image");
                    UCrop.of(Uri.fromFile(imageFile), uri).withOptions(options).start(MainActivity.this);
                } catch (Exception e2) {
                }
            }
        });
        if (requestCode == UCrop.REQUEST_CROP) {
            try {
                img = BitmapFactory.decodeFile(UCrop.getOutput(data2).getPath());
                mPhotoEditor.addImage(img);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (!hasPermissions(MainActivity.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PERMISSION_ALL);
                    } else {
                        File folder = new File(Environment.getExternalStorageDirectory() + "/" + Temp.foldername);
                        if (!folder.exists()) {
                            folder.mkdir();
                            try {
                                new File(Environment.getExternalStorageDirectory() + "/" + Temp.foldername + "/.nomedia").createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        selectImage();
                    }


                } else {

                }
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {

        if(filterproperty.getVisibility()==View.VISIBLE)
        {
            filterproperty.setVisibility(View.GONE);
        }
        else if(textproperty.getVisibility()==View.VISIBLE)
        {
            textproperty.setVisibility(View.GONE);
        }
        else if(brushproperty.getVisibility()==View.VISIBLE)
        {
            brushproperty.setVisibility(View.GONE);
        }
        else
        {
            super.onBackPressed();
        }

    }
}
