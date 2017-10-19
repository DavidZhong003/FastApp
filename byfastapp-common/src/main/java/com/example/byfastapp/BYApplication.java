package com.example.byfastapp;

import android.app.Application;

import com.example.byfastapp.utils.LogUtils;
import com.example.byfastapp.utils.Utils;

/**
 * Created by admin on 2017/9/11.
 * 简单application ,提供全局context
 */

public class BYApplication
        extends Application {

    private static Application sApplication;

    private static Application getInstance() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        sApplication = this;
        Utils.init(this);//工具类初始化配置
        initLog();
    }

    /**
     * 日志类初始化
     */
    private void initLog() {
        LogUtils.Config config = LogUtils.getConfig()
                                         .setLogSwitch(BuildConfig.DEBUG)//log 总开关
                                         .setLogSwitch(true)
                                         .setConsoleSwitch(BuildConfig.DEBUG)//输出总开关
                                         .setConsoleSwitch(true)
                                         .setGlobalTag(null)//全局标签
                                         // 当全局标签不为空时，我们输出的log全部为该tag，
                                         // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                                         .setLogHeadSwitch(true)// 设置log头信息开关，默认为开
                                         .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                                         .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                                         .setFilePrefix("")// 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
                                         .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                                         .setConsoleFilter(LogUtils.V)// log的控制台过滤器，和logcat过滤器同理，默认Verbose
                                         .setFileFilter(LogUtils.V)// log文件过滤器，和logcat过滤器同理，默认Verbose
                                         .setStackDeep(1);// log栈深度，默认为1
//        LogUtils.d(config.toString());

    }
}
