package com.ctd.wechat.properties.tencent.wx.meun;

import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * WeChatMenuProperties
 *
 * @author chentudong
 * @date 2020/4/21 2:39 下午
 * @since 1.0
 */
@ConfigurationProperties(prefix = "tencent.wx.menu")
public class WeChatMenuProperties
{
    private boolean enabled = false;

    private List<WxMenuButton> buttons = new ArrayList<>(5);

    private String sendFollowInformationText = "您好，【ctdys】平台官方订阅号，欢迎您的关注！\n\n " +
            "客服热线：13672784597";

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public List<WxMenuButton> getButtons()
    {
        return buttons;
    }

    public void setButtons(List<WxMenuButton> buttons)
    {
        this.buttons = buttons;
    }

    public String getSendFollowInformationText()
    {
        return sendFollowInformationText;
    }

    public void setSendFollowInformationText(String sendFollowInformationText)
    {
        this.sendFollowInformationText = sendFollowInformationText;
    }
}
