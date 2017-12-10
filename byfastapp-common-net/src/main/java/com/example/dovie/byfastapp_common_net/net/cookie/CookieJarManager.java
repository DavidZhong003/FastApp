package com.example.dovie.byfastapp_common_net.net.cookie;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 *
 * @author doive
 * on 2017/10/25 17:00
 */

public class CookieJarManager implements CookieJar {
    private final Map<HttpUrl,List<Cookie>> cookieStore = new LinkedHashMap<>();
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.put(url,cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies==null?new ArrayList<Cookie>():cookies;
    }
}
