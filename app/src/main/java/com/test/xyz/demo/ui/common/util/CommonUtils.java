package com.test.xyz.demo.ui.common.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class CommonUtils {
    public static void showDefaultAlert(Activity activity, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();

        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }

    public static void showToastMessage(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
