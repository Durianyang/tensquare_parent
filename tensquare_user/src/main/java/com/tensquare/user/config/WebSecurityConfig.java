package com.tensquare.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Author: Durian
 * Date: 2020/2/12 19:50
 * Description:配置为所有地址都可以匿名访问。
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
            .antMatchers("/**").permitAll()
            .anyRequest().authenticated()
            .and().csrf().disable();
    }
}
