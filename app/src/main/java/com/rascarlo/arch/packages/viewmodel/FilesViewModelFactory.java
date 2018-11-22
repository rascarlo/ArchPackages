package com.rascarlo.arch.packages.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class FilesViewModelFactory implements ViewModelProvider.Factory {

    private final String repo;
    private final String arch;
    private final String pkgname;

    public FilesViewModelFactory(String repo,
                                 String arch,
                                 String pkgname) {
        this.repo = repo;
        this.arch = arch;
        this.pkgname = pkgname;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FilesViewModel.class)) {
            return (T) new FilesViewModel(repo, arch, pkgname);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}