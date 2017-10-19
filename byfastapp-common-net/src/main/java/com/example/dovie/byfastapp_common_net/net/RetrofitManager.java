package com.example.dovie.byfastapp_common_net.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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
    }

    private void initOkHttpClient() {
        if (mOkHttpClient==null){
            mOkHttpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(30,TimeUnit.SECONDS)
                    .readTimeout(30,TimeUnit.SECONDS)
//                    .addNetworkInterceptor()
                    .build();

        }
    }

    private void getmsg(){
        Retrofit retrofit = new Retrofit.Builder()
                 .client(mOkHttpClient).build();
    }

}
