package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Author: Durian
 * Date: 2020/2/13 22:12
 * Description:
 */
@FeignClient("tensquare-user")
public interface UserClient
{
    @PutMapping("/user/updatefans/{userId}/{count}")
    void updateFans(@PathVariable("userId") String userId, @PathVariable("count") int count);

    @PutMapping("/user/updatefollows/{userId}/{count}")
    void updateFollows(@PathVariable("userId") String userId, @PathVariable("count") int count);
}
