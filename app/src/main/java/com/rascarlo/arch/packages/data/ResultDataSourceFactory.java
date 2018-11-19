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

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.rascarlo.arch.packages.api.model.Result;

import java.util.List;

public class ResultDataSourceFactory extends DataSource.Factory {

    private final MutableLiveData<ResultDataSource> mutableLiveData;
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
        this.mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, Result> create() {
        ResultDataSource resultDataSource = new ResultDataSource(this.keywordsParameter,
                this.query,
                this.listRepo,
                this.listArch,
                this.flagged);
        mutableLiveData.postValue(resultDataSource);
        return resultDataSource;
    }
}