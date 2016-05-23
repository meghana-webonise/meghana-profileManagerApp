package com.weboniselab.meghana.android.app.profilemanager.utils;

public class Constants {
    public static final String Format_Of_Time="HH:mm";
    public static final int Radius_Of_Geofence=20;
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS= 12 * 60* 60 * 1000;


    public static final String DATABASE_NAME="ggklorrupjkirpfgg";
    public static final String TABLE_NAME_TIME="timeSetByUser";
    public static final String COLUMN_ID_TIME ="id";
    public static final String COLUMN_FROM_TIME="fromTime";
    public static final String COLUMN_TO_TIME="toTime";
    public static final String COLUMN_MODE_OF_PHONE_TIME ="modeOfPhone";

    public static final String TABLE_NAME_MOVEMENT="movementOfUser";
    public static final String COLUMN_ID_MOVEMENT="id";
    public static final String COLUMN_MODE_OF_MOVEMENT="modeOfMovement";
    public static final String COLUMN_MODE_OF_PHONE_MOVEMENT="modeOfPhone";

    public static final String TABLE_NAME_BATTERY="batteryLevel";
    public static final String COLUMN_ID_BATTERY="id";
    public static final String COLUMN_BATTERY_LEVEL="batteryLevel";
    public static final String COLUMN_MODE_OF_PHONE_BATTERY="modeOfPhone";
    public static final String COLUMN_MODE_OF_NETWORK="modeOfNetwork";

    public static final String TABLE_NAME_LOCATION="locationOfUser";
    public static final String COLUMN_ID_LOCATION="id";
    public static final String COLUMN_LATITUDE="latitude";
    public static final String COLUMN_LONGITUDE="longitude";
    public static final String COLUMN_RADIUS="radius";
    public static final String COLUMN_ADDRESS="address";
    public static final String COLUMN_MODE_OF_PHONE_LOCATION="modeOfPhone";
}
