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


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment  {


    private static final String REGISTER_URL = "http://loycards-eb0bc.appspot.com/hello";

    public static final String KEY_USERNAME = "user_name";
    public static final String KEY_PASSWORD = "device_id";
    public static final String KEY_EMAIL = "email";

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

//        Button bActivateCard = (Button) view.findViewById(R.id.b_activate_loy_card);

//        bActivateCard.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//            //    registerUser();
//            }
//        });

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
