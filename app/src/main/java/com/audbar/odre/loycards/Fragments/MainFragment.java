package com.audbar.odre.loycards.Fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.audbar.odre.loycards.MainActivity;
import com.audbar.odre.loycards.R;
import com.audbar.odre.loycards.Servlet.ServletPostAsyncTask;


import org.xml.sax.XMLReader;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment  {

//    public static final MediaType JSON
//            = MediaType.parse("application/json; charset=utf-8");


    private static final String REGISTER_URL = "http://loycards-eb0bc.appspot.com/hello";

    public static final String KEY_USERNAME = "user_name";
    public static final String KEY_PASSWORD = "device_id";
    public static final String KEY_EMAIL = "email";

//    OkHttpClient client;
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button bActivateCard = (Button) view.findViewById(R.id.b_activate_loy_card);

//        client = new OkHttpClient();
        bActivateCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                ActivateLoyaltyCardFragment activateLoyaltyCardFragment = new ActivateLoyaltyCardFragment();
//                activateLoyaltyCardFragment.setShowsDialog(true);
//                activateLoyaltyCardFragment.show(getFragmentManager(), "dialog");
                new ServletPostAsyncTask().execute(new Pair<Context, String>(getActivity(), "Manfred"));


                  //  testMethod();
//                try{
//                    String response =  post("http://localhost:8080/hello", "{'user_name':'lolol', 'device_id': '4sdcsdcse56'}");
//                    Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
//                }
//                catch (IOException e)
//                {
//                    Toast.makeText(getActivity(), e.getCause().getMessage(), Toast.LENGTH_LONG).show();
//                }

                registerUser();
//                new RetrieveFeedTask().execute("");
            }
        });

        return view;
    }




    private void registerUser(){
        final String username = "aasd";
        final String password = "ffvf";
        final String email = "cdscdsfv";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME,username);
                params.put(KEY_PASSWORD,password);
                params.put(KEY_EMAIL, email);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
