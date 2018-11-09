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

    private Context context;
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
    public ResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        ResultItemBinding resultItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.result_item, viewGroup, false);
        resultItemBinding.setHandler(resultsAdapterCallback);
        return new ViewHolder(resultItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsAdapter.ViewHolder viewHolder, int i) {
        if (getItem(i) != null) {
            Result result = getItem(i);
            viewHolder.binding.setResult(result);
            viewHolder.bind(result);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ResultItemBinding binding;

        ViewHolder(@NonNull ResultItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Result result) {
            binding.setResult(result);
            binding.executePendingBindings();
            binding.executePendingBindings();
            binding.resultItemTextViewCompressedSize
                    .setText(String.format(context.getString(R.string.formatted_compressed_size),
                            result.getCompressedSize(),
                            UtilStringConverters.convertBytesToMb(context, result.getCompressedSize())));
            binding.resultItemTextViewInstalledSize
                    .setText(String.format(context.getString(R.string.formatted_installed_size),
                            result.getInstalledSize(),
                            UtilStringConverters.convertBytesToMb(context, result.getInstalledSize())));

        }
    }
}