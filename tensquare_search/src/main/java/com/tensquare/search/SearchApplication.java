package com.tensquare.search;

import com.tensquare.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Author: Durian
 * Date: 2020/2/11 15:33
 * Description:
 */
@SpringBootApplication
public class SearchApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(SearchApplication.class, args);
    }

    @Bean
    public IdWorker idWorker()
    {
        return new IdWorker(1, 1);
    }
}
