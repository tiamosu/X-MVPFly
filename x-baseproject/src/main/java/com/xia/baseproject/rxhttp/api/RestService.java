package com.xia.baseproject.rxhttp.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author xia
 * @date 2018/7/27.
 */
public interface RestService {

    @GET
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> params);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);

    @POST()
    Observable<ResponseBody> postBody(@Url String url, @Body Object object);

    @POST()
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ResponseBody> postJson(@Url String url, @Body RequestBody jsonBody);

    @POST()
    Observable<ResponseBody> postBody(@Url String url, @Body RequestBody body);

    @POST
    Observable<ResponseBody> post(@Url String url, @Body RequestBody body);

    @DELETE()
    Observable<ResponseBody> delete(@Url String url, @QueryMap Map<String, String> params);

    //@DELETE()//delete body请求比较特殊 需要自定义
    @HTTP(method = "DELETE",/*path = "",*/hasBody = true)
    Observable<ResponseBody> deleteBody(@Url String url, @Body Object object);

    //@DELETE()//delete body请求比较特殊 需要自定义
    @HTTP(method = "DELETE",/*path = "",*/hasBody = true)
    Observable<ResponseBody> deleteBody(@Url String url, @Body RequestBody body);

    //@DELETE()//delete body请求比较特殊 需要自定义
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @HTTP(method = "DELETE",/*path = "",*/hasBody = true)
    Observable<ResponseBody> deleteJson(@Url String url, @Body RequestBody jsonBody);

    @PUT()
    Observable<ResponseBody> put(@Url String url, @QueryMap Map<String, String> maps);

    @PUT()
    Observable<ResponseBody> putBody(@Url String url, @Body Object object);

    @PUT()
    Observable<ResponseBody> putBody(@Url String url, @Body RequestBody body);

    @PUT()
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ResponseBody> putJson(@Url String url, @Body RequestBody jsonBody);

    @HEAD
    Observable<ResponseBody> head(@Url String url);

    @PATCH
    Observable<ResponseBody> patch(@Url String url, @Body RequestBody body);
}
