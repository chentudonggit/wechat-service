package com.ctd.wechat.config.tencent.wx;

import com.ctd.wechat.properties.tencent.wx.WeChatProperties;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * 微信配置
 *
 * @author chentudong
 * @date 2020/4/4 22:38
 * @since 1.0
 */
@Configuration
public class WeChatConfig
{
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private WxMpService wxMpService;

    /**
     * loginWxCode
     *
     * @return String
     */
    public String qrConnectUrl(String scope, String state)
    {
        return wxMpService.buildQrConnectUrl(weChatProperties.getRedirectUrl(), scope, state);
    }

    /**
     * connectOauth2AuthorizeUrl
     *
     * @param scope scope
     * @param state state
     * @return String
     */
    public String connectOauth2AuthorizeUrl(String scope, String state)
    {
        return wxMpService.oauth2buildAuthorizationUrl(weChatProperties.getRedirectUrl(), scope, state);
    }

    public WeChatProperties getWxProperties()
    {
        return weChatProperties;
    }
}
