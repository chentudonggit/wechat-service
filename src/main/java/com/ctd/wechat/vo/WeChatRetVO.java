package com.ctd.wechat.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * WeChatRetVO
 *
 * @author chentudong
 * @date 2020/4/22 4:30 下午
 * @since 1.0
 */
public class WeChatRetVO implements Serializable
{
    private static final long serialVersionUID = 6437065397972449062L;

    /**
     * appId
     */
    @JsonProperty(value = "appid")
    @JSONField(name = "appid")
    private String appId;

    /**
     * url
     */
    @JsonProperty(value = "url")
    @JSONField(name = "url")
    private String url;

    /**
     * jsapiTicket
     */
    @JsonProperty(value = "jsapi_ticket")
    @JSONField(name = "jsapi_ticket")
    private String jsapiTicket;

    /**
     * nonceStr
     */
    @JsonProperty(value = "nonce_str")
    @JSONField(name = "nonce_str")
    private String nonceStr;

    /**
     * timestamp
     */
    @JsonProperty(value = "timestamp")
    @JSONField(name = "timestamp")
    private String timestamp;

    /**
     * signature
     */
    @JsonProperty(value = "signature")
    @JSONField(name = "signature")
    private String signature;

    public WeChatRetVO()
    {
    }

    public WeChatRetVO(String url, String jsapiTicket, String nonceStr, String timestamp, String signature)
    {
        this.url = url;
        this.jsapiTicket = jsapiTicket;
        this.nonceStr = nonceStr;
        this.timestamp = timestamp;
        this.signature = signature;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getJsapiTicket()
    {
        return jsapiTicket;
    }

    public void setJsapiTicket(String jsapiTicket)
    {
        this.jsapiTicket = jsapiTicket;
    }

    public String getNonceStr()
    {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr)
    {
        this.nonceStr = nonceStr;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getSignature()
    {
        return signature;
    }

    public void setSignature(String signature)
    {
        this.signature = signature;
    }

    public String getAppId()
    {
        return appId;
    }

    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    @Override
    public String toString()
    {
        return "WeChatRetVO{" +
                "appId='" + appId + '\'' +
                ", url='" + url + '\'' +
                ", jsapiTicket='" + jsapiTicket + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
