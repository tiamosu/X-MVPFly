package com.xia.baseproject.rxhttp.request1;

import android.net.Uri;

import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/8/3.
 */
public class GetRequest extends BaseRequest<GetRequest> {

    public GetRequest(String url) {
        super(url);
    }

    public Observable<ResponseBody> getObservable() {
        return generateRequest();
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        final String url = appendParams(mUrl, mParams);
        return mRestService.get(url);
    }

    private String appendParams(String url, Map<String, String> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        final Uri.Builder builder = Uri.parse(url).buildUpon();
        final Set<String> keys = params.keySet();
        for (String key : keys) {
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }
}
