package com.example.dovie.byfastapp_common_net.net;

import com.example.dovie.byfastapp_common_net.net.interceptor.GzipRequestInterceptor;
import com.example.dovie.byfastapp_common_net.net.interceptor.logInterceptor.Level;
import com.example.dovie.byfastapp_common_net.net.interceptor.logInterceptor.LoggingInterceptor;

import okhttp3.Interceptor;
import okhttp3.internal.platform.Platform;

/**
 * Created by admin on 2017/10/17.
 */

public class InterceptorHelper {

    private InterceptorHelper(){

    }

    public static Interceptor creatLoggingInterceptor(boolean ableLog){
        return new LoggingInterceptor.Builder()
                .loggable(ableLog)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .build();
    }

    public static Interceptor creatGzipInterceptor(boolean ableLog){
        return new GzipRequestInterceptor();
    }
}
