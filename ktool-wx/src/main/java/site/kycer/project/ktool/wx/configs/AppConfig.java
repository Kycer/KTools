package site.kycer.project.ktool.wx.configs;

import lombok.Data;

/**
 * 应用配置文件
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-19
 */
@Data
public class AppConfig {

    /**
     * appId
     */
    private String appId;

    /**
     * appSecret
     */
    private String appSecret;
}
