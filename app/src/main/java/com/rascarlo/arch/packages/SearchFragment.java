package com.rascarlo.arch.packages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.rascarlo.arch.packages.util.UtilConstants;
import com.rascarlo.arch.packages.util.UtilSharedPreferences;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private Context context;
    private PackageSearchFragmentCallback packageSearchFragmentCallback;
    private TextInputLayout textInputLayout;
    private FloatingActionButton floatingActionButton;
    // keywords radio group
    private RadioButton radioButtonNameOrDescription;
    private RadioButton radioButtonExactName;
    private RadioButton radioButtonDescription;
    // repo check boxes
    private CheckBox checkBoxRepoCommunityTesting;
    private CheckBox checkBoxRepoCore;
    private CheckBox checkBoxRepoExtra;
    private CheckBox checkBoxRepoTesting;
    private CheckBox checkBoxRepoMultilib;
    private CheckBox checkBoxRepoMultilibTesting;
    private CheckBox checkBoxRepoCommunity;
    // arch check boxes
    private CheckBox checkBoxArchAny;
    private CheckBox checkBoxArchX84_64;
    // flagged check boxes
    private CheckBox checkBoxFlagged;
    private CheckBox checkBoxNotFlagged;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        textInputLayout = rootView.findViewById(R.id.fragment_search_keywords_text_input_layout);
        floatingActionButton = rootView.findViewById(R.id.fragment_search_fab);
        // keywords radio group
        RadioGroup radioGroup = rootView.findViewById(R.id.fragment_search_keywords_radio_group);
        radioButtonNameOrDescription = radioGroup.findViewById(R.id.fragment_search_name_or_description_radio_button);
        radioButtonExactName = radioGroup.findViewById(R.id.fragment_search_exact_name_radio_button);
        radioButtonDescription = radioGroup.findViewById(R.id.fragment_search_description_radio_button);
        // repo check boxes
        // core
        checkBoxRepoCore = rootView.findViewById(R.id.fragment_search_repo_core_check_box);
        checkBoxRepoCore.setOnCheckedChangeListener(this);
        checkBoxRepoCore.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_core),
                context.getResources().getBoolean(R.bool.key_repo_core_default_value)));
        // extra
        checkBoxRepoExtra = rootView.findViewById(R.id.fragment_search_repo_extra_check_box);
        checkBoxRepoExtra.setOnCheckedChangeListener(this);
        checkBoxRepoExtra.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_extra),
                context.getResources().getBoolean(R.bool.key_repo_extra_default_value)));
        // testing
        checkBoxRepoTesting = rootView.findViewById(R.id.fragment_search_repo_testing_check_box);
        checkBoxRepoTesting.setOnCheckedChangeListener(this);
        checkBoxRepoTesting.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_testing),
                context.getResources().getBoolean(R.bool.key_repo_testing_default_value)));
        // multilib
        checkBoxRepoMultilib = rootView.findViewById(R.id.fragment_search_repo_multilib_check_box);
        checkBoxRepoMultilib.setOnCheckedChangeListener(this);
        checkBoxRepoMultilib.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_multilib),
                context.getResources().getBoolean(R.bool.key_repo_multilib_default_value)));
        // multilib-testing
        checkBoxRepoMultilibTesting = rootView.findViewById(R.id.fragment_search_repo_multilib_testing_check_box);
        checkBoxRepoMultilibTesting.setOnCheckedChangeListener(this);
        checkBoxRepoMultilibTesting.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_multilib_testing),
                context.getResources().getBoolean(R.bool.key_repo_multilib_testing_default_value)));
        // community
        checkBoxRepoCommunity = rootView.findViewById(R.id.fragment_search_repo_community_check_box);
        checkBoxRepoCommunity.setOnCheckedChangeListener(this);
        checkBoxRepoCommunity.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_community),
                context.getResources().getBoolean(R.bool.key_repo_community_default_value)));
        // community-testing
        checkBoxRepoCommunityTesting = rootView.findViewById(R.id.fragment_search_repo_community_testing_check_box);
        checkBoxRepoCommunityTesting.setOnCheckedChangeListener(this);
        checkBoxRepoCommunityTesting.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_community_testing),
                context.getResources().getBoolean(R.bool.key_repo_community_testing_default_value)));
        // arch check boxes
        // arch any
        checkBoxArchAny = rootView.findViewById(R.id.fragment_search_arch_any_check_box);
        checkBoxArchAny.setOnCheckedChangeListener(this);
        checkBoxArchAny.setChecked(getSharedPreferenceBoolean(getString(R.string.key_arch_any),
                context.getResources().getBoolean(R.bool.key_arch_any_default_value)));
        // arch x86_64
        checkBoxArchX84_64 = rootView.findViewById(R.id.fragment_search_arch_x86_64_check_box);
        checkBoxArchX84_64.setOnCheckedChangeListener(this);
        checkBoxArchX84_64.setChecked(getSharedPreferenceBoolean(getString(R.string.key_arch_x86_64),
                context.getResources().getBoolean(R.bool.key_arch_x86_64_default_value)));
        // flagged check boxes
        // flagged
        checkBoxFlagged = rootView.findViewById(R.id.fragment_search_flagged_check_box);
        checkBoxFlagged.setOnCheckedChangeListener(this);
        checkBoxFlagged.setChecked(getSharedPreferenceBoolean(getString(R.string.key_flagged),
                context.getResources().getBoolean(R.bool.key_flagged_default_value)));
        // not flagged
        checkBoxNotFlagged = rootView.findViewById(R.id.fragment_search_not_flagged_check_box);
        checkBoxNotFlagged.setOnCheckedChangeListener(this);
        checkBoxNotFlagged.setChecked(getSharedPreferenceBoolean(getString(R.string.key_not_flagged),
                context.getResources().getBoolean(R.bool.key_not_flagged_default_value)));
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // search action
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textInputLayout.getEditText() != null
                        && textInputLayout.getEditText().getText() != null
                        && !TextUtils.isEmpty(textInputLayout.getEditText().getText().toString())) {
                    onFabClicked(
                            getKeywordsParameter(),
                            textInputLayout.getEditText().getText().toString(),
                            getListRepo(),
                            getListArch(),
                            getListFlagged());
                }
            }
        });
    }

    private void onFabClicked(int keywordsParameter,
                              String keywords,
                              ArrayList<String> listRepo,
                              ArrayList<String> listArch,
                              ArrayList<String> listFlagged) {
        if (packageSearchFragmentCallback != null) {
            packageSearchFragmentCallback.onSearchFragmentCallbackFabClicked(
                    keywordsParameter,
                    keywords,
                    listRepo,
                    listArch,
                    listFlagged);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof PackageSearchFragmentCallback) {
            packageSearchFragmentCallback = (PackageSearchFragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PackageSearchFragmentCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        packageSearchFragmentCallback = null;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == checkBoxRepoCore) {
            setSharedPreferenceBoolean(getString(R.string.key_repo_core), isChecked);
        } else if (buttonView == checkBoxRepoExtra) {
            setSharedPreferenceBoolean(getString(R.string.key_repo_extra), isChecked);
        } else if (buttonView == checkBoxRepoTesting) {
            setSharedPreferenceBoolean(getString(R.string.key_repo_testing), isChecked);
        } else if (buttonView == checkBoxRepoMultilib) {
            setSharedPreferenceBoolean(getString(R.string.key_repo_multilib), isChecked);
        } else if (buttonView == checkBoxRepoMultilibTesting) {
            setSharedPreferenceBoolean(getString(R.string.key_repo_multilib_testing), isChecked);
        } else if (buttonView == checkBoxRepoCommunity) {
            setSharedPreferenceBoolean(getString(R.string.key_repo_community), isChecked);
        } else if (buttonView == checkBoxRepoCommunityTesting) {
            setSharedPreferenceBoolean(getString(R.string.key_repo_community_testing), isChecked);
        } else if (buttonView == checkBoxArchAny) {
            setSharedPreferenceBoolean(getString(R.string.key_arch_any), isChecked);
        } else if (buttonView == checkBoxArchX84_64) {
            setSharedPreferenceBoolean(getString(R.string.key_arch_x86_64), isChecked);
        } else if (buttonView == checkBoxFlagged) {
            setSharedPreferenceBoolean(getString(R.string.key_flagged), isChecked);
        } else if (buttonView == checkBoxNotFlagged) {
            setSharedPreferenceBoolean(getString(R.string.key_not_flagged), isChecked);
        }
    }

    private int getKeywordsParameter() {
        // keywords parameter
        int keywordParameter;
        if (radioButtonNameOrDescription.isChecked()) {
            keywordParameter = UtilConstants.SEARCH_KEYWORDS_PARAMETER_NAME_OR_DESCRIPTION;
        } else if (radioButtonExactName.isChecked()) {
            keywordParameter = UtilConstants.SEARCH_KEYWORDS_PARAMETER_EXACT_NAME;
        } else if (radioButtonDescription.isChecked()) {
            keywordParameter = UtilConstants.SEARCH_KEYWORDS_PARAMETER_DESCRIPTION;
        } else {
            keywordParameter = UtilConstants.SEARCH_KEYWORDS_PARAMETER_NAME_OR_DESCRIPTION;
        }
        return keywordParameter;
    }

    private ArrayList<String> getListRepo() {
        // repo list
        ArrayList<String> listRepo = new ArrayList<>();
        if (checkBoxRepoCore.isChecked()) {
            listRepo.add(getString(R.string.core));
        }
        if (checkBoxRepoExtra.isChecked()) {
            listRepo.add(getString(R.string.extra));
        }
        if (checkBoxRepoTesting.isChecked()) {
            listRepo.add(getString(R.string.testing));
        }
        if (checkBoxRepoMultilib.isChecked()) {
            listRepo.add(getString(R.string.multilib));
        }
        if (checkBoxRepoMultilibTesting.isChecked()) {
            listRepo.add(getString(R.string.multilib_testing));
        }
        if (checkBoxRepoCommunity.isChecked()) {
            listRepo.add(getString(R.string.community));
        }
        if (checkBoxRepoCommunityTesting.isChecked()) {
            listRepo.add(getString(R.string.community_testing));
        }
        return listRepo;
    }

    private ArrayList<String> getListArch() {
        // arch list
        ArrayList<String> listArch = new ArrayList<>();
        if (checkBoxArchAny.isChecked()) {
            listArch.add(getString(R.string.arch_any));
        }
        if (checkBoxArchX84_64.isChecked()) {
            listArch.add(getString(R.string.arch_x86_64));
        }
        return listArch;
    }

    private ArrayList<String> getListFlagged() {
        // flagged list
        ArrayList<String> listFlagged = new ArrayList<>();
        if (checkBoxFlagged.isChecked()) {
            listFlagged.add(getString(R.string.flagged));
        }
        if (checkBoxNotFlagged.isChecked()) {
            listFlagged.add(getString(R.string.not_flagged));
        }
        return listFlagged;
    }

    private boolean getSharedPreferenceBoolean(String string, boolean b) {
        return UtilSharedPreferences.getSharedPreferenceBoolean(context, string, b);
    }

    private void setSharedPreferenceBoolean(String string, boolean b) {
        UtilSharedPreferences.setSharedPreferenceBoolean(context, string, b);
    }

    public interface PackageSearchFragmentCallback {

        void onSearchFragmentCallbackFabClicked(int keywordsParameter,
                                                String keywords,
                                                ArrayList<String> listRepo,
                                                ArrayList<String> listArch,
                                                ArrayList<String> listFlagged);
    }
}