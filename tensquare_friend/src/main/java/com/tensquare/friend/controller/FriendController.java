package com.tensquare.friend.controller;

import cn.hutool.core.util.StrUtil;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.friend.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Author: Durian
 * Date: 2020/2/13 20:49
 * Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/friend")
public class FriendController
{
    private final FriendService friendService;
    private final HttpServletRequest request;

    @Autowired
    public FriendController(FriendService friendService, HttpServletRequest request)
    {
        this.friendService = friendService;
        this.request = request;
    }


    /**
     * 屏蔽用户
     *
     * @param userId   用户id
     * @param friendId 被屏蔽用户id
     * @return json
     */
    @PutMapping("/shield/{userId}/{friendId}")
    public Result shieldFriend(@PathVariable String userId, @PathVariable String friendId)
    {
        String roles = (String) request.getAttribute("roles");
        if (!StrUtil.isBlank(roles) || Objects.equals("admin", roles) || Objects.equals("user", roles))
        {
            if (friendService.addNoFriend(userId, friendId) == 0)
            {
                return new Result(StatusCode.REP_ERROR, false, "你已屏蔽该用户");
            } else
            {
                return new Result(StatusCode.OK, true, "屏蔽成功");
            }
        }
        return new Result(StatusCode.ACCESS_ERROR, false, "未登录");
    }

    @PutMapping("/like//{friendId}")
    public Result addFriendOrDelFriend(@PathVariable String friendId)
    {
        String userId = (String) request.getAttribute("userid");
        if (!StrUtil.isBlank(userId))
        {
            if (friendService.addFriend(userId, friendId) == 0)
            {
                return new Result(StatusCode.REP_ERROR, false, "你已关注该用户");
            } else
            {
                return new Result(StatusCode.OK, true, "关注成功");
            }
        }
        return new Result(StatusCode.ACCESS_ERROR, false, "未登录");

    }

    @DeleteMapping("/{friendId}")
    public Result deleteFriend(@PathVariable String friendId)
    {
        String userId = (String) request.getAttribute("userid");
        if (!StrUtil.isBlank(userId))
        {
            friendService.deleteFriend(userId, friendId);
            return new Result(StatusCode.OK, true, "取消关注成功");
        }
        return new Result(StatusCode.ACCESS_ERROR, false, "未登录");
    }

}
