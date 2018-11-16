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