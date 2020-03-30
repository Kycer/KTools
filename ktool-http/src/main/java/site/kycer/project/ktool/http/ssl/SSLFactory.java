package site.kycer.project.ktool.http.ssl;

import site.kycer.project.ktool.basic.core.ArrayUtils;
import site.kycer.project.ktool.basic.io.IOUtils;
import site.kycer.project.ktool.http.exception.HttpClientException;

import javax.net.ssl.*;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;

/**
 * 证书创建
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-19
 */
public class SSLFactory {

    private static final String KEY_STORE_TYPE_P12 = "PKCS12";

    public static final String TSL = "TSL";

    /**
     * 尝试解析SSL证书
     *
     * @param certificates     远程服务端的证书
     * @param x509TrustManager 自定义证书管理器
     * @param pfxCertificate   客户端证书
     * @param keyStorePassword 客户端证书密码
     * @return {@link SSLContainer}
     */
    public static SSLContainer create(InputStream[] certificates, X509TrustManager x509TrustManager,
                                      InputStream pfxCertificate, String keyStorePassword) {
        try {
            KeyManager[] keyManagers = generateKeyManager(pfxCertificate, keyStorePassword.toCharArray());
            X509TrustManager trustManager;
            //自定义 -> 默认 -> 不使用
            if (x509TrustManager != null) {
                trustManager = x509TrustManager;
            } else {
                trustManager = prepareX509TrustManager(certificates);
                if (trustManager == null) {
                    trustManager = new NullX509TrustManager();
                }
            }
            //创建TLS类型的SSLContext对象.
            SSLContext sslContext = SSLContext.getInstance(TSL);
            // 用上面得到的trustManagers初始化SSLContext，这样sslContext就会信任keyStore中的证书
            sslContext.init(keyManagers, new TrustManager[]{trustManager}, new SecureRandom());
            return new SSLContainer(sslContext.getSocketFactory(), trustManager);
        } catch (Exception e) {
            throw new HttpClientException(e);
        }
    }

    /**
     * 根据证书文件生成
     *
     * @param certificates 证书
     * @return {@link X509KeyManager}
     * @throws Exception e
     */
    private static X509TrustManager prepareX509TrustManager(InputStream... certificates) throws Exception {
        if (ArrayUtils.isEmpty(certificates)) {
            return null;
        }

        //创建证书工厂
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        //创建一个默认类型的KeyStore,存储我们信任的证书
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);

        //开始处理证书
        for (int i = 0, length = certificates.length; i < length; i++) {
            //将证书对象作为可信证书放入到keyStore中
            keyStore.setCertificateEntry(String.valueOf(i + 1), factory.generateCertificate(certificates[i]));
            IOUtils.close(certificates[i]);
        }

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        //用我们之前的keyStore实例初始化TrustManagerFactory,这样tmf就会信任keyStore中的证书
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new HttpClientException("Unexpected default trust managers:" + ArrayUtils.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    /**
     * 生成 {@link KeyManager} 数组
     *
     * @param pfxCertificate 客户端证书
     * @param pfxPassword    客户端密码
     * @return {@link KeyManager}
     * @throws Exception e
     */
    private static KeyManager[] generateKeyManager(InputStream pfxCertificate, char[] pfxPassword) throws Exception {
        if (pfxCertificate == null || ArrayUtils.isEmpty(pfxPassword)) {
            return null;
        }
        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
        keyStore.load(pfxCertificate, pfxPassword);
        KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        factory.init(keyStore, pfxPassword);
        return factory.getKeyManagers();
    }
}
