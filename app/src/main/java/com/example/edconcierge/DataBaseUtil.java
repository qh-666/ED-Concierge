package com.example.edconcierge;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataBaseUtil {
    private Context mContext;
    public static String dbName = "EDConcierge.db";
    private static String DATABASE_PATH;

    public DataBaseUtil(Context context) {
        mContext = context;
        String packageName = context.getPackageName();
        DATABASE_PATH = "/data/data/" + packageName + "/databases/";
    }

    public boolean checkDataBase() {
        SQLiteDatabase db = null;
        try {
            String databaseFilename = DATABASE_PATH + dbName;
            db = SQLiteDatabase.openDatabase(databaseFilename, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        if (db != null) {
            db.close();
        }
        return db != null ? true : false;
    }

    public void copyDataBase() throws IOException {
        String databaseFilename = DATABASE_PATH + dbName;
        File dir = new File(DATABASE_PATH);
        if (!dir.exists()) {
            dir.mkdir();
        }
        FileOutputStream os = new FileOutputStream(databaseFilename);
        InputStream is = mContext.getAssets().open("EDConcierge.db");
        byte[] buffer = new byte[8192];
        int count = 0;
        while ((count = is.read(buffer)) > 0) {
            os.write(buffer, 0, count);
            os.flush();
        }
        is.close();
        os.close();
    }
}
