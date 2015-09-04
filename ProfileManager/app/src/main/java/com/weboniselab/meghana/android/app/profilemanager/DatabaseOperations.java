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

    public static final String createTableTime=" CREATE TABLE " +Constants.TABLE_NAME_TIME+ " ( " +Constants.COLUMN_ID_TIME + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            +Constants.COLUMN_FROM_TIME+ " TEXT , " +Constants.COLUMN_TO_TIME+ " TEXT , "
            +Constants.COLUMN_MODE_OF_PHONE_TIME + " TEXT ) ";

    public static final String createTableMovement=" CREATE TABLE " +Constants.TABLE_NAME_MOVEMENT+ " ( " +Constants.COLUMN_ID_MOVEMENT + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            +Constants.COLUMN_MODE_OF_MOVEMENT+ " TEXT , " +Constants.COLUMN_MODE_OF_PHONE_MOVEMENT+ " TEXT ) ";

    public DatabaseOperations(Context context){
        super(context,Constants.DATABASE_NAME,null,DB_VERSION);
        mcontext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableTime);
        db.execSQL(createTableMovement);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
//method to add the details to the time table in the database
    public void addDetailsToDatabase(String fromTime,String toTime,String modeOfPhone){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constants.COLUMN_FROM_TIME,fromTime);
        values.put(Constants.COLUMN_TO_TIME,toTime);
        values.put(Constants.COLUMN_MODE_OF_PHONE_TIME,modeOfPhone);
        database.insert(Constants.TABLE_NAME_TIME, null, values);
        database.close();
    }
//method to get all details from time table of the database
    public List<DetailsOfPhone> getAllDetails(){
        List<DetailsOfPhone> details=new ArrayList<DetailsOfPhone>();
        String getDetailsQuery="SELECT * FROM " +Constants.TABLE_NAME_TIME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(getDetailsQuery, null);
        try {
            if (cursor.moveToFirst()) do{
                DetailsOfPhone detailsOfPhone=new DetailsOfPhone();
                detailsOfPhone.setFromTime(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_FROM_TIME)));
                detailsOfPhone.setToTime(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TO_TIME)));
                detailsOfPhone.setModeOfPhone(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_MODE_OF_PHONE_TIME)));
                details.add(detailsOfPhone);
            }while (cursor.moveToNext());
        }catch (Exception e){
            e.printStackTrace();
        }
        return details;
    }

//method to add details to the movement table in the database
    public void addDetailsToDatabaseMovementTable(String modeOfMovement,String modeOfPhone){
       SQLiteDatabase database=this.getWritableDatabase();
       ContentValues values=new ContentValues();
        values.put(Constants.COLUMN_MODE_OF_MOVEMENT,modeOfMovement);
        values.put(Constants.COLUMN_MODE_OF_PHONE_MOVEMENT,modeOfPhone);
        database.insert(Constants.TABLE_NAME_MOVEMENT, null, values);
        database.close();
}
//method to get all details from movement table of the database
public List<MovementModel> getAllDetailsFromMovementTable(){
    List<MovementModel> details=new ArrayList<MovementModel>();
    String getDetailsQuery="SELECT * FROM " +Constants.TABLE_NAME_MOVEMENT;
    SQLiteDatabase database = this.getWritableDatabase();
    Cursor cursor = database.rawQuery(getDetailsQuery, null);
    try {
        if (cursor.moveToFirst()) do{
            MovementModel movementModel=new MovementModel();
            movementModel.setModeOfMovement(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_MODE_OF_MOVEMENT)));
            movementModel.setModeOfPhone(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_MODE_OF_PHONE_MOVEMENT)));
            details.add(movementModel);
        }while (cursor.moveToNext());
    }catch (Exception e){
        e.printStackTrace();
    }
    return details;
}
}
