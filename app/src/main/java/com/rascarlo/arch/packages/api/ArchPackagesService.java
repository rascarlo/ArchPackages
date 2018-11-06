package com.rascarlo.arch.packages.api;

import com.rascarlo.arch.packages.api.pojo.Packages;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArchPackagesService {

    @GET("search/json/?")
    Call<Packages> searchByNameOrDescription(@Query("q") String query,
                                             @Query("repo") List<String> listRepo,
                                             @Query("arch") List<String> listArch,
                                             @Query("flagged") List<String> listFlagged,
                                             @Query("page") int page);

    @GET("search/json/?")
    Call<Packages> searchByExactName(@Query("name") String query,
                                     @Query("repo") List<String> listRepo,
                                     @Query("arch") List<String> listArch,
                                     @Query("flagged") List<String> listFlagged,
                                     @Query("page") int page);

    @GET("search/json/?")
    Call<Packages> searchByDescription(@Query("desc") String query,
                                       @Query("repo") List<String> listRepo,
                                       @Query("arch") List<String> listArch,
                                       @Query("flagged") List<String> listFlagged,
                                       @Query("page") int page);
}
