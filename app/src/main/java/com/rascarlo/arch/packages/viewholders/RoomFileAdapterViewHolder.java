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

package com.rascarlo.arch.packages.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rascarlo.arch.packages.databinding.RoomFileItemBinding;
import com.rascarlo.arch.packages.persistence.RoomFile;

public class RoomFileAdapterViewHolder extends RecyclerView.ViewHolder {

    private final RoomFileItemBinding binding;

    public RoomFileAdapterViewHolder(@NonNull RoomFileItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindRoomFile(RoomFile roomFile) {
        binding.setRoomFile(roomFile);
        binding.executePendingBindings();
    }
}