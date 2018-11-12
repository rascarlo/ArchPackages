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