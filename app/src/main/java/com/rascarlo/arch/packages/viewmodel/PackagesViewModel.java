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

package com.rascarlo.arch.packages.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.rascarlo.arch.packages.api.model.Result;
import com.rascarlo.arch.packages.data.ResultDataSource;
import com.rascarlo.arch.packages.data.ResultDataSourceFactory;

import java.util.List;

public class PackagesViewModel extends ViewModel {

    private final LiveData<PagedList<Result>> pagedListLiveData;

    PackagesViewModel(int keywordsParameter,
                      String query,
                      List<String> listRepo,
                      List<String> listArch,
                      String flagged) {
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