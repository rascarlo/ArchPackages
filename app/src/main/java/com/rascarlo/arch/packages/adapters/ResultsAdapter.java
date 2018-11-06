package com.rascarlo.arch.packages.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rascarlo.arch.packages.R;
import com.rascarlo.arch.packages.api.pojo.Result;
import com.rascarlo.arch.packages.databinding.ResultItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private final List<Result> resultList;

    public ResultsAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        resultList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ResultItemBinding resultItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.result_item, viewGroup, false);
        return new ViewHolder(resultItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsAdapter.ViewHolder viewHolder, int i) {
        if (resultList != null && !resultList.isEmpty()) {
            if (resultList.get(i) != null) {
                Result result = resultList.get(i);
                viewHolder.resultItemBinding.setPkgname(result.getPkgname());
                viewHolder.resultItemBinding.setPkgdesc(result.getPkgdesc());
                viewHolder.resultItemBinding.setPkgver(result.getPkgver());
                viewHolder.resultItemBinding.setLastUpdate(result.getLastUpdate());
                viewHolder.resultItemBinding.setFlagDate(result.getFlagDate());
                viewHolder.resultItemBinding.setArch(result.getArch());
                viewHolder.resultItemBinding.setRepo(result.getRepo());
            }
        }
    }

    @Override
    public int getItemCount() {
        return resultList != null ? resultList.size() : 0;
    }

    public void addResults(List<Result> results) {
        resultList.addAll(results);
        notifyItemInserted(resultList.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ResultItemBinding resultItemBinding;

        ViewHolder(@NonNull ResultItemBinding resultItemBinding) {
            super(resultItemBinding.getRoot());
            this.resultItemBinding = resultItemBinding;
        }
    }
}