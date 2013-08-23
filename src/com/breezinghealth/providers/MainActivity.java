package com.breezinghealth.providers;


import com.breezinghealth.R;
import com.breezinghealth.providers.Breezing.Account;
import com.breezinghealth.providers.Breezing.EnergyCost;
import com.breezinghealth.providers.Breezing.Information;
import com.breezinghealth.providers.Breezing.Ingestion;
import com.breezinghealth.providers.Breezing.WeightChange;
import com.breezinghealth.transation.DataReceiver;
import com.breezinghealth.transation.DataTaskService;


import android.os.Bundle;
import android.provider.ContactsContract.RawContacts;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
    Button button;
    Button mButtonQuery;
    private ContentResolver mContentResolver;
    private static final String TAG = "MainActivity";
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        mButtonQuery = (Button)findViewById(R.id.query_button);
        button.setOnClickListener(this);
        mButtonQuery.setOnClickListener(this);
        mContentResolver = this.getContentResolver();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == button) {
            sendBroadcast(new Intent(DataTaskService.ACTION_IMPORT_DATA,
                    null,
                    this,
                    DataReceiver.class));
        } else if (view == mButtonQuery) {
            /**
             * 热量摄入参考表 内的数据已经装载 数据在 food.xml
             */
            
            
            /**
             * 单元设置表，内的数据已经装载 数据在 settings.xml
             */
            
            /***
             * 帐户信息已经测试，数据已经装载，数据在 account.xml
             */
            
            /***
             * 基本信息已经测试，数据已经装载，数据在info.xml
             */
            
            /****
             * 我的体重已经测试，数据已经装载，数据在weight.xml
             */
            
            /***
             * 测试基本信息的view
             */
            
            testBaseInfoView();
            /**
             * 以下三个是总能量消耗的view测试
             */
            testCostWeeklyAndAccount();
            testCostlyMonthlyAndAccount();
            testCostlyYearlyAndAccount();
            
            
            /**
             * 以下三个是总能量摄入的view测试
             */
            testIngestionWeeklyAndAccount();
            testIngestionMonthlyAndAccount();
            testIngestionYearlyAndAccount();
            
            /***
             *  以下三个是我的体重的view测试
             */
            testWeightWeeklyAndAccount();
            testWeightMonthlyAndAccount();
            testWeightYearlyAndAccount();
        }

    }
    
    /**
     * 我的总能量消耗查看每一周，某一个帐户的周信息
     */
    private static final String[] PROJECTION_BASE_INFO = new String[] {
        Account.ACCOUNT_NAME,          // 0
        Account.ACCOUNT_PASSWORD,      // 1
        Information.GENDER,    // 2
        Information.HEIGHT ,     //3
        Information.BIRTHDAY ,   //4
        Information.CUSTOM ,            //5
        WeightChange.WEIGHT,            //6
        WeightChange.EXPECTED_WEIGHT,   //7
        WeightChange.DATE              //8
    };

    private static final int INFO_ACCOUNT_NAME_INDEX = 0;
    private static final int INFO_ACCOUNT_PASSWORD_INDEX = 1;
    private static final int INFO_GENDER_INDEX = 2;
    private static final int INFO_HEIGHT_INDEX = 3;
    private static final int INFO_BIRTHDAY_INDEX = 4;
    private static final int INFO_CUSTOM_INDEX = 5;
    private static final int INFO_WEIGHT_INDEX = 6;
    private static final int INFO_EXPECTED_WEIGHT_INDEX = 7;
    private static final int INFO_DATE_INDEX = 8;
    
    private void testBaseInfoView() {
        Log.d(TAG, "testBaseInfoView");
        
        String accountClause =  Account.ACCOUNT_ID + " = ?";
        String sortOrder = WeightChange.DATE + " DESC";
        String[] args = new String[] {"001"};
        
        Cursor cursor  = mContentResolver.query(Information.CONTENT_BASE_INFO_URI,
                PROJECTION_BASE_INFO, accountClause, args, sortOrder);
        if (cursor == null) {
            Log.d(TAG, " testBaseInfoView cursor = " + cursor);
        }

  
        try {
            cursor.moveToPosition(-1);
            while (cursor.moveToNext() ) {
                String  accountName = cursor.getString(INFO_ACCOUNT_NAME_INDEX);
                String  accountPass = cursor.getString(INFO_ACCOUNT_PASSWORD_INDEX);
                int     gender = cursor.getInt(INFO_GENDER_INDEX);
                float  height = cursor.getFloat(INFO_HEIGHT_INDEX);
                int    birthday = cursor.getInt(INFO_BIRTHDAY_INDEX);
                int    custom = cursor.getInt(INFO_CUSTOM_INDEX);
                float  weight = cursor.getFloat(INFO_WEIGHT_INDEX);
                float  expectedWeight = cursor.getFloat(INFO_EXPECTED_WEIGHT_INDEX);
                float  date = cursor.getFloat(INFO_DATE_INDEX);
                Log.d(TAG, " testCostWeeklyAndAccount accountName = " + accountName + " accountPass = " + accountPass
                        + " gender = " + gender + " height = " + height
                        + " birthday = " + birthday + " birthday = " + birthday 
                        + " custom =  " + custom + " weight = " + weight 
                        + " expectedWeight = " + expectedWeight
                        + " date = " + date);
            }
        } finally {
            cursor.close();
        }
    }
    
    
    
    /**
     * 我的总能量消耗查看每一周，某一个帐户的周信息
     */
    private static final String[] PROJECTION_ENERGY_COST_WEEKLY = new String[] {
        EnergyCost.ACCOUNT_ID,          // 0
        EnergyCost.AVG_METABOLISM,      // 1
        EnergyCost.AVG_TOTAL_ENERGY,    // 2
        EnergyCost.ALL_METABOLISM ,     //3
        EnergyCost.ALL_TOTAL_ENERGY ,   //4
        EnergyCost.YEAR_WEEK            //5
    };

    private static final int ACCOUNT_ID_COLUMN_WEEKLY_INDEX = 0;
    private static final int AVG_METABOLISM_COLUMN_WEEKLY_INDEX = 1;
    private static final int AVG_TOTAL_ENERGY_COLUMN_WEEKLY_INDEX = 2;
    private static final int ALL_METABOLISM_COLUMN_WEEKLY_INDEX = 3;
    private static final int ALL_TOTAL_ENERGY_COLUMN_WEEKLY_INDEX = 4;
    private static final int YEAR_WEEK_COLUMN_WEEKLY_INDEX = 5;
    
    private void testCostWeeklyAndAccount() {
        Log.d(TAG, "testCostWeeklyAndAccount");
        String accountClause = EnergyCost.ACCOUNT_ID + " = ?";
        String sortOrder = EnergyCost.YEAR_WEEK + " DESC";
        String[] args = new String[] {"001"};
        Cursor cursor  = mContentResolver.query(EnergyCost.CONTENT_WEEKLY_URI,
                PROJECTION_ENERGY_COST_WEEKLY, accountClause, args, sortOrder);
        if (cursor == null) {
            Log.d(TAG, " testCostWeekly cursor = " + cursor);
        }

  
        try {
            cursor.moveToPosition(-1);
            while (cursor.moveToNext() ) {
                int  accountId = cursor.getInt(ACCOUNT_ID_COLUMN_WEEKLY_INDEX);
                long avgMetabolism = cursor.getLong(AVG_METABOLISM_COLUMN_WEEKLY_INDEX);
                long avgTotalEnergy = cursor.getLong(AVG_TOTAL_ENERGY_COLUMN_WEEKLY_INDEX);
                long allMetabolism = cursor.getLong(ALL_METABOLISM_COLUMN_WEEKLY_INDEX);
                long allTotalEnergy = cursor.getLong(ALL_TOTAL_ENERGY_COLUMN_WEEKLY_INDEX);
                long yearWeek = cursor.getInt(YEAR_WEEK_COLUMN_WEEKLY_INDEX);
                Log.d(TAG, " testCostWeeklyAndAccount accountId = " + accountId + " avgMetabolism = " + avgMetabolism
                        + " avgTotalEnergy = " + avgTotalEnergy + " allMetabolism = " + allMetabolism
                        + " allTotalEnergy = " + allTotalEnergy + " yearWeek = " + yearWeek);
            }
        } finally {
            cursor.close();
        }
    }
    
    /**
     * 我的总能量消耗查看每一月，某一个帐户的周信息
     */
    private static final String[] PROJECTION_ENERGY_MONTHLY_COST = new String[] {
        EnergyCost.ACCOUNT_ID,          // 0
        EnergyCost.AVG_METABOLISM,      // 1
        EnergyCost.AVG_TOTAL_ENERGY,    // 2
        EnergyCost.ALL_METABOLISM ,     //3
        EnergyCost.ALL_TOTAL_ENERGY ,   //4
        EnergyCost.YEAR_MONTH           //5
    };

    private static final int ACCOUNT_ID_COLUMN_MONTHLY_INDEX = 0;
    private static final int AVG_METABOLISM_COLUMN_MONTHLY_INDEX = 1;
    private static final int AVG_TOTAL_ENERGY_COLUMN_MONTHLY_INDEX = 2;
    private static final int ALL_METABOLISM_COLUMN_MONTHLY_INDEX = 3;
    private static final int ALL_TOTAL_ENERGY_COLUMN_MONTHLY_INDEX = 4;
    private static final int YEAR_MONTH_COLUMN_MONTHLY_INDEX = 5;
    
    private void testCostlyMonthlyAndAccount() {
        Log.d(TAG, "testCostlyMonthlyAndAccount");
        String accountClause = EnergyCost.ACCOUNT_ID + " = ?";
        String sortOrder = EnergyCost.YEAR_MONTH + " DESC";
        String[] args = new String[] {"002"};
        Cursor cursor  = mContentResolver.query(EnergyCost.CONTENT_MONTHLY_URI,
                PROJECTION_ENERGY_MONTHLY_COST, accountClause, args, sortOrder);
        if (cursor == null) {
            Log.d(TAG, " testCostWeekly cursor = " + cursor);
        }

  
        try {
            cursor.moveToPosition(-1);
            while (cursor.moveToNext() ) {
                int  accountId = cursor.getInt(ACCOUNT_ID_COLUMN_MONTHLY_INDEX);
                long avgMetabolism = cursor.getLong(AVG_METABOLISM_COLUMN_MONTHLY_INDEX);
                long avgTotalEnergy = cursor.getLong(AVG_TOTAL_ENERGY_COLUMN_MONTHLY_INDEX);
                long allMetabolism = cursor.getLong(ALL_METABOLISM_COLUMN_MONTHLY_INDEX);
                long allTotalEnergy = cursor.getLong(ALL_TOTAL_ENERGY_COLUMN_MONTHLY_INDEX);
                long yearMonth = cursor.getInt(YEAR_MONTH_COLUMN_MONTHLY_INDEX);
                Log.d(TAG, " testCostlyMonthlyAndAccount accountId = " + accountId + " avgMetabolism = " + avgMetabolism
                        + " avgTotalEnergy = " + avgTotalEnergy + " allMetabolism = " + allMetabolism
                        + " allTotalEnergy = " + allTotalEnergy + " yearWeek = " + yearMonth);
            }
        } finally {
            cursor.close();
        }
    }
    
    /**
     * 我的总能量消耗查看每一月，某一个帐户的周信息
     */
    private static final String[] PROJECTION_ENERGY_YEARLY_COST = new String[] {
        EnergyCost.ACCOUNT_ID,          // 0
        EnergyCost.AVG_METABOLISM,      // 1
        EnergyCost.AVG_TOTAL_ENERGY,    // 2
        EnergyCost.ALL_METABOLISM ,     //3
        EnergyCost.ALL_TOTAL_ENERGY ,   //4
        EnergyCost.YEAR           //5
    };

    private static final int ACCOUNT_ID_COLUMN_YEARLY_INDEX = 0;
    private static final int AVG_METABOLISM_COLUMN_YEARLY_INDEX = 1;
    private static final int AVG_TOTAL_ENERGY_COLUMN_YEARLY_INDEX = 2;
    private static final int ALL_METABOLISM_COLUMN_YEARLY_INDEX = 3;
    private static final int ALL_TOTAL_ENERGY_COLUMN_YEARLY_INDEX = 4;
    private static final int YEAR_COLUMN_YEARLY_INDEX = 5;
    
    private void testCostlyYearlyAndAccount() {
        Log.d(TAG, "testCostlyMonthlyAndAccount");
        String accountClause = EnergyCost.ACCOUNT_ID + " = ?";
        String sortOrder = EnergyCost.YEAR + " DESC";
        String[] args = new String[] {"003"};
        Cursor cursor  = mContentResolver.query(EnergyCost.CONTENT_YEAR_URI,
                PROJECTION_ENERGY_YEARLY_COST, accountClause, args, sortOrder);
        if (cursor == null) {
            Log.d(TAG, " testCostWeekly cursor = " + cursor);
        }

  
        try {
            cursor.moveToPosition(-1);
            while (cursor.moveToNext() ) {
                int  accountId = cursor.getInt(ACCOUNT_ID_COLUMN_YEARLY_INDEX);
                long avgMetabolism = cursor.getLong(AVG_METABOLISM_COLUMN_YEARLY_INDEX);
                long avgTotalEnergy = cursor.getLong(AVG_TOTAL_ENERGY_COLUMN_YEARLY_INDEX);
                long allMetabolism = cursor.getLong(ALL_METABOLISM_COLUMN_YEARLY_INDEX);
                long allTotalEnergy = cursor.getLong(ALL_TOTAL_ENERGY_COLUMN_YEARLY_INDEX);
                long year = cursor.getInt(YEAR_COLUMN_YEARLY_INDEX);
                Log.d(TAG, " testCostlyYearlyAndAccount accountId = " + accountId + " avgMetabolism = " + avgMetabolism
                        + " avgTotalEnergy = " + avgTotalEnergy + " allMetabolism = " + allMetabolism
                        + " allTotalEnergy = " + allTotalEnergy + " year = " + year);
            }
        } finally {
            cursor.close();
        }
    }
    
    /**
     * 我的总能量消耗查看每一周，某一个帐户的周信息
     */
        
    private static final String[] PROJECTION_INGESTION_WEEKLY = new String[] {
        Ingestion.ACCOUNT_ID,               // 0
        Ingestion.AVG_TOTAL_INGESTION,      // 1
        Ingestion.ALL_TOTAL_INGESTION,      // 2
        Ingestion.YEAR_WEEK                 //3     
    };

    private static final int ACCOUNT_ID_INGESTION_WEEKLY_INDEX = 0;
    private static final int AVG_TOTAL_INGESTION_WEEKLY_INDEX = 1;
    private static final int ALL_TOTAL_INGESTION_WEEKLY_INDEX = 2;
    private static final int YEAR_WEEK_INGESTION_INDEX = 3;

    private void testIngestionWeeklyAndAccount() {
        
        Log.d(TAG, "testIngestionWeeklyAndAccount");
        String accountClause = Ingestion.ACCOUNT_ID + " = ?";
        String sortOrder = Ingestion.YEAR_WEEK + " DESC";
        String[] args = new String[] {"001"};
        Cursor cursor  = mContentResolver.query(Ingestion.CONTENT_WEEKLY_URI,
                PROJECTION_INGESTION_WEEKLY, accountClause, args, sortOrder);
        if (cursor == null) {
            Log.d(TAG, " testIngestionWeeklyAndAccount cursor = " + cursor);
        }

  
        try {
            cursor.moveToPosition(-1);
            while (cursor.moveToNext() ) {
                int  accountId = cursor.getInt(ACCOUNT_ID_INGESTION_WEEKLY_INDEX);
                long avgTotalIngestion = cursor.getLong(AVG_TOTAL_INGESTION_WEEKLY_INDEX);
                long allTotalIngestion = cursor.getLong(ALL_TOTAL_INGESTION_WEEKLY_INDEX);
                int  yearWeek = cursor.getInt(YEAR_WEEK_INGESTION_INDEX);
             
                Log.d(TAG, " testIngestionWeeklyAndAccount accountId = " + accountId + " avgTotalIngestion = " 
                   + avgTotalIngestion
                   + " allTotalIngestion = " + allTotalIngestion + " yearWeek = " + yearWeek );
            }
        } finally {
            cursor.close();
        }
    }
    
    /**
     * 我的总能量消耗查看每一月，某一个帐户的月信息
     */
        
    private static final String[] PROJECTION_INGESTION_MONTHLY = new String[] {
        Ingestion.ACCOUNT_ID,            // 0
        Ingestion.AVG_TOTAL_INGESTION,      // 1
        Ingestion.ALL_TOTAL_INGESTION,      // 2
        Ingestion.YEAR_MONTH               //3     
    };

    private static final int ACCOUNT_ID_INGESTION_MONTHLY_INDEX = 0;
    private static final int AVG_TOTAL_INGESTION_MONTHLY_INDEX = 1;
    private static final int ALL_TOTAL_INGESTION_MONTHLY_INDEX = 2;
    private static final int YEAR_MONTH_INGESTION_INDEX = 3;

    private void testIngestionMonthlyAndAccount() {
        
        Log.d(TAG, "testIngestionMonthlyAndAccount");
        String accountClause = Ingestion.ACCOUNT_ID + " = ?";
        String sortOrder = Ingestion.YEAR_MONTH + " DESC";
        String[] args = new String[] {"001"};
        Cursor cursor  = mContentResolver.query(Ingestion.CONTENT_MONTHLY_URI,
                PROJECTION_INGESTION_MONTHLY, accountClause, args, sortOrder);
        if (cursor == null) {
            Log.d(TAG, " testIngestionMonthlyAndAccount cursor = " + cursor);
        }

  
        try {
            cursor.moveToPosition(-1);
            while (cursor.moveToNext() ) {
                int  accountId = cursor.getInt(ACCOUNT_ID_INGESTION_MONTHLY_INDEX);
                long avgTotalIngestion = cursor.getLong(AVG_TOTAL_INGESTION_MONTHLY_INDEX);
                long allTotalIngestion = cursor.getLong(ALL_TOTAL_INGESTION_MONTHLY_INDEX);
                int  yearMonth = cursor.getInt(YEAR_MONTH_INGESTION_INDEX);
             
                Log.d(TAG, " testIngestionMonthlyAndAccount accountId = " + accountId + " avgTotalIngestion = " 
                   + avgTotalIngestion
                   + " allTotalIngestion = " + allTotalIngestion + " yearMonth = " + yearMonth );
            }
        } finally {
            cursor.close();
        }
    }
    
    private static final String[] PROJECTION_INGESTION_YEARLY = new String[] {
        Ingestion.ACCOUNT_ID,          // 0
        Ingestion.AVG_TOTAL_INGESTION,      // 1
        Ingestion.ALL_TOTAL_INGESTION,    // 2
        Ingestion.YEAR         //3     
    };

    private static final int ACCOUNT_ID_INGESTION_YEARLY_INDEX = 0;
    private static final int AVG_TOTAL_INGESTION_YEARLY_INDEX = 1;
    private static final int ALL_TOTAL_INGESTION_YEARLY_INDEX = 2;
    private static final int YEAR_INGESTION_INDEX = 3;
    
    /**
     * 我的总能量消耗查看每一年，某一个帐户的年信息
     */
    private void testIngestionYearlyAndAccount() {
        
        Log.d(TAG, "testIngestionYearlyAndAccount");
        String accountClause = Ingestion.ACCOUNT_ID + " = ?";
        String sortOrder = Ingestion.YEAR + " DESC";
        String[] args = new String[] {"003"};
        Cursor cursor  = mContentResolver.query(Ingestion.CONTENT_YEAR_URI,
                PROJECTION_INGESTION_YEARLY, accountClause, args, sortOrder);
        if (cursor == null) {
            Log.d(TAG, " testIngestionYearlyAndAccount cursor = " + cursor);
        }

  
        try {
            cursor.moveToPosition(-1);
            while (cursor.moveToNext() ) {
                int  accountId = cursor.getInt(ACCOUNT_ID_INGESTION_YEARLY_INDEX);
                long avgTotalIngestion = cursor.getLong(AVG_TOTAL_INGESTION_YEARLY_INDEX);
                long allTotalIngestion = cursor.getLong(ALL_TOTAL_INGESTION_YEARLY_INDEX);
                int  year = cursor.getInt(YEAR_INGESTION_INDEX);
             
                Log.d(TAG, " testIngestionYearlyAndAccount accountId = " + accountId + " avgTotalIngestion = " 
                   + avgTotalIngestion
                   + " allTotalIngestion = " + allTotalIngestion + " yearMonth = " + year );
            }
        } finally {
            cursor.close();
        }
    }
    
    /**
     * 我的体重变化查看每一周，某一个帐户的周信息
     */
        
    private static final String[] PROJECTION_WEIGHT_WEEKLY = new String[] {
        WeightChange.ACCOUNT_ID,               // 0
        WeightChange.AVG_WEIGHT,      // 1
        WeightChange.AVG_EXPECTED_WEIGHT,      // 2
        WeightChange.YEAR_WEEK                 //3     
    };

    private static final int ACCOUNT_ID_WEIGHT_WEEKLY_INDEX = 0;
    private static final int AVG_WEIGHT_WEEKLY_INDEX = 1;
    private static final int AVG_EXPECTED_WEIGHT_WEEKLY_INDEX = 2;
    private static final int YEAR_WEEK_WEIGHT_INDEX = 3;

    private void testWeightWeeklyAndAccount() {
        
        Log.d(TAG, "testWeightWeeklyAndAccount");
        String accountClause = WeightChange.ACCOUNT_ID + " = ?";
        String sortOrder = WeightChange.YEAR_WEEK + " DESC";
        String[] args = new String[] {"001"};
        Cursor cursor  = mContentResolver.query(WeightChange.CONTENT_WEEKLY_URI,
                PROJECTION_WEIGHT_WEEKLY, accountClause, args, sortOrder);
        if (cursor == null) {
            Log.d(TAG, " testWeightWeeklyAndAccount cursor = " + cursor);
        }

  
        try {
            cursor.moveToPosition(-1);
            while (cursor.moveToNext() ) {
                int  accountId = cursor.getInt(ACCOUNT_ID_WEIGHT_WEEKLY_INDEX);
                float avgWeight = cursor.getFloat(AVG_WEIGHT_WEEKLY_INDEX);
                float avgExpectedWeight = cursor.getFloat(AVG_EXPECTED_WEIGHT_WEEKLY_INDEX);
                int  yearWeek = cursor.getInt(YEAR_WEEK_WEIGHT_INDEX);
             
                Log.d(TAG, " testWeightWeeklyAndAccount accountId = " + accountId
                        + " avgWeight = " 
                   + avgWeight
                   + " avgExpectedWeight = " + avgExpectedWeight + " yearWeek = " + yearWeek );
            }
        } finally {
            cursor.close();
        }
    }
     
    /**
     * 我的体重变化查看每一月，某一个帐户的月信息
     */
    private static final String[] PROJECTION_WEIGHT_MONTHLY = new String[] {
        WeightChange.ACCOUNT_ID,               // 0
        WeightChange.AVG_WEIGHT,      // 1
        WeightChange.AVG_EXPECTED_WEIGHT,      // 2
        WeightChange.YEAR_MONTH                 //3     
    };

    private static final int ACCOUNT_ID_WEIGHT_MONTHLY_INDEX = 0;
    private static final int AVG_WEIGHT_MONTHLY_INDEX = 1;
    private static final int AVG_EXPECTED_WEIGHT_MONTHLY_INDEX = 2;
    private static final int YEAR_MONTH_WEIGHT_INDEX = 3;

     private void testWeightMonthlyAndAccount() {
        
        Log.d(TAG, "testWeightMonthlyAndAccount");
        String accountClause = WeightChange.ACCOUNT_ID + " = ?";
        String sortOrder = WeightChange.YEAR_MONTH + " DESC";
        String[] args = new String[] {"001"};
        Cursor cursor  = mContentResolver.query(WeightChange.CONTENT_MONTHLY_URI,
                PROJECTION_WEIGHT_MONTHLY, accountClause, args, sortOrder);
        if (cursor == null) {
            Log.d(TAG, " testWeightWeeklyAndAccount cursor = " + cursor);
        }

  
        try {
            cursor.moveToPosition(-1);
            while (cursor.moveToNext() ) {
                int  accountId = cursor.getInt(ACCOUNT_ID_WEIGHT_MONTHLY_INDEX);
                float avgWeight = cursor.getFloat(AVG_WEIGHT_MONTHLY_INDEX);
                float avgExpectedWeight = cursor.getFloat(AVG_EXPECTED_WEIGHT_MONTHLY_INDEX);
                int  yearWeek = cursor.getInt(YEAR_MONTH_WEIGHT_INDEX);
             
                Log.d(TAG, " testWeightMonthlyAndAccount accountId = " + accountId
                        + " avgWeight = " 
                   + avgWeight
                   + " avgExpectedWeight = " + avgExpectedWeight + " yearWeek = " + yearWeek );
            }
        } finally {
            cursor.close();
        }
    }
     
     /**
      * 我的体重变化查看每一年，某一个帐户的年信息
      */
     private static final String[] PROJECTION_WEIGHT_YEARLY = new String[] {
         WeightChange.ACCOUNT_ID,               // 0
         WeightChange.AVG_WEIGHT,      // 1
         WeightChange.AVG_EXPECTED_WEIGHT,      // 2
         WeightChange.YEAR                 //3     
     };

     private static final int ACCOUNT_ID_WEIGHT_YEARLY_INDEX = 0;
     private static final int AVG_WEIGHT_YEARLY_INDEX = 1;
     private static final int AVG_EXPECTED_WEIGHT_YEARLY_INDEX = 2;
     private static final int YEAR_WEIGHT_INDEX = 3;

      private void testWeightYearlyAndAccount() {
         
         Log.d(TAG, "testWeightYearlyAndAccount");
         String accountClause = WeightChange.ACCOUNT_ID + " = ?";
         String sortOrder = WeightChange.YEAR + " DESC";
         String[] args = new String[] {"001"};
         Cursor cursor  = mContentResolver.query(WeightChange.CONTENT_YEAR_URI,
                 PROJECTION_WEIGHT_YEARLY, accountClause, args, sortOrder);
         if (cursor == null) {
             Log.d(TAG, " testWeightYearlyAndAccount cursor = " + cursor);
         }

   
         try {
             cursor.moveToPosition(-1);
             while (cursor.moveToNext() ) {
                 int  accountId = cursor.getInt(ACCOUNT_ID_WEIGHT_YEARLY_INDEX);
                 float avgWeight = cursor.getFloat(AVG_WEIGHT_YEARLY_INDEX);
                 float avgExpectedWeight = cursor.getFloat(AVG_EXPECTED_WEIGHT_YEARLY_INDEX);
                 int  yearWeek = cursor.getInt(YEAR_WEIGHT_INDEX);
              
                 Log.d(TAG, " testWeightYearlyAndAccount accountId = " + accountId
                         + " avgWeight = " 
                    + avgWeight
                    + " avgExpectedWeight = " + avgExpectedWeight + " yearWeek = " + yearWeek );
             }
         } finally {
             cursor.close();
         }
     }
}
