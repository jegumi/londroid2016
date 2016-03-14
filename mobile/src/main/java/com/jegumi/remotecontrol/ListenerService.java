package com.jegumi.remotecontrol;

import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.jegumi.remotecontrol.ui.helpers.RemoteHelper;

public class ListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        String message = messageEvent.getPath();

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        RemoteHelper.sendMessageToBox(this, message);
    }
}