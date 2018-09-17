package com.xia.baseproject.di.named;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @author xia
 * @date 2018/9/17.
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RxCacheDirectory {
}
