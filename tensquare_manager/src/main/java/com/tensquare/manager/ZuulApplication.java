package com.tensquare.manager;

import com.tensquare.utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * Author: Durian
 * Date: 2020/2/14 14:53
 * Description: 后台管理网关
 */
@SpringBootApplication
@EnableZuulProxy // @EnableZuulServer 加强版
public class ZuulApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ZuulApplication.class, args);
    }

    @Bean
    public JwtUtils jwtUtils()
    {
        return new JwtUtils();
    }
}
