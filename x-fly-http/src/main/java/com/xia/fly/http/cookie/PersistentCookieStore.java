package com.xia.fly.http.cookie;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.blankj.utilcode.util.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * <p>描述：cookie存储器</p>
 *
 * @author xia
 */
@SuppressWarnings("WeakerAccess")
public class PersistentCookieStore {
    private static final String COOKIE_PREFS = "COOKIE_PREFS";

    private final Map<String, ConcurrentHashMap<String, Cookie>> cookies;
    private final SharedPreferences cookiePrefs;

    public PersistentCookieStore() {
        cookiePrefs = Utils.getApp().getSharedPreferences(COOKIE_PREFS, 0);
        cookies = new HashMap<>();
        final Map<String, ?> prefsMap = cookiePrefs.getAll();
        for (Map.Entry<String, ?> entry : prefsMap.entrySet()) {
            final String[] cookieNames = TextUtils.split((String) entry.getValue(), ",");
            for (String name : cookieNames) {
                final String encodedCookie = cookiePrefs.getString(name, null);
                if (encodedCookie != null) {
                    final Cookie decodedCookie = decodeCookie(encodedCookie);
                    if (decodedCookie != null) {
                        if (!cookies.containsKey(entry.getKey())) {
                            cookies.put(entry.getKey(), new ConcurrentHashMap<>());
                        }
                        cookies.get(entry.getKey()).put(name, decodedCookie);
                    }
                }
            }
        }
    }

    private String getCookieToken(Cookie cookie) {
        return cookie.name() + "@" + cookie.domain();
    }

    public void add(HttpUrl url, Cookie cookie) {
        final String name = getCookieToken(cookie);
        // 添加 host key. 否则有可能抛空.
        if (!cookies.containsKey(url.host())) {
            cookies.put(url.host(), new ConcurrentHashMap<>());
        }
        // 删除已经有的.
        if (cookies.containsKey(url.host())) {
            cookies.get(url.host()).remove(name);
        }
        // 添加新的进去
        cookies.get(url.host()).put(name, cookie);
        // 是否保存到 SP 中
        if (cookie.persistent()) {
            final SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
            prefsWriter.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
            prefsWriter.putString(name, encodeCookie(new SerializableOkHttpCookies(cookie)));
            prefsWriter.apply();
        } else {
            final SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
            prefsWriter.remove(url.host());
            prefsWriter.remove(name);
            prefsWriter.apply();
        }
    }

    public void addCookies(List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            final String domain = cookie.domain();
            ConcurrentHashMap<String, Cookie> domainCookies = this.cookies.get(domain);
            if (domainCookies == null) {
                domainCookies = new ConcurrentHashMap<>();
                this.cookies.put(domain, domainCookies);
            }
        }
    }

    public List<Cookie> get(HttpUrl url) {
        final ArrayList<Cookie> ret = new ArrayList<>();
        if (cookies.containsKey(url.host())) {
            ret.addAll(cookies.get(url.host()).values());
        }
        return ret;
    }

    public boolean removeAll() {
        final SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        prefsWriter.clear();
        prefsWriter.apply();
        cookies.clear();
        return true;
    }

    public boolean remove(HttpUrl url, Cookie cookie) {
        final String name = getCookieToken(cookie);
        if (cookies.containsKey(url.host()) && cookies.get(url.host()).containsKey(name)) {
            cookies.get(url.host()).remove(name);

            final SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
            if (cookiePrefs.contains(name)) {
                prefsWriter.remove(name);
            }
            prefsWriter.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
            prefsWriter.apply();
            return true;
        } else {
            return false;
        }
    }

    public List<Cookie> getCookies() {
        final ArrayList<Cookie> ret = new ArrayList<>();
        for (Map.Entry<String, ConcurrentHashMap<String, Cookie>> entry : cookies.entrySet()) {
            final ConcurrentHashMap<String, Cookie> cookieMap = entry.getValue();
            ret.addAll(cookieMap.values());
        }
        return ret;
    }

    /**
     * cookies to string
     */
    protected String encodeCookie(SerializableOkHttpCookies cookie) {
        if (cookie == null) {
            return null;
        }
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            final ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(cookie);
        } catch (IOException e) {
            return null;
        }
        return byteArrayToHexString(os.toByteArray());
    }

    /**
     * String to cookies
     */
    private Cookie decodeCookie(String cookieString) {
        final byte[] bytes = hexStringToByteArray(cookieString);
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableOkHttpCookies) objectInputStream.readObject()).getCookies();
        } catch (IOException ignored) {
        } catch (ClassNotFoundException ignored) {
        }
        return cookie;
    }

    /**
     * byteArrayToHexString
     */
    private String byteArrayToHexString(byte[] bytes) {
        final StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    /**
     * hexStringToByteArray
     */
    private byte[] hexStringToByteArray(String hexString) {
        final int len = hexString.length();
        final byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}