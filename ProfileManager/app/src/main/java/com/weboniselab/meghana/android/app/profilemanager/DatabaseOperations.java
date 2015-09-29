package com.weboniselab.meghana.android.app.profilemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    public static final String createTableBattery=" CREATE TABLE " +Constants.TABLE_NAME_BATTERY+ " ( " +Constants.COLUMN_ID_BATTERY + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            +Constants.COLUMN_BATTERY_LEVEL+ " TEXT , " +Constants.COLUMN_MODE_OF_PHONE_BATTERY+ " TEXT , "
            +Constants.COLUMN_MODE_OF_NETWORK + " TEXT ) ";

    public static final String createTableLocation=" CREATE TABLE " +Constants.TABLE_NAME_LOCATION+ " ( " +Constants.COLUMN_ID_LOCATION + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            +Constants.COLUMN_LATITUDE+ " DOUBLE , " +Constants.COLUMN_LONGITUDE+ " DOUBLE , "
            +Constants.COLUMN_RADIUS + " INTEGER , " +Constants.COLUMN_ADDRESS + " TEXT ," +Constants.COLUMN_MODE_OF_PHONE_LOCATION + " TEXT ) ";

    public DatabaseOperations(Context context){
        super(context,Constants.DATABASE_NAME,null,DB_VERSION);
        mcontext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableTime);
        db.execSQL(createTableMovement);
        db.execSQL(createTableBattery);
        db.execSQL(createTableLocation);
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

    //method to add or update details to the movement table in the database
    public void insertOrUpdateToDatabaseMovementTable(String modeOfMovement,String modeOfPhone){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.rawQuery(" SELECT count(*) FROM " + Constants.TABLE_NAME_MOVEMENT,null);
        cursor.moveToFirst();
            if (cursor.getInt(0)>1){
                ContentValues values1=new ContentValues();
                values1.put(Constants.COLUMN_MODE_OF_PHONE_MOVEMENT, modeOfPhone);
                String where = "modeOfMovement=?";
                String[] whereArgs = new String[] {String.valueOf(modeOfMovement)};
                database.update(Constants.TABLE_NAME_MOVEMENT,values1,where,whereArgs);
                database.close();
        }
        else {
                ContentValues values=new ContentValues();
                values.put(Constants.COLUMN_MODE_OF_MOVEMENT,modeOfMovement);
                values.put(Constants.COLUMN_MODE_OF_PHONE_MOVEMENT,modeOfPhone);
                database.insert(Constants.TABLE_NAME_MOVEMENT, null, values);
                database.close();
        }
    }
    //method to add or update details to the battery table in the database
    public void insertOrUpdateToDatabaseBatteryTable(String batteryLevel,String modeOfPhone,String modeOfNetwork){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.rawQuery(" SELECT count(*) FROM " + Constants.TABLE_NAME_BATTERY,null);
        cursor.moveToFirst();
        if (cursor.getInt(0)>2){
            ContentValues values1=new ContentValues();
            values1.put(Constants.COLUMN_MODE_OF_NETWORK, modeOfNetwork);
            values1.put(Constants.COLUMN_MODE_OF_PHONE_BATTERY,modeOfPhone);
            String where = "batteryLevel=?";
            String[] whereArgs = new String[] {String.valueOf(batteryLevel)};
            database.update(Constants.TABLE_NAME_BATTERY,values1,where,whereArgs);
            database.close();
        }
        else {
            ContentValues values=new ContentValues();
            values.put(Constants.COLUMN_BATTERY_LEVEL,batteryLevel);
            values.put(Constants.COLUMN_MODE_OF_PHONE_BATTERY,modeOfPhone);
            values.put(Constants.COLUMN_MODE_OF_NETWORK,modeOfNetwork);
            database.insert(Constants.TABLE_NAME_BATTERY, null, values);
            database.close();
        }
    }
    //method to get al details from battery table
    public List<BatteryModel> getAllDetailsFromBatteryTable(){
        List<BatteryModel> details=new ArrayList<BatteryModel>();
        String getDetailsQuery="SELECT * FROM " +Constants.TABLE_NAME_BATTERY;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(getDetailsQuery, null);
        try {
            if (cursor.moveToFirst()) do{
                BatteryModel batteryModel=new BatteryModel();
                batteryModel.setBatteryLevel(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_BATTERY_LEVEL)));
                batteryModel.setModeOfPhoneBattery(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_MODE_OF_PHONE_BATTERY)));
                batteryModel.setModeOfNetwork(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_MODE_OF_NETWORK)));
                details.add(batteryModel);
            }while (cursor.moveToNext());
        }catch (Exception e){
            e.printStackTrace();
        }
        return details;
    }
    //method to add details to location table
    public void addDetailsToLocationTable(double latitude,double longitude,int radius,String address,String modeOfPhone){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constants.COLUMN_LATITUDE,latitude);
        values.put(Constants.COLUMN_LONGITUDE,longitude);
        values.put(Constants.COLUMN_RADIUS,radius);
        values.put(Constants.COLUMN_ADDRESS,address);
        values.put(Constants.COLUMN_MODE_OF_PHONE_LOCATION,modeOfPhone);
        database.insert(Constants.TABLE_NAME_LOCATION, null, values);
        database.close();
    }
    //method to get all details from location table
    public List<LocationModel> getAllDetailsFromLocationTable(){
        List<LocationModel> details=new ArrayList<LocationModel>();
        String getDetailsQuery="SELECT * FROM " +Constants.TABLE_NAME_LOCATION;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(getDetailsQuery, null);
        try {
            if (cursor.moveToFirst()) do{
                LocationModel locationModel=new LocationModel();
                locationModel.setLatitude(cursor.getDouble(cursor.getColumnIndex(Constants.COLUMN_LATITUDE)));
                locationModel.setLongitude(cursor.getDouble(cursor.getColumnIndex(Constants.COLUMN_LONGITUDE)));
                locationModel.setRadius(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_RADIUS)));
                locationModel.setAddress(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_ADDRESS)));
                locationModel.setModeOfPhone(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_MODE_OF_PHONE_LOCATION)));
                details.add(locationModel);
            }while (cursor.moveToNext());
        }catch (Exception e){
            e.printStackTrace();
        }
        return details;
    }
}
