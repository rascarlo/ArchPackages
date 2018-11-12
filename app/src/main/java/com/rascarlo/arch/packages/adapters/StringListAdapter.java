package com.rascarlo.arch.packages.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rascarlo.arch.packages.R;
import com.rascarlo.arch.packages.databinding.StringItemBinding;

public class StringListAdapter extends ListAdapter<String, StringListAdapter.ViewHolder> {

    public StringListAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<String> DIFF_CALLBACK = new DiffUtil.ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(@NonNull String s, @NonNull String t1) {
            return TextUtils.equals(s.trim(), t1.trim());
        }

        @Override
        public boolean areContentsTheSame(@NonNull String s, @NonNull String t1) {
            return TextUtils.equals(s.trim(), t1.trim());
        }
    };

    @NonNull
    @Override
    public StringListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        StringItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.string_item, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StringListAdapter.ViewHolder viewHolder, int i) {
        if (getItem(i) != null) {
            String s = getItem(i);
            viewHolder.bind(s);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final StringItemBinding binding;

        ViewHolder(@NonNull StringItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(String s) {
            binding.setString(s);
            binding.executePendingBindings();
        }
    }
}