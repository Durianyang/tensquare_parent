package com.tensquare.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Author: Durian
 * Date: 2020/2/13 18:05
 * Description:
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(EurekaApplication.class, args);
    }
}
