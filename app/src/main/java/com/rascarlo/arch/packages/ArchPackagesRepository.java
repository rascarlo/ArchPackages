package com.rascarlo.arch.packages;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.rascarlo.arch.packages.api.ArchPackagesService;
import com.rascarlo.arch.packages.api.model.Packages;
import com.rascarlo.arch.packages.util.UtilConstants;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArchPackagesRepository {

    private final ArchPackagesService archPackagesService;
    private static ArchPackagesRepository archPackagesRepository;

    private ArchPackagesRepository() {
        // http logging interceptor
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // okhttp client
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(httpLoggingInterceptor);
        // retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilConstants.ARCH_PACKAGES_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build();
        // service
        archPackagesService = retrofit.create(ArchPackagesService.class);
    }

    public synchronized static ArchPackagesRepository getArchPackagesRepositoryInstance() {
        if (archPackagesRepository == null) {
            archPackagesRepository = new ArchPackagesRepository();
        }
        return archPackagesRepository;
    }

    public LiveData<Packages> getArchPackagesLiveData(int keywordsParameter,
                                                      String keywords,
                                                      List<String> listRepo,
                                                      List<String> listArch,
                                                      List<String> listFlagged,
                                                      int numPage) {
        final MutableLiveData<Packages> archPackagesMutableLiveData = new MutableLiveData<>();
        Call<Packages> archPackagesCall;
        // by name or description
        if (keywordsParameter == UtilConstants.SEARCH_KEYWORDS_PARAMETER_NAME_OR_DESCRIPTION) {
            archPackagesCall = archPackagesService.searchByNameOrDescription(keywords, listRepo, listArch, listFlagged, numPage);
            // by exact name
        } else if (keywordsParameter == UtilConstants.SEARCH_KEYWORDS_PARAMETER_EXACT_NAME) {
            archPackagesCall = archPackagesService.searchByExactName(keywords, listRepo, listArch, listFlagged, numPage);
            // by description
        } else if (keywordsParameter == UtilConstants.SEARCH_KEYWORDS_PARAMETER_DESCRIPTION) {
            archPackagesCall = archPackagesService.searchByDescription(keywords, listRepo, listArch, listFlagged, numPage);
            // DEFAULT by name or description
        } else {
            archPackagesCall = archPackagesService.searchByNameOrDescription(keywords, listRepo, listArch, listFlagged, numPage);
        }
        archPackagesCall.enqueue(new Callback<Packages>() {
            @Override
            public void onResponse(Call<Packages> call, Response<Packages> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200)
                    archPackagesMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Packages> call, Throwable t) {
                archPackagesMutableLiveData.setValue(null);
            }
        });
        return archPackagesMutableLiveData;
    }
}
