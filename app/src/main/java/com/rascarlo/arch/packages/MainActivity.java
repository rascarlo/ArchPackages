package com.rascarlo.arch.packages;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rascarlo.arch.packages.api.model.Result;
import com.rascarlo.arch.packages.callbacks.ResultsFragmentCallback;
import com.rascarlo.arch.packages.callbacks.SearchFragmentCallback;
import com.rascarlo.arch.packages.ui.DetailsFragment;
import com.rascarlo.arch.packages.ui.ResultsFragment;
import com.rascarlo.arch.packages.ui.SearchFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchFragmentCallback, ResultsFragmentCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_YES:
                menu.findItem(R.id.action_main_night_mode_night).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                menu.findItem(R.id.action_main_night_mode_day).setChecked(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main_night_mode_day:
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.action_main_night_mode_night:
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
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
    public void onResultFragmentCallbackResultClicked(Result result) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailsFragment detailsFragment = DetailsFragment.newInstance(
                AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? R.style.AppTheme_BottomSheet_Dark : R.style.AppTheme_BottomSheet_Light,
                result.getRepo(),
                result.getArch(),
                result.getPkgname());
        detailsFragment.show(fragmentTransaction, detailsFragment.getTag());
    }

    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);
        recreate();
    }
}
