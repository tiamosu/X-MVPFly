package com.xia.baseproject.integration;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xia
 * @date 2018/9/14.
 */
public final class ManifestParser {
    private static final String MODULE_VALUE = "ConfigModule";

    private final Context context;

    public ManifestParser(Context context) {
        this.context = context;
    }

    public List<ConfigModule> parse() {
        final List<ConfigModule> modules = new ArrayList<>();
        try {
            final ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            final Bundle bundle = appInfo.metaData;
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    if (MODULE_VALUE.equals(bundle.get(key))) {
                        modules.add(parseModule(key));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Unable to find metadata to parse ConfigModule", e);
        }
        return modules;
    }

    private static ConfigModule parseModule(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to find ConfigModule implementation", e);
        }

        Object module;
        try {
            module = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to instantiate ConfigModule implementation for " + clazz, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to instantiate ConfigModule implementation for " + clazz, e);
        }

        if (!(module instanceof ConfigModule)) {
            throw new RuntimeException("Expected instanceof ConfigModule, but found: " + module);
        }
        return (ConfigModule) module;
    }
}
