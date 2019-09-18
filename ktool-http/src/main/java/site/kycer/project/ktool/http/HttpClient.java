package site.kycer.project.ktool.http;


import okhttp3.OkHttpClient;
import site.kycer.project.ktool.http.request.GetRequest;
import site.kycer.project.ktool.http.request.PostRequest;
import site.kycer.project.ktool.http.request.TextRequest;

import java.util.concurrent.TimeUnit;

/**
 * @author Kycer
 * @version 1.0
 * @date 2019-09-17
 */
public class HttpClient {


    public static Builder newBuilder() {
        return new Builder();
    }

    public final static class Builder {

        /**
         * 超时时间
         */
        private final static Integer TIME_OUT = 15;

        private OkHttpClient.Builder okHttpClientBuilder;

        public Builder() {
            this.okHttpClientBuilder = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS);
        }

        /**
         * 单独设置 连接超时
         *
         * @param connectTimeout 连接超时
         * @return Builder
         */
        public Builder connectTimeout(Integer connectTimeout, TimeUnit timeUnit) {
            this.okHttpClientBuilder.connectTimeout(connectTimeout, timeUnit);
            return this;
        }

        /**
         * 单独设置 写超时
         *
         * @param writeTimeout 写超时
         * @return Builder
         */
        public Builder writeTimeout(Integer writeTimeout, TimeUnit timeUnit) {
            this.okHttpClientBuilder.writeTimeout(writeTimeout, timeUnit);
            return this;
        }

        /**
         * 单独设置 读超时
         *
         * @param readTimeout 读超时
         * @return Builder
         */
        public Builder readTimeout(Integer readTimeout, TimeUnit timeUnit) {
            this.okHttpClientBuilder.readTimeout(readTimeout, timeUnit);
            return this;
        }

        /**
         * get 请求
         *
         * @param url url
         * @return GetRequest
         */
        public GetRequest get(String url) {
            return new GetRequest(okHttpClientBuilder, url);
        }

        /**
         * post 请求
         *
         * @param url url
         * @return PostRequest
         */
        public PostRequest post(String url) {
            return new PostRequest(okHttpClientBuilder, url);
        }

        /**
         * 文本类型请求
         *
         * @param url url
         * @return PostRequest
         */
        public TextRequest text(String url) {
            return new TextRequest(okHttpClientBuilder, url);
        }

    }

}
