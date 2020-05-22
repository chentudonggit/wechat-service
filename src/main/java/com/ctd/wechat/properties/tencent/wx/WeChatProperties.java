package com.ctd.wechat.properties.tencent.wx;

import com.ctd.wechat.properties.tencent.wx.storage.CacheConfigStorageProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * WxProperties
 *
 * @author chentudong
 * @date 2020/4/4 22:39
 * @since 1.0
 */
@ConfigurationProperties(prefix = "tencent.wx")
public class WeChatProperties
{
    /**
     * appId
     */
    private String appId;

    /**
     * appSecret
     */
    private String appSecret;

    /**
     * redirectUrl
     */
    private String redirectUrl;

    /**
     * 设置微信公众号的token
     */
    private String token;

    /**
     * 设置微信公众号的EncodingAESKey
     */
    private String aesKey;

    /**
     * 存储策略, memory, redis
     */
    private CacheConfigStorageProperties configStorage;

    public String getAppId()
    {
        return appId;
    }

    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getAppSecret()
    {
        return appSecret;
    }

    public void setAppSecret(String appSecret)
    {
        this.appSecret = appSecret;
    }

    public String getRedirectUrl()
    {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl)
    {
        this.redirectUrl = redirectUrl;
    }

    public CacheConfigStorageProperties getConfigStorage()
    {
        return configStorage;
    }

    public void setConfigStorage(CacheConfigStorageProperties configStorage)
    {
        this.configStorage = configStorage;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getAesKey()
    {
        return aesKey;
    }

    public void setAesKey(String aesKey)
    {
        this.aesKey = aesKey;
    }
}
