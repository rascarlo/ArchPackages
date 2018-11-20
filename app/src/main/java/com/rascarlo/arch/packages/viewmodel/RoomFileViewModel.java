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

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.rascarlo.arch.packages.data.RoomFileRepository;
import com.rascarlo.arch.packages.persistence.RoomFile;

import java.util.List;

public class RoomFileViewModel extends AndroidViewModel {

    private final RoomFileRepository roomFileRepository;
    private final LiveData<List<RoomFile>> listLiveData;
    private final LiveData<PagedList<RoomFile>> pagedListLiveData;

    public RoomFileViewModel(@NonNull Application application) {
        super(application);
        roomFileRepository = new RoomFileRepository(application);
        listLiveData = roomFileRepository.getListLiveData();
        pagedListLiveData = roomFileRepository.getPagedListLiveData();
    }

    public LiveData<List<RoomFile>> getListLiveData() {
        return listLiveData;
    }

    public LiveData<PagedList<RoomFile>> getPagedListLiveData() {
        return pagedListLiveData;
    }

    public void insertRoomFile(RoomFile roomFile) {
        roomFileRepository.insertRoomFile(roomFile);
    }

    public void wipeRoomFileDatabase() {
        roomFileRepository.wipeRoomFileDatabase();
    }
}