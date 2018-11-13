package com.rascarlo.arch.packages.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.rascarlo.arch.packages.api.model.Result;
import com.rascarlo.arch.packages.data.ResultDataSource;
import com.rascarlo.arch.packages.data.ResultDataSourceFactory;

import java.util.List;

public class PackagesViewModel extends ViewModel {

    public LiveData<PagedList<Result>> pagedListLiveData;
    public LiveData<PageKeyedDataSource<Integer, Result>> pageKeyedDataSourceLiveData;

    public PackagesViewModel() {
    }

    public void init(int keywordsParameter, String query, List<String> listRepo, List<String> listArch, String flagged) {
        ResultDataSourceFactory resultDataSourceFactory = new ResultDataSourceFactory(keywordsParameter, query, listRepo, listArch, flagged);
        pageKeyedDataSourceLiveData = resultDataSourceFactory.getDataSourceMutableLiveData();
        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(ResultDataSource.PAGE_SIZE)
                .build();
        pagedListLiveData = new LivePagedListBuilder(resultDataSourceFactory, pagedListConfig).build();
    }
}
