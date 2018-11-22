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

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.rascarlo.arch.packages.api.model.Files;
import com.rascarlo.arch.packages.data.ArchPackagesRepository;

public class FilesViewModel extends ViewModel {

    private final LiveData<Files> filesLiveData;

    FilesViewModel(String repo,
                   String arch,
                   String pkgname) {
        filesLiveData = ArchPackagesRepository.getArchPackagesRepositoryInstance().getFilesLiveData(repo, arch, pkgname);

    }

    public LiveData<Files> getFilesLiveData() {
        return filesLiveData;
    }
}
