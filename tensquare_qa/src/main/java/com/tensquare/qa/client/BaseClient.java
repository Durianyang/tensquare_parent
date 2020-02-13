package com.tensquare.qa.client;

import com.tensquare.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Author: Durian
 * Date: 2020/2/13 19:46
 * Description: 调用基础模块的服务
 */
@FeignClient("tensquare-base") // 名称与被调用的服务名保持一致，并且不能包含下划线
public interface BaseClient
{
    @GetMapping("/label/{labelId}") // 确保名称一致
    Result findById(@PathVariable("labelId") String labelId);
}
