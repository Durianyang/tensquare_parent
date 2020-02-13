package com.tensquare.friend;

import com.tensquare.utils.IdWorker;
import com.tensquare.utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * Author: Durian
 * Date: 2020/2/13 20:42
 * Description:
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableDiscoveryClient
public class FriendApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(FriendApplication.class, args);
    }

    @Bean
    public IdWorker idWorker()
    {
        return new IdWorker();
    }

    @Bean
    public JwtUtils jwtUtils()
    {
        return new JwtUtils();
    }
}
