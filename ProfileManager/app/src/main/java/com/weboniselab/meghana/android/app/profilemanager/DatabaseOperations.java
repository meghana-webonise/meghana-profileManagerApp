package com.weboniselab.meghana.android.app.profilemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by webonise on 31/8/15.
 */
public class DatabaseOperations extends SQLiteOpenHelper {
    private static final int DB_VERSION=1;
    Context mcontext;

    public DatabaseOperations(Context context){
        super(context,Constants.DATABASE_NAME,null,DB_VERSION);
        mcontext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " +Constants.TABLE_NAME+ " ( " +Constants.COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT , "
                +Constants.COLUMN_FROM_TIME+ " TEXT , " +Constants.COLUMN_TO_TIME+ " INTEGER , "
                +Constants.COLUMN_TYPE+ " TEXT ) " );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addDetailsToDatabase(){

    }
}
