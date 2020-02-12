package com.tensquare.sms.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Author: Durian
 * Date: 2020/2/12 18:08
 * Description:
 */
@Configuration
@ConfigurationProperties(prefix = "access", ignoreUnknownFields = false)
@PropertySource("classpath:accessKey.properties")
@Data
@Component
public class AccessKey
{
    // id
    private String keyId;
    // secret
    private String secret;
    // 地区代码
    private String regionId;
    // 短信签名名称
    private String signName;
    // 短信模板代码
    private String templateCode;
}
