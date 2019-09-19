package site.kycer.project.ktool.wx.response;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kycer
 * @version 1.0
 * @date 2019-09-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccessTokenResponse extends AbstractBaseResponse {

    /**
     * accessToken
     */
    private String accessToken;

    /**
     * 有效时间
     */
    private String expiresIn;
}
