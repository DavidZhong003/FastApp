package com.example.dovie.byfastapp_common_net.net;

import com.example.dovie.byfastapp_common_net.net.cookie.CookieJarManager;
import com.example.dovie.byfastapp_common_net.net.interceptor.GzipRequestInterceptor;
import com.example.dovie.byfastapp_common_net.net.interceptor.logInterceptor.Level;
import com.example.dovie.byfastapp_common_net.net.interceptor.logInterceptor.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;

/**
 * Created by admin on 2017/9/14.
 * RetrofitManager
 *   拦截器可选配置
 *   数据类统一
 *
 *
 */

public class RetrofitManager {
    private static volatile RetrofitManager mRetrofitManager;
    private Retrofit mRetrofit;

    public static RetrofitManager getInstance() {
        if (mRetrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (mRetrofitManager == null) {
                    mRetrofitManager = new RetrofitManager();
                }
            }
        }
        return mRetrofitManager;
    }

    private OkHttpClient mOkHttpClient;

    private RetrofitManager() {
        initOkHttpClient();
        initRetrofit();
    }

    private void initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .build();
    }

    /**
     * 初始化OkttpClient
     */
    private void initOkHttpClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    //失败重连
                    .retryOnConnectionFailure(true)
                    //连接超时
                    .connectTimeout(15, TimeUnit.SECONDS)
                    //写超时
                    .writeTimeout(30, TimeUnit.SECONDS)
                    //读超时
                    .readTimeout(30, TimeUnit.SECONDS)
                    //cookie jar
                    .cookieJar(new CookieJarManager())
                    .addNetworkInterceptor(new LoggingInterceptor.Builder().loggable(true)
                                                                           .setLevel(Level.BASIC)
                                                                           .log(Platform.INFO)
                                                                           .request("Request")
                                                                           .response("Response").build())
                    .addNetworkInterceptor(new GzipRequestInterceptor())
                    .build();
        }
    }


}
