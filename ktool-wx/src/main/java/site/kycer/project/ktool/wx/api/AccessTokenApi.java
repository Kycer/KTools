package site.kycer.project.ktool.wx.api;

import site.kycer.project.ktool.wx.response.AccessTokenResponse;

/**
 * @author Kycer
 * @version 1.0
 * @date 2019-09-19
 */
public class AccessTokenApi extends BaseApi {

    public AccessTokenResponse getAccessToken(String appId) {
        // AppConfig appConfig = storageStrategy.getConfigByAppId(appId);
        // String res = HttpClient.newBuilder()
        //         .get(getAccessTokenUrl(appId, appConfig.getAppSecret()))
        //         .string();
        // return JSONObject.parseObject(res, AccessTokenResponse.class);
        return null;
    }
}
