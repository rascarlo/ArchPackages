package com.rascarlo.arch.packages.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rascarlo.arch.packages.R;
import com.rascarlo.arch.packages.api.model.Details;
import com.rascarlo.arch.packages.databinding.FragmentDetailsBinding;
import com.rascarlo.arch.packages.util.UtilStringConverters;
import com.rascarlo.arch.packages.viewmodel.DetailsViewModel;

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
        if (savedInstanceState == null) {
            detailsViewModel.init(bundleRepo, bundleArch, bundlePkgname);
        }
        detailsViewModel.getDetailsLiveData().observe(this, new Observer<Details>() {
            @Override
            public void onChanged(@Nullable Details details) {
                if (details != null && fragmentDetailsBinding != null) {
                    fragmentDetailsBinding.setDetails(details);
                    bindViewModel(details);
                    Log.e("LOG", "REL:" + details.pkgrel);
                    Log.e("LOG", "REPLACES:" + details.replaces);
                    Log.e("LOG", "CONFLICTS:" + details.conflicts);
                    Log.e("LOG", "GROUPS:" + details.groups);
                }
            }
        });
    }

    private void bindViewModel(Details details) {
        // license
        if (details.licenses != null) {
            fragmentDetailsBinding.fragmentDetailsBodyLayout.fragmentDetailsTextViewLicense
                    .setText(String.format(getString(R.string.formatted_license),
                            UtilStringConverters.convertListToCommaSeparatedString(details.licenses)));
        }
        // maintainers
        if (details.maintainers != null) {
            fragmentDetailsBinding.fragmentDetailsBodyLayout.fragmentDetailsTextViewMaintainers
                    .setText(String.format(getString(R.string.formatted_maintainers),
                            UtilStringConverters.convertListToCommaSeparatedString(details.maintainers)));
        }
        // compressed size
        if (details.compressedSize != null && !TextUtils.isEmpty(details.compressedSize)) {
            fragmentDetailsBinding.fragmentDetailsBodyLayout.fragmentDetailsTextViewCompressedSize
                    .setText(String.format(getString(R.string.formatted_compressed_size),
                            details.compressedSize,
                            UtilStringConverters.convertBytesToMb(context, details.compressedSize)));
        }
        // installed size
        if (details.installedSize != null && !TextUtils.isEmpty(details.installedSize)) {
            fragmentDetailsBinding.fragmentDetailsBodyLayout.fragmentDetailsTextViewInstalledSize
                    .setText(String.format(getString(R.string.formatted_installed_size),
                            details.installedSize,
                            UtilStringConverters.convertBytesToMb(context, details.installedSize)));
        }
        // dependencies
        if (details.depends != null) {
            if (details.depends.size() > 0) {
                fragmentDetailsBinding.fragmentDetailsDependenciesLayout.fragmentDetailsTextViewDependencies
                        .setText(UtilStringConverters.convertListToNewLineSeparatedString(details.depends));
            } else {
                fragmentDetailsBinding.fragmentDetailsDependenciesLayout.fragmentDetailsTextViewDependencies.setText("-");
            }
        }
        // make dependencies
        if (details.makedepends != null) {
            if (details.makedepends.size() > 0) {
                fragmentDetailsBinding.fragmentDetailsMakeDependenciesLayout.fragmentDetailsTextViewMakeDependencies
                        .setText(UtilStringConverters.convertListToNewLineSeparatedString(details.makedepends));
            } else {
                fragmentDetailsBinding.fragmentDetailsMakeDependenciesLayout.fragmentDetailsTextViewMakeDependencies.setText("-");
            }
        }
        // check dependencies
        if (details.checkdepends != null) {
            if (details.checkdepends.size() > 0) {
                fragmentDetailsBinding.fragmentDetailsCheckDependenciesLayout.fragmentDetailsTextViewCheckDependencies
                        .setText(UtilStringConverters.convertListToNewLineSeparatedString(details.checkdepends));
            } else {
                fragmentDetailsBinding.fragmentDetailsCheckDependenciesLayout.fragmentDetailsTextViewCheckDependencies.setText("-");
            }
        }
        // opt dependencies
        if (details.optdepends != null) {
            if (details.optdepends.size() > 0) {
                fragmentDetailsBinding.fragmentDetailsOptDependenciesLayout.fragmentDetailsTextViewOptDependencies
                        .setText(UtilStringConverters.convertListToNewLineSeparatedString(details.optdepends));
            } else {
                fragmentDetailsBinding.fragmentDetailsOptDependenciesLayout.fragmentDetailsTextViewOptDependencies.setText("-");
            }
        }
    }
}
