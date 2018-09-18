package com.xia.fly.http.cookie.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class MemoryCookieStore implements CookieStore {
    private final HashMap<String, List<Cookie>> allCookies = new HashMap<>();

    @Override
    public void add(HttpUrl url, List<Cookie> cookies) {
        final List<Cookie> oldCookies = allCookies.get(url.host());
        if (oldCookies != null) {
            final Iterator<Cookie> itNew = cookies.iterator();
            final Iterator<Cookie> itOld = oldCookies.iterator();
            while (itNew.hasNext()) {
                final String va = itNew.next().name();
                while (va != null && itOld.hasNext()) {
                    final String v = itOld.next().name();
                    if (v != null && va.equals(v)) {
                        itOld.remove();
                    }
                }
            }
            oldCookies.addAll(cookies);
        } else {
            allCookies.put(url.host(), cookies);
        }
    }

    @Override
    public List<Cookie> get(HttpUrl uri) {
        List<Cookie> cookies = allCookies.get(uri.host());
        if (cookies == null) {
            cookies = new ArrayList<>();
            allCookies.put(uri.host(), cookies);
        }
        return cookies;
    }

    @Override
    public boolean removeAll() {
        allCookies.clear();
        return true;
    }

    @Override
    public List<Cookie> getCookies() {
        final List<Cookie> cookies = new ArrayList<>();
        final Set<String> httpUrls = allCookies.keySet();
        for (String url : httpUrls) {
            cookies.addAll(allCookies.get(url));
        }
        return cookies;
    }

    @Override
    public boolean remove(HttpUrl uri, Cookie cookie) {
        final List<Cookie> cookies = allCookies.get(uri.host());
        return cookie != null && cookies.remove(cookie);
    }
}
