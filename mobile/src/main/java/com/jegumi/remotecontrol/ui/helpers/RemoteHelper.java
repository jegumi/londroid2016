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
import com.jegumi.remotecontrol.R;
import com.jegumi.remotecontrol.ui.settings.RemoteSettingsActivity;

public class RemoteHelper {

    private static final String TAG = RemoteHelper.class.getSimpleName();
    private static final String KEYPRESS_PATH = "keypress";

    private static final String UP_BUTTON_COMMAND = "up";
    private static final String DOWN_BUTTON_COMMAND = "down";
    private static final String LEFT_BUTTON_COMMAND = "left";
    private static final String RIGHT_BUTTON_COMMAND = "right";
    private static final String SELECT_BUTTON_COMMAND = "select";
    private static final String BACK_BUTTON_COMMAND = "back";
    private static final String LAUNCH_BUTTON_COMMAND = "home";

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
        String url = getRemoteUrl(context, command);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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

    public static void sendMessageToBox(Context context, int resId) {
        sendMessageToBox(context, getCommandForButton(resId));
    }

    public static int getResIdForCommand(String command) {
        if (BACK_BUTTON_COMMAND.equals(command)) {
            return R.id.back;
        }
        if (UP_BUTTON_COMMAND.equals(command)) {
            return R.id.up;
        }
        if (LEFT_BUTTON_COMMAND.equals(command)) {
            return R.id.left;
        }
        if (SELECT_BUTTON_COMMAND.equals(command)) {
            return R.id.select;
        }
        if (RIGHT_BUTTON_COMMAND.equals(command)) {
            return R.id.right;
        }
        if (DOWN_BUTTON_COMMAND.equals(command)) {
            return R.id.down;
        }

        return R.id.launch;
    }

    private static String getCommandForButton(int resId) {
        switch (resId) {
            case R.id.back:
                return BACK_BUTTON_COMMAND;
            case R.id.up:
                return UP_BUTTON_COMMAND;
            case R.id.left:
                return LEFT_BUTTON_COMMAND;
            case R.id.select:
                return SELECT_BUTTON_COMMAND;
            case R.id.right:
                return RIGHT_BUTTON_COMMAND;
            case R.id.down:
                return DOWN_BUTTON_COMMAND;
            default:
                return LAUNCH_BUTTON_COMMAND;
        }
    }
}