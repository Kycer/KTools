package site.kycer.project.ktool.http;


import okhttp3.OkHttpClient;
import site.kycer.project.ktool.http.request.GetRequest;
import site.kycer.project.ktool.http.request.PostRequest;
import site.kycer.project.ktool.http.request.TextRequest;
import site.kycer.project.ktool.http.ssl.SSLContainer;
import site.kycer.project.ktool.http.ssl.SSLFactory;

import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
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
         * @return {@link Builder}
         */
        public Builder connectTimeout(Integer connectTimeout, TimeUnit timeUnit) {
            this.okHttpClientBuilder.connectTimeout(connectTimeout, timeUnit);
            return this;
        }

        /**
         * 单独设置 写超时
         *
         * @param writeTimeout 写超时
         * @return {@link Builder}
         */
        public Builder writeTimeout(Integer writeTimeout, TimeUnit timeUnit) {
            this.okHttpClientBuilder.writeTimeout(writeTimeout, timeUnit);
            return this;
        }

        /**
         * 单独设置 读超时
         *
         * @param readTimeout 读超时
         * @return {@link Builder}
         */
        public Builder readTimeout(Integer readTimeout, TimeUnit timeUnit) {
            this.okHttpClientBuilder.readTimeout(readTimeout, timeUnit);
            return this;
        }

        /**
         * 为构建本次请求设置单独SSL证书
         *
         * @param certificates SSL证书文件
         * @return {@link Builder}
         */
        public Builder ssl(InputStream... certificates) {
            return ssl(null, null, certificates);
        }

        /**
         * 为构建本次请求设置SSL单向认证
         *
         * @param trustManager 证书管理器
         * @return {@link Builder}
         */
        public Builder ssl(X509TrustManager trustManager) {
            return ssl(null, null, trustManager);
        }

        /**
         * SSL双向认证
         *
         * @param pfxStream    客户端证书，支持P12的证书
         * @param pfxPwd       客户端证书密码
         * @param certificates 含有服务端公钥的证书
         * @return {@link Builder}
         */
        public Builder ssl(InputStream pfxStream, String pfxPwd, InputStream... certificates) {
            SSLContainer sslContainer = SSLFactory.create(certificates, null, pfxStream, pfxPwd);
            this.okHttpClientBuilder.sslSocketFactory(sslContainer.getSslSocketFactory(), sslContainer.getX509TrustManager());
            return this;
        }

        /**
         * SSL双向认证
         *
         * @param pfxStream    客户端证书，支持P12的证书
         * @param pfxPwd       客户端证书密码
         * @param trustManager 证书管理器
         * @return {@link Builder}
         */
        public Builder ssl(InputStream pfxStream, String pfxPwd, X509TrustManager trustManager) {
            SSLContainer sslContainer = SSLFactory.create(null, trustManager, pfxStream, pfxPwd);
            this.okHttpClientBuilder.sslSocketFactory(sslContainer.getSslSocketFactory(), sslContainer.getX509TrustManager());
            return this;
        }

        /**
         * get 请求
         *
         * @param url url
         * @return {@link GetRequest}
         */
        public GetRequest get(String url) {
            return new GetRequest(okHttpClientBuilder, url);
        }

        /**
         * post 请求
         *
         * @param url url
         * @return {@link PostRequest}
         */
        public PostRequest post(String url) {
            return new PostRequest(okHttpClientBuilder, url);
        }

        /**
         * 文本类型请求
         *
         * @param url url
         * @return {@link PostRequest}
         */
        public TextRequest text(String url) {
            return new TextRequest(okHttpClientBuilder, url);
        }

    }

}
