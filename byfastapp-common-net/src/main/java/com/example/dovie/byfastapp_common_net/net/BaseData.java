package com.example.dovie.byfastapp_common_net.net;

import android.support.annotation.Keep;

/**
 * Created by admin on 2017/9/19.
 *
 */
@Keep
public class BaseData<T>{
    public int errorcode;
    public String errormsg;
    public T data;
}
