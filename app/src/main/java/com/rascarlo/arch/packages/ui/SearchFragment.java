package com.rascarlo.arch.packages.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.rascarlo.arch.packages.R;
import com.rascarlo.arch.packages.callbacks.SearchFragmentCallback;
import com.rascarlo.arch.packages.util.ArchPackagesConstants;
import com.rascarlo.arch.packages.util.ArchPackagesSharedPreferences;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {
    private Context context;
    private SearchFragmentCallback searchFragmentCallback;
    private TextInputLayout textInputLayout;
    // keywords radio group
    private RadioGroup radioGroupKeywords;
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
    private RadioGroup radioGroupFlagged;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        textInputLayout = rootView.findViewById(R.id.fragment_search_keywords_text_input_layout);
        // keywords radio group
        radioGroupKeywords = rootView.findViewById(R.id.fragment_search_keywords_radio_group);
        setUpKeywords();
        // repo check boxes
        setUpRepo(rootView);
        // arch check boxes
        setUpArch(rootView);
        // flagged radio buttons
        radioGroupFlagged = rootView.findViewById(R.id.fragment_search_flagged_radio_group);
        setUpFlagged();
        // search fab
        FloatingActionButton floatingActionButton = rootView.findViewById(R.id.fragment_search_fab);
        floatingActionButton.setOnClickListener(v -> onFabClicked(
                getKeywordsParameter(),
                getQuery(),
                getListRepo(),
                getListArch(),
                getFlagged()));
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof SearchFragmentCallback) {
            searchFragmentCallback = (SearchFragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SearchFragmentCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        searchFragmentCallback = null;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.fragment_search_check_box_repo_core:
                setSharedPreferenceBoolean(getString(R.string.key_repo_core), isChecked);
                break;
            case R.id.fragment_search_check_box_repo_extra:
                setSharedPreferenceBoolean(getString(R.string.key_repo_extra), isChecked);
                break;
            case R.id.fragment_search_check_box_repo_testing:
                setSharedPreferenceBoolean(getString(R.string.key_repo_testing), isChecked);
                break;
            case R.id.fragment_search_check_box_repo_multilib:
                setSharedPreferenceBoolean(getString(R.string.key_repo_multilib), isChecked);
                break;
            case R.id.fragment_search_check_box_repo_multilib_testing:
                setSharedPreferenceBoolean(getString(R.string.key_repo_multilib_testing), isChecked);
                break;
            case R.id.fragment_search_check_box_repo_community:
                setSharedPreferenceBoolean(getString(R.string.key_repo_community), isChecked);
                break;
            case R.id.fragment_search_check_box_repo_community_testing:
                setSharedPreferenceBoolean(getString(R.string.key_repo_community_testing), isChecked);
                break;
            case R.id.fragment_search_check_box_arch_any:
                setSharedPreferenceBoolean(getString(R.string.key_arch_any), isChecked);
                break;
            case R.id.fragment_search_check_box_arch_x86_64:
                setSharedPreferenceBoolean(getString(R.string.key_arch_x86_64), isChecked);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            // keywords
            case R.id.fragment_search_keywords_radio_group:
                switch (checkedId) {
                    case R.id.fragment_search_radio_button_name_or_description:
                        ArchPackagesSharedPreferences.setSharedPreferenceString(context,
                                getString(R.string.key_keywords),
                                getString(R.string.key_keywords_name_or_description));
                        break;
                    case R.id.fragment_search_radio_button_exact_name:
                        ArchPackagesSharedPreferences.setSharedPreferenceString(context,
                                getString(R.string.key_keywords),
                                getString(R.string.key_keywords_exact_name));
                        break;
                    case R.id.fragment_search_radio_button_description:
                        ArchPackagesSharedPreferences.setSharedPreferenceString(context,
                                getString(R.string.key_keywords),
                                getString(R.string.key_keywords_description));
                        break;
                }
                break;
            // flagged
            case R.id.fragment_search_flagged_radio_group:
                switch (checkedId) {
                    case R.id.fragment_search_radio_button_flagged_all:
                        ArchPackagesSharedPreferences.setSharedPreferenceString(context,
                                getString(R.string.key_flagged),
                                getString(R.string.key_flagged_all));
                        break;
                    case R.id.fragment_search_radio_button_flagged_flagged:
                        ArchPackagesSharedPreferences.setSharedPreferenceString(context,
                                getString(R.string.key_flagged),
                                getString(R.string.key_flagged_flagged));
                        break;
                    case R.id.fragment_search_radio_button_flagged_not_flagged:
                        ArchPackagesSharedPreferences.setSharedPreferenceString(context,
                                getString(R.string.key_flagged),
                                getString(R.string.key_flagged_not_flagged));
                        break;
                }
                break;
        }
    }

    private void setUpKeywords() {
        RadioButton radioButtonNameOrDescription = radioGroupKeywords.findViewById(R.id.fragment_search_radio_button_name_or_description);
        RadioButton radioButtonExactName = radioGroupKeywords.findViewById(R.id.fragment_search_radio_button_exact_name);
        RadioButton radioButtonDescription = radioGroupKeywords.findViewById(R.id.fragment_search_radio_button_description);
        String sharedPreferenceKeywords = ArchPackagesSharedPreferences.getSharedPreferenceString(context,
                getString(R.string.key_keywords), getString(R.string.key_keywords_name_or_description));
        if (TextUtils.equals(sharedPreferenceKeywords, getString(R.string.key_keywords_name_or_description))) {
            radioButtonNameOrDescription.setChecked(true);
        } else if (TextUtils.equals(sharedPreferenceKeywords, getString(R.string.key_keywords_exact_name))) {
            radioButtonExactName.setChecked(true);
        } else if (TextUtils.equals(sharedPreferenceKeywords, getString(R.string.key_keywords_description))) {
            radioButtonDescription.setChecked(true);
        }
        radioGroupKeywords.setOnCheckedChangeListener(this);
    }

    private void setUpRepo(View rootView) {
        // core
        checkBoxRepoCore = rootView.findViewById(R.id.fragment_search_check_box_repo_core);
        checkBoxRepoCore.setOnCheckedChangeListener(this);
        checkBoxRepoCore.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_core),
                getResources().getBoolean(R.bool.key_repo_core_default_value)));
        // extra
        checkBoxRepoExtra = rootView.findViewById(R.id.fragment_search_check_box_repo_extra);
        checkBoxRepoExtra.setOnCheckedChangeListener(this);
        checkBoxRepoExtra.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_extra),
                getResources().getBoolean(R.bool.key_repo_extra_default_value)));
        // testing
        checkBoxRepoTesting = rootView.findViewById(R.id.fragment_search_check_box_repo_testing);
        checkBoxRepoTesting.setOnCheckedChangeListener(this);
        checkBoxRepoTesting.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_testing),
                getResources().getBoolean(R.bool.key_repo_testing_default_value)));
        // multilib
        checkBoxRepoMultilib = rootView.findViewById(R.id.fragment_search_check_box_repo_multilib);
        checkBoxRepoMultilib.setOnCheckedChangeListener(this);
        checkBoxRepoMultilib.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_multilib),
                getResources().getBoolean(R.bool.key_repo_multilib_default_value)));
        // multilib-testing
        checkBoxRepoMultilibTesting = rootView.findViewById(R.id.fragment_search_check_box_repo_multilib_testing);
        checkBoxRepoMultilibTesting.setOnCheckedChangeListener(this);
        checkBoxRepoMultilibTesting.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_multilib_testing),
                getResources().getBoolean(R.bool.key_repo_multilib_testing_default_value)));
        // community
        checkBoxRepoCommunity = rootView.findViewById(R.id.fragment_search_check_box_repo_community);
        checkBoxRepoCommunity.setOnCheckedChangeListener(this);
        checkBoxRepoCommunity.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_community),
                getResources().getBoolean(R.bool.key_repo_community_default_value)));
        // community-testing
        checkBoxRepoCommunityTesting = rootView.findViewById(R.id.fragment_search_check_box_repo_community_testing);
        checkBoxRepoCommunityTesting.setOnCheckedChangeListener(this);
        checkBoxRepoCommunityTesting.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_community_testing),
                getResources().getBoolean(R.bool.key_repo_community_testing_default_value)));
    }

    private void setUpArch(View rootView) {
        // arch any
        checkBoxArchAny = rootView.findViewById(R.id.fragment_search_check_box_arch_any);
        checkBoxArchAny.setOnCheckedChangeListener(this);
        checkBoxArchAny.setChecked(getSharedPreferenceBoolean(getString(R.string.key_arch_any),
                getResources().getBoolean(R.bool.key_arch_any_default_value)));
        // arch x86_64
        checkBoxArchX84_64 = rootView.findViewById(R.id.fragment_search_check_box_arch_x86_64);
        checkBoxArchX84_64.setOnCheckedChangeListener(this);
        checkBoxArchX84_64.setChecked(getSharedPreferenceBoolean(getString(R.string.key_arch_x86_64),
                getResources().getBoolean(R.bool.key_arch_x86_64_default_value)));
    }

    private void setUpFlagged() {
        RadioButton radioButtonFlaggedAll = radioGroupFlagged.findViewById(R.id.fragment_search_radio_button_flagged_all);
        RadioButton radioButtonFlaggedFlagged = radioGroupFlagged.findViewById(R.id.fragment_search_radio_button_flagged_flagged);
        RadioButton radioButtonFlaggedNotFlagged = radioGroupFlagged.findViewById(R.id.fragment_search_radio_button_flagged_not_flagged);
        String sharedPreferenceFlagged = ArchPackagesSharedPreferences.getSharedPreferenceString(context,
                getString(R.string.key_flagged), getString(R.string.key_flagged_all));
        if (TextUtils.equals(sharedPreferenceFlagged, getString(R.string.key_flagged_all))) {
            radioButtonFlaggedAll.setChecked(true);
        } else if (TextUtils.equals(sharedPreferenceFlagged, getString(R.string.key_flagged_flagged))) {
            radioButtonFlaggedFlagged.setChecked(true);
        } else if (TextUtils.equals(sharedPreferenceFlagged, getString(R.string.key_flagged_not_flagged))) {
            radioButtonFlaggedNotFlagged.setChecked(true);
        }
        radioGroupFlagged.setOnCheckedChangeListener(this);
    }

    private void onFabClicked(int keywordsParameter,
                              String keywords,
                              ArrayList<String> listRepo,
                              ArrayList<String> listArch,
                              String flagged) {
        if (searchFragmentCallback != null) {
            searchFragmentCallback.onSearchFragmentCallbackFabClicked(
                    keywordsParameter,
                    keywords,
                    listRepo,
                    listArch,
                    flagged);
        }
    }

    private String getQuery() {
        return textInputLayout.getEditText() != null
                && textInputLayout.getEditText().getText() != null
                && !TextUtils.isEmpty(textInputLayout.getEditText().getText().toString()) ? textInputLayout.getEditText().getText().toString() : null;
    }

    private int getKeywordsParameter() {
        // keywords parameter
        int keywordParameter;
        switch (radioGroupKeywords.getCheckedRadioButtonId()) {
            case R.id.fragment_search_radio_button_name_or_description:
                keywordParameter = ArchPackagesConstants.SEARCH_KEYWORDS_PARAMETER_NAME_OR_DESCRIPTION;
                break;
            case R.id.fragment_search_radio_button_exact_name:
                keywordParameter = ArchPackagesConstants.SEARCH_KEYWORDS_PARAMETER_EXACT_NAME;
                break;
            case R.id.fragment_search_radio_button_description:
                keywordParameter = ArchPackagesConstants.SEARCH_KEYWORDS_PARAMETER_DESCRIPTION;
                break;
            default:
                keywordParameter = ArchPackagesConstants.SEARCH_KEYWORDS_PARAMETER_NAME_OR_DESCRIPTION;
                break;
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

    private String getFlagged() {
        String flagged;
        switch (radioGroupFlagged.getCheckedRadioButtonId()) {
            case R.id.fragment_search_radio_button_flagged_all:
                flagged = null;
                break;
            case R.id.fragment_search_radio_button_flagged_flagged:
                flagged = getString(R.string.flagged);
                break;
            case R.id.fragment_search_radio_button_flagged_not_flagged:
                flagged = getString(R.string.not_flagged);
                break;
            default:
                flagged = null;
        }
        return flagged;
    }

    private boolean getSharedPreferenceBoolean(String string, boolean defaultValue) {
        return ArchPackagesSharedPreferences.getSharedPreferenceBoolean(context, string, defaultValue);
    }

    private void setSharedPreferenceBoolean(String string, boolean value) {
        ArchPackagesSharedPreferences.setSharedPreferenceBoolean(context, string, value);
    }
}