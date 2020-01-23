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

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

public class FilesViewModelFactory implements ViewModelProvider.Factory {

    private final String repo;
    private final String arch;
    private final String pkgname;

    public FilesViewModelFactory(String repo,
                                 String arch,
                                 String pkgname) {
        this.repo = repo;
        this.arch = arch;
        this.pkgname = pkgname;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FilesViewModel.class)) {
            return (T) new FilesViewModel(repo, arch, pkgname);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}