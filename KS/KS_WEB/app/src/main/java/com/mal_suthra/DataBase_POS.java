package com.mal_suthra;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBase_POS extends SQLiteOpenHelper {
    private Context context;
    private String dbName;
    private String db_path;

    public DataBase_POS(Context context2, String dbName2) {
        super(context2, dbName2, null, 1);
        dbName = dbName2;
        context = context2;
        db_path = "/data/data/"+context2.getPackageName()+"/databases/";
    }

    public void importIfNotExist() throws IOException {
        boolean chekpos_Exist = chekpos_Exist();
        getReadableDatabase();
        try {
            db_Copy();
            try {

                File f = new File(context.getFilesDir()+"/"+Static_Veriable.foldername+"/chithram.sqlite");
                if (f.exists()) {
                    f.delete();
                }
            } catch (Exception e) {
            }
        } catch (IOException e2) {
            throw new Error("Error copying database");
        }
    }

    public boolean chekpos_Exist() {
        SQLiteDatabase checkposDB = null;
        try {
            checkposDB = SQLiteDatabase.openDatabase(db_path+dbName, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } catch (Exception ep) {
            ep.printStackTrace();
        }
        if (checkposDB != null) {
            checkposDB.close();
        }
        if (checkposDB != null) {
            return true;
        }
        return false;
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public String getpic(String id1) {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM chithrangal WHERE pos=?", new String[]{id1});
        while (c.moveToNext()) {
            link = c.getString(2);
        }
        c.close();
        return link;
    }

    private void db_Copy() throws IOException {
        InputStream is = new FileInputStream(new File(context.getFilesDir()+"/"+Static_Veriable.foldername+"/chithram.sqlite"));
        OutputStream os = new FileOutputStream(db_path+dbName);
        byte[] buffer = new byte[4096];
        while (true) {
            int read = is.read(buffer);
            int length = read;
            if (read > 0) {
                os.write(buffer, 0, length);
            } else {
                os.flush();
                os.close();
                is.close();
                close();
                return;
            }
        }
    }
}
