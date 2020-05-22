package com.ctd.wechat.config.tencent.wx;

import com.ctd.wechat.properties.tencent.wx.WeChatProperties;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WeChatServiceAutoConfiguration
 *
 * @author chentudong
 * @date 2020/4/21 1:43 下午
 * @since 1.0
 */
@Configuration
public class WeChatServiceAutoConfiguration
{
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private WeChatProperties weChatProperties;

    /**
     * 通过此注解，可以自已实现一个ConfigStorage来替换此存储策略
     *
     * @param configStorage configStorage
     * @return WxMpService
     */
    @Bean
    @ConditionalOnMissingBean
    public WxMpService wxMpService(WxMpConfigStorage configStorage)
    {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(configStorage);
        wxMpService.addConfigStorage(weChatProperties.getAppId(), configStorage);
        registerWxMpSubService(wxMpService);
        return wxMpService;
    }

    @ConditionalOnBean(WxMpService.class)
    public void registerWxMpSubService(WxMpService wxMpService)
    {
        //进行bean单例注册，注册后可注入使用
        ConfigurableListableBeanFactory factory = (ConfigurableListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        factory.registerSingleton("wxMpKefuService", wxMpService.getKefuService());
        factory.registerSingleton("wxMpMaterialService", wxMpService.getMaterialService());
        factory.registerSingleton("wxMpMenuService", wxMpService.getMenuService());
        factory.registerSingleton("wxMpUserService", wxMpService.getUserService());
        factory.registerSingleton("wxMpUserTagService", wxMpService.getUserTagService());
        factory.registerSingleton("wxMpQrcodeService", wxMpService.getQrcodeService());
        factory.registerSingleton("wxMpCardService", wxMpService.getCardService());
        factory.registerSingleton("wxMpDataCubeService", wxMpService.getDataCubeService());
        factory.registerSingleton("wxMpUserBlacklistService", wxMpService.getBlackListService());
        factory.registerSingleton("wxMpStoreService", wxMpService.getStoreService());
        factory.registerSingleton("wxMpTemplateMsgService", wxMpService.getTemplateMsgService());
        factory.registerSingleton("wxMpSubscribeMsgService", wxMpService.getSubscribeMsgService());
        factory.registerSingleton("wxMpDeviceService", wxMpService.getDeviceService());
        factory.registerSingleton("wxMpShakeService", wxMpService.getShakeService());
        factory.registerSingleton("wxMpMemberCardService", wxMpService.getMemberCardService());
        factory.registerSingleton("wxMpMassMessageService", wxMpService.getMassMessageService());
    }
}
