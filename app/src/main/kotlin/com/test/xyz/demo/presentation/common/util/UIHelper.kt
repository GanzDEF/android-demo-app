package com.test.xyz.demo.presentation.common.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

object UIHelper {
    fun showToastMessage(activity: Activity, message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun hideKeyboard(activity: Activity) {
        val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val view = activity.currentFocus ?: return

        inputManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS)
    }

    interface Constants {
        companion object {
            val PROJECT_OWNER = "google"
            val PROJECT_TITLE = "Repo_Title"
        }
    }
}
