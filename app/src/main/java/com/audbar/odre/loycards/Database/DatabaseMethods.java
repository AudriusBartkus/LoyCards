package com.audbar.odre.loycards.Database;

import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.audbar.odre.loycards.Fragments.LoyCardsListFragment;
import com.audbar.odre.loycards.Model.LoyCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.audbar.odre.loycards.Model.LoyCardList;
import com.audbar.odre.loycards.Model.User;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Audrius on 2016-04-22.
 */
public final class DatabaseMethods {

    private static final String REGISTER_URL = "http://loycards-eb0bc.appspot.com/hello";
    private static final String ACTION = "action";
    private static final String GOOGLE_ID = "google_id";

    //LoyCard
    private static final String LOY_CARD_TYPE = "loy_card_type";
    private static final String CARD_NUMBER = "card_number";
    private static final String USER_NAME = "user_name";
    private static final String BIRTH_DATE = "birth_date";
    private static final String PASSWORD = "password";
    private static final String USER_ID = "user_id";

    //User
    private static final String DATE_CREATED = "date_created";
    private static final String DEVICE_ID = "device_id";
    private static final String USER_LAST_NAME = "user_last_name";
    private static final String IMAGE_URL = "image_url";
    private static final String EMAIL = "email";


    private static String action;
    private DatabaseMethods(){

    }

    //public static void getUserLoyCards(final Activity activity, final LoyCardsListFragment fragment, final String user_id){
    public static void getUserLoyCards(final Activity activity, final String user_id){
        action = "getUserLoyCards";
        final List<LoyCard> loyCards = new ArrayList<LoyCard>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LoyCardList loyCardList = new LoyCardList();
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            loyCardList = mapper.readValue(response, LoyCardList.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        LocalDatabaseMethods localDb = new LocalDatabaseMethods(activity.getApplicationContext());
                        localDb.saveLoyCards(loyCardList.loyCardList);
//                        fragment.handleLoyCardsResult(loyCardList.loyCardList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(ACTION, action);
                params.put(GOOGLE_ID, user_id);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    public static void registerLoyCard(final Activity activity, final LoyCard loyCard){
        action = "registerLoyCard";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        LocalDatabaseMethods localDb = new LocalDatabaseMethods(activity.getApplicationContext());
                        localDb.registerLoyCard(loyCard);
                        Toast.makeText(activity,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(ACTION, action);
                params.put(LOY_CARD_TYPE, loyCard.cardType);
                params.put(CARD_NUMBER, loyCard.cardNumber);
                params.put(USER_NAME, loyCard.userName);
                params.put(BIRTH_DATE, loyCard.birthDate.toString());
                params.put(PASSWORD, loyCard.password);
                params.put(USER_ID, loyCard.userId);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    public static void registerUser(final Activity activity, final User user){
        action = "registerUser";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        LocalDatabaseMethods localDb = new LocalDatabaseMethods(activity.getApplicationContext());
                        localDb.InsertOrUpdateUser(user.googleId, user.userName, user.email, user.imageUrl, user.deviceId);

                        getUserLoyCards(activity, user.googleId);
                       // Toast.makeText(activity,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(ACTION, action);
                params.put(DATE_CREATED, user.dateCreated.toString());
                params.put(DEVICE_ID, user.deviceId);
                params.put(USER_NAME, user.userName);
                params.put(USER_LAST_NAME, user.userLastName);
                params.put(IMAGE_URL, user.imageUrl);
                params.put(EMAIL, user.email);
                params.put(GOOGLE_ID, user.googleId);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

}
