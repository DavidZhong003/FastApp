package com.example.dovie.byfastapp_common_net.net.interceptor;


import java.io.IOException;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by admin on 2017/9/14. 废弃
 * 日志拦截器
 *   (网络拦截器)
 *   网络拦截器&应用拦截器的区别:
 *      Application interceptors:
 *          只会调用一次,即使响应从缓存获得
 *          只关心最开始的请求和最后的结果
 *          不用关心重定向/重试操作
 *      Network Interceptors:
 *          排在第 6 个拦截器中，因此会经过 RetryAndFollowup 进行失败重试或者重定向，可以操作中间的resposne
 *          不调用缓存的响应
 *          观察数据
 *          排在链接拦截器后面.
 *
 *     请求---->Application Interceptors(应用拦截器)
 *             ------->RetryAndFollowInterceptors(失败重试拦截器)
 *                   ----->BridgeInterceptor(请求/响应转化拦截器)
 *                          ------>CacheInterceptor(缓存拦截器)
 *                                  ------>ConnectInterceptor(服务器连接拦截器)
 *                                          ------>Network Interceptor(网络拦截器)
 *                                               ----->CallServerInterceptor(数据传输拦截器)
 *                                               <-----CallServerInterceptor(数据传输拦截器)
 *                                          <------Network Interceptor(网络拦截器)
 *                                  <------ConnectInterceptor(服务器连接拦截器)
 *                         <------CacheInterceptor(缓存拦截器)
 *                  <------BridgeInterceptor(请求/响应转化拦截器)
 *             <------RetryAndFollowInterceptors(失败重试拦截器)
 *      响应<------Application Interceptors(应用拦截器)
 */

@Deprecated
public class LogInterceptor implements Interceptor {
    private static final String TAG = "网络日志查看器";
    @Override
    public Response intercept(Chain chain)
            throws IOException
    {
        Request request = chain.request();
        //请求前--打印请求信息
        long t1 = System.nanoTime();
        printRequestInformation(request.url(),chain.connection(),request.headers());
        //网络响应
        Response response = chain.proceed(request);
        //网络响应后--打印响应信息
        long t2 = System.nanoTime();
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        printResponseInformation(response.request().url(), response.headers(), (t2 - t1) / 1e6d, responseBody.string());
        return response;

    }

    private void printResponseInformation(HttpUrl url,
                                          Headers headers,
                                          double v,
                                          String responseBody)
    {
//        LogUtils.d(TAG,"相应地址",url,"响应时间",v,"响应头",headers);
//        LogUtils.json(TAG,responseBody);
    }

    private void printRequestInformation(HttpUrl url, Connection connection, Headers headers) {
//        LogUtils.d(TAG,"请求地址",url,"连接状态",connection,"请求头",headers);
    }

}
