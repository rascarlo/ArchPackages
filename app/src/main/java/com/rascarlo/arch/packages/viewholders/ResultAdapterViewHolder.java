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

package com.rascarlo.arch.packages.viewholders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.rascarlo.arch.packages.R;
import com.rascarlo.arch.packages.api.model.Result;
import com.rascarlo.arch.packages.databinding.ResultItemBinding;
import com.rascarlo.arch.packages.util.ArchPackagesStringConverters;

public class ResultAdapterViewHolder extends RecyclerView.ViewHolder {

    public final ResultItemBinding binding;

    public ResultAdapterViewHolder(@NonNull ResultItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindResult(Result result) {
        Context context = binding.getRoot().getContext();
        binding.setResult(result);
        binding.executePendingBindings();
        // compressed size
        binding.resultItemTextViewCompressedSize
                .setText(String.format(context.getString(R.string.formatted_compressed_size),
                        result.getCompressedSize(),
                        ArchPackagesStringConverters.convertBytesToMb(context, result.getCompressedSize())));
        // installed size
        binding.resultItemTextViewInstalledSize
                .setText(String.format(context.getString(R.string.formatted_installed_size),
                        result.getInstalledSize(),
                        ArchPackagesStringConverters.convertBytesToMb(context, result.getInstalledSize())));

    }
}