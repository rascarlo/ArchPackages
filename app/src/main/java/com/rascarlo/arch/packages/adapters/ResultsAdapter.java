package com.rascarlo.arch.packages.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rascarlo.arch.packages.R;
import com.rascarlo.arch.packages.api.model.Result;
import com.rascarlo.arch.packages.callbacks.ResultsAdapterCallback;
import com.rascarlo.arch.packages.databinding.ResultItemBinding;
import com.rascarlo.arch.packages.viewholders.ResultAdapterViewHolder;

public class ResultsAdapter extends ListAdapter<Result, ResultAdapterViewHolder> {

    private final ResultsAdapterCallback resultsAdapterCallback;

    public ResultsAdapter(ResultsAdapterCallback resultsAdapterCallback) {
        super(DIFF_CALLBACK);
        this.resultsAdapterCallback = resultsAdapterCallback;
    }

    private static final DiffUtil.ItemCallback<Result> DIFF_CALLBACK = new DiffUtil.ItemCallback<Result>() {
        @Override
        public boolean areItemsTheSame(Result oldItem, Result newItem) {
            return TextUtils.equals(oldItem.getPkgname(), newItem.getPkgname())
                    && TextUtils.equals(oldItem.getPkgver(), newItem.getPkgrel())
                    && TextUtils.equals(oldItem.getFilename(), newItem.getFilename())
                    && TextUtils.equals(oldItem.getArch(), newItem.getArch())
                    && TextUtils.equals(oldItem.getRepo(), newItem.getRepo());
        }

        @Override
        public boolean areContentsTheSame(Result oldItem, Result newItem) {
            return TextUtils.equals(oldItem.getPkgname(), newItem.getPkgname())
                    && TextUtils.equals(oldItem.getFilename(), newItem.getFilename())
                    && TextUtils.equals(oldItem.getPkgver(), newItem.getPkgver())
                    && TextUtils.equals(oldItem.getArch(), newItem.getArch())
                    && TextUtils.equals(oldItem.getRepo(), newItem.getRepo());
        }
    };

    @NonNull
    @Override
    public ResultAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ResultItemBinding resultItemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.result_item, viewGroup, false);
        resultItemBinding.setHandler(resultsAdapterCallback);
        return new ResultAdapterViewHolder(resultItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapterViewHolder resultAdapterViewHolder, int i) {
        if (getItem(i) != null) {
            Result result = getItem(i);
            resultAdapterViewHolder.binding.setResult(result);
            resultAdapterViewHolder.bindResult(result);
        }
    }
}