package site.kycer.project.ktool.wx.api;

import site.kycer.project.ktool.wx.storage.IStorageStrategy;

/**
 * 基类Api
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-19
 */
@SuppressWarnings("WeakerAccess")
public abstract class BaseApi {

    /**
     * 存储策略
     */
    protected IStorageStrategy storageStrategy;

    protected static final String HOST = "https://api.weixin.qq.com";

    protected String getUrl(String busUri) {
        return HOST + "/cgi-bin" + busUri + "?access_token=" + getAccessToken();
    }

    private String getAccessToken() {
        return storageStrategy.getAccessToken("");
    }


    protected static String getAccessTokenUrl(String appId, String secret) {
        return HOST + "/cgi-bin/token?grant_type=client_credential&appId=" + appId + "&secret=" + secret;
    }
}
