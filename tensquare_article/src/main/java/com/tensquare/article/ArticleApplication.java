package com.tensquare.article;

import com.tensquare.utils.IdWorker;
import com.tensquare.utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class ArticleApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ArticleApplication.class, args);
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
