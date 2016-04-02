package com.audbar.odre.loycards;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Audrius on 2016-04-02.
 */
public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL ="s.lt";

    public RegisterRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }
}
