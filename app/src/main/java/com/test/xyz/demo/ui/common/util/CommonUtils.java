package com.test.xyz.demo.ui.common.util;

import android.app.Activity;
import android.widget.Toast;

public class CommonUtils {
    public static void showToastMessage(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
