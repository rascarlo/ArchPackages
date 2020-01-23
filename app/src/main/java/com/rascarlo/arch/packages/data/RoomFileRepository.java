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

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import android.os.AsyncTask;

import com.rascarlo.arch.packages.persistence.RoomFile;
import com.rascarlo.arch.packages.persistence.RoomFileDao;
import com.rascarlo.arch.packages.persistence.RoomFileDatabase;

import java.util.List;

public class RoomFileRepository {

    private final RoomFileDao roomFileDao;
    private final LiveData<List<RoomFile>> listLiveData;
    private final LiveData<PagedList<RoomFile>> pagedListLiveData;

    public RoomFileRepository(Application application) {
        RoomFileDatabase roomFileDatabase = RoomFileDatabase.getRoomFileDatabase(application);
        roomFileDao = roomFileDatabase.roomFileDao();
        listLiveData = roomFileDao.geAlphabetizedFiles();
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(25)
                .setEnablePlaceholders(false)
                .build();
        pagedListLiveData = new LivePagedListBuilder<>(
                roomFileDao.getPagedFiles(), config)
                .setInitialLoadKey(1)
                .build();
    }

    public LiveData<List<RoomFile>> getListLiveData() {
        return listLiveData;
    }

    public LiveData<PagedList<RoomFile>> getPagedListLiveData() {
        return pagedListLiveData;
    }

    public void insertRoomFile(RoomFile roomFile) {

        new InsertRoomFileAsyncTask(roomFileDao).execute(roomFile);
    }

    public void wipeRoomFileDatabase() {
        new WipeRoomFileDatabaseAsyncTask(roomFileDao).execute();
    }

    private static class InsertRoomFileAsyncTask extends AsyncTask<RoomFile, Void, Void> {

        private final RoomFileDao roomFileDao;

        InsertRoomFileAsyncTask(RoomFileDao roomFileDao) {
            this.roomFileDao = roomFileDao;
        }

        @Override
        protected Void doInBackground(RoomFile... roomFiles) {
            roomFileDao.insert(roomFiles[0]);
            return null;
        }
    }

    private static class WipeRoomFileDatabaseAsyncTask extends AsyncTask<RoomFile, Void, Void> {

        private final RoomFileDao roomFileDao;

        WipeRoomFileDatabaseAsyncTask(RoomFileDao roomFileDao) {
            this.roomFileDao = roomFileDao;
        }

        @Override
        protected Void doInBackground(RoomFile... roomFiles) {
            roomFileDao.deleteAll();
            return null;
        }
    }
}