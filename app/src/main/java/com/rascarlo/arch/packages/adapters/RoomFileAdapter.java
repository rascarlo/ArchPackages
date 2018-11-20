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

package com.rascarlo.arch.packages.adapters;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rascarlo.arch.packages.R;
import com.rascarlo.arch.packages.databinding.RoomFileItemBinding;
import com.rascarlo.arch.packages.persistence.RoomFile;
import com.rascarlo.arch.packages.viewholders.RoomFileAdapterViewHolder;

public class RoomFileAdapter extends PagedListAdapter<RoomFile, RoomFileAdapterViewHolder> {

    public RoomFileAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<RoomFile> DIFF_CALLBACK = new DiffUtil.ItemCallback<RoomFile>() {
        @Override
        public boolean areItemsTheSame(@NonNull RoomFile s, @NonNull RoomFile t1) {
            return TextUtils.equals(s.getPackageName().trim(), t1.getPackageName().trim());
        }

        @Override
        public boolean areContentsTheSame(@NonNull RoomFile s, @NonNull RoomFile t1) {
            return t1.equals(s);
        }
    };

    @NonNull
    @Override
    public RoomFileAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RoomFileItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.room_file_item, viewGroup, false);
        return new RoomFileAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomFileAdapterViewHolder roomFileAdapterViewHolder, int i) {
        if (getItem(i) != null) {
            RoomFile roomFile = getItem(i);
            roomFileAdapterViewHolder.bindRoomFile(roomFile);
        }
    }
}