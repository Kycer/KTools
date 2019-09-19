package site.kycer.project.ktool.http.ssl;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * ssl证书解析后容器
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-19
 */
public class SSLContainer {

    private SSLSocketFactory sslSocketFactory;

    private X509TrustManager x509TrustManager;


    public SSLContainer(SSLSocketFactory sslSocketFactory, X509TrustManager x509TrustManager) {
        this.sslSocketFactory = sslSocketFactory;
        this.x509TrustManager = x509TrustManager;
    }


    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public X509TrustManager getX509TrustManager() {
        return x509TrustManager;
    }
}
