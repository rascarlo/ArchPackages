package com.rascarlo.arch.packages;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.rascarlo.arch.packages.api.model.Result;
import com.rascarlo.arch.packages.callbacks.ResultsFragmentCallback;
import com.rascarlo.arch.packages.callbacks.SearchFragmentCallback;
import com.rascarlo.arch.packages.ui.DetailsFragment;
import com.rascarlo.arch.packages.ui.ResultsFragment;
import com.rascarlo.arch.packages.ui.SearchFragment;
import com.rascarlo.arch.packages.ui.SettingsFragment;
import com.rascarlo.arch.packages.util.ArchPackagesSharedPreferences;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements SearchFragmentCallback,
        ResultsFragmentCallback,
        SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAppTheme(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState != null) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SearchFragment searchFragment = new SearchFragment();
        fragmentTransaction.add(R.id.content_main_fragment_container, searchFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(MainActivity.this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(MainActivity.this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (getAppTheme()) {
            case R.style.AppThemeDark:
                menu.findItem(R.id.menu_main_action_theme_dark).setChecked(true);
                break;
            case R.style.AppThemeBlack:
                menu.findItem(R.id.menu_main_action_theme_black).setChecked(true);
                break;
            case R.style.AppThemeLight:
                menu.findItem(R.id.menu_main_action_theme_light).setChecked(true);
                break;
            default:
                menu.findItem(R.id.menu_main_action_theme_dark).setChecked(true);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_action_theme_dark:
                ArchPackagesSharedPreferences.setSharedPreferenceString(MainActivity.this,
                        getString(R.string.key_theme),
                        getString(R.string.key_theme_dark));
                break;
            case R.id.menu_main_action_theme_black:
                ArchPackagesSharedPreferences.setSharedPreferenceString(MainActivity.this,
                        getString(R.string.key_theme),
                        getString(R.string.key_theme_black));
                break;
            case R.id.menu_main_action_theme_light:
                ArchPackagesSharedPreferences.setSharedPreferenceString(MainActivity.this,
                        getString(R.string.key_theme),
                        getString(R.string.key_theme_light));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchFragmentCallbackFabClicked(int keywordsParameter,
                                                   String keywords,
                                                   ArrayList<String> listRepo,
                                                   ArrayList<String> listArch,
                                                   String flagged) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ResultsFragment resultsFragment = ResultsFragment.newInstance(
                keywordsParameter,
                keywords,
                listRepo,
                listArch,
                flagged);
        fragmentTransaction.replace(R.id.content_main_fragment_container, resultsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onSearchFragmentCallbackMenuActionSettingsClicked() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SettingsFragment settingsFragment = new SettingsFragment();
        fragmentTransaction.replace(R.id.content_main_fragment_container, settingsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onResultFragmentCallbackResultClicked(Result result) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailsFragment detailsFragment = DetailsFragment.newInstance(
                result.getRepo(),
                result.getArch(),
                result.getPkgname());
        fragmentTransaction.add(R.id.content_main_fragment_container, detailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key != null && !TextUtils.isEmpty(key)) {
            if (TextUtils.equals(getString(R.string.key_theme), key)) {
                setAppTheme(true);
            }
        }
    }

    private void setAppTheme(boolean recreate) {
        setTheme(getAppTheme());
        if (recreate) recreate();
    }

    private int getAppTheme() {
        String sharedPreferenceTheme = ArchPackagesSharedPreferences.getSharedPreferenceString(MainActivity.this,
                getString(R.string.key_theme),
                getString(R.string.key_theme_default_value));
        if (TextUtils.equals(getString(R.string.key_theme_dark), sharedPreferenceTheme)) {
            return R.style.AppThemeDark;
        } else if (TextUtils.equals(getString(R.string.key_theme_black), sharedPreferenceTheme)) {
            return R.style.AppThemeBlack;
        } else if (TextUtils.equals(getString(R.string.key_theme_light), sharedPreferenceTheme)) {
            return R.style.AppThemeLight;
        } else {
            return R.style.AppThemeDark;
        }
    }
}
