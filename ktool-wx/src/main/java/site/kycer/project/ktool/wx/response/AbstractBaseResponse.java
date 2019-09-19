package site.kycer.project.ktool.wx.response;

import lombok.Data;
import site.kycer.project.ktool.wx.exceptions.WxToolsException;

import java.util.Objects;

/**
 * 请求结果基类
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-19
 */
@SuppressWarnings("SpellCheckingInspection")
@Data
public abstract class AbstractBaseResponse {


    public AbstractBaseResponse() {
        this.isSuccess();
    }

    /**
     * 微信返回错误码
     */
    private Integer errcode = 0;

    /**
     * 微信返回错误消息
     */
    private String errmsg = "请求成功";

    public boolean isSuccess() throws WxToolsException {
        if (!Objects.equals(this.errcode, 0)) {
            throw new WxToolsException(String.format("微信接口请求错误, 错误码：%s，错误信息：%s", this.errcode, this.errmsg));
        }
        return true;
    }
}
