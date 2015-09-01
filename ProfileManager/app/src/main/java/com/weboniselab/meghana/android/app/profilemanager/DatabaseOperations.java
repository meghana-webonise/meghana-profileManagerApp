package com.weboniselab.meghana.android.app.profilemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

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
                +Constants.COLUMN_FROM_TIME+ " TEXT , " +Constants.COLUMN_TO_TIME+ " TEXT , "
                +Constants.COLUMN_MODE_OF_PHONE+ " TEXT ) " );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addDetailsToDatabase(String fromTime,String toTime,String modeOfPhone){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constants.COLUMN_FROM_TIME,fromTime);
        values.put(Constants.COLUMN_TO_TIME,toTime);
        values.put(Constants.COLUMN_MODE_OF_PHONE,modeOfPhone);
        database.insert(Constants.TABLE_NAME, null, values);
        database.close();
    }

    public List<DetailsOfPhone> getAllDetails(){
        List<DetailsOfPhone> details=new ArrayList<DetailsOfPhone>();
        String getDetailsQuery="SELECT * FROM " +Constants.TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(getDetailsQuery, null);

        try {
            if (cursor.moveToFirst()) do{
                DetailsOfPhone detailsOfPhone=new DetailsOfPhone();
                detailsOfPhone.setFromTime(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_FROM_TIME)));
                detailsOfPhone.setToTime(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TO_TIME)));
                detailsOfPhone.setModeOfPhone(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_MODE_OF_PHONE)));
                details.add(detailsOfPhone);
            }while (cursor.moveToNext());
        }catch (Exception e){
            e.printStackTrace();
        }
        return details;
    }
}
