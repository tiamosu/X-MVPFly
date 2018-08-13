package com.xia.baseproject.rxhttp;

import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author xia
 * @date 2018/8/13.
 */
public class RxHttpDisposableManager {

    /**
     * 网络请求订阅池，管理Subscribers订阅，防止内存泄漏
     */
    private ConcurrentHashMap<Object, CompositeDisposable> maps = new ConcurrentHashMap<>();

    private static class SingleTonHolder {
        private static final RxHttpDisposableManager INSTANCE = new RxHttpDisposableManager();
    }

    public static RxHttpDisposableManager getInstance() {
        return SingleTonHolder.INSTANCE;
    }

    private RxHttpDisposableManager() {
    }

    /**
     * 根据tagName管理订阅【注册订阅信息】
     *
     * @param tagName    标志
     * @param disposable 订阅信息
     */
    public Disposable add(@NonNull Object tagName, Disposable disposable) {
        /* 订阅管理 */
        CompositeDisposable compositeDisposable = maps.get(tagName);
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
            maps.put(tagName, compositeDisposable);
        }
        compositeDisposable.add(disposable);
        return disposable;
    }

    /**
     * 取消订阅【取消标志内所有订阅信息】
     *
     * @param tagName 标志
     */
    public void remove(@NonNull Object tagName) {
        final CompositeDisposable compositeDisposable = maps.get(tagName);
        if (compositeDisposable != null) {
            compositeDisposable.dispose(); //取消订阅
            maps.remove(tagName);
        }
    }

    /**
     * 取消订阅【单个订阅取消】
     *
     * @param tagName    标志
     * @param disposable 订阅信息
     */
    public void remove(@NonNull Object tagName, Disposable disposable) {
        CompositeDisposable compositeDisposable = maps.get(tagName);
        if (compositeDisposable != null) {
            compositeDisposable.remove(disposable);
            if (compositeDisposable.size() == 0) {
                maps.remove(tagName);
            }
        }
    }

    /**
     * 取消订阅
     *
     * @param disposable 订阅信息
     */
    public static void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * 取消所有订阅
     */
    public void removeAll() {
        final Iterator<Map.Entry<Object, CompositeDisposable>> it = maps.entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry<Object, CompositeDisposable> entry = it.next();
            final CompositeDisposable compositeDisposable = entry.getValue();
            if (compositeDisposable != null) {
                compositeDisposable.dispose(); //取消订阅
                it.remove();
            }
        }
        maps.clear();
    }
}
