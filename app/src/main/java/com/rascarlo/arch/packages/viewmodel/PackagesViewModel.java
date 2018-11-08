package com.rascarlo.arch.packages.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.rascarlo.arch.packages.api.model.Packages;
import com.rascarlo.arch.packages.data.ArchPackagesRepository;

import java.util.List;

public class PackagesViewModel extends AndroidViewModel {

    private LiveData<Packages> packagesLiveData;

    public PackagesViewModel(Application application) {
        super(application);
    }

    public LiveData<Packages> getPackagesLiveData() {
        return packagesLiveData;
    }

    public void init(int keywordsParameter,
                     String keywords,
                     List<String> listRepo,
                     List<String> listArch,
                     String flagged,
                     int numPage) {
        packagesLiveData = ArchPackagesRepository.getArchPackagesRepositoryInstance().getPackagesLiveData(
                keywordsParameter,
                keywords,
                listRepo,
                listArch,
                flagged,
                numPage);

    }
}
