package com.xia.fly.http.cookie;

import android.support.annotation.NonNull;

import com.xia.fly.http.cookie.store.CookieStore;
import com.xia.fly.utils.Preconditions;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by zhy on 16/3/10.
 */
public class CookieJarImpl implements CookieJar {
    private final CookieStore cookieStore;

    public CookieJarImpl(@NonNull CookieStore cookieStore) {
        Preconditions.checkNotNull(cookieStore, "cookieStore can not be null");
        this.cookieStore = cookieStore;
    }

    @Override
    public synchronized void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
        cookieStore.add(url, cookies);
    }

    @NonNull
    @Override
    public synchronized List<Cookie> loadForRequest(@NonNull HttpUrl url) {
        return cookieStore.get(url);
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }
}
