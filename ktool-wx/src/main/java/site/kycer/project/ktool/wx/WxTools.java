package site.kycer.project.ktool.wx;

import site.kycer.project.ktool.wx.api.AccessTokenApi;
import site.kycer.project.ktool.wx.response.AccessTokenResponse;

/**
 * 所有接口主入口
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-19
 */
public class WxTools {

    private WxTools() {

    }

    private static WxTools getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final WxTools INSTANCE = new WxTools();
    }

    public AccessTokenResponse token(String appId) {
        return new AccessTokenApi().getAccessToken(appId);
    }

}
