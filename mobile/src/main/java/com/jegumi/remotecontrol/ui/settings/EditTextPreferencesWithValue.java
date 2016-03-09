package com.jegumi.remotecontrol.ui.settings;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class EditTextPreferencesWithValue extends EditTextPreference {

    public EditTextPreferencesWithValue(Context context) {
        super(context);
    }

    public EditTextPreferencesWithValue(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        this.setSummary(this.getText());
        return super.onCreateView(parent);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            this.setSummary(getText());
        }
    }
}