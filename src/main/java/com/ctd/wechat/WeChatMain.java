package com.ctd.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * WeChatMain
 *
 * @author chentudong
 * @date 2020/4/21 22:32
 * @since 1.0
 */
@EnableSwagger2
@SpringBootApplication
public class WeChatMain
{
    public static void main(String[] args)
    {
       SpringApplication.run(WeChatMain.class, args);
    }
}
