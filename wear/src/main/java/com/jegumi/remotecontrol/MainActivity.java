package com.jegumi.remotecontrol;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final int CONNECTION_TIME_OUT_MS = 3000;

    private String mNodeId;
    private GoogleApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WatchViewStub watchViewStub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        watchViewStub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                stub.findViewById(R.id.backButton).setOnClickListener(MainActivity.this);
                stub.findViewById(R.id.up).setOnClickListener(MainActivity.this);
                stub.findViewById(R.id.left).setOnClickListener(MainActivity.this);
                stub.findViewById(R.id.select).setOnClickListener(MainActivity.this);
                stub.findViewById(R.id.right).setOnClickListener(MainActivity.this);
                stub.findViewById(R.id.bottomleft).setOnClickListener(MainActivity.this);
                stub.findViewById(R.id.down).setOnClickListener(MainActivity.this);
                stub.findViewById(R.id.bottomleft).setOnClickListener(MainActivity.this);
                stub.findViewById(R.id.launch).setOnClickListener(MainActivity.this);
            }
        });

        setUpCommunication();
    }

    private void setUpCommunication() {
        mClient = new GoogleApiClient.Builder(this).addApi(Wearable.API).build();
        retrieveDeviceNode();
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

    private void sendMessageToDevice(final int message) {
        if (mNodeId != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mClient.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);
                    Wearable.MessageApi.sendMessage(mClient, mNodeId, String.valueOf(message), null);
                    mClient.disconnect();
                }
            }).start();
        }
    }

    @Override
    public void onClick(View v) {
        sendMessageToDevice(getCodeForButton(v.getId()));
    }

    private int getCodeForButton(int resId) {
        switch (resId) {
            case R.id.backButton:
                return 5;
            case R.id.up:
                return 0;
            case R.id.launch:
                return 6;
            case R.id.left:
                return 2;
            case R.id.select:
                return 4;
            case R.id.right:
                return 3;
            case R.id.down:
                return 1;
            default:
                return 6;
        }
    }
}
