package com.tensquare.qa;

import com.tensquare.utils.IdWorker;
import com.tensquare.utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient // 只适用于eureka注册中心
@EnableFeignClients // 可用于其他注册中心
@EnableDiscoveryClient
public class QaApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(QaApplication.class, args);
    }

    @Bean
    public IdWorker idWorker()
    {
        return new IdWorker(1, 1);
    }

    @Bean
    public JwtUtils jwtUtils()
    {
        return new JwtUtils();
    }

}
