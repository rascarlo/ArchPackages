package com.rascarlo.arch.packages.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rascarlo.arch.packages.api.model.Details;
import com.rascarlo.arch.packages.databinding.DetailsFragmentBinding;
import com.rascarlo.arch.packages.viewmodel.DetailsViewModel;

public class DetailsFragment extends Fragment {

    private static final String BUNDLE_REPO = "bundle_repo";
    private static final String BUNDLE_ARCH = "bundle_arch";
    private static final String BUNDLE_PKGNAME = "bundle_pkgname";
    private String bundleRepo;
    private String bundleArch;
    private String bundlePkgname;
    private DetailsFragmentBinding detailsFragmentBinding;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        detailsFragmentBinding = DetailsFragmentBinding.inflate(inflater, container, false);
        return detailsFragmentBinding.getRoot();
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
                if (details != null && detailsFragmentBinding != null) {
                    detailsFragmentBinding.setDetails(details);
                }
            }
        });
    }
}
