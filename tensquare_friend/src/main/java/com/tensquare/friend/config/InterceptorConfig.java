package com.tensquare.friend.config;

import com.tensquare.friend.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Author: Durian
 * Date: 2020/2/13 15:36
 * Description:
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport
{
    private final JwtInterceptor jwtInterceptor;

    @Autowired
    public InterceptorConfig(JwtInterceptor jwtInterceptor)
    {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**");
    }
}
