package com.test.xyz.demo.presentation.common.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class UIHelper {
    public static void showToastMessage(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();

        if (view == null) {
            return;
        }

        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public interface Constants {
        String REPO_OWNER = "google";
        String REPO_TITLE = "Repo_Title";
    }
}
