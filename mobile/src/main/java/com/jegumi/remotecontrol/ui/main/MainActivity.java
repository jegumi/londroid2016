package com.jegumi.remotecontrol.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.jegumi.remotecontrol.R;
import com.jegumi.remotecontrol.RemoteControlApplication;
import com.jegumi.remotecontrol.ui.events.ButtonFeedbackEvent;
import com.jegumi.remotecontrol.ui.helpers.RemoteHelper;
import com.jegumi.remotecontrol.ui.settings.RemoteSettingsActivity;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Bus mBus;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFields();
        mBus = RemoteControlApplication.getBus();
        mHandler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBus.unregister(this);
    }

    public void initFields() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.up).setOnClickListener(this);
        findViewById(R.id.left).setOnClickListener(this);
        findViewById(R.id.select).setOnClickListener(this);
        findViewById(R.id.right).setOnClickListener(this);
        findViewById(R.id.down).setOnClickListener(this);
        findViewById(R.id.launch).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            Intent intent = new Intent(this, RemoteSettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onReceiveCommandFromWearable(ButtonFeedbackEvent event) {
        final View view = findViewById(RemoteHelper.getResIdForCommand(event.command));
        view.setPressed(true);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setPressed(false);
            }
        }, 100);
    }

    @Override
    public void onClick(View v) {
        RemoteHelper.sendMessageToBox(this, v.getId());
    }
}
