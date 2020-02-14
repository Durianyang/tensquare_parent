package com.tensquare.article.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Author: Durian
 * Date: 2020/2/12 23:14
 * Description:
 */
@Configuration
@Component
public class DataSourceConfiguration
{
//    @ConfigurationProperties(prefix = "spring.datasource.druid")
//    @Bean
//    public DruidDataSource druidDataSource()
//    {
//        return new DruidDataSource();
//    }
//
//    /**
//     * 配置监控服务器
//     *
//     * @return 返回监控注册的servlet对象
//     * @author SimpleWu
//     */
//    @Bean
//    public ServletRegistrationBean<StatViewServlet> statViewServlet()
//    {
//        // 一下配置也可以在yml设置
//        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(
//                new StatViewServlet(), "/druid/*");
//        // 添加IP白名单
//        servletRegistrationBean.addInitParameter("allow", "0.0.0.0");
//        // 添加IP黑名单，当白名单和黑名单重复时，黑名单优先级更高
//        servletRegistrationBean.addInitParameter("deny", "0.0.0.0");
//        // 添加控制台管理用户
//        servletRegistrationBean.addInitParameter("loginUsername", "root");
//        servletRegistrationBean.addInitParameter("loginPassword", "123");
//        // 是否能够重置数据
//        servletRegistrationBean.addInitParameter("resetEnable", "false");
//        return servletRegistrationBean;
//    }
//
//    /**
//     * 配置服务过滤器
//     *
//     * @return 返回过滤器配置对象
//     */
//    @Bean
//    public FilterRegistrationBean<WebStatFilter> statFilter()
//    {
//        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(
//                new WebStatFilter());
//        // 添加过滤规则
//        filterRegistrationBean.addUrlPatterns("/*");
//        // 忽略过滤格式
//        filterRegistrationBean.addInitParameter("exclusions",
//                "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,");
//        return filterRegistrationBean;
//    }
}
