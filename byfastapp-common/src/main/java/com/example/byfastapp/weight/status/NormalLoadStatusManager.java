package com.example.byfastapp.weight.status;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by admin on 2017/10/13.
 * 管理Loading
 *    empty
 *    error状态页面
 */

public class NormalLoadStatusManager {
    private Map<String,IStatusView> mStatusViewMap = new LinkedHashMap<>();

    private static volatile NormalLoadStatusManager sManager;

    public static NormalLoadStatusManager getInstance(){
        if (sManager==null){
            synchronized (NormalLoadStatusManager.class){
                if (sManager==null){
                    sManager = new NormalLoadStatusManager();
                }
            }
        }
        return sManager;
    }


}
