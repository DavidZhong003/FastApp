package com.example.admin.byfastapp;

import com.antfortune.freeline.FreelineCore;
import com.antfortune.freeline.IDynamic;
import com.example.byfastapp.BYApplication;

import java.util.HashMap;

/**
 * Created by admin on 2017/9/21.
 */

public class MyApplication extends BYApplication {
    @Override
    public void onCreate() {
        FreelineCore.init(this, new IDynamic() {
            @Override
            public boolean applyDynamicRes(HashMap<String, String> hashMap) {
                return false;
            }

            @Override
            public String getOriginResPath(String s) {
                return null;
            }

            @Override
            public void clearResourcesCache() {

            }
        });
        super.onCreate();
    }
}
