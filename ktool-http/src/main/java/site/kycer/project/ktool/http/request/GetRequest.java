package site.kycer.project.ktool.http.request;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * get请求创造器
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-18
 */
public class GetRequest extends AbstractRequest<GetRequest> {

    public GetRequest(OkHttpClient.Builder okHttpClient, String url) {
        super(okHttpClient, url);
    }

    @Override
    Request.Builder getRequestBuilder() {
        return new Request.Builder().url(this.getUrl()).get();
    }
}
