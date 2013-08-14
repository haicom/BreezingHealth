package com.breezinghealth.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BreezingDatabaseHelper extends SQLiteOpenHelper {
    private final static String TAG = "TakeOutDatabaseHelper";
    private static BreezingDatabaseHelper sInstance = null;
    static final String DATABASE_NAME = "breezing.db";
    static final int DATABASE_VERSION = 1;
    private final Context mContext;

    private BreezingDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    /**
     * Return a singleton helper for the combined Breezing health
     * database.
     */
    /* package */
    static synchronized BreezingDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new BreezingDatabaseHelper(context);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createAccountTables(db);
        createInformationTables(db);
        createEnergyCostTables(db);
        createIngestionTables(db);
        createWeightChangeTables(db);
        createHeatConsumptionTables(db);
        createHeatIngestionTables(db);
    }


    /***
     * 产生帐户表
     * @param db
     */
    private void createAccountTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BreezingProvider.TABLE_ACCOUNT  + " (" +
                   " _id INTEGER PRIMARY KEY, " +
                   " account_name TEXT NOT NULL , " +
                   " account_id INTEGER NOT NULL DEFAULT 0 , " +
                   " account_password TEXT NOT NULL " +
                   ");");
    }

    /***
     * 产生基本信息表
     * @param db
     */
    private void createInformationTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BreezingProvider.TABLE_INFORMATION  + " (" +
                   " _id INTEGER PRIMARY KEY, " +
                   " account_id INTEGER NOT NULL , " +
                   " gender INTEGER NOT NULL , " +
                   " height INTEGER NOT NULL , " +
                   " weight INTEGER NOT NULL , " +
                   " birthday DATE NOT NULL , " +
                   " custom INTEGER NOT NULL , " +
                   " expected_weight INTEGER NOT NULL " +
                   ");");
    }

    /***
     * 产生我的总能量消耗表
     * @param db
     */
    private void createEnergyCostTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BreezingProvider.TABLE_COST  + " (" +
                   " _id INTEGER PRIMARY KEY, " +
                   " account_id INTEGER NOT NULL , " +
                   " metabolism INTEGER NOT NULL , " +
                   " sport INTEGER NOT NULL , " +
                   " digest INTEGER NOT NULL , " +
                   " train INTEGER NOT NULL , " +
                   " total_energy INTEGER NOT NULL , " +
                   " date INTEGER NOT NULL , " +
                   " year INTEGER NOT NULL , " +
                   " year_month INTEGER NOT NULL , " +
                   " year_week INTEGER NOT NULL " +
                   ");");
    }

    /***
     * 产生我的摄入表
     * @param db
     */
    private void createIngestionTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BreezingProvider.TABLE_INGESTION  + " (" +
                   " _id INTEGER PRIMARY KEY, " +
                   " account_id INTEGER NOT NULL , " +
                   " breakfast INTEGER NOT NULL , " +
                   " lunch INTEGER NOT NULL , " +
                   " dinner INTEGER NOT NULL , " +
                   " etc INTEGER NOT NULL , " +
                   " total_ingestion INTEGER NOT NULL , " +
                   " date INTEGER NOT NULL , " +
                   " year INTEGER NOT NULL , " +
                   " year_month INTEGER NOT NULL , " +
                   " year_week INTEGER NOT NULL " +
                   ");");
    }

    /***
     * 产生体重变化表
     * @param db
     */
    private void createWeightChangeTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BreezingProvider.TABLE_WEIGHT  + " (" +
                   " _id INTEGER PRIMARY KEY, " +
                   " account_id INTEGER NOT NULL , " +
                   " weight INTEGER NOT NULL , " +
                   " expected_weight INTEGER NOT NULL , " +
                   " date INTEGER NOT NULL , " +
                   " year INTEGER NOT NULL , " +
                   " year_month INTEGER NOT NULL , " +
                   " year_week INTEGER NOT NULL " +
                   ");");
    }

    /***
     * 产生热量消耗参考表
     * @param db
     */
    private void createHeatConsumptionTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BreezingProvider.TABLE_HEAT_CONSUMPTION  + " (" +
                   " _id INTEGER PRIMARY KEY , " +
                   " sport_type TEXT NOT NULL , " +
                   " sport_long INTEGER , " +
                   " sport_distance INTEGER , " +
                   " sport_times INTEGER , " +
                   " calorie INTEGER NOT NULL , " +
                   " date INTEGER NOT NULL " +
                   ");");
    }

    /***
     * 产生热量摄入参考表
     * @param db
     */

    private void createHeatIngestionTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BreezingProvider.TABLE_HEAT_INGESTION  + " (" +
                   " _id INTEGER PRIMARY KEY , " +
                   " food_type TEXT NOT NULL , " +
                   " food_name TEXT NOT NULL , " +
                   " name_express TEXT NOT NULL , " +
                   " priority INTEGER , " +
                   " food_size INTEGER NOT NULL , " +
                   " calorie INTEGER NOT NULL " +
                   ");");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BreezingProvider.TABLE_ACCOUNT );
        db.execSQL("DROP TABLE IF EXISTS " + BreezingProvider.TABLE_INFORMATION );
        db.execSQL("DROP TABLE IF EXISTS " + BreezingProvider.TABLE_COST );
        db.execSQL("DROP TABLE IF EXISTS " + BreezingProvider.TABLE_INGESTION );
        db.execSQL("DROP TABLE IF EXISTS " + BreezingProvider.TABLE_WEIGHT );
        db.execSQL("DROP TABLE IF EXISTS " + BreezingProvider.TABLE_HEAT_CONSUMPTION );
        db.execSQL("DROP TABLE IF EXISTS " + BreezingProvider.TABLE_HEAT_INGESTION );
        onCreate(db);
    }

}
