package com.payumoney.sdkui.ui.events;

import android.app.Activity;
import android.support.v4.app.Fragment;
import com.payumoney.sdkui.ui.utils.ResultModel;

public interface FragmentCallbacks {
    void clearAllFragments();

    void dismissProgressDialog();

    void displayTerms(Activity activity);

    String getAmount();

    String getEmail();

    String getMobile();

    int getStyle();

    void navigateTo(Fragment fragment, int i);

    void navigateWithReplace(Fragment fragment, int i);

    void navigateWithReplaceBackStack(Fragment fragment, int i);

    void popBackStackTillTag(String str);

    void processAndShowResult(ResultModel resultModel, boolean z);

    void showProgressDialog(boolean z, String str);

    void updateProgressMsg(String str);
}
