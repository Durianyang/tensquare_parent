package com.tensquare.user;

import com.tensquare.utils.IdWorker;
import com.tensquare.utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableEurekaClient
public class UserApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    public IdWorker idWorker()
    {
        return new IdWorker(1, 1);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtils jwtUtils()
    {
        return new JwtUtils();
    }


}
