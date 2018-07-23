package com.test.xyz.demo.presentation.common.util;

import android.util.Log;

public class LoggerProxy implements Logger {

    @Override
    public void info(String tag, String message) {
        Log.i(tag, message);
    }

    @Override
    public void error(String tag, String message, Throwable throwable) {
        Log.e(tag, message, throwable);
    }
}
