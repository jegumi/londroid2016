package com.jegumi.remotecontrol.ui.settings;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

import com.jegumi.remotecontrol.R;

public class RemoteSettingsActivity extends AppCompatActivity {

    public static final String IP_KEY = "ip";
    public static final String PORT_KEY = "port";

    public static final String IP_DEFAULT_VALUE = "192.168.0.1";
    public static final String PORT_DEFAULT_VALUE = "8060";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragment();
    }

    public void loadFragment() {
        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        PrefsFragment mPrefsFragment = new PrefsFragment();
        mFragmentTransaction.replace(android.R.id.content, mPrefsFragment);
        mFragmentTransaction.commit();
    }

    public static class PrefsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
