package com.rascarlo.arch.packages.callbacks;

import java.util.ArrayList;

public interface SearchFragmentCallback {

    void onSearchFragmentCallbackOnFabClicked(int keywordsParameter,
                                              String keywords,
                                              ArrayList<String> listRepo,
                                              ArrayList<String> listArch,
                                              String flagged);
}
