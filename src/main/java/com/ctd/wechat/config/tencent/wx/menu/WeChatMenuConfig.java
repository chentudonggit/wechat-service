package com.ctd.wechat.config.tencent.wx.menu;

import com.ctd.wechat.properties.tencent.wx.meun.WeChatMenuProperties;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * WeChatMenuConfig
 *
 * @author chentudong
 * @date 2020/4/21 2:36 下午
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties(WeChatMenuProperties.class)
public class WeChatMenuConfig
{
    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatMenuConfig.class);
    @Autowired
    private WeChatMenuProperties weChatMenuProperties;

    @Autowired
    private WxMpService wxMpService;

    /**
     * 项目启动立即加载
     * 创建微信自定义菜单
     *
     * @return String
     */
    @PostConstruct
    public String createMenu() throws WxErrorException
    {
        if (weChatMenuProperties.isEnabled())
        {
            LOGGER.info("创建微信自定义菜单 ==== start ====");
            List<WxMenuButton> buttons = weChatMenuProperties.getButtons();
            WxMpMenuService menuService = wxMpService.getMenuService();
            WxMenu wxMenu = new WxMenu();
            wxMenu.setButtons(buttons);
            String menuCreate = menuService.menuCreate(wxMenu);
            LOGGER.info("创建微信自定义菜单 data: {}", wxMenu);
            LOGGER.info("创建微信自定义菜单 ====  end  ====");
            return menuCreate;
        }
        return null;
    }
}
