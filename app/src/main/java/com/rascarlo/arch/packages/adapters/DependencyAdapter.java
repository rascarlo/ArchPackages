package com.rascarlo.arch.packages.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rascarlo.arch.packages.R;
import com.rascarlo.arch.packages.callbacks.DependencyAdapterCallback;
import com.rascarlo.arch.packages.databinding.DependencyItemBinding;
import com.rascarlo.arch.packages.viewholders.DependencyAdapterViewHolder;

public class DependencyAdapter extends ListAdapter<String, DependencyAdapterViewHolder> {

    private DependencyAdapterCallback dependencyAdapterCallback;

    public DependencyAdapter(DependencyAdapterCallback dependencyAdapterCallback) {
        super(DIFF_CALLBACK);
        this.dependencyAdapterCallback = dependencyAdapterCallback;
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
    public DependencyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        DependencyItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.dependency_item, viewGroup, false);
        binding.setDependencyAdapterCallback(dependencyAdapterCallback);
        return new DependencyAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DependencyAdapterViewHolder dependencyAdapterViewHolder, int i) {
        if (getItem(i) != null) {
            String s = getItem(i);
            dependencyAdapterViewHolder.bindString(s);
        }
    }
}