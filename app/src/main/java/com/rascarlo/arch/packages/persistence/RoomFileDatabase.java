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

package com.rascarlo.arch.packages.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {RoomFile.class}, version = 1)
public abstract class RoomFileDatabase extends RoomDatabase {

    public abstract RoomFileDao roomFileDao();

    private static volatile RoomFileDatabase roomFileDatabase;

    public static RoomFileDatabase getRoomFileDatabase(final Context context) {
        if (roomFileDatabase == null) {
            synchronized (RoomFileDatabase.class) {
                if (roomFileDatabase == null) {
                    roomFileDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            RoomFileDatabase.class,
                            "room_file_database")
                            .build();
                }
            }
        }
        return roomFileDatabase;
    }
}