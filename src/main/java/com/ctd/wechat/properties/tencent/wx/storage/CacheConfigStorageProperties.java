package com.ctd.wechat.properties.tencent.wx.storage;

import com.ctd.wechat.enums.cache.CacheStorageTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * CacheConfigStorageProperties
 *
 * @author chentudong
 * @date 2020/4/21 1:47 下午
 * @since 1.0
 */
@ConfigurationProperties(prefix = "tencent.wx.config-storage")
public class CacheConfigStorageProperties
{
    private CacheStorageTypeEnum type = CacheStorageTypeEnum.Memory;

    public CacheStorageTypeEnum getType()
    {
        return type;
    }

    public void setType(CacheStorageTypeEnum type)
    {
        this.type = type;
    }
}
