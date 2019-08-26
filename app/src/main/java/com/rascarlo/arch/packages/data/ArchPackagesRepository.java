/*
 *     Copyright (C) 2018 rascarlo <rascarlo@gmail.com>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.rascarlo.arch.packages.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.rascarlo.arch.packages.BuildConfig;
import com.rascarlo.arch.packages.api.ArchPackagesService;
import com.rascarlo.arch.packages.api.model.Details;
import com.rascarlo.arch.packages.api.model.Files;
import com.rascarlo.arch.packages.util.ArchPackagesConstants;

import java.util.concurrent.TimeUnit;

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
        httpLoggingInterceptor.level(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        // okhttp client
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.readTimeout(30, TimeUnit.SECONDS);
        okHttpClient.addInterceptor(httpLoggingInterceptor);
        // retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ArchPackagesConstants.ARCH_PACKAGES_API_BASE_URL)
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

    ArchPackagesService getArchPackagesService() {
        return archPackagesService;
    }

    public LiveData<Details> getDetailsLiveData(final String repo,
                                                String arch,
                                                String pkgname) {
        final MutableLiveData<Details> detailsMutableLiveData = new MutableLiveData<>();
        Call<Details> detailsCall = archPackagesService.searchDetails(repo, arch, pkgname);
        detailsCall.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(@NonNull Call<Details> call, @NonNull Response<Details> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200)
                    detailsMutableLiveData.setValue(response.body());
                else detailsMutableLiveData.setValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<Details> call, @NonNull Throwable t) {
                detailsMutableLiveData.setValue(null);
            }
        });
        return detailsMutableLiveData;
    }

    public LiveData<Files> getFilesLiveData(final String repo,
                                            String arch,
                                            String pkgname) {
        final MutableLiveData<Files> filesMutableLiveData = new MutableLiveData<>();
        Call<Files> filesCall = archPackagesService.searchFiles(repo, arch, pkgname);
        filesCall.enqueue(new Callback<Files>() {
            @Override
            public void onResponse(@NonNull Call<Files> call, @NonNull Response<Files> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200)
                    filesMutableLiveData.setValue(response.body());
                else filesMutableLiveData.setValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<Files> call, @NonNull Throwable t) {
                filesMutableLiveData.setValue(null);
            }
        });
        return filesMutableLiveData;
    }
}
