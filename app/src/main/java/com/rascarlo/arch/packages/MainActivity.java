package com.rascarlo.arch.packages;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rascarlo.arch.packages.api.model.Result;
import com.rascarlo.arch.packages.callbacks.PackageSearchFragmentCallback;
import com.rascarlo.arch.packages.callbacks.ResultsFragmentCallback;
import com.rascarlo.arch.packages.ui.DetailsFragment;
import com.rascarlo.arch.packages.ui.ResultsFragment;
import com.rascarlo.arch.packages.ui.SearchFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PackageSearchFragmentCallback, ResultsFragmentCallback {

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
    public void onSearchFragmentCallbackFabClicked(int keywordsParameter,
                                                   String keywords,
                                                   ArrayList<String> listRepo,
                                                   ArrayList<String> listArch,
                                                   ArrayList<String> listFlagged) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ResultsFragment resultsFragment = ResultsFragment.newInstance(
                keywordsParameter,
                keywords,
                listRepo,
                listArch,
                listFlagged);
        fragmentTransaction.replace(R.id.content_main_fragment_container, resultsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onResultClicked(Result result) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailsFragment detailsFragment = DetailsFragment.newInstance(
                result.getRepo(),
                result.getArch(),
                result.getPkgname());
        detailsFragment.show(fragmentTransaction, detailsFragment.getTag());
    }
}
