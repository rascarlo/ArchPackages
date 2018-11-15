package com.rascarlo.arch.packages.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rascarlo.arch.packages.R;
import com.rascarlo.arch.packages.adapters.StringListAdapter;
import com.rascarlo.arch.packages.api.model.Details;
import com.rascarlo.arch.packages.api.model.Files;
import com.rascarlo.arch.packages.databinding.FragmentDetailsBinding;
import com.rascarlo.arch.packages.util.ArchPackagesStringConverters;
import com.rascarlo.arch.packages.viewmodel.DetailsViewModel;
import com.rascarlo.arch.packages.viewmodel.FilesViewModel;

import java.util.HashMap;
import java.util.List;

public class DetailsFragment extends Fragment {

    private static final String BUNDLE_REPO = "bundle_repo";
    private static final String BUNDLE_ARCH = "bundle_arch";
    private static final String BUNDLE_PKGNAME = "bundle_pkgname";
    private String bundleRepo;
    private String bundleArch;
    private String bundlePkgname;
    private Context context;
    private FragmentDetailsBinding fragmentDetailsBinding;

    public DetailsFragment() {
    }

    public static DetailsFragment newInstance(String repo,
                                              String arch,
                                              String pkgname) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_REPO, repo);
        bundle.putString(BUNDLE_ARCH, arch);
        bundle.putString(BUNDLE_PKGNAME, pkgname);
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bundleRepo = getArguments().getString(BUNDLE_REPO);
            bundleArch = getArguments().getString(BUNDLE_ARCH);
            bundlePkgname = getArguments().getString(BUNDLE_PKGNAME);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false);
        return fragmentDetailsBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DetailsViewModel detailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        FilesViewModel filesViewModel = ViewModelProviders.of(this).get(FilesViewModel.class);
        if (savedInstanceState == null) {
            detailsViewModel.init(bundleRepo, bundleArch, bundlePkgname);
            filesViewModel.init(bundleRepo, bundleArch, bundlePkgname);
        }
        detailsViewModel.getDetailsLiveData().observe(this, details -> {
            if (details != null && fragmentDetailsBinding != null) {
                fragmentDetailsBinding.setDetails(details);
                fragmentDetailsBinding.executePendingBindings();
                bindDetailsViewModel(details);
            }
        });
        filesViewModel.getFilesLiveData().observe(this, files -> {
            if (files != null && fragmentDetailsBinding != null) {
                fragmentDetailsBinding.detailsFilesLayout.setFiles(files);
                bindFilesViewModel(files);
            }
        });
    }

    private void bindDetailsViewModel(Details details) {
        if (fragmentDetailsBinding != null && details != null) {
            HashMap<RecyclerView, List<String>> hashMap = new HashMap<>();
            // compressed size
            bindCompressedSize(details);
            // installed size
            bindInstalledSize(details);
            // dependencies
            hashMap.put(fragmentDetailsBinding.detailsDependenciesLayout.detailsDependenciesRecyclerView, details.depends);
            // make dependencies
            hashMap.put(fragmentDetailsBinding.detailsMakeDependenciesLayout.detailsMakeDependenciesRecyclerView, details.makedepends);
            // check dependencies
            hashMap.put(fragmentDetailsBinding.detailsCheckDependenciesLayout.detailsCheckDependenciesRecyclerView, details.checkdepends);
            // opt dependencies
            hashMap.put(fragmentDetailsBinding.detailsOptDependenciesLayout.detailsOptDependenciesRecyclerView, details.optdepends);
            // conflicts
            hashMap.put(fragmentDetailsBinding.detailsConflictsLayout.detailsConflictsRecyclerView, details.conflicts);
            // provides
            hashMap.put(fragmentDetailsBinding.detailsProvidesLayout.detailsProvidesRecyclerView, details.provides);
            // replaces
            hashMap.put(fragmentDetailsBinding.detailsReplacesLayout.detailsReplacesRecyclerView, details.replaces);
            for (HashMap.Entry<RecyclerView, List<String>> entry : hashMap.entrySet()) {
                populateRecyclerView(entry.getKey(), entry.getValue());
            }
        }
    }

    private void bindFilesViewModel(Files files) {
        if (fragmentDetailsBinding != null && files != null) {
            // files
            bindFiles(files);
        }
    }

    private void bindCompressedSize(Details details) {
        if (details.compressedSize != null && !TextUtils.isEmpty(details.compressedSize)) {
            fragmentDetailsBinding.detailsBodyLayout.detailsTextViewCompressedSize
                    .setText(String.format(getString(R.string.formatted_compressed_size),
                            details.compressedSize,
                            ArchPackagesStringConverters.convertBytesToMb(context, details.compressedSize)));
        }
    }

    private void bindInstalledSize(Details details) {
        if (details.installedSize != null && !TextUtils.isEmpty(details.installedSize)) {
            fragmentDetailsBinding.detailsBodyLayout.detailsTextViewInstalledSize
                    .setText(String.format(getString(R.string.formatted_installed_size),
                            details.installedSize,
                            ArchPackagesStringConverters.convertBytesToMb(context, details.installedSize)));
        }
    }

    private void bindFiles(Files files) {
        populateRecyclerView(fragmentDetailsBinding.detailsFilesLayout.detailsFilesRecyclerView, files.files);
    }

    private void populateRecyclerView(RecyclerView recyclerView, List<String> stringList) {
        if (stringList != null && !stringList.isEmpty()) {
            StringListAdapter stringListAdapter = new StringListAdapter();
            stringListAdapter.submitList(stringList);
            recyclerView.setAdapter(stringListAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        }
    }
}
