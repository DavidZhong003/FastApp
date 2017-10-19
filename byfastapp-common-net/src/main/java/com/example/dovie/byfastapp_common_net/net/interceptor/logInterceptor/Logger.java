package com.example.dovie.byfastapp_common_net.net.interceptor.logInterceptor;

import okhttp3.internal.platform.Platform;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Logger {
    void log(int level, String tag, String msg);

    Logger DEFAULT = new Logger() {
        @Override
        public void log(int level, String tag, String message) {
            Platform.get().log(level, message, null);
        }
    };
}