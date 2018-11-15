package com.rascarlo.arch.packages.ui;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.rascarlo.arch.packages.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.settings);
    }
}
