package com.xia.baseproject.rxhttp.request;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;

import io.reactivex.Observable;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/8/3.
 */
@SuppressWarnings("WeakerAccess")
public class PostRequest extends BaseBodyRequest<PostRequest> {

    public PostRequest(@NonNull String url) {
        super(url);
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        if (mRequestBody != null) {
            return mRestService.postBody(mUrl, mRequestBody);
        }
        if (mJson != null) {
            final RequestBody body = RequestBody.create(mMediaType, mJson);
            return mRestService.postJson(mUrl, body);
        }
        if (mObject != null) {
            return mRestService.postBody(mUrl, mObject);
        }
        if (mBytes != null) {
            final RequestBody body = RequestBody.create(mMediaType, mBytes);
            return mRestService.postBody(mUrl, body);
        }
        if (mString != null) {
            final RequestBody body = RequestBody.create(mMediaType, mString);
            return mRestService.postBody(mUrl, body);
        }

        final RequestBody body;
        if (mFileParams == null || mFileParams.isEmpty()) {
            final FormBody.Builder builder = new FormBody.Builder();
            body = addParams(builder);
        } else {
            final MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            body = addParams(builder);
        }
        return mRestService.post(mUrl, body);
    }

    private RequestBody addParams(FormBody.Builder builder) {
        if (mParams != null && !mParams.isEmpty()) {
            for (String key : mParams.keySet()) {
                String value = mParams.get(key);
                builder.add(key, value);
            }
        }
        return builder.build();
    }

    private RequestBody addParams(MultipartBody.Builder builder) {
        if (mParams != null && !mParams.isEmpty()) {
            for (String key : mParams.keySet()) {
                String value = mParams.get(key);
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, value));
            }
        }
        if (mFileParams != null && !mFileParams.isEmpty()) {
            for (String key : mFileParams.keySet()) {
                final File file = mFileParams.get(key);
                final RequestBody fileBody = RequestBody.create(
                        MediaType.parse(guessMimeType(file.getName())), file);
                builder.addFormDataPart(key, file.getName(), fileBody);
            }
        }
        return builder.build();
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
}
