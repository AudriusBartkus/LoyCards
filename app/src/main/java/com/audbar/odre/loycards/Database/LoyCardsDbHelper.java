package com.audbar.odre.loycards.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.audbar.odre.loycards.Database.DbReaderContract;

/**
 * Created by Audrius on 2016-04-04.
 */
public class LoyCardsDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "LoyCards.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String DATETIME_TYPE = " DATETIME";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DbReaderContract.UsersCardsDb.TABLE_NAME +
                    " (" + DbReaderContract.UsersCardsDb._ID + " INTEGER PRIMARY KEY," +
                    DbReaderContract.UsersCardsDb.COLUMN_NAME_DATE_REGISTERED + DATETIME_TYPE + COMMA_SEP +
                    DbReaderContract.UsersCardsDb.COLUMN_NAME_REGISTERED_BY + INTEGER_TYPE + COMMA_SEP +
                    DbReaderContract.UsersCardsDb.COLUMN_NAME_LOY_CARD_TYPE_ID + INTEGER_TYPE + COMMA_SEP +
                    DbReaderContract.UsersCardsDb.COLUMN_NAME_CARD_NUMBER + TEXT_TYPE + COMMA_SEP +
                    DbReaderContract.UsersCardsDb.COLUMN_NAME_USER_NAME + TEXT_TYPE + COMMA_SEP +
                    DbReaderContract.UsersCardsDb.COLUMN_NAME_USER_BIRTH_DATE + DATETIME_TYPE +
                    DbReaderContract.UsersCardsDb.COLUMN_NAME_PASSWORD + TEXT_TYPE +
                    DbReaderContract.UsersCardsDb.COLUMN_NAME_DEVICE_ID + TEXT_TYPE +
                    " );"
            +
                    "CREATE TABLE " + DbReaderContract.LoyaltyCardTypesDb.TABLE_NAME + " (" +
                    DbReaderContract.LoyaltyCardTypesDb._ID + " INTEGER PRIMARY KEY," +
                    DbReaderContract.LoyaltyCardTypesDb.COLUMN_NAME_DATE_CREATED + DATETIME_TYPE + COMMA_SEP +
                    DbReaderContract.LoyaltyCardTypesDb.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    DbReaderContract.LoyaltyCardTypesDb.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    DbReaderContract.LoyaltyCardTypesDb.COLUMN_NAME_IMAGE_URL + TEXT_TYPE + COMMA_SEP +
                    " );"
            +
                    "CREATE TABLE " + DbReaderContract.UsersDb.TABLE_NAME + " (" +
                    DbReaderContract.UsersDb._ID + " INTEGER PRIMARY KEY," +
                    DbReaderContract.UsersDb.COLUMN_NAME_DATE_CREATED + DATETIME_TYPE + COMMA_SEP +
                    DbReaderContract.UsersDb.COLUMN_NAME_DEVICE_ID + INTEGER_TYPE +
                    DbReaderContract.UsersDb.COLUMN_NAME_USER_NAME + TEXT_TYPE + COMMA_SEP +
                    DbReaderContract.UsersDb.COLUMN_NAME_USER_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                    DbReaderContract.UsersDb.COLUMN_NAME_IMAGE_URL + TEXT_TYPE + COMMA_SEP +
                    " );"
            ;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DbReaderContract.UsersCardsDb.TABLE_NAME + ";" +
                    "DROP TABLE IF EXISTS " + DbReaderContract.LoyaltyCardTypesDb.TABLE_NAME + ";" +
                    "DROP TABLE IF EXISTS " + DbReaderContract.UsersDb.TABLE_NAME + ";";


    public LoyCardsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
