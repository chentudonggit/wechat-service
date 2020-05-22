package com.ctd.wechat.controller;

import com.ctd.springboot.common.core.enums.code.CodeEnum;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import com.ctd.springboot.common.core.vo.response.ResponseVO;
import com.ctd.wechat.conduct.WeChatConduct;
import com.ctd.wechat.properties.tencent.wx.WeChatProperties;
import com.ctd.wechat.utils.WeChatSignUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * WeChatController
 *
 * @author chentudong
 * @date 2020/4/22 11:28 上午
 * @since 1.0
 */
@Api(value = "WeChatController", tags = "微信逻辑处理")
@RestController
@RequestMapping("wechat")
public class WeChatController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatController.class);

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WeChatConduct weChatConduct;
    @Autowired
    private WeChatProperties weChatProperties;

    /**
     * 微信授权回调地址
     *
     * @param code code
     * @return ResponseVO
     */
    @ApiOperation("微信授权回调地址")
    @RequestMapping(value = "/callback", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseVO callback(String code)
    {
        return ResponseVO.data(code);
    }


    /**
     * 微信回调
     *
     * @param appId     appId
     * @param signature signature
     * @param timestamp timestamp
     * @param nonce     nonce
     * @param echostr   echostr
     * @return ResponseVO
     */
    @ApiOperation("微信服务器认证地址")
    @GetMapping(value = "/portal/{appId}")
    public ResponseVO authPortal(@PathVariable String appId,
                                 @RequestParam(name = "signature", required = false) String signature,
                                 @RequestParam(name = "timestamp", required = false) String timestamp,
                                 @RequestParam(name = "nonce", required = false) String nonce,
                                 @RequestParam(name = "echostr", required = false) String echostr)
    {
        LOGGER.info("\n接收到来自微信服务器的认证消息：{}, {}, {}, {}", signature, timestamp, nonce, echostr);
        ResponseVO response = new ResponseVO(1002, false, "请求参数非法，请核实!");
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr))
        {
            return response;
        }
        weChatConduct.switchover(appId);
        if (wxMpService.checkSignature(timestamp, nonce, signature))
        {
            response = ResponseVO.data(echostr);
        }
        LOGGER.info("处理成功 response = {}", response);
        return response;
    }

    /**
     * 微信回调
     *
     * @param appId       appId
     * @param signature   signature
     * @param timestamp   timestamp
     * @param nonce       nonce
     * @param requestBody requestBody
     */
    @ApiOperation("微信微信回调，处理各种事件")
    @PostMapping(value = "/portal/{appId}", produces = "application/xml; charset=UTF-8")
    public void callback(@PathVariable String appId,
                         @RequestParam("signature") String signature,
                         @RequestParam("timestamp") String timestamp,
                         @RequestParam("nonce") String nonce,
                         @RequestBody String requestBody)
    {
        LOGGER.info("\n接收微信请求：signature：{}, timestamp: {}, nonce: {} [requestBody=\n{}\n ", signature, timestamp, nonce, requestBody);
        weChatConduct.switchover(appId);
        if (!wxMpService.checkSignature(timestamp, nonce, signature))
        {
            AssertUtils.msgUser("请求参数非法，请核实!");
        }
        WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
        String msgType = inMessage.getMsgType();
        if (StringUtils.isNotBlank(msgType))
        {
            String openId = inMessage.getFromUser();
            String event = inMessage.getEvent();
            switch (msgType)
            {
                case WxConsts.XmlMsgType.EVENT:
                    weChatConduct.event(openId, event);
                    break;
                case WxConsts.XmlMsgType.TEXT:
                    weChatConduct.msgText(openId, inMessage.getContent());
                    break;
                case WxConsts.XmlMsgType.MUSIC:
                    weChatConduct.msgMusic(openId, inMessage);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 获取自动回复规则
     *
     * @return ResponseVO
     */
    @ApiOperation("获取自动回复规则")
    @GetMapping("findCurrentAutoReplyInfo")
    public ResponseVO findCurrentAutoReplyInfo()
    {
        try
        {
            return ResponseVO.data(wxMpService.getCurrentAutoReplyInfo());
        } catch (WxErrorException e)
        {
            e.printStackTrace();
        }
        return new ResponseVO(CodeEnum.SERVER_ERROR);
    }

    /**
     * 创建 JsapiSignature
     *
     * @param url url
     * @return ResponseVO
     */
    @ApiOperation("创建 createJsApiSignature")
    @GetMapping("createJsApiSignature")
    public ResponseVO createJsApiSignature(@RequestParam(value = "url", defaultValue = "http://hm-keji.com") String url)
    {
        try
        {
            return ResponseVO.data(wxMpService.createJsapiSignature(url));
        } catch (WxErrorException e)
        {
            e.printStackTrace();
        }
        return new ResponseVO(CodeEnum.UNAUTHENTICATED);
    }

    /**
     * 微信验证token
     *
     * @param signature signature
     * @param timestamp timestamp
     * @param nonce     nonce
     * @param echostr   echostr
     * @return String
     */
    @GetMapping(value = "/checkToken", produces = "text/html;charset=utf-8")
    public String checkToken(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
                             @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr)
    {
        if (WeChatSignUtils.check(signature, WeChatSignUtils.checkToken(weChatProperties.getToken(), timestamp, nonce).toString()))
        {
            return echostr;
        }
        return null;
    }
}
