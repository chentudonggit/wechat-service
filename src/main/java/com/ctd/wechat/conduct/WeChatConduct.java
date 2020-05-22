package com.ctd.wechat.conduct;

import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import com.ctd.wechat.properties.tencent.wx.meun.WeChatMenuProperties;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 微信行为
 *
 * @author chentudong
 * @date 2020/4/21 2:33 下午
 * @since 1.0
 */
@Component
public class WeChatConduct
{
    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatConduct.class);

    @Autowired
    private WeChatMenuProperties weChatMenuProperties;
    @Autowired
    private WxMpService wxMpService;

    /**
     * 公众号关注消息推送
     *
     * @param openId openId
     */
    public void sendFollowInformation(String openId)
    {
        isNull(openId, "openId 不能为空");
        LOGGER.info("接到发送消息事件：openId {}", openId);
        sendMsg(makeTextCustomMessage(openId, weChatMenuProperties.getSendFollowInformationText()));
    }

    /**
     * sendMsg
     *
     * @param json json
     */
    public void sendMsg(String json)
    {
        try
        {
            String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
            LOGGER.info("发送json：{}", json);
            LOGGER.info("发送成功：{}", this.wxMpService.post(url, json));
        } catch (WxErrorException e)
        {
            e.printStackTrace();
            AssertUtils.msgUser("发送消息失败！");
        }
    }

    /**
     * 组装发送文本消息
     *
     * @return String
     */
    public static String makeTextCustomMessage(String openId, String content)
    {
        isNull(openId, "openId 不能为空");
        isNull(content, "content 不能为空");
        content = content.replace("\"", "\\\"");
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"text\",\"agentid\":\"%s\",\"text\":{\"content\":\"%s\"}}";
        return String.format(jsonMsg, openId, 14, content);
    }

    /**
     * isNull
     *
     * @param object  object
     * @param message message
     */
    private static void isNull(Object object, String message)
    {
        if (Objects.isNull(object) || StringUtils.isBlank(object.toString()))
        {
            throw new RuntimeException(StringUtils.isBlank(message) ? "网络异常，请稍后重试！" : message);
        }
    }

    /**
     * 事件处理
     *
     * @param openId openId
     * @param event  event
     */
    public void event(String openId, String event)
    {
        if (StringUtils.isNotBlank(event))
        {
            LOGGER.info("请求事件: {}", event);
            switch (event)
            {
                case WxConsts.EventType.SUBSCRIBE:
                    this.sendFollowInformation(openId);
                    break;
                case WxConsts.EventType.SCANCODE_PUSH:
                    this.scanCodePush(openId);
                    break;
                case WxConsts.EventType.MERCHANT_ORDER:
                    this.merchantOrder(openId);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 微信小店 订单付款通知.
     *
     * @param openId openId
     */
    private void merchantOrder(String openId)
    {
        AssertUtils.isNull(openId, "appId 不能为空");
    }

    /**
     * 扫码推事件的事件推送
     *
     * @param openId openId
     */
    private void scanCodePush(String openId)
    {
        AssertUtils.isNull(openId, "appId 不能为空");
    }

    /**
     * switchover
     *
     * @param appId appId
     */
    public void switchover(String appId)
    {
        AssertUtils.isNull(appId, "appId 不能为空");
        //checkSignature是必须补回功能的函数
        if (!this.wxMpService.switchover(appId))
        {
            AssertUtils.msgUser("未找到对应 appid=[%s]的配置，请核实！", appId);
        }
    }

    /**
     * checkSignature
     *
     * @param timestamp timestamp
     * @param nonce     nonce
     * @param signature signature
     */
    public void checkSignature(String timestamp, String nonce, String signature)
    {
        if (!wxMpService.checkSignature(timestamp, nonce, signature))
        {
            AssertUtils.msgUser("请求参数非法，请核实!");
        }
    }

    /**
     * 处理文本消息
     *
     * @param openId  openId
     * @param content content
     */
    public void msgText(String openId, String content)
    {
        StringBuilder sb = new StringBuilder("自动回复的功能, 接到未处理的消息：");
        if (StringUtils.isNotBlank(content))
        {
            //处理自动回复的功能
            sb.append(content);
        }
        sendMsg(makeTextCustomMessage(openId, sb.toString()));
    }

    /**
     * 音乐消息
     *
     * @param openId    openId
     * @param inMessage inMessage
     */
    public void msgMusic(String openId, WxMpXmlMessage inMessage)
    {
        sendMsg(makeTextCustomMessage(openId, "音乐文件"));
    }
}
