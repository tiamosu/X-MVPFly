package com.xia.baseproject.rxhttp.api;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author xia
 * @date 2018/7/27.
 */
public interface RestService {

    @GET
    Observable<ResponseBody> get(@Url String url);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    @POST
    Observable<ResponseBody> post(@Url String url, @Body RequestBody body);

    @PUT
    Observable<ResponseBody> put(@Url String url, @Body RequestBody body);

    @DELETE
    Observable<ResponseBody> delete(@Url String url);

    @DELETE
    Observable<ResponseBody> delete(@Url String url, @Body RequestBody body);

    @HEAD
    Observable<ResponseBody> head(@Url String url);

    @PATCH
    Observable<ResponseBody> patch(@Url String url, @Body RequestBody body);
}
