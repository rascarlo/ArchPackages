package com.rascarlo.arch.packages.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.rascarlo.arch.packages.api.model.Result;
import com.rascarlo.arch.packages.data.ResultDataSource;
import com.rascarlo.arch.packages.data.ResultDataSourceFactory;

import java.util.List;

public class PackagesViewModel extends ViewModel {

    private LiveData<PagedList<Result>> pagedListLiveData;

    public PackagesViewModel() {
    }

    public void init(int keywordsParameter, String query, List<String> listRepo, List<String> listArch, String flagged) {
        ResultDataSourceFactory resultDataSourceFactory = new ResultDataSourceFactory(keywordsParameter, query, listRepo, listArch, flagged);
        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(ResultDataSource.PAGE_SIZE)
                .build();
        pagedListLiveData = new LivePagedListBuilder(resultDataSourceFactory, pagedListConfig).build();
    }

    public LiveData<PagedList<Result>> getPagedListLiveData() {
        return pagedListLiveData;
    }
}
