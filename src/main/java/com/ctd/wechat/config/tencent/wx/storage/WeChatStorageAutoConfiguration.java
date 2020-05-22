package com.ctd.wechat.config.tencent.wx.storage;

import com.ctd.wechat.enums.cache.CacheStorageTypeEnum;
import com.ctd.wechat.properties.tencent.wx.WeChatProperties;
import com.ctd.wechat.properties.tencent.wx.storage.CacheConfigStorageProperties;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInRedisConfigStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Objects;

/**
 * WeChatStorageAutoConfiguration
 *
 * @author chentudong
 * @date 2020/4/21 1:46 下午
 * @since 1.0
 */
@Configuration
public class WeChatStorageAutoConfiguration
{
    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatStorageAutoConfiguration.class);
    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private RedisProperties redisProperties;

    @Autowired(required = false)
    private JedisPool jedisPool;

    @Bean
    @ConditionalOnMissingBean(WxMpConfigStorage.class)
    public WxMpConfigStorage wxMpInMemoryConfigStorage()
    {
        CacheConfigStorageProperties configStorage = weChatProperties.getConfigStorage();
        if (CacheStorageTypeEnum.Redis.equals(configStorage.getType()))
        {
            return getWxMpInRedisConfigStorage();
        }
        return getWxMpInMemoryConfigStorage();
    }

    private WxMpInMemoryConfigStorage getWxMpInMemoryConfigStorage()
    {
        LOGGER.info("cacheConfigStorage type: memory");
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        setWxMpInfo(config);
        return config;
    }


    private WxMpInRedisConfigStorage getWxMpInRedisConfigStorage()
    {
        LOGGER.info("cacheConfigStorage type: redis");
        JedisPool poolToUse = jedisPool;
        if (Objects.isNull(poolToUse))
        {
            poolToUse = getJedisPool();
        }
        WxMpInRedisConfigStorage config = new WxMpInRedisConfigStorage(poolToUse);
        config.setHttpProxyHost(redisProperties.getHost());
        setWxMpInfo(config);
        return config;
    }

    private void setWxMpInfo(WxMpInMemoryConfigStorage config)
    {
        config.setAppId(weChatProperties.getAppId());
        config.setSecret(weChatProperties.getAppSecret());
        config.setToken(weChatProperties.getToken());
        config.setAesKey(weChatProperties.getAesKey());
    }

    private JedisPool getJedisPool()
    {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setTestOnBorrow(true);
        config.setTestWhileIdle(true);
        return new JedisPool(config, redisProperties.getHost(), redisProperties.getPort(), 60, redisProperties.getPassword());
    }
}
