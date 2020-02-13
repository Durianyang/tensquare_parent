package com.tensquare.user.controller;

import java.util.HashMap;
import java.util.Map;

import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController
{

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserController(UserService userService, JwtUtils jwtUtils)
    {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * 发送手机验证码
     */
    @GetMapping("/sendsms/{mobile}")
    public Result sendSms(@PathVariable String mobile)
    {
        userService.sendSms(mobile);
        return new Result(StatusCode.OK, true, "短信验证码发送成功");
    }

    /**
     * 登录
     *
     */
    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody User userInfo)
    {
        User user = userService.findByNickNameAndPassword(userInfo.getNickname(), userInfo.getPassword());
        if (user != null)
        {
            String token = jwtUtils.createJWT(user.getId(), user.getNickname(), "user");
            Map<String, String> data = new HashMap<>(2);
            data.put("token", token);
            data.put("roles", "user");
            data.put("avatar", user.getAvatar());
            return new Result<>(StatusCode.OK, true, "登录成功", data);
        } else
        {
            return new Result<>(StatusCode.ERROR, false, null);
        }
    }


    /**
     * 查询全部数据
     *
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll()
    {
        return new Result<>(StatusCode.OK, true, "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", userService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size)
    {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result<>(StatusCode.OK, true, "查询成功",
                new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", userService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     */
    @PostMapping("/register/{code}")
    public Result add(@RequestBody User user, @PathVariable String code)
    {
        boolean flag = userService.add(user, code);
        if (flag)
        {
            return new Result<>(StatusCode.OK, true, "注册成功");
        }
        return new Result<>(StatusCode.ERROR, false, "昵称重复");
    }

    /**
     * 修改
     *
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody User user, @PathVariable String id)
    {
        user.setId(id);
        userService.update(user);
        return new Result(StatusCode.OK, true, "修改成功");
    }

    /**
     * 删除
     *
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id)
    {
        userService.deleteById(id);
        return new Result(StatusCode.OK, true, "删除成功");
    }

}
