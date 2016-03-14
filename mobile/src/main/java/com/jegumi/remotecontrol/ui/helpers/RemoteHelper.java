package com.jegumi.remotecontrol.ui.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jegumi.remotecontrol.ui.settings.RemoteSettingsActivity;

public class RemoteHelper {

    private static final String TAG = RemoteHelper.class.getSimpleName();
    private static final String KEYPRESS_PATH = "keypress";

    public static String getRemoteUrl(Context context, String command) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = sharedPreferences.getString(RemoteSettingsActivity.IP_KEY, RemoteSettingsActivity.IP_DEFAULT_VALUE);
        String port = sharedPreferences.getString(RemoteSettingsActivity.PORT_KEY, RemoteSettingsActivity.PORT_DEFAULT_VALUE);

        Uri.Builder builder = Uri.parse("http://" + ip + ":" + port).buildUpon();
        builder.appendPath(KEYPRESS_PATH);
        builder.appendPath(command);

        return builder.toString();
    }

    public static void sendMessageToBox(Context context, String command) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getRemoteUrl(context, command),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "onResponse: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        });
        queue.add(stringRequest);
    }
}