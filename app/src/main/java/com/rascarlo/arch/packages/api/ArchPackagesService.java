/*
 *     Copyright (C) 2018 rascarlo
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
                                             @Query("page") int page,
                                             @Query("limit") int limit);

    @GET("search/json/?")
    Call<Packages> searchByExactName(@Query("name") String query,
                                     @Query("repo") List<String> listRepo,
                                     @Query("arch") List<String> listArch,
                                     @Query("flagged") String flagged,
                                     @Query("page") int page,
                                     @Query("limit") int limit);

    @GET("search/json/?")
    Call<Packages> searchByDescription(@Query("desc") String query,
                                       @Query("repo") List<String> listRepo,
                                       @Query("arch") List<String> listArch,
                                       @Query("flagged") String flagged,
                                       @Query("page") int page,
                                       @Query("limit") int limit);

    @GET("{repository}/{architecture}/{package}/json")
    Call<Details> searchDetails(@Path("repository") String repo,
                                @Path("architecture") String arch,
                                @Path("package") String pkgname);

    @GET("{repository}/{architecture}/{package}/files/json")
    Call<Files> searchFiles(@Path("repository") String repo,
                            @Path("architecture") String arch,
                            @Path("package") String pkgname);
}
