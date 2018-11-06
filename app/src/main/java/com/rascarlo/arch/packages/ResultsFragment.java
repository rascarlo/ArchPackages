package com.rascarlo.arch.packages;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rascarlo.arch.packages.adapters.ResultsAdapter;
import com.rascarlo.arch.packages.api.model.Packages;
import com.rascarlo.arch.packages.viewmodel.PackagesViewModel;

import java.util.ArrayList;
import java.util.List;

public class ResultsFragment extends Fragment {

    private static final String LOG_TAG = ResultsFragment.class.getSimpleName();
    private static final String BUNDLE_KEYWORDS_PARAMETER = "bundle_keywords_parameter";
    private static final String BUNDLE_KEYWORDS = "bundle_keywords";
    private static final String BUNDLE_LIST_REPO = "bundle_list_repo";
    private static final String BUNDLE_LIST_ARCH = "bundle_list_arch";
    private static final String BUNDLE_LIST_FLAGGED = "bundle_list_flagged";
    private Context context;
    private int bundleKeywordsParameter;
    private String bundleKeywords;
    private List<String> bundleListRepo;
    private List<String> bundleListArch;
    private List<String> bundleListFlagged;

    public ResultsFragment() {
    }

    public static ResultsFragment newInstance(int keywordsParameter,
                                              String keywords,
                                              ArrayList<String> listRepo,
                                              ArrayList<String> listArch,
                                              ArrayList<String> listFlagged) {
        ResultsFragment resultsFragment = new ResultsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEYWORDS_PARAMETER, keywordsParameter);
        bundle.putString(BUNDLE_KEYWORDS, keywords);
        bundle.putStringArrayList(BUNDLE_LIST_REPO, listRepo);
        bundle.putStringArrayList(BUNDLE_LIST_ARCH, listArch);
        bundle.putStringArrayList(BUNDLE_LIST_FLAGGED, listFlagged);
        resultsFragment.setArguments(bundle);
        return resultsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bundleKeywordsParameter = getArguments().getInt(BUNDLE_KEYWORDS_PARAMETER);
            bundleKeywords = getArguments().getString(BUNDLE_KEYWORDS);
            bundleListRepo = getArguments().getStringArrayList(BUNDLE_LIST_REPO);
            bundleListArch = getArguments().getStringArrayList(BUNDLE_LIST_ARCH);
            bundleListFlagged = getArguments().getStringArrayList(BUNDLE_LIST_FLAGGED);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_results, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        final RecyclerView recyclerView = rootView.findViewById(R.id.fragment_results_recycler_view);
        final ProgressBar progressBar = rootView.findViewById(R.id.fragment_results_progress_bar);
        final ResultsAdapter resultsAdapter = new ResultsAdapter(context);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(resultsAdapter);
        PackagesViewModel packagesViewModel = ViewModelProviders.of(this).get(PackagesViewModel.class);
        if (savedInstanceState == null) {
            packagesViewModel.init(bundleKeywordsParameter,
                    bundleKeywords,
                    bundleListRepo,
                    bundleListArch,
                    bundleListFlagged,
                    1);
        }
        packagesViewModel.getArchPackagesLiveData().observe(this, new Observer<Packages>() {
            @Override
            public void onChanged(@Nullable Packages packages) {
                if (packages != null) {
                    resultsAdapter.addResults(packages.getResults());
                }
                progressBar.setVisibility(View.GONE);
            }
        });
        return rootView;
    }
}