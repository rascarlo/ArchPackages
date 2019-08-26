/*
 *     Copyright (C) 2018 rascarlo <rascarlo@gmail.com>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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

import com.rascarlo.arch.packages.api.model.Files;
import com.rascarlo.arch.packages.api.model.Result;
import com.rascarlo.arch.packages.callbacks.DetailsFragmentCallback;
import com.rascarlo.arch.packages.callbacks.ResultFragmentCallback;
import com.rascarlo.arch.packages.callbacks.SearchFragmentCallback;
import com.rascarlo.arch.packages.ui.DetailsFragment;
import com.rascarlo.arch.packages.ui.FilesFragment;
import com.rascarlo.arch.packages.ui.ResultFragment;
import com.rascarlo.arch.packages.ui.SearchFragment;
import com.rascarlo.arch.packages.ui.SettingsFragment;
import com.rascarlo.arch.packages.util.ArchPackagesConstants;
import com.rascarlo.arch.packages.util.ArchPackagesSharedPreferences;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements SearchFragmentCallback,
        ResultFragmentCallback,
        DetailsFragmentCallback,
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
        int appTheme = getAppTheme();
        if (appTheme == R.style.AppThemeDark) {
            menu.findItem(R.id.menu_main_action_theme_dark).setChecked(true);
        } else if (appTheme == R.style.AppThemeBlack) {
            menu.findItem(R.id.menu_main_action_theme_black).setChecked(true);
        } else if (appTheme == R.style.AppThemeLight) {
            menu.findItem(R.id.menu_main_action_theme_light).setChecked(true);
        } else {
            menu.findItem(R.id.menu_main_action_theme_dark).setChecked(true);
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

    /*
    listen for app theme shared preference change
    to apply new theme
    */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key != null && !TextUtils.isEmpty(key)) {
            if (TextUtils.equals(getString(R.string.key_theme), key)) {
                setAppTheme(true);
            }
        }
    }

    /**
     * callback from {@link SearchFragment}
     * inflate a new {@link ResultFragment}
     *
     * @param keywordsParameter: one of {@link ArchPackagesConstants}
     * @param keywords:          query
     * @param listRepo:          repo list
     * @param listArch:          arch list
     * @param flag:              flag
     */
    @Override
    public void onSearchFragmentCallbackOnFabClicked(int keywordsParameter,
                                                     String keywords,
                                                     ArrayList<String> listRepo,
                                                     ArrayList<String> listArch,
                                                     String flag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ResultFragment resultFragment = ResultFragment.newInstance(
                keywordsParameter,
                keywords,
                listRepo,
                listArch,
                flag);
        fragmentTransaction.add(R.id.content_main_fragment_container, resultFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * callback from {@link SearchFragment}
     * inflate the settings {@link SettingsFragment}
     * REPLACE the fragment, not add
     */
    @Override
    public void onSearchFragmentCallbackOnMenuActionSettingsClicked() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SettingsFragment settingsFragment = new SettingsFragment();
        fragmentTransaction.replace(R.id.content_main_fragment_container, settingsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * callback from {@link ResultFragment}
     * inflate a new {@link DetailsFragment}
     *
     * @param result: the result clicked
     */
    @Override
    public void onResultFragmentCallbackOnResultClicked(Result result) {
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

    /**
     * callback from {@link DetailsFragment}
     * inflate a new {@link ResultFragment}
     * with keywords parameter by exact name
     *
     * @param packageName: the dependency clicked
     */
    @Override
    public void onDetailsFragmentCallbackOnPackageClicked(String packageName) {
        if (packageName != null && !TextUtils.isEmpty(packageName)) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ResultFragment resultFragment = ResultFragment.newInstance(
                    ArchPackagesConstants.SEARCH_KEYWORDS_PARAMETER_EXACT_NAME,
                    packageName,
                    null,
                    null,
                    null);
            fragmentTransaction.add(R.id.content_main_fragment_container, resultFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    /**
     * callback from {@link DetailsFragment}
     * inflate a new {@link FilesFragment}
     *
     * @param files: the {@link Files} to bind dirs and files count
     */
    @Override
    public void onDetailsFragmentCallbackOnFilesClicked(Files files) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FilesFragment filesFragment = FilesFragment.newInstance(files);
        fragmentTransaction.add(R.id.content_main_fragment_container, filesFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
