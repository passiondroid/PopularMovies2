package com.app.popularmovies.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Arif on 02-Jan-16.
 */
public class DBHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "popularmovies.db";
    private static final int DATABASE_VERSION = 1;
    private static DBHelper dbHelper = null;

    private DBHelper() {
        super(null, null, null, 1);
    }

    /**
     * @param context
     * @return
     */
    public static synchronized DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    /**
     * @param context
     */
    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //super(context, DATABASE_NAME, Environment.getExternalStorageDirectory().getAbsolutePath(), null, DATABASE_VERSION);
    }

}
