package com.breezinghealth.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class BreezingProvider extends ContentProvider {
    private final static String TAG = "BreezingProvider";
    public  static String TABLE_ACCOUNT = "account";
    public  static String TABLE_INFORMATION = "information";
    public static String TABLE_COST = "cost";
    public static String TABLE_INGESTION = "ingestion";
    public static String TABLE_WEIGHT = "weight";
    public static String TABLE_HEAT_CONSUMPTION  = "heat_consumption";
    public static String TABLE_HEAT_INGESTION = "heat_ingestion";
    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        
        return 0;
    }

    @Override
    public String getType(Uri uri) {
       
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        
        return null;
    }

    @Override
    public boolean onCreate() {
        
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
       
        return 0;
    }

}
