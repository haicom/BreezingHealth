package com.breezinghealth.providers;

import android.net.Uri;
import android.provider.BaseColumns;

public class Breezing {
    private final static String TAG = "Breezing";
    /** The authority for the contacts provider */
    public static final String AUTHORITY = "breezing";
    /** A content:// style uri to the authority for the contacts provider */
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    // Constructor
    public Breezing() {

    }

    public static final class Account implements BaseColumns {
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(AUTHORITY_URI, "account");
        public static final String ACCOUNT_NAME = "account_name";
        public static final String ACCOUNT_ID   = "account_id";
        public static final String ACCOUNT_PASSWORD = "account_password";

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of
         * people.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/account";

        /**
         * The MIME type of a {@link #CONTENT_URI} subdirectory of a single
         * person.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/account";


    }

    public static final class Information implements BaseColumns {
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(AUTHORITY_URI, "information");
        public static final String ACCOUNT_ID   = "account_id";
        public static final String GENDER   = "gender";
        public static final String HEIGHT   = "height";
        public static final String WEIGHT   = "weight";
        public static final String BIRTHDAY   = "birthday";
        public static final String CUSTOM   = "custom";
        public static final String EXPECTED_WEIGHT   = "expected_weight";

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of
         * people.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/information";

        /**
         * The MIME type of a {@link #CONTENT_URI} subdirectory of a single
         * person.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/information";
    }

    /**
     * Base columns for tables that contain EnergyCost Ingestion WeightChange.
     */
    public interface BaseDateColumns extends BaseColumns {
        public static final String DATE   = "date";
        public static final String YEAR   = "year";
        public static final String YEAR_MONTH   = "year_month";
        public static final String YEAR_WEEK   = "year_week";
    }

    public static final class EnergyCost implements BaseDateColumns {
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(AUTHORITY_URI, "cost");
        public static final String ACCOUNT_ID   = "account_id";
        public static final String METABOLISM   = "metabolism";
        public static final String SPORT   = "sport";
        public static final String DIGEST   = "digest";
        public static final String TRAIN   = "train";
        public static final String TOTAL_ENERGY   = "total_energy";
        /**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER = "date DESC";

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of
         * people.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/cost";

        /**
         * The MIME type of a {@link #CONTENT_URI} subdirectory of a single
         * person.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/cost";
    }

    public static final class Ingestion implements BaseDateColumns {
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(AUTHORITY_URI, "ingestion");
        public static final String ACCOUNT_ID   = "account_id";
        public static final String BREAKFAST   = "breakfast";
        public static final String LUNCH   = "lunch";
        public static final String DINNER   = "dinner";
        public static final String ETC  = "etc";
        public static final String TOTAL_INGESTION   = "total_ingestion";
        /**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER = "date DESC";

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of
         * people.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/ingestion";

        /**
         * The MIME type of a {@link #CONTENT_URI} subdirectory of a single
         * person.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/ingestion";
    }

    public static final class WeightChange implements BaseDateColumns {
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(AUTHORITY_URI, "weight");
        public static final String ACCOUNT_ID   = "account_id";
        public static final String WEIGHT   = "weight";
        public static final String EXPECTED_WEIGHT   = "expected_weight";
        /**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER = "date DESC";

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of
         * people.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/weight";

        /**
         * The MIME type of a {@link #CONTENT_URI} subdirectory of a single
         * person.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/weight";
    }

    public static final class HeatConsumption implements BaseColumns {
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(AUTHORITY_URI, "heat_consumption");
        public static final String SPORT_TYPE   = "sport_type";
        public static final String SPORT_LONG   = "sport_long";
        public static final String SPORT_DISTANCE   = "sport_distance";
        public static final String SPORT_TIMES   = "sport_times";
        public static final String CALORIE   = "calorie";
        public static final String DATE   = "date";
        /**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER = "date DESC";

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of
         * people.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/heat_consumption";

        /**
         * The MIME type of a {@link #CONTENT_URI} subdirectory of a single
         * person.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/heat_consumption";
    }

    public static final class HeatIngestion implements BaseColumns {
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(AUTHORITY_URI, "heat_ingestion");
        public static final String FOOD_TYPE   = "food_type ";
        public static final String FOOD_NAME   = "food_name";
        public static final String NAME_EXPRESS   = "name_express";
        public static final String PRIORITY   = "priority";
        public static final String FOOD_SIZE   = "food_size";
        public static final String CALORIE   = "calorie";
        /**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER = "date DESC";

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of
         * people.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/heat_ingestion";

        /**
         * The MIME type of a {@link #CONTENT_URI} subdirectory of a single
         * person.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/heat_ingestion";
    }
}
