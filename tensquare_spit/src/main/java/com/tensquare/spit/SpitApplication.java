package com.tensquare.spit;

import com.tensquare.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Author: Durian
 * Date: 2020/2/10 17:32
 * Description:
 */
@SpringBootApplication
public class SpitApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(SpitApplication.class, args);
    }

    @Bean
    IdWorker idWorker()
    {
        return new IdWorker(1 ,1);
    }
}
