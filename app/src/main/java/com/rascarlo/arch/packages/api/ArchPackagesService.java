package com.rascarlo.arch.packages.api;

import com.rascarlo.arch.packages.api.model.Details;
import com.rascarlo.arch.packages.api.model.Files;
import com.rascarlo.arch.packages.api.model.Packages;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArchPackagesService {

    @GET("search/json/?")
    Call<Packages> searchByNameOrDescription(@Query("q") String query,
                                             @Query("repo") List<String> listRepo,
                                             @Query("arch") List<String> listArch,
                                             @Query("flagged") String flagged,
                                             @Query("page") int page);

    @GET("search/json/?")
    Call<Packages> searchByExactName(@Query("name") String query,
                                     @Query("repo") List<String> listRepo,
                                     @Query("arch") List<String> listArch,
                                     @Query("flagged") String flagged,
                                     @Query("page") int page);

    @GET("search/json/?")
    Call<Packages> searchByDescription(@Query("desc") String query,
                                       @Query("repo") List<String> listRepo,
                                       @Query("arch") List<String> listArch,
                                       @Query("flagged") String flagged,
                                       @Query("page") int page);

    @GET("{repository}/{architecture}/{package}/json")
    Call<Details> searchDetails(@Path("repository") String repo,
                                @Path("architecture") String arch,
                                @Path("package") String pkgname);

    @GET("{repository}/{architecture}/{package}/files/json")
    Call<Files> searchFiles(@Path("repository") String repo,
                            @Path("architecture") String arch,
                            @Path("package") String pkgname);
}
