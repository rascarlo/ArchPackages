package com.rascarlo.arch.packages.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.rascarlo.arch.packages.databinding.StringItemBinding;

public class StringListAdapterViewHolder extends RecyclerView.ViewHolder {

    private final StringItemBinding binding;

    public StringListAdapterViewHolder(@NonNull StringItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindString(String s) {
        binding.setString(s);
        binding.executePendingBindings();
    }
}