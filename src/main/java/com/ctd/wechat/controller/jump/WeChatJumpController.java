package com.ctd.wechat.controller.jump;

import com.ctd.wechat.config.tencent.wx.WeChatConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * WechatLoginController
 *
 * @author chentudong
 * @date 2020/4/20 23:08
 * @since 1.0
 */
@Api(value = "WeChatJumpController", tags = "微信跳转")
@Controller
@RequestMapping("wechat")
public class WeChatJumpController
{
    @Autowired
    private WeChatConfig weChatConfig;

    /**
     * qrConnectUrl
     *
     * @param scope scope
     * @param state state
     * @return String
     */
    @ApiOperation("生成登录二维码")
    @GetMapping("qrConnectUrl")
    public String qrConnectUrl(String scope, String state)
    {
        return "redirect:" + weChatConfig.qrConnectUrl(scope, state);
    }

    /**
     * connectOauth2AuthorizeUrl
     *
     * @param scope scope
     * @param state state
     * @return String
     */
    @ApiOperation("用户授权")
    @GetMapping("connectOauth2AuthorizeUrl")
    public String connectOauth2AuthorizeUrl(@RequestParam(value = "scope", defaultValue = "snsapi_userinfo") String scope,
                                            @RequestParam(value = "state", defaultValue = "STATE") String state)
    {
        return "redirect:" + weChatConfig.connectOauth2AuthorizeUrl(scope, state);
    }
}
