package com.jegumi.remotecontrol;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivityPresenter {

    public static final int CONNECTION_TIME_OUT_MS = 2000;
    private static final String UP_BUTTON_CODE = "up";
    private static final String DOWN_BUTTON_CODE = "down";
    private static final String LEFT_BUTTON_CODE = "left";
    private static final String RIGHT_BUTTON_CODE = "right";
    private static final String SELECT_BUTTON_CODE = "select";
    private static final String BACK_BUTTON_CODE = "back";
    private static final String LAUNCH_BUTTON_CODE = "home";

    private Context mContext;
    private String mNodeId;
    private GoogleApiClient mClient;

    public MainActivityPresenter(Context context) {
        mContext = context;
        setUpCommunication();
    }

    private void setUpCommunication() {
        mClient = new GoogleApiClient.Builder(mContext).addApi(Wearable.API).build();
        retrieveDeviceNode();
    }

    public void sendMessageToDevice(final int command) {
        if (mNodeId != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mClient.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);
                    Wearable.MessageApi.sendMessage(mClient, mNodeId, getCommandForButton(command), null);
                    mClient.disconnect();
                }
            }).start();
        }
    }

    private void retrieveDeviceNode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mClient.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);
                NodeApi.GetConnectedNodesResult result = Wearable.NodeApi.getConnectedNodes(mClient).await();
                List<Node> nodes = result.getNodes();
                if (!nodes.isEmpty()) {
                    mNodeId = nodes.get(0).getId();
                }
                mClient.disconnect();
            }
        }).start();
    }

    private String getCommandForButton(int resId) {
        switch (resId) {
            case R.id.backButton:
                return BACK_BUTTON_CODE;
            case R.id.up:
                return UP_BUTTON_CODE;
            case R.id.left:
                return LEFT_BUTTON_CODE;
            case R.id.select:
                return SELECT_BUTTON_CODE;
            case R.id.right:
                return RIGHT_BUTTON_CODE;
            case R.id.down:
                return DOWN_BUTTON_CODE;
            default:
                return LAUNCH_BUTTON_CODE;
        }
    }
}
