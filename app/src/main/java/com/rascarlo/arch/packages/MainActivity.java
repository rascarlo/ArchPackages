package com.rascarlo.arch.packages;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.rascarlo.arch.packages.api.model.Result;
import com.rascarlo.arch.packages.callbacks.DetailsFragmentCallback;
import com.rascarlo.arch.packages.callbacks.ResultsFragmentCallback;
import com.rascarlo.arch.packages.callbacks.SearchFragmentCallback;
import com.rascarlo.arch.packages.ui.DetailsFragment;
import com.rascarlo.arch.packages.ui.ResultFragment;
import com.rascarlo.arch.packages.ui.SearchFragment;
import com.rascarlo.arch.packages.util.ArchPackagesConstants;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchFragmentCallback, ResultsFragmentCallback, DetailsFragmentCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchFragmentCallbackOnFabClicked(int keywordsParameter,
                                                     String keywords,
                                                     ArrayList<String> listRepo,
                                                     ArrayList<String> listArch,
                                                     String flagged) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ResultFragment resultsFragment = ResultFragment.newInstance(
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
        if (result != null) {
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
    }

    @Override
    public void onDetailsFragmentCallbackOnPackageClicked(String packageName) {
        if (packageName != null && !TextUtils.isEmpty(packageName)) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ResultFragment resultsFragment = ResultFragment.newInstance(
                    ArchPackagesConstants.SEARCH_KEYWORDS_PARAMETER_EXACT_NAME,
                    packageName,
                    null,
                    null,
                    null);
            fragmentTransaction.add(R.id.content_main_fragment_container, resultsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
