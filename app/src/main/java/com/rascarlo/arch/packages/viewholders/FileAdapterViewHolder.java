package com.rascarlo.arch.packages.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.rascarlo.arch.packages.databinding.FileItemBinding;

public class FileAdapterViewHolder extends RecyclerView.ViewHolder {

    private final FileItemBinding binding;

    public FileAdapterViewHolder(@NonNull FileItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindString(String s) {
        binding.setString(s);
        binding.executePendingBindings();
    }
}