package com.ctd.wechat.config.tencent.wx;

import com.ctd.wechat.config.tencent.wx.storage.WeChatStorageAutoConfiguration;
import com.ctd.wechat.properties.tencent.wx.WeChatProperties;
import com.ctd.wechat.properties.tencent.wx.storage.CacheConfigStorageProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * WeChatAutoConfiguration
 *
 * @author chentudong
 * @date 2020/4/21 6:18 下午
 * @since 1.0
 */
@ComponentScan
@Configuration
@EnableConfigurationProperties({WeChatProperties.class, CacheConfigStorageProperties.class})
@Import({WeChatStorageAutoConfiguration.class, WeChatServiceAutoConfiguration.class})
public class WeChatAutoConfiguration
{
}
