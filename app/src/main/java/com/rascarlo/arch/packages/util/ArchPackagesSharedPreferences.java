package com.rascarlo.arch.packages.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ArchPackagesSharedPreferences {

    public static void setSharedPreferenceString(Context context, String sharedPreferenceString, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(sharedPreferenceString, value);
        editor.apply();
    }

    public static String getSharedPreferenceString(Context context, String sharedPreferenceString, String defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(sharedPreferenceString, defaultValue);
    }

    public static void setSharedPreferenceBoolean(Context context, String sharedPreferenceString, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(sharedPreferenceString, value);
        editor.apply();
    }

    public static boolean getSharedPreferenceBoolean(Context context, String sharedPreference, boolean defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(sharedPreference, defaultValue);
    }

}
