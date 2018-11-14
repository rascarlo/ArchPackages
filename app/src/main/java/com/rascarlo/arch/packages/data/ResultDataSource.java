package com.rascarlo.arch.packages.data;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.rascarlo.arch.packages.api.model.Packages;
import com.rascarlo.arch.packages.api.model.Result;
import com.rascarlo.arch.packages.util.ArchPackagesConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultDataSource extends PageKeyedDataSource<Integer, Result> {

    public static final int PAGE_SIZE = 20;
    private static final int FIRST_PAGE = 1;
    private final int keywordsParameter;
    private final String query;
    private final List<String> listRepo;
    private final List<String> listArch;
    private final String flagged;

    ResultDataSource(int keywordsParameter,
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
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Result> callback) {
        getCall(FIRST_PAGE)
                .enqueue(new Callback<Packages>() {
                    @Override
                    public void onResponse(@NonNull Call<Packages> call, @NonNull Response<Packages> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onResult(response.body().getResults(),
                                    null,
                                    FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Packages> call, @NonNull Throwable t) {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Result> callback) {
        getCall(params.key)
                .enqueue(new Callback<Packages>() {
                    @Override
                    public void onResponse(@NonNull Call<Packages> call, @NonNull Response<Packages> response) {
                        Integer adjacentPageKey = (params.key > 1) ? params.key - 1 : null;
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onResult(response.body().getResults(), adjacentPageKey);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Packages> call, @NonNull Throwable t) {

                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Result> callback) {
        getCall(params.key)
                .enqueue(new Callback<Packages>() {
                    @Override
                    public void onResponse(@NonNull Call<Packages> call, @NonNull Response<Packages> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getPage() < response.body().getNumPages()) {
                                Integer adjacentPageKey = response.body().getPage() < response.body().getNumPages() ? params.key + 1 : null;
                                callback.onResult(response.body().getResults(), adjacentPageKey);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Packages> call, @NonNull Throwable t) {

                    }
                });
    }

    private Call<Packages> getCall(int firstPage) {
        Call<Packages> call;
        switch (keywordsParameter) {
            case ArchPackagesConstants.SEARCH_KEYWORDS_PARAMETER_NAME_OR_DESCRIPTION:
                call = ArchPackagesRepository.getArchPackagesRepositoryInstance()
                        .getArchPackagesService()
                        .searchByNameOrDescription(query,
                                listRepo,
                                listArch,
                                flagged,
                                firstPage,
                                PAGE_SIZE);
                break;
            case ArchPackagesConstants.SEARCH_KEYWORDS_PARAMETER_EXACT_NAME:
                call = ArchPackagesRepository.getArchPackagesRepositoryInstance()
                        .getArchPackagesService()
                        .searchByExactName(query,
                                listRepo,
                                listArch,
                                flagged,
                                firstPage,
                                PAGE_SIZE);
                break;
            case ArchPackagesConstants.SEARCH_KEYWORDS_PARAMETER_DESCRIPTION:
                call = ArchPackagesRepository.getArchPackagesRepositoryInstance()
                        .getArchPackagesService()
                        .searchByDescription(query,
                                listRepo,
                                listArch,
                                flagged,
                                firstPage,
                                PAGE_SIZE);
                break;
            default:
                call = ArchPackagesRepository.getArchPackagesRepositoryInstance()
                        .getArchPackagesService()
                        .searchByNameOrDescription(query,
                                listRepo,
                                listArch,
                                flagged,
                                firstPage,
                                PAGE_SIZE);
                break;
        }
        return call;
    }
}