package com.jegumi.remotecontrol;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {

    private MainActivityPresenter mMainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainActivityPresenter = new MainActivityPresenter(this);
        initFields();
    }

    private void initFields() {
        WatchViewStub watchViewStub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        watchViewStub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                stub.findViewById(R.id.backButton).setOnClickListener(MainActivity.this);
                stub.findViewById(R.id.up).setOnClickListener(MainActivity.this);
                stub.findViewById(R.id.left).setOnClickListener(MainActivity.this);
                stub.findViewById(R.id.select).setOnClickListener(MainActivity.this);
                stub.findViewById(R.id.right).setOnClickListener(MainActivity.this);
                stub.findViewById(R.id.bottom_left).setOnClickListener(MainActivity.this);
                stub.findViewById(R.id.down).setOnClickListener(MainActivity.this);
                stub.findViewById(R.id.bottom_left).setOnClickListener(MainActivity.this);
                stub.findViewById(R.id.launch).setOnClickListener(MainActivity.this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        mMainActivityPresenter.sendMessageToDevice(v.getId());
    }
}
