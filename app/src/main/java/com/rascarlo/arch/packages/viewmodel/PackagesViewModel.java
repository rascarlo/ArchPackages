package com.rascarlo.arch.packages.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.rascarlo.arch.packages.ArchPackagesRepository;
import com.rascarlo.arch.packages.api.pojo.Packages;

import java.util.List;

public class PackagesViewModel extends AndroidViewModel {

    private LiveData<Packages> archPackagesLiveData;

    public PackagesViewModel(Application application) {
        super(application);
    }

    public LiveData<Packages> getArchPackagesLiveData() {
        return archPackagesLiveData;
    }

    public void init(int keywordsParameter,
                     String keywords,
                     List<String> listRepo,
                     List<String> listArch,
                     List<String> listFlagged,
                     int numPage) {
        archPackagesLiveData = ArchPackagesRepository.getArchPackagesRepositoryInstance().getArchPackagesLiveData(
                keywordsParameter,
                keywords,
                listRepo,
                listArch,
                listFlagged,
                numPage);

    }
}
