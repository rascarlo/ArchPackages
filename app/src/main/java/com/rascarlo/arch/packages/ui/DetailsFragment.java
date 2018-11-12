package com.rascarlo.arch.packages.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
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

import java.util.List;

public class DetailsFragment extends BottomSheetDialogFragment {

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

    @Override
    public int getTheme() {
        return R.style.AppTheme_BottomSheet;
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
                bindDetailsViewModel(details);
            }
        });
        filesViewModel.getFilesLiveData().observe(this, files -> {
            if (files != null && fragmentDetailsBinding != null) {
                bindFilesViewModel(files);
            }
        });
    }

    private void bindDetailsViewModel(Details details) {
        if (fragmentDetailsBinding != null && details != null) {
            // compressed size
            bindCompressedSize(details);
            // installed size
            bindInstalledSize(details);
            // dependencies
            bindDepends(details);
            // make dependencies
            bindMakeDepends(details);
            // check dependencies
            bindCheckDepends(details);
            // opt dependencies
            bindOptDepends(details);
            // conflicts
            bindConflicts(details);
            // provides
            bindProvides(details);
            // replaces
            bindReplaces(details);
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
            fragmentDetailsBinding.fragmentDetailsBodyLayout.fragmentDetailsTextViewCompressedSize
                    .setText(String.format(getString(R.string.formatted_compressed_size),
                            details.compressedSize,
                            ArchPackagesStringConverters.convertBytesToMb(context, details.compressedSize)));
        }
    }

    private void bindInstalledSize(Details details) {
        if (details.installedSize != null && !TextUtils.isEmpty(details.installedSize)) {
            fragmentDetailsBinding.fragmentDetailsBodyLayout.fragmentDetailsTextViewInstalledSize
                    .setText(String.format(getString(R.string.formatted_installed_size),
                            details.installedSize,
                            ArchPackagesStringConverters.convertBytesToMb(context, details.installedSize)));
        }
    }

    private void bindDepends(Details details) {
        populateRecyclerView(fragmentDetailsBinding.fragmentDetailsDependenciesLayout.fragmentDetailsDependenciesRecyclerView, details.depends);
    }

    private void bindMakeDepends(Details details) {
        populateRecyclerView(fragmentDetailsBinding.fragmentDetailsMakeDependenciesLayout.fragmentDetailsMakeDependenciesRecyclerView, details.makedepends);
    }

    private void bindCheckDepends(Details details) {
        populateRecyclerView(fragmentDetailsBinding.fragmentDetailsCheckDependenciesLayout.fragmentDetailsCheckDependenciesRecyclerView, details.checkdepends);
    }

    private void bindOptDepends(Details details) {
        populateRecyclerView(fragmentDetailsBinding.fragmentDetailsOptDependenciesLayout.fragmentDetailsOptDependenciesRecyclerView, details.optdepends);
    }

    private void bindConflicts(Details details) {
        populateRecyclerView(fragmentDetailsBinding.fragmentDetailsConflictsLayout.fragmentDetailsConflictsRecyclerView, details.conflicts);
    }

    private void bindProvides(Details details) {
        populateRecyclerView(fragmentDetailsBinding.fragmentDetailsProvidesLayout.fragmentDetailsProvidesRecyclerView, details.provides);
    }

    private void bindReplaces(Details details) {
        populateRecyclerView(fragmentDetailsBinding.fragmentDetailsReplacesLayout.fragmentDetailsReplacesRecyclerView, details.replaces);
    }

    private void bindFiles(Files files) {
        populateRecyclerView(fragmentDetailsBinding.fragmentDetailsFilesLayout.fragmentDetailsFilesRecyclerView, files.files);
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
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    outRect.top = (int) (2 * Resources.getSystem().getDisplayMetrics().density);
                    outRect.bottom = (int) (2 * Resources.getSystem().getDisplayMetrics().density);
                }
            });
        }
    }
}
