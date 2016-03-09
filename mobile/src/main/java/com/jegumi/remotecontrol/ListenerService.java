package com.jegumi.remotecontrol;

import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.jegumi.remotecontrol.ui.helpers.RemoteHelper;

public class ListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        String messageStr = messageEvent.getPath();
        int code = Integer.valueOf(messageStr);

        Toast.makeText(this, messageStr, Toast.LENGTH_LONG).show();
        RemoteHelper.sendMessageToBox(this, getCommand(code));
    }

    private RemoteHelper.RemoteCommands getCommand(int code) {
        switch (code) {
            case 5:
                return RemoteHelper.RemoteCommands.back;
            case 0:
                return RemoteHelper.RemoteCommands.up;
            case 2:
                return RemoteHelper.RemoteCommands.left;
            case 4:
                return RemoteHelper.RemoteCommands.select;
            case 3:
                return RemoteHelper.RemoteCommands.right;
            case 1:
                return RemoteHelper.RemoteCommands.down;
            default:
                return RemoteHelper.RemoteCommands.launch;
        }
    }
}