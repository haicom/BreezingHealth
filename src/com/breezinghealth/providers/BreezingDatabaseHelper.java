package com.breezinghealth.providers;

import com.breezinghealth.providers.Breezing.Account;
import com.breezinghealth.providers.Breezing.EnergyCost;
import com.breezinghealth.providers.Breezing.HeatConsumption;
import com.breezinghealth.providers.Breezing.HeatIngestion;
import com.breezinghealth.providers.Breezing.Information;
import com.breezinghealth.providers.Breezing.Ingestion;
import com.breezinghealth.providers.Breezing.UnitSettings;
import com.breezinghealth.providers.Breezing.WeightChange;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BreezingDatabaseHelper extends SQLiteOpenHelper {
    private final static String TAG = "BreezingDatabaseHelper";
    private static BreezingDatabaseHelper sInstance = null;
    static final String DATABASE_NAME = "breezing.db";
    static final int DATABASE_VERSION = 2;
    private final Context mContext;

    public interface Views {
        public static final String COST_WEEKLY = "view_cost_weekly";
        public static final String COST_MONTHLY = "view_cost_monthly";
        public static final String COST_YEARLY = "view_cost_yearly";
        public static final String INGESTION_WEEKLY = "view_ingestion_weekly";
        public static final String INGESTION_MONTHLY = "view_ingestion_monthly";
        public static final String INGESTION_YEARLY = "view_ingestion_yearly";
        public static final String WEIGHT_WEEKLY = "view_weight_weekly";
        public static final String WEIGHT_MONTHLY = "view_weight_monthly";
        public static final String WEIGHT_YEARLY = "view_weight_yearly";
        public static final String BASE_INFO = "view_base_info";
    }

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
        createEnergyCostViews(db);
        createIngestionViews(db);
        createWeightChangeViews(db);
        createUnitSettings(db);
        createBaseInfomationViews(db);
    }


    /***
     * 产生帐户表
     * @param db
     */
    private void createAccountTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BreezingProvider.TABLE_ACCOUNT  + " ("
              +  Account._ID +  " INTEGER PRIMARY KEY, "
              +  Account.ACCOUNT_NAME + " TEXT NOT NULL , "
              +  Account.ACCOUNT_ID +  " INTEGER NOT NULL DEFAULT 0 , "
              +  Account.ACCOUNT_PASSWORD +  " TEXT NOT NULL " +
                   ");");
    }

    /***
     * 产生基本信息表
     * @param db
     */
    private void createInformationTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BreezingProvider.TABLE_INFORMATION  + " ("
              +   Information._ID + " INTEGER PRIMARY KEY, "
              +   Information.ACCOUNT_ID + " INTEGER NOT NULL , "
              +   Information.GENDER +  " INTEGER NOT NULL , "
              +   Information.HEIGHT +  " FLOAT NOT NULL , "
              +   Information.BIRTHDAY + " INTEGER NOT NULL , "
              +   Information.CUSTOM + " INTEGER NOT NULL  " +
                   ");");
    }

    /***
     * 产生我的总能量消耗表
     * @param db
     */
    private void createEnergyCostTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BreezingProvider.TABLE_COST  + " ("
                +  EnergyCost._ID + " INTEGER PRIMARY KEY, "
                +  EnergyCost.ACCOUNT_ID + " INTEGER NOT NULL , "
                +  EnergyCost.METABOLISM + " INTEGER NOT NULL , "
                +  EnergyCost.SPORT + " INTEGER NOT NULL , "
                +  EnergyCost.DIGEST + " INTEGER NOT NULL , "
                +  EnergyCost.TRAIN + " INTEGER NOT NULL , "
                +  EnergyCost.TOTAL_ENERGY + " INTEGER NOT NULL , "
                +  EnergyCost.ENERGY_COST_DATE + " INTEGER NOT NULL , "
                +  EnergyCost.DATE + " INTEGER NOT NULL , "
                +  EnergyCost.YEAR + " INTEGER NOT NULL , "
                +  EnergyCost.YEAR_MONTH + " INTEGER NOT NULL , "
                +  EnergyCost.YEAR_WEEK + " INTEGER NOT NULL " +
                   ");");
    }
    /**
     * 产生总能量消耗视图，显示周，月，年
     * @param db
     */
    private void createEnergyCostViews(SQLiteDatabase db) {
        db.execSQL("DROP VIEW IF EXISTS " + Views.COST_WEEKLY + ";");
        db.execSQL("DROP VIEW IF EXISTS " + Views.COST_MONTHLY + ";");
        db.execSQL("DROP VIEW IF EXISTS " + Views.COST_YEARLY + ";");

        String weeklyCostSelect =  "SELECT "
                + EnergyCost.ACCOUNT_ID + ","
                + " round( avg( " + EnergyCost.METABOLISM + ") ) AS " + EnergyCost.AVG_METABOLISM + " , "
                + " round( avg( " + EnergyCost.TOTAL_ENERGY + " ) )  AS " + EnergyCost.AVG_TOTAL_ENERGY + " , "
                + " total( " + EnergyCost.METABOLISM + " ) AS " +  EnergyCost.ALL_METABOLISM + " , "
                + " total( " + EnergyCost.TOTAL_ENERGY + " )  AS " + EnergyCost.ALL_TOTAL_ENERGY + " , "
                + EnergyCost.YEAR_WEEK
                + " FROM " + BreezingProvider.TABLE_COST
                + " GROUP BY " +  EnergyCost.ACCOUNT_ID + " , " + EnergyCost.YEAR_WEEK;

        db.execSQL("CREATE VIEW " + Views.COST_WEEKLY + " AS " + weeklyCostSelect);

        String monthlyCostSelect =  "SELECT "
                + EnergyCost.ACCOUNT_ID + ","
                + " round( avg( " + EnergyCost.METABOLISM + ") ) AS " + EnergyCost.AVG_METABOLISM + " , "
                + " round( avg( " + EnergyCost.TOTAL_ENERGY + " ) )  AS " + EnergyCost.AVG_TOTAL_ENERGY + " ,"
                + " total( " + EnergyCost.METABOLISM + " ) AS " + EnergyCost.ALL_METABOLISM + " , "
                + " total( " + EnergyCost.TOTAL_ENERGY + " )  AS " + EnergyCost.ALL_TOTAL_ENERGY + " , "
                + EnergyCost.YEAR_MONTH
                + " FROM " + BreezingProvider.TABLE_COST
                + " GROUP BY " +  EnergyCost.ACCOUNT_ID + "  , " + EnergyCost.YEAR_MONTH;

        db.execSQL("CREATE VIEW " + Views.COST_MONTHLY + " AS " + monthlyCostSelect);

        String yearlyCostSelect =  "SELECT "
                + EnergyCost.ACCOUNT_ID + ","
                + " round( avg( " + EnergyCost.METABOLISM + ") ) AS " + EnergyCost.AVG_METABOLISM + "   , "
                + " round( avg( " + EnergyCost.TOTAL_ENERGY + " ) )  AS " + EnergyCost.AVG_TOTAL_ENERGY + " , "
                + " total( " + EnergyCost.METABOLISM + " ) AS " + EnergyCost.ALL_METABOLISM + " , "
                + " total( " + EnergyCost.TOTAL_ENERGY + " )  AS " + EnergyCost.ALL_TOTAL_ENERGY + " , "
                + EnergyCost.YEAR
                + " FROM " + BreezingProvider.TABLE_COST
                + " GROUP BY " +  EnergyCost.ACCOUNT_ID + " , " + EnergyCost.YEAR;

        db.execSQL("CREATE VIEW " + Views.COST_YEARLY + " AS " + yearlyCostSelect);
    }



    /***
     * 产生我的摄入表
     * @param db
     */
    private void createIngestionTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BreezingProvider.TABLE_INGESTION  + " ("
                  + Ingestion._ID + " INTEGER PRIMARY KEY, "
                  + Ingestion.ACCOUNT_ID + " INTEGER NOT NULL , "
                  + Ingestion.BREAKFAST + " INTEGER NOT NULL , "
                  + Ingestion.LUNCH + " INTEGER NOT NULL , "
                  + Ingestion.DINNER + " INTEGER NOT NULL , "
                  + Ingestion.ETC + " INTEGER NOT NULL , "
                  + Ingestion.TOTAL_INGESTION + " INTEGER NOT NULL , "
                  + Ingestion.DATE + " INTEGER NOT NULL , "
                  + Ingestion.YEAR + " INTEGER NOT NULL , "
                  + Ingestion.YEAR_MONTH + " INTEGER NOT NULL , "
                  + Ingestion.YEAR_WEEK + " INTEGER NOT NULL " +
                   ");");
    }

    /***
     * 产生我的摄入视图，周，月，年
     *
     */
    private void createIngestionViews(SQLiteDatabase db) {
        db.execSQL("DROP VIEW IF EXISTS " + Views.INGESTION_WEEKLY + ";");
        db.execSQL("DROP VIEW IF EXISTS " + Views.INGESTION_MONTHLY + ";");
        db.execSQL("DROP VIEW IF EXISTS " + Views.INGESTION_YEARLY + ";");

        String weeklyIngestionSelect =  "SELECT "
                + Ingestion.ACCOUNT_ID + " , "
                + " round ( avg( " +  Ingestion.TOTAL_INGESTION + " ) ) AS " + Ingestion.AVG_TOTAL_INGESTION + " , "
                + " total( " + Ingestion.TOTAL_INGESTION + " )  AS "+ Ingestion.ALL_TOTAL_INGESTION + " , "
                +  Ingestion.YEAR_WEEK
                + " FROM " + BreezingProvider.TABLE_INGESTION
                + " GROUP BY " + Ingestion.ACCOUNT_ID + " , " + Ingestion.YEAR_WEEK;

        db.execSQL("CREATE VIEW " + Views.INGESTION_WEEKLY + " AS " + weeklyIngestionSelect);

        String monthlyIngestionSelect =  "SELECT "
                + Ingestion.ACCOUNT_ID + " , "
                + " round ( avg( " +  Ingestion.TOTAL_INGESTION + " ) ) AS  " + Ingestion.AVG_TOTAL_INGESTION + " , "
                + " total( " + Ingestion.TOTAL_INGESTION + " )  AS "+ Ingestion.ALL_TOTAL_INGESTION + " , "
                +  Ingestion.YEAR_MONTH
                + " FROM " + BreezingProvider.TABLE_INGESTION
                + " GROUP BY " + Ingestion.ACCOUNT_ID + " , " + Ingestion.YEAR_MONTH;

        db.execSQL("CREATE VIEW " + Views.INGESTION_MONTHLY + " AS " + monthlyIngestionSelect);

        String yearlyIngestionSelect =  "SELECT "
                + Ingestion.ACCOUNT_ID + " , "
                + " round ( avg( " +  Ingestion.TOTAL_INGESTION + " ) ) AS   " + Ingestion.AVG_TOTAL_INGESTION + " , "
                + " total( " + Ingestion.TOTAL_INGESTION + " )  AS "+ Ingestion.ALL_TOTAL_INGESTION + " , "
                +  Ingestion.YEAR
                + " FROM " + BreezingProvider.TABLE_INGESTION
                + " GROUP BY " + Ingestion.ACCOUNT_ID + " , " +  Ingestion.YEAR;

        db.execSQL("CREATE VIEW " + Views.INGESTION_YEARLY + " AS " + yearlyIngestionSelect);
    }

    /***
     * 产生体重变化表
     * @param db
     */
    private void createWeightChangeTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BreezingProvider.TABLE_WEIGHT  + " ("
                 + WeightChange._ID + " INTEGER PRIMARY KEY, "
                 + WeightChange.ACCOUNT_ID + " INTEGER NOT NULL , "
                 + WeightChange.WEIGHT + " FLOAT NOT NULL , "
                 + WeightChange.EXPECTED_WEIGHT + " FLOAT NOT NULL , "
                 + WeightChange.DATE + " INTEGER NOT NULL , "
                 + WeightChange.YEAR + " INTEGER NOT NULL , "
                 + WeightChange.YEAR_MONTH + " INTEGER NOT NULL , "
                 + WeightChange.YEAR_WEEK + " INTEGER NOT NULL " +
                   ");");
    }

    /***
     * 产生体重变化视图，周，月，年
     * @param db
     */
    private void createWeightChangeViews(SQLiteDatabase db) {
        db.execSQL("DROP VIEW IF EXISTS " + Views.WEIGHT_WEEKLY + ";");
        db.execSQL("DROP VIEW IF EXISTS " + Views.WEIGHT_MONTHLY + ";");
        db.execSQL("DROP VIEW IF EXISTS " + Views.WEIGHT_YEARLY + ";");

        String weeklyWeightSelect =  " SELECT "
                + WeightChange.ACCOUNT_ID + " , "
                + " round( avg( " + WeightChange.WEIGHT + " ), 2 ) AS " + WeightChange.AVG_WEIGHT + " , "
                + " round( avg( " + WeightChange.EXPECTED_WEIGHT + " ) ,2)   AS "
                + WeightChange.AVG_EXPECTED_WEIGHT + " , "
                + WeightChange.YEAR_WEEK
                + " FROM " + BreezingProvider.TABLE_WEIGHT
                + " GROUP BY " +  WeightChange.ACCOUNT_ID  + " ," + WeightChange.YEAR_WEEK;

        db.execSQL("CREATE VIEW " + Views.WEIGHT_WEEKLY + " AS " + weeklyWeightSelect);

        String monthlyWeightSelect =  " SELECT "
                + WeightChange.ACCOUNT_ID + " , "
                + " round( avg( " + WeightChange.WEIGHT + " ), 2 ) AS " + WeightChange.AVG_WEIGHT + " , "
                + " round( avg( " + WeightChange.EXPECTED_WEIGHT + " ) ,2)   AS " 
                + WeightChange.AVG_EXPECTED_WEIGHT + " , "
                + WeightChange.YEAR_MONTH
                + " FROM " + BreezingProvider.TABLE_WEIGHT
                + " GROUP BY " +  WeightChange.ACCOUNT_ID  + " ," + WeightChange.YEAR_MONTH;

        db.execSQL("CREATE VIEW " + Views.WEIGHT_MONTHLY + " AS " + monthlyWeightSelect);

        String yearlyWeightSelect =  " SELECT "
                + WeightChange.ACCOUNT_ID + " , "
                + " round( avg( " + WeightChange.WEIGHT + " ), 2 ) AS  " +  WeightChange.AVG_WEIGHT + " , "
                + " round( avg( " + WeightChange.EXPECTED_WEIGHT + " ) ,2) AS " 
                + WeightChange.AVG_EXPECTED_WEIGHT + " , "
                + WeightChange.YEAR
                + " FROM " + BreezingProvider.TABLE_WEIGHT
                + " GROUP BY " +  WeightChange.ACCOUNT_ID  + " ," + WeightChange.YEAR;

        db.execSQL("CREATE VIEW " + Views.WEIGHT_YEARLY + " AS " + yearlyWeightSelect);
    }

    /***
     * 产生热量消耗参考表
     * @param db
     */
    private void createHeatConsumptionTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BreezingProvider.TABLE_HEAT_CONSUMPTION  + " ("
                 +  HeatConsumption._ID + " INTEGER PRIMARY KEY , "
                 +  HeatConsumption.SPORT_TYPE + " TEXT NOT NULL , "
                 +  HeatConsumption.SPORT_LONG + " INTEGER , "
                 +  HeatConsumption.SPORT_STRENGTH + " TEXT NOT NULL, "
                 +  HeatConsumption.SPORT_DISTANCE + " INTEGER , "
                 +  HeatConsumption.SPORT_TIMES + " INTEGER , "
                 +  HeatConsumption.CALORIE + " INTEGER NOT NULL , "
                 +  HeatConsumption.DATE + " INTEGER NOT NULL " +
                   ");");
    }

    /***
     * 产生热量摄入参考表
     * @param db
     */
    private void createHeatIngestionTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BreezingProvider.TABLE_HEAT_INGESTION  + " ("
                   +  HeatIngestion._ID + " INTEGER PRIMARY KEY , "
                   +  HeatIngestion.FOOD_TYPE + " TEXT NOT NULL , "
                   +  HeatIngestion.FOOD_NAME + " TEXT NOT NULL , "
                   +  HeatIngestion.NAME_EXPRESS + " TEXT NOT NULL , "
                   +  HeatIngestion.PRIORITY + " INTEGER , "
                   +  HeatIngestion.FOOD_SIZE + " INTEGER NOT NULL , "
                   +  HeatIngestion.CALORIE + " INTEGER NOT NULL " +
                   ");");
        
        
    }

    /***
     * 产生单位设置表
     * @param db
     */
    private void createUnitSettings(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BreezingProvider.TABLE_UNIT_SETTINGS  + " ("
                +  UnitSettings._ID + " INTEGER PRIMARY KEY , "
                +  UnitSettings.UNIT_TYPE + " TEXT NOT NULL , "
                +  UnitSettings.UNIT_NAME + " TEXT NOT NULL  " +             
                ");");
    }
    
    /***
     * 产生基本信息 view
     * @param db
     */
    private void createBaseInfomationViews(SQLiteDatabase db) {      
        String baseInfoSelect =  " SELECT "
                + Account.INFO_ACCOUNT_ID + " , "
                + Account.INFO_ACCOUNT_NAME + " , "
                + Account.INFO_ACCOUNT_PASSWORD + " , "
                + Information.INFO_GENDER + " , "
                + Information.INFO_HEIGHT + " , "
                + Information.INFO_BIRTHDAY + " , "
                + Information.INFO_CUSTOM + " , "
                + WeightChange.INFO_WEIGHT + " , "
                + WeightChange.INFO_EXPECTED_WEIGHT + " , "
                + WeightChange.INFO_DATE 
                + " FROM " + BreezingProvider.TABLE_ACCOUNT
                + " LEFT OUTER JOIN " + BreezingProvider.TABLE_INFORMATION + " ON " 
                + BreezingProvider.TABLE_INFORMATION + "." + Information.ACCOUNT_ID + " = "
                + BreezingProvider.TABLE_ACCOUNT + "." + Account.ACCOUNT_ID
                +  " LEFT OUTER JOIN " + BreezingProvider.TABLE_WEIGHT + " ON "
                + BreezingProvider.TABLE_WEIGHT + "." +  WeightChange.ACCOUNT_ID + " = "
                + BreezingProvider.TABLE_INFORMATION + "." 
                + Information.ACCOUNT_ID;
        
        db.execSQL("CREATE VIEW " + Views.BASE_INFO + " AS " + baseInfoSelect);

    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, " onUpgrade oldVersion = " + oldVersion + " newVersion = " + newVersion);

        if (oldVersion == 1) {
            upgradeToVersion202(db);
        }
    }

    private void upgradeToVersion202(SQLiteDatabase db) {
        db.execSQL(
                "ALTER TABLE " + BreezingProvider.TABLE_COST +
                " ADD " + EnergyCost.ENERGY_COST_DATE + " INTEGER;");
    }

}
