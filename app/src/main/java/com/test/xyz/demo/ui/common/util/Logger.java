package com.test.xyz.demo.ui.common.util;

public interface Logger {
    void info(String tag, String message);
    void error(String tag, String message, Throwable throwable);
}
