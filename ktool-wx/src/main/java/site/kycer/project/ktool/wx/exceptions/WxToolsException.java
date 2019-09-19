package site.kycer.project.ktool.wx.exceptions;

/**
 * 微信业务异常
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-19
 */
public class WxToolsException extends RuntimeException {
    public WxToolsException() {
        super();
    }

    public WxToolsException(String s) {
        super(s);
    }

    public WxToolsException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public WxToolsException(Throwable throwable) {
        super(throwable);
    }
}
