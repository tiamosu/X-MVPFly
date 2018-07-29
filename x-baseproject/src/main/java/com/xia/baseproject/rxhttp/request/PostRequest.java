package com.xia.baseproject.rxhttp.request;

import com.xia.baseproject.rxhttp.exception.Exceptions;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author xia
 * @date 2018/7/27.
 */
public class PostRequest extends BaseRequest {
    private String mUrl;
    private Map<String, String> mParams;
    private Map<String, File> mFileParams;

    public PostRequest(String url, Map<String, String> params, Map<String, File> fileParams) {
        this.mUrl = url;
        this.mParams = params;
        this.mFileParams = fileParams;

        if (this.mUrl == null) {
            Exceptions.illegalArgument("the mUrl can not be null !");
        }
    }

    @Override
    protected RequestBody buildRequestBody() {
        //POST方式提交表单
        if (mFileParams == null || mFileParams.isEmpty()) {
            final FormBody.Builder builder = new FormBody.Builder();
            addParams(builder);
            return builder.build();
        }
        //POST方式提交分块请求
        else {
            final MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            addParams(builder);

            for (String key : mFileParams.keySet()) {
                final File file = mFileParams.get(key);
                final RequestBody fileBody = RequestBody.create(
                        MediaType.parse(guessMimeType(file.getName())), file);
                builder.addFormDataPart(key, file.getName(), fileBody);
            }
            return builder.build();
        }
    }

    @Override
    protected Observable getObservable() {
        return mRestService.post(mUrl, buildRequestBody());
    }

    private String guessMimeType(String path) {
        final FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private void addParams(FormBody.Builder builder) {
        if (mParams != null && !mParams.isEmpty()) {
            for (String key : mParams.keySet()) {
                String value = mParams.get(key);
                value = value == null ? "" : value;
                builder.add(key, value);
            }
        }
    }

    private void addParams(MultipartBody.Builder builder) {
        if (mParams != null && !mParams.isEmpty()) {
            for (String key : mParams.keySet()) {
                String value = mParams.get(key);
                value = value == null ? "" : value;
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, value));
            }
        }
    }
}

