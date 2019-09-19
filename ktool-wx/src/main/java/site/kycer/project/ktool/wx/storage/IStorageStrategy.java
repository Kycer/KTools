package site.kycer.project.ktool.wx.storage;

import site.kycer.project.ktool.wx.configs.AppConfig;
import site.kycer.project.ktool.wx.enums.StorageEnum;

import java.util.List;

/**
 * 存储配置策略接口
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-19
 */
public interface IStorageStrategy {

    /**
     * 保存配置文件
     *
     * @param appConfigs 应用配置文件
     */
    void saveConfig(List<AppConfig> appConfigs);


    /**
     * 通过 appId 获取 {@linkplain AppConfig}
     *
     * @param appId 应用ID
     * @return {@linkplain AppConfig}
     */
    AppConfig getConfigByAppId(String appId);

    /**
     * 通过 appId 获取 AccessToken
     *
     * @param appId 应用Id
     * @return AccessToken
     */
    String getAccessToken(String appId);

    /**
     * 对应支持的类型
     *
     * @return {@linkplain StorageEnum}
     */
    StorageEnum supports();
}
