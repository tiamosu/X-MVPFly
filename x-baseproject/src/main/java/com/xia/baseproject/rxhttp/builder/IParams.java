package com.xia.baseproject.rxhttp.builder;

import java.util.Map;

/**
 * @author xia
 * @date 2018/7/27.
 */
public interface IParams {

    BaseBuilder params(Map<String, String> params);

    BaseBuilder addParams(String key, String val);
}
