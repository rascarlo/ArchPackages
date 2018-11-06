package com.rascarlo.arch.packages.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UtilSharedPreferences {


    public static void setSharedPreferenceBoolean(Context context, String string, boolean b) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(string, b);
        editor.apply();
    }

    public static boolean getSharedPreferenceBoolean(Context context, String sharedPreference, boolean defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(sharedPreference, defaultValue);
    }

}
