package com.audbar.odre.loycards.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.audbar.odre.loycards.Model.LoyCard;
import com.audbar.odre.loycards.Model.LoyCardType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Audrius on 2016-04-23.
 */
public class LocalDatabaseMethods {

    LoyCardsDbHelper mDbHelper;

    public LocalDatabaseMethods(Context context){
        mDbHelper = new LoyCardsDbHelper(context);
    }

    public void InsertOrUpdateUser(String googleId, String userName, String email, String imageUrl, String deviceId){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Cursor cs = db.rawQuery("select count(*) from app_users;", null);
        String dddd;
        if (cs.moveToFirst()) {
            do {
                dddd = cs.getString(0);
            }while(cs.moveToNext());
        }
        cs.close();

        Cursor css = db.rawQuery("select count(*) from users_cards;", null);
        if (css.moveToFirst()) {
            do {
                dddd = css.getString(0);
            }while(css.moveToNext());
        }
        css.close();


        Cursor c = db.rawQuery("Select * from " + DbReaderContract.UsersDb.TABLE_NAME + " where " + DbReaderContract.UsersDb.COLUMN_NAME_GOOGLE_ID + " = '" + googleId + "';", null);
        if(c.getCount() <= 0)
        {
            //insert
            ContentValues values = new ContentValues();
            values.put(DbReaderContract.UsersDb.COLUMN_NAME_DATE_CREATED, new Date().toString());
            values.put(DbReaderContract.UsersDb.COLUMN_NAME_DEVICE_ID, deviceId);
            values.put(DbReaderContract.UsersDb.COLUMN_NAME_USER_NAME, userName);
            values.put(DbReaderContract.UsersDb.COLUMN_NAME_IMAGE_URL, imageUrl);
            values.put(DbReaderContract.UsersDb.COLUMN_NAME_EMAIL, email);
            values.put(DbReaderContract.UsersDb.COLUMN_NAME_GOOGLE_ID, googleId);
            long newRowId;
            newRowId = db.insert(
                    DbReaderContract.UsersDb.TABLE_NAME,
                    null,
                    values);
        }
        else {
            //update
            db.rawQuery("UPDATE " + DbReaderContract.UsersDb.TABLE_NAME +
                    " SET "+ DbReaderContract.UsersDb.COLUMN_NAME_DEVICE_ID + " = '" + deviceId + "' " +
                    " where " + DbReaderContract.UsersDb.COLUMN_NAME_GOOGLE_ID + " = '" + googleId+"';", null);
        }
        c.close();
        db.close();
    }

    public void saveLoyCards(List<LoyCard> loyCards){
        for (LoyCard card: loyCards) {
            registerLoyCard(card);
        }
    }

    public void clearLoyCards(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(DbReaderContract.UsersCardsDb.TABLE_NAME, null, null);

        db.close();
    }



    public void registerLoyCard(LoyCard card){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Cursor c = db.rawQuery("Select * from " + DbReaderContract.UsersCardsDb.TABLE_NAME + " where " + DbReaderContract.UsersCardsDb.COLUMN_NAME_LOY_CARD_TYPE_ID + " = " + card.cardTypeId + " AND " + DbReaderContract.UsersCardsDb.COLUMN_NAME_USER_ID + " =  '" + card.userId + "';", null);
        if(c.getCount() <= 0)
        {
            //insert
            ContentValues values = new ContentValues();
            values.put(DbReaderContract.UsersCardsDb.COLUMN_NAME_CARD_NUMBER, card.cardNumber);
            values.put(DbReaderContract.UsersCardsDb.COLUMN_NAME_DATE_REGISTERED, String.valueOf(card.dateRegistered));
            values.put(DbReaderContract.UsersCardsDb.COLUMN_NAME_LOY_CARD_TYPE_ID, card.cardTypeId);
            values.put(DbReaderContract.UsersCardsDb.COLUMN_NAME_LOY_CARD_TYPE, card.cardType);
            values.put(DbReaderContract.UsersCardsDb.COLUMN_NAME_USER_NAME, card.userName);
            values.put(DbReaderContract.UsersCardsDb.COLUMN_NAME_USER_ID, card.userId);
            values.put(DbReaderContract.UsersCardsDb.COLUMN_NAME_USER_BIRTH_DATE, String.valueOf(card.birthDate));


            long newRowId;
            newRowId = db.insertWithOnConflict(
                    DbReaderContract.UsersCardsDb.TABLE_NAME,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
        else {
            //update
        }
        c.close();

        db.close();
    }

    public List<LoyCard> getLocalLoyCards(String user_id){
        List<LoyCard> list=new ArrayList<LoyCard>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String selectQuery = "SELECT uc._id as id, uc.card_number, uc.date_registered, uc.loy_card_type_id, lct.title, lct.image_url, lct.description, uc.user_name, u.google_id, uc.birth_date " +
                "FROM app_users u  " +
                "INNER JOIN users_cards uc ON u.google_id = uc.user_id  " +
                "LEFT JOIN loyalty_card_types lct ON uc.loy_card_type_id = lct._id " +
                "WHERE u.google_id = '"  + user_id + "';";



        Cursor c = db.rawQuery(selectQuery, null);
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (c.moveToFirst())
        {
            do {
//                try{
                    LoyCard card = new LoyCard();
                    card.id = Integer.parseInt(c.getString(0));
                    card.cardNumber = c.getString(1);
                    //card.dateRegistered = df.parse(c.getString(2));
                    card.cardTypeId = Integer.parseInt(c.getString(3));
                    card.cardType =c.getString(4);
                    card.imgUrl = c.getString(5);
                    card.description = c.getString(6);
                    card.userName = c.getString(7);
                    card.userId = c.getString(8);
                    //card.birthDate = df.parse(c.getString(9));
                    list.add(card);
                //}
//               catch (ParseException e){
//                   Log.e("date parse", e.getLocalizedMessage());
//               }
            }while(c.moveToNext());
        }
        c.close();
        return list;
    }

    public List<LoyCardType> getLocalLoyCardTypes(String userId){
        List<LoyCardType> list=new ArrayList<LoyCardType>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String selectQuery = "SELECT ct._id as id, ct.date_created, ct.title, ct.description, ct.image_url " +
                "FROM " + DbReaderContract.LoyaltyCardTypesDb.TABLE_NAME + " as ct left join " + DbReaderContract.UsersCardsDb.TABLE_NAME + " as uc on uc.loy_card_type_id = ct._id and uc.user_id = '" + userId + "' " +
                "where uc.loy_card_type_id is null;";



        Cursor c = db.rawQuery(selectQuery, null);
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (c.moveToFirst())
        {
            do {
//                try{
                LoyCardType cardType = new LoyCardType();
                cardType.id = Integer.parseInt(c.getString(0));
                //cardType.dateCreated = df.parse(c.getString(1));
                cardType.title = c.getString(2);
                cardType.description =c.getString(3);
                cardType.imageUrl = c.getString(4);
                list.add(cardType);
                //}
//               catch (ParseException e){
//                   Log.e("date parse", e.getLocalizedMessage());
//               }
            }while(c.moveToNext());
        }
        c.close();
        return list;
    }

}
