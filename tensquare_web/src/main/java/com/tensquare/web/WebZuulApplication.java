package com.tensquare.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Author: Durian
 * Date: 2020/2/14 15:37
 * Description:
 */
@SpringBootApplication
@EnableZuulProxy
public class WebZuulApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(WebZuulApplication.class, args);
    }
}
