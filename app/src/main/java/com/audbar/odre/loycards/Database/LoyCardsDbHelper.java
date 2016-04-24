package com.audbar.odre.loycards.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.audbar.odre.loycards.Database.DbReaderContract;
import com.audbar.odre.loycards.Model.LoyCardType;

import java.util.Date;

/**
 * Created by Audrius on 2016-04-04.
 */
public class LoyCardsDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.

    private Context context;
    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "LoyCards.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String DATETIME_TYPE = " DATETIME";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_USERS_CARDS =
            "CREATE TABLE " + DbReaderContract.UsersCardsDb.TABLE_NAME +
                    " (" + DbReaderContract.UsersCardsDb._ID + " INTEGER PRIMARY KEY," +
                    DbReaderContract.UsersCardsDb.COLUMN_NAME_DATE_REGISTERED + DATETIME_TYPE + COMMA_SEP +
                    DbReaderContract.UsersCardsDb.COLUMN_NAME_LOY_CARD_TYPE_ID + INTEGER_TYPE + COMMA_SEP +
                    DbReaderContract.UsersCardsDb.COLUMN_NAME_LOY_CARD_TYPE + TEXT_TYPE + COMMA_SEP +
                    DbReaderContract.UsersCardsDb.COLUMN_NAME_CARD_NUMBER + TEXT_TYPE + COMMA_SEP +
                    DbReaderContract.UsersCardsDb.COLUMN_NAME_USER_NAME + TEXT_TYPE + COMMA_SEP +
                    DbReaderContract.UsersCardsDb.COLUMN_NAME_USER_BIRTH_DATE + DATETIME_TYPE + COMMA_SEP +
                    DbReaderContract.UsersCardsDb.COLUMN_NAME_PASSWORD + TEXT_TYPE + COMMA_SEP +
                    DbReaderContract.UsersCardsDb.COLUMN_NAME_USER_ID + TEXT_TYPE + COMMA_SEP +
                    DbReaderContract.UsersCardsDb.COLUMN_NAME_IMAGE_URL + TEXT_TYPE +
                    " ); ";
    private static final String SQL_CREATE_CARD_TYPES =
                    "CREATE TABLE " + DbReaderContract.LoyaltyCardTypesDb.TABLE_NAME + " (" +
                    DbReaderContract.LoyaltyCardTypesDb._ID + " INTEGER PRIMARY KEY," +
                    DbReaderContract.LoyaltyCardTypesDb.COLUMN_NAME_DATE_CREATED + DATETIME_TYPE + COMMA_SEP +
                    DbReaderContract.LoyaltyCardTypesDb.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    DbReaderContract.LoyaltyCardTypesDb.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    DbReaderContract.LoyaltyCardTypesDb.COLUMN_NAME_IMAGE_URL + TEXT_TYPE +
                    " ); ";
    private static final String SQL_CREATE_USERS =
                    "CREATE TABLE " + DbReaderContract.UsersDb.TABLE_NAME + " (" +
                    DbReaderContract.UsersDb._ID + " INTEGER PRIMARY KEY," +
                    DbReaderContract.UsersDb.COLUMN_NAME_DATE_CREATED + DATETIME_TYPE + COMMA_SEP +
                    DbReaderContract.UsersDb.COLUMN_NAME_DEVICE_ID + INTEGER_TYPE + COMMA_SEP +
                    DbReaderContract.UsersDb.COLUMN_NAME_USER_NAME + TEXT_TYPE + COMMA_SEP +
                    DbReaderContract.UsersDb.COLUMN_NAME_USER_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                    DbReaderContract.UsersDb.COLUMN_NAME_IMAGE_URL + TEXT_TYPE + COMMA_SEP +
                    DbReaderContract.UsersDb.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                    DbReaderContract.UsersDb.COLUMN_NAME_GOOGLE_ID + TEXT_TYPE +
                    " );"
            ;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DbReaderContract.UsersCardsDb.TABLE_NAME + ";" +
                    "DROP TABLE IF EXISTS " + DbReaderContract.LoyaltyCardTypesDb.TABLE_NAME + ";" +
                    "DROP TABLE IF EXISTS " + DbReaderContract.UsersDb.TABLE_NAME + ";";


    public LoyCardsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS_CARDS);
        db.execSQL(SQL_CREATE_CARD_TYPES);
        db.execSQL(SQL_CREATE_USERS);
        populateData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void populateData(SQLiteDatabase db){
        LoyCardType cardType = new LoyCardType();
        cardType.title = "Maxima";
        cardType.description = "Maximos nuolaidų kortelė";
        cardType.dateCreated = new Date();
        cardType.imageUrl = "https://www.maxima.lt/images/front/logos/maxima_logo.png";

        insertLoyCardType(db, cardType);

        cardType = new LoyCardType();
        cardType.title = "iki";
        cardType.description = "IKI nuolaidų kortelė";
        cardType.dateCreated = new Date();
        cardType.imageUrl = "http://riesutainis.com/wp-content/uploads/2015/04/Iki.png";

        insertLoyCardType(db, cardType);

        cardType = new LoyCardType();
        cardType.title = "Drogas";
        cardType.description = "Drogas lojalumo kortelė";
        cardType.dateCreated = new Date();
        cardType.imageUrl = "https://www.drogas.lv/css/main/images/logo_alternate.png";

        insertLoyCardType(db, cardType);

    }

    private void insertLoyCardType(SQLiteDatabase db, LoyCardType loyCardType){
        ContentValues values = new ContentValues();
        values.put(DbReaderContract.LoyaltyCardTypesDb.COLUMN_NAME_DATE_CREATED, String.valueOf(loyCardType.dateCreated));
        values.put(DbReaderContract.LoyaltyCardTypesDb.COLUMN_NAME_TITLE, loyCardType.title);
        values.put(DbReaderContract.LoyaltyCardTypesDb.COLUMN_NAME_DESCRIPTION, loyCardType.description);
        values.put(DbReaderContract.LoyaltyCardTypesDb.COLUMN_NAME_IMAGE_URL, loyCardType.imageUrl);


        long newRowId;
        newRowId = db.insert(
                DbReaderContract.LoyaltyCardTypesDb.TABLE_NAME,
                null,
                values);
    }
}
