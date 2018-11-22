package com.rascarlo.arch.packages.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.List;

public class PackagesViewModelFactory implements ViewModelProvider.Factory {
    private final int keywordsParameter;
    private final String query;
    private final List<String> listRepo;
    private final List<String> listArch;
    private final String flagged;

    public PackagesViewModelFactory(int keywordsParameter,
                                    String query,
                                    List<String> listRepo,
                                    List<String> listArch,
                                    String flagged) {
        this.keywordsParameter = keywordsParameter;
        this.query = query;
        this.listRepo = listRepo;
        this.listArch = listArch;
        this.flagged = flagged;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PackagesViewModel.class)) {
            return (T) new PackagesViewModel(keywordsParameter, query, listRepo, listArch, flagged);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}