package site.kycer.project.ktool.wx.configs;

import lombok.Data;

import java.util.List;

/**
 * 主配置文件
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-19
 */
@Data
public class WxToolsConfig {

    private List<AppConfig> appConfigs;
}
