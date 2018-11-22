package com.rascarlo.arch.packages.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class DetailsViewModelFactory implements ViewModelProvider.Factory {
    private final String repo;
    private final String arch;
    private final String packageName;

    public DetailsViewModelFactory(String repo,
                                   String arch,
                                   String packageName) {
        this.repo = repo;
        this.arch = arch;
        this.packageName = packageName;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailsViewModel.class)) {
            return (T) new DetailsViewModel(repo, arch, packageName);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}