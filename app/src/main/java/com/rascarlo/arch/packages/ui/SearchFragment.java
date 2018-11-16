package com.rascarlo.arch.packages.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.rascarlo.arch.packages.R;
import com.rascarlo.arch.packages.callbacks.SearchFragmentCallback;
import com.rascarlo.arch.packages.databinding.FragmentSearchBinding;
import com.rascarlo.arch.packages.util.ArchPackagesConstants;
import com.rascarlo.arch.packages.util.ArchPackagesSharedPreferences;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
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
    private RadioGroup radioGroupFlag;
    private FragmentSearchBinding fragmentSearchBinding;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false);
        return fragmentSearchBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (fragmentSearchBinding != null) {
            textInputLayout = fragmentSearchBinding.fragmentSearchKeywordsLayout.searchKeywordsTextInputLayout;
            // keywords radio group
            radioGroupKeywords = fragmentSearchBinding.fragmentSearchKeywordsLayout.searchKeywordsRadioGroup;
            // repo check boxes
            checkBoxRepoCore = fragmentSearchBinding.fragmentSearchRepoLayout.searchCheckBoxRepoCore;
            checkBoxRepoExtra = fragmentSearchBinding.fragmentSearchRepoLayout.searchCheckBoxRepoExtra;
            checkBoxRepoTesting = fragmentSearchBinding.fragmentSearchRepoLayout.searchCheckBoxRepoTesting;
            checkBoxRepoMultilib = fragmentSearchBinding.fragmentSearchRepoLayout.searchCheckBoxRepoMultilib;
            checkBoxRepoMultilibTesting = fragmentSearchBinding.fragmentSearchRepoLayout.searchCheckBoxRepoMultilibTesting;
            checkBoxRepoCommunity = fragmentSearchBinding.fragmentSearchRepoLayout.searchCheckBoxRepoCommunity;
            checkBoxRepoCommunityTesting = fragmentSearchBinding.fragmentSearchRepoLayout.searchCheckBoxRepoCommunityTesting;
            // arch check boxes
            checkBoxArchAny = fragmentSearchBinding.fragmentSearchArchLayout.searchCheckBoxArchAny;
            checkBoxArchX84_64 = fragmentSearchBinding.fragmentSearchArchLayout.searchCheckBoxArchX8664;
            // flagged radio buttons
            radioGroupFlag = fragmentSearchBinding.fragmentSearchFlagLayout.searchFlagRadioGroup;
            // search fab
            FloatingActionButton floatingActionButton = fragmentSearchBinding.fragmentSearchFab;
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            floatingActionButton.setOnClickListener(v -> {
                if (inputMethodManager != null)
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                onFabClicked(
                        getKeywordsParameter(),
                        getQuery(),
                        getListRepo(),
                        getListArch(),
                        getFlagged());
            });
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // show action settings if the callback is not null
        menu.findItem(R.id.menu_search_action_settings).setVisible(searchFragmentCallback != null);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search_action_settings:
                if (searchFragmentCallback != null) {
                    searchFragmentCallback.onSearchFragmentCallbackOnMenuActionSettingsClicked();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
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
    public void onResume() {
        setUpKeywords();
        setUpRepo();
        setUpArch();
        setUpFlagged();
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        searchFragmentCallback = null;
    }

    private void setUpKeywords() {
        RadioButton radioButtonNameOrDescription = radioGroupKeywords.findViewById(R.id.search_radio_button_name_or_description);
        RadioButton radioButtonExactName = radioGroupKeywords.findViewById(R.id.search_radio_button_exact_name);
        RadioButton radioButtonDescription = radioGroupKeywords.findViewById(R.id.search_radio_button_description);
        String sharedPreferenceKeywords = ArchPackagesSharedPreferences.getSharedPreferenceString(context,
                getString(R.string.key_keywords), getString(R.string.key_keywords_name_or_description));
        if (TextUtils.equals(sharedPreferenceKeywords, getString(R.string.key_keywords_name_or_description))) {
            radioButtonNameOrDescription.setChecked(true);
        } else if (TextUtils.equals(sharedPreferenceKeywords, getString(R.string.key_keywords_exact_name))) {
            radioButtonExactName.setChecked(true);
        } else if (TextUtils.equals(sharedPreferenceKeywords, getString(R.string.key_keywords_description))) {
            radioButtonDescription.setChecked(true);
        } else {
            radioButtonNameOrDescription.setChecked(true);
        }
    }

    private void setUpRepo() {
        // core
        checkBoxRepoCore.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_core),
                getResources().getBoolean(R.bool.key_repo_core_default_value)));
        // extra
        checkBoxRepoExtra.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_extra),
                getResources().getBoolean(R.bool.key_repo_extra_default_value)));
        // testing
        checkBoxRepoTesting.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_testing),
                getResources().getBoolean(R.bool.key_repo_testing_default_value)));
        // multilib
        checkBoxRepoMultilib.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_multilib),
                getResources().getBoolean(R.bool.key_repo_multilib_default_value)));
        // multilib-testing
        checkBoxRepoMultilibTesting.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_multilib_testing),
                getResources().getBoolean(R.bool.key_repo_multilib_testing_default_value)));
        // community
        checkBoxRepoCommunity.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_community),
                getResources().getBoolean(R.bool.key_repo_community_default_value)));
        // community-testing
        checkBoxRepoCommunityTesting.setChecked(getSharedPreferenceBoolean(getString(R.string.key_repo_community_testing),
                getResources().getBoolean(R.bool.key_repo_community_testing_default_value)));
    }

    private void setUpArch() {
        // arch any
        checkBoxArchAny.setChecked(getSharedPreferenceBoolean(getString(R.string.key_arch_any),
                getResources().getBoolean(R.bool.key_arch_any_default_value)));
        // arch x86_64
        checkBoxArchX84_64.setChecked(getSharedPreferenceBoolean(getString(R.string.key_arch_x86_64),
                getResources().getBoolean(R.bool.key_arch_x86_64_default_value)));
    }

    private void setUpFlagged() {
        RadioButton radioButtonFlaggedAll = radioGroupFlag.findViewById(R.id.search_radio_button_flagged_all);
        RadioButton radioButtonFlaggedFlagged = radioGroupFlag.findViewById(R.id.search_radio_button_flagged_flagged);
        RadioButton radioButtonFlaggedNotFlagged = radioGroupFlag.findViewById(R.id.search_radio_button_flagged_not_flagged);
        String sharedPreferenceFlag = ArchPackagesSharedPreferences.getSharedPreferenceString(context,
                getString(R.string.key_flag), getString(R.string.key_flag_flagged_all));
        if (TextUtils.equals(sharedPreferenceFlag, getString(R.string.key_flag_flagged_all))) {
            radioButtonFlaggedAll.setChecked(true);
        } else if (TextUtils.equals(sharedPreferenceFlag, getString(R.string.key_flag_flagged))) {
            radioButtonFlaggedFlagged.setChecked(true);
        } else if (TextUtils.equals(sharedPreferenceFlag, getString(R.string.key_flag_not_flagged))) {
            radioButtonFlaggedNotFlagged.setChecked(true);
        } else {
            radioButtonFlaggedAll.setChecked(true);
        }
    }

    private void onFabClicked(int keywordsParameter,
                              String keywords,
                              ArrayList<String> listRepo,
                              ArrayList<String> listArch,
                              String flagged) {
        if (searchFragmentCallback != null) {
            searchFragmentCallback.onSearchFragmentCallbackOnFabClicked(
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
        switch (radioGroupKeywords.getCheckedRadioButtonId()) {
            case R.id.search_radio_button_name_or_description:
                return ArchPackagesConstants.SEARCH_KEYWORDS_PARAMETER_NAME_OR_DESCRIPTION;
            case R.id.search_radio_button_exact_name:
                return ArchPackagesConstants.SEARCH_KEYWORDS_PARAMETER_EXACT_NAME;
            case R.id.search_radio_button_description:
                return ArchPackagesConstants.SEARCH_KEYWORDS_PARAMETER_DESCRIPTION;
            default:
                return ArchPackagesConstants.SEARCH_KEYWORDS_PARAMETER_NAME_OR_DESCRIPTION;
        }
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
        switch (radioGroupFlag.getCheckedRadioButtonId()) {
            case R.id.search_radio_button_flagged_all:
                return null;
            case R.id.search_radio_button_flagged_flagged:
                return getString(R.string.flagged);
            case R.id.search_radio_button_flagged_not_flagged:
                return getString(R.string.not_flagged);
            default:
                return null;
        }
    }

    private boolean getSharedPreferenceBoolean(String string, boolean defaultValue) {
        return ArchPackagesSharedPreferences.getSharedPreferenceBoolean(context, string, defaultValue);
    }
}