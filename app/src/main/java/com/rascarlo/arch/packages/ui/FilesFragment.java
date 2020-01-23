/*
 *     Copyright (C) 2018 rascarlo <rascarlo@gmail.com>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.rascarlo.arch.packages.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rascarlo.arch.packages.adapters.RoomFileAdapter;
import com.rascarlo.arch.packages.api.model.Files;
import com.rascarlo.arch.packages.databinding.FragmentFilesBinding;
import com.rascarlo.arch.packages.viewmodel.RoomFileViewModel;

public class FilesFragment extends Fragment {

    private static final String BUNDLE_FILES = "bundle_files";
    private Files files;
    private FragmentFilesBinding fragmentFilesBinding;

    public FilesFragment() {
    }

    public static FilesFragment newInstance(Files files) {
        FilesFragment filesFragment = new FilesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_FILES, files);
        filesFragment.setArguments(bundle);
        return filesFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            files = getArguments().getParcelable(BUNDLE_FILES);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentFilesBinding = FragmentFilesBinding.inflate(inflater, container, false);
        return fragmentFilesBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (fragmentFilesBinding != null) {
            fragmentFilesBinding.setFiles(files);
            fragmentFilesBinding.executePendingBindings();
            Context context = fragmentFilesBinding.getRoot().getContext();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            RecyclerView recyclerView = fragmentFilesBinding.fragmentFilesRecyclerView;
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
            RoomFileAdapter roomFileAdapter = new RoomFileAdapter();
            RoomFileViewModel mViewModel = new ViewModelProvider(this).get(RoomFileViewModel.class);
            mViewModel.getPagedListLiveData().observe(getViewLifecycleOwner(), roomFiles -> {
                if (roomFiles != null) {
                    roomFileAdapter.submitList(roomFiles);
                }
            });
            recyclerView.setAdapter(roomFileAdapter);
        }
    }
}