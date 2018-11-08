package com.rascarlo.arch.packages.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.rascarlo.arch.packages.api.model.Details;
import com.rascarlo.arch.packages.data.ArchPackagesRepository;

public class DetailsViewModel extends AndroidViewModel {

    private LiveData<Details> detailsLiveData;

    public DetailsViewModel(Application application) {
        super(application);
    }

    public LiveData<Details> getDetailsLiveData() {
        return detailsLiveData;
    }

    public void init(String repo,
                     String arch,
                     String pkgname) {
        detailsLiveData = ArchPackagesRepository.getArchPackagesRepositoryInstance().getDetailsLiveData(repo, arch, pkgname);

    }
}
