package com.rascarlo.arch.packages.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.rascarlo.arch.packages.api.model.Result;

import java.util.List;

public class ResultDataSourceFactory extends DataSource.Factory {
    private final MutableLiveData<PageKeyedDataSource<Integer, Result>> dataSourceMutableLiveData = new MutableLiveData<>();
    private final int keywordsParameter;
    private final String query;
    private final List<String> listRepo;
    private final List<String> listArch;
    private final String flagged;

    public ResultDataSourceFactory(int keywordsParameter,
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

    @Override
    public DataSource<Integer, Result> create() {
        ResultDataSource resultDataSource = new ResultDataSource(this.keywordsParameter,
                this.query,
                this.listRepo,
                this.listArch,
                this.flagged);
        dataSourceMutableLiveData.postValue(resultDataSource);
        return resultDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Result>> getDataSourceMutableLiveData() {
        return dataSourceMutableLiveData;
    }
}