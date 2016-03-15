package com.jegumi.remotecontrol;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.jegumi.remotecontrol.ui.events.ButtonFeedbackEvent;
import com.jegumi.remotecontrol.ui.helpers.RemoteHelper;
import com.squareup.otto.Bus;

public class ListenerService extends WearableListenerService {

    private MainThreadBus mBus;

    public ListenerService() {
        mBus = RemoteControlApplication.getBus();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        String message = messageEvent.getPath();

        mBus.post(new ButtonFeedbackEvent(message));
        RemoteHelper.sendMessageToBox(this, message);
    }
}