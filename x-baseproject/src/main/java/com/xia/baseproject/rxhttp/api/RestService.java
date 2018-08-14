package com.xia.baseproject.rxhttp.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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

    //==========================//
    //         POST请求          //
    // =========================//

    @POST
    @FormUrlEncoded
    Observable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> maps);

    @POST
    Observable<ResponseBody> postBody(@Url String url, @Body Object object);

    @POST
    Observable<ResponseBody> postBody(@Url String url, @Body RequestBody body);

    @POST
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ResponseBody> postJson(@Url String url, @Body RequestBody jsonBody);


    //==========================//
    //         GET请求           //
    // =========================//

    @GET
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> params);


    //==========================//
    //         DELETE请求        //
    // =========================//

    @DELETE
    Observable<ResponseBody> delete(@Url String url, @QueryMap Map<String, String> maps);

    @DELETE
    Observable<ResponseBody> deleteBody(@Url String url, @Body Object object);

    @DELETE
    Observable<ResponseBody> deleteBody(@Url String url, @Body RequestBody body);

    @DELETE
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ResponseBody> deleteJson(@Url String url, @Body RequestBody jsonBody);


    //==========================//
    //         PUT请求           //
    // =========================//

    @PUT
    Observable<ResponseBody> put(@Url String url, @QueryMap Map<String, String> maps);

    @PUT
    Observable<ResponseBody> putBody(@Url String url, @Body Object object);

    @PUT
    Observable<ResponseBody> putBody(@Url String url, @Body RequestBody body);

    @PUT
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ResponseBody> putJson(@Url String url, @Body RequestBody jsonBody);


    //==========================//
    //       文件上传下载         //
    // =========================//

    @POST
    Observable<ResponseBody> uploadFiles(@Url String url, @Body RequestBody body);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);
}
