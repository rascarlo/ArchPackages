package com.rascarlo.arch.packages.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rascarlo.arch.packages.R;
import com.rascarlo.arch.packages.api.model.Result;
import com.rascarlo.arch.packages.callbacks.ResultsAdapterCallback;
import com.rascarlo.arch.packages.databinding.ResultItemBinding;
import com.rascarlo.arch.packages.util.UtilStringConverters;

public class ResultsAdapter extends ListAdapter<Result, ResultsAdapter.ViewHolder> {

    private final Context context;
    private final ResultsAdapterCallback resultsAdapterCallback;

    public ResultsAdapter(Context context, ResultsAdapterCallback resultsAdapterCallback) {
        super(DIFF_CALLBACK);
        this.context = context;
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
    public ResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ResultItemBinding resultItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.result_item, viewGroup, false);
        resultItemBinding.setHandler(resultsAdapterCallback);
        return new ViewHolder(resultItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsAdapter.ViewHolder viewHolder, int i) {
        if (getItem(i) != null) {
            Result result = getItem(i);
            viewHolder.resultItemBinding.setResult(result);
            viewHolder.resultItemBinding.resultItemTextViewCompressedSize
                    .setText(String.format(context.getString(R.string.formatted_compressed_size),
                            result.getCompressedSize(),
                            UtilStringConverters.convertBytesToMb(context, result.getCompressedSize())));
            viewHolder.resultItemBinding.resultItemTextViewInstalledSize
                    .setText(String.format(context.getString(R.string.formatted_installed_size),
                            result.getInstalledSize(),
                            UtilStringConverters.convertBytesToMb(context, result.getInstalledSize())));
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ResultItemBinding resultItemBinding;

        ViewHolder(@NonNull ResultItemBinding resultItemBinding) {
            super(resultItemBinding.getRoot());
            this.resultItemBinding = resultItemBinding;
        }
    }
}