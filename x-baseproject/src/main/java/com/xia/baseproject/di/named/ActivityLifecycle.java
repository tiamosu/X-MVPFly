package com.xia.baseproject.di.named;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @author xia
 * @date 2018/9/14.
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityLifecycle {
}
