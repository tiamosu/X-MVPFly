package com.xia.fly.http.request;

import android.support.annotation.NonNull;

import com.xia.fly.http.body.UploadProgressRequestBody;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import io.reactivex.Observable;
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

        if (mFileParams == null || mFileParams.isEmpty()) {
            return mRestService.post(mUrl, mParams);
        } else {
            final MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            final RequestBody body = addParams(builder);
            return mRestService.uploadFiles(mUrl, body);
        }
    }

    private RequestBody addParams(MultipartBody.Builder builder) {
        if (mParams != null && !mParams.isEmpty()) {
            for (Map.Entry<String, String> entry : mParams.entrySet()) {
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + entry.getKey() + "\""),
                        RequestBody.create(null, entry.getValue()));
            }
        }
        if (mFileParams != null && !mFileParams.isEmpty()) {
            for (Map.Entry<String, File> entry : mFileParams.entrySet()) {
                final File file = entry.getValue();
                final RequestBody fileBody = RequestBody.create(
                        MediaType.parse(guessMimeType(file.getName())), file);
                final UploadProgressRequestBody uploadProgressRequestBody
                        = new UploadProgressRequestBody(fileBody, mUpdateFileCallback);
                builder.addFormDataPart(entry.getKey(), file.getName(), uploadProgressRequestBody);
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
