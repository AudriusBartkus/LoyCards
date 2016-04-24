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
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Audrius on 2016-04-22.
 */
public final class DatabaseMethods {

    private static final String REGISTER_URL = "http://loycards-eb0bc.appspot.com/hello";
    private static final String USER_ID = "user_id";
    private static final String ACTION = "action";
    private static final String GOOGLE_ID = "google_id";

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
}
