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

package com.rascarlo.arch.packages.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rascarlo.arch.packages.R;
import com.rascarlo.arch.packages.databinding.FileItemBinding;
import com.rascarlo.arch.packages.viewholders.FileAdapterViewHolder;

public class FileAdapter extends ListAdapter<String, FileAdapterViewHolder> {

    public FileAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<String> DIFF_CALLBACK = new DiffUtil.ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(@NonNull String s, @NonNull String t1) {
            return TextUtils.equals(s.trim(), t1.trim());
        }

        @Override
        public boolean areContentsTheSame(@NonNull String s, @NonNull String t1) {
            return t1.equals(s);
        }
    };

    @NonNull
    @Override
    public FileAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FileItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.file_item, viewGroup, false);
        return new FileAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FileAdapterViewHolder fileAdapterViewHolder, int i) {
        if (getItem(i) != null) {
            String s = getItem(i);
            fileAdapterViewHolder.bindString(s);
        }
    }
}