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

package com.rascarlo.arch.packages.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rascarlo.arch.packages.adapters.ResultAdapter;
import com.rascarlo.arch.packages.api.model.Result;
import com.rascarlo.arch.packages.callbacks.ResultAdapterCallback;
import com.rascarlo.arch.packages.callbacks.ResultFragmentCallback;
import com.rascarlo.arch.packages.databinding.FragmentResultBinding;
import com.rascarlo.arch.packages.viewmodel.PackagesViewModel;
import com.rascarlo.arch.packages.viewmodel.PackagesViewModelFactory;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ResultFragment extends Fragment implements ResultAdapterCallback {

    private static final String LOG_TAG = ResultFragment.class.getSimpleName();
    private static final String BUNDLE_KEYWORDS_PARAMETER = "bundle_keywords_parameter";
    private static final String BUNDLE_KEYWORDS = "bundle_keywords";
    private static final String BUNDLE_LIST_REPO = "bundle_list_repo";
    private static final String BUNDLE_LIST_ARCH = "bundle_list_arch";
    private static final String BUNDLE_STRING_FLAGGED = "bundle_string_flagged";
    private Context context;
    private int bundleKeywordsParameter;
    private String bundleKeywords;
    private List<String> bundleListRepo;
    private List<String> bundleListArch;
    private String bundleStringFlagged;
    private ResultFragmentCallback resultFragmentCallback;
    private FragmentResultBinding fragmentResultBinding;

    public ResultFragment() {
    }

    public static ResultFragment newInstance(int keywordsParameter,
                                             String keywords,
                                             ArrayList<String> listRepo,
                                             ArrayList<String> listArch,
                                             String flagged) {
        ResultFragment resultFragment = new ResultFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEYWORDS_PARAMETER, keywordsParameter);
        bundle.putString(BUNDLE_KEYWORDS, keywords);
        bundle.putStringArrayList(BUNDLE_LIST_REPO, listRepo);
        bundle.putStringArrayList(BUNDLE_LIST_ARCH, listArch);
        bundle.putString(BUNDLE_STRING_FLAGGED, flagged);
        resultFragment.setArguments(bundle);
        return resultFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        setRetainInstance(true);
        if (getArguments() != null) {
            bundleKeywordsParameter = getArguments().getInt(BUNDLE_KEYWORDS_PARAMETER);
            bundleKeywords = getArguments().getString(BUNDLE_KEYWORDS);
            bundleListRepo = getArguments().getStringArrayList(BUNDLE_LIST_REPO);
            bundleListArch = getArguments().getStringArrayList(BUNDLE_LIST_ARCH);
            bundleStringFlagged = getArguments().getString(BUNDLE_STRING_FLAGGED);
        }
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof ResultFragmentCallback) {
            resultFragmentCallback = (ResultFragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ResultFragmentCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        resultFragmentCallback = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentResultBinding = FragmentResultBinding.inflate(inflater, container, false);
        return fragmentResultBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (fragmentResultBinding != null) {
            PackagesViewModelFactory factory = new PackagesViewModelFactory(bundleKeywordsParameter,
                    bundleKeywords,
                    bundleListRepo,
                    bundleListArch,
                    bundleStringFlagged);
            PackagesViewModel packagesViewModel = new ViewModelProvider(this, factory).get(PackagesViewModel.class);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            RecyclerView recyclerView = fragmentResultBinding.fragmentResultRecyclerView;
            ProgressBar progressBar = fragmentResultBinding.fragmentResultProgressBar;
            ResultAdapter resultAdapter = new ResultAdapter(this);
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            packagesViewModel.getPagedListLiveData().observe(getViewLifecycleOwner(), results -> {
                if (results != null) {
                    resultAdapter.submitList(results);
                }
                progressBar.setVisibility(View.GONE);
            });
            recyclerView.setAdapter(resultAdapter);
        }
    }

    @Override
    public void onResultAdapterCallbackOnResultClicked(Result result) {
        if (resultFragmentCallback != null) {
            resultFragmentCallback.onResultFragmentCallbackOnResultClicked(result);
        }
    }
}