package com.tensquare.gathering;

import com.tensquare.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class GatheringApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(GatheringApplication.class, args);
    }

    @Bean
    public IdWorker idWorker()
    {
        return new IdWorker(1, 1);
    }

}
