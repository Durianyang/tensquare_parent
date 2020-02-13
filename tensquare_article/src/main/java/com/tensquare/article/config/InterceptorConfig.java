package com.tensquare.article.config;

import com.tensquare.article.interceptor.JwtInterceptor;
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
        // 虽然所有路径都拦截，但是对于未登录进行查看操作时，token不存在，直接放行了
        // 登录状态token存在，检查合法性正确后放行
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**");
    }
}
