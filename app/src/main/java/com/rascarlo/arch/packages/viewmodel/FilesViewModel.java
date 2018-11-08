package com.rascarlo.arch.packages.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.rascarlo.arch.packages.api.model.Files;
import com.rascarlo.arch.packages.data.ArchPackagesRepository;

public class FilesViewModel extends AndroidViewModel {

    private LiveData<Files> filesLiveData;

    public FilesViewModel(Application application) {
        super(application);
    }

    public LiveData<Files> getFilesLiveData() {
        return filesLiveData;
    }

    public void init(String repo,
                     String arch,
                     String pkgname) {
        filesLiveData = ArchPackagesRepository.getArchPackagesRepositoryInstance().getFilesLiveData(repo, arch, pkgname);

    }
}
