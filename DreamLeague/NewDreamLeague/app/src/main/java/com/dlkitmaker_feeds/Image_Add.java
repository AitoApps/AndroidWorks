package com.dlkitmaker_feeds;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileOutputStream;

import es.dmoral.toasty.Toasty;

public class Image_Add extends AppCompatActivity {

    final DB db=new DB(this);
    RelativeLayout layout1;
    public ImageView currentview;
    private static final int CAMERA_IMAGE_REQUEST=1000;
    public static int RESULT_LOAD_IMAGE=1001;
    private String imageName = null;
    private static Uri fileUri = null;
    private static String root = null;
    public String selectimagepath="";
    TextView text;
    ImageView move_back,save,addimage, drop;
    ImageView image_preview;
    Typeface face;
    final int PERMISSION_ALL = 1;
    final String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvty_image_add);

        move_back =findViewById(R.id.back);
        save=findViewById(R.id.save);
        drop =findViewById(R.id.delete);
        layout1=findViewById(R.id.layout1);
        text=findViewById(R.id.text);
        image_preview=findViewById(R.id.image_preview);
        addimage=findViewById(R.id.addimage);
        face= Typeface.createFromAsset(getAssets(), "fonts/heading.otf");
        text.setTypeface(face);
        image_loadin();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    layout1.buildDrawingCache();
                    Bitmap bm = layout1.getDrawingCache();
                    try {

                            File file = new File(Environment.getExternalStorageDirectory()+"/"+ Temp.foldername+"/", "mykit.png");
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            Bitmap resized = Bitmap.createScaledBitmap(bm, 512, 512, true);
                            resized.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            finish();

                    } catch (Exception e) {

                    } finally {

                    }


                }
                catch (Exception a)
                {

                }


            }
        });
        move_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!hasPermissions(Image_Add.this, PERMISSIONS )){
                    ActivityCompat.requestPermissions(Image_Add.this, PERMISSIONS, PERMISSION_ALL);
                }
                else
                {
                        browseimage();

                }

            }
        });
        drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    if(currentview==null)
                    {
                        Toasty.info(getApplicationContext(),"Please press on any image", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        layout1.removeView(currentview);
                    }

                }
                catch (Exception a)
                {

                }
            }
        });

    }

    public void image_loadin()
    {
        if(!db.get_kittheme().equalsIgnoreCase(""))
        {
            File file = new File(Environment.getExternalStorageDirectory()+"/"+ Temp.foldername+"/", "mykit.png");
            if(file.exists())
            {
                float ogwidth=(Float.valueOf(db.getscreenwidth()));
                image_preview.getLayoutParams().height= Math.round(ogwidth);
                layout1.getLayoutParams().height= Math.round(ogwidth);

                Bitmap bm =  BitmapFactory.decodeFile(file.getAbsolutePath());
                image_preview.setImageBitmap(bm);

            }
            else
            {
                Toasty.info(getApplicationContext(),"Sorry ! Please try later", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else
        {
            Toasty.info(getApplicationContext(),"Sorry ! Please try later", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void dumpEvent(MotionEvent event) {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(
                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }
        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }
        sb.append("]");

    }

    /** Determine the space between the first two fingers */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /** Calculate the mid point of the first two fingers */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }





    private void browseimage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery","Close"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Image_Add.this);
        builder.setTitle("Select Image");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))
                {
                    root = Environment.getExternalStorageDirectory().toString()
                            + "/"+ Temp.foldername+"/";
                    imageName = "selectimage.jpg";
                    File image = new File(root, imageName);
                    fileUri = Uri.fromFile(image);

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(takePictureIntent,
                            CAMERA_IMAGE_REQUEST);
                }

                else if (options[item].equals("Choose from Gallery"))

                {

                    try
                    {

                        Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i,RESULT_LOAD_IMAGE);
                    }
                    catch(Exception a)
                    {

                    }

                }

                else if (options[item].equals("Close")) {

                    dialog.dismiss();

                }



            }

        });

        builder.show();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && null!=data)
        {

            try
            {
                Uri selectedImage=data.getData();

                Cursor cursor = getContentResolver().query(selectedImage, null, null, null, null);
                if (cursor == null) {
                    selectimagepath= selectedImage.getPath();
                } else {
                    cursor.moveToFirst();
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    selectimagepath=cursor.getString(index);
                }
                CropImage.activity(selectedImage)
                        .start(this);

            }
            catch(Exception a)
            {
                //Toasty.info(getApplicationContext(), a.toString()+"",Toast.LENGTH_SHORT).show();

            }
        }


        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            try
            {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri resultUri = result.getUri();
                final ImageView img = new ImageView(Image_Add.this);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                img.setLayoutParams(layoutParams);
                img.setScaleType(ImageView.ScaleType.MATRIX);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),resultUri);
                img.setImageBitmap(bitmap);
                layout1.addView(img);
                currentview=img;

                img.setOnTouchListener(new View.OnTouchListener()
                {

                    Matrix matrix = new Matrix();
                    Matrix savedMatrix = new Matrix();

                    // We can be in one of these 3 states
                    static final int NONE = 0;
                    static final int DRAG = 1;
                    static final int ZOOM = 2;
                    int mode = NONE;

                    // Remember some things for zooming
                    PointF start = new PointF();
                    PointF mid = new PointF();
                    float oldDist = 1f;



                    @Override
                    public boolean onTouch(View v, MotionEvent event)
                    {

                        ImageView view = (ImageView) v;
                        dumpEvent(event);

                        // Handle touch events here...
                        switch (event.getAction() & MotionEvent.ACTION_MASK) {
                            case MotionEvent.ACTION_DOWN:
                                currentview=img;
                                savedMatrix.set(matrix);
                                start.set(event.getX(), event.getY());
                                mode = DRAG;
                                break;
                            case MotionEvent.ACTION_POINTER_DOWN:
                                oldDist = spacing(event);
                                if (oldDist > 10f) {
                                    savedMatrix.set(matrix);
                                    midPoint(mid, event);
                                    mode = ZOOM;
                                }
                                break;
                            case MotionEvent.ACTION_UP:
                            case MotionEvent.ACTION_POINTER_UP:
                                mode = NONE;
                                break;
                            case MotionEvent.ACTION_MOVE:
                                if (mode == DRAG) {
                                    // ...
                                    matrix.set(savedMatrix);
                                    matrix.postTranslate(event.getX() - start.x, event.getY()
                                            - start.y);
                                } else if (mode == ZOOM) {
                                    float newDist = spacing(event);
                                    if (newDist > 10f) {
                                        matrix.set(savedMatrix);
                                        float scale = newDist / oldDist;
                                        matrix.postScale(scale, scale, mid.x, mid.y);
                                    }
                                }
                                break;
                        }

                        view.setImageMatrix(matrix);
                        return true;
                    }
                });
            }
            catch (Exception a)
            {

            }


        }

        else if (requestCode==CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {


            CropImage.activity(fileUri)
                    .start(this);

        }
    }


}
