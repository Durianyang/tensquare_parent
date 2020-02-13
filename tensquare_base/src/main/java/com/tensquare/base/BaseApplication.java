package com.tensquare.base;

import com.tensquare.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * Author: Durian
 * Date: 2020/2/8 20:59
 * Description:
 */
@SpringBootApplication
@EnableEurekaClient
public class BaseApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(BaseApplication.class, args);
    }

    @Bean
    public IdWorker idWorker()
    {
        return new IdWorker(1, 1);
    }
}
