package com.rascarlo.arch.packages.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.rascarlo.arch.packages.databinding.DependencyItemBinding;

public class DependencyAdapterViewHolder extends RecyclerView.ViewHolder {

    private final DependencyItemBinding binding;

    public DependencyAdapterViewHolder(@NonNull DependencyItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindString(String s) {
        binding.setString(s);
        binding.executePendingBindings();
    }
}