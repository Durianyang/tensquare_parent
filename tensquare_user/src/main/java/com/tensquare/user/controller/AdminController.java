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

import com.tensquare.user.pojo.Admin;
import com.tensquare.user.service.AdminService;


/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController
{

    private final AdminService adminService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AdminController(AdminService adminService, JwtUtils jwtUtils)
    {
        this.adminService = adminService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody Admin adminInfo)
    {
        Admin admin = adminService.findByLoginNameAndPassword(adminInfo.getLoginname(), adminInfo.getPassword());
        if (admin != null)
        {
            String token = jwtUtils.createJWT(admin.getId(), admin.getLoginname(), "admin");
            Map<String, String> data = new HashMap<>(2);
            data.put("token", token);
            data.put("roles", "admin");
            return new Result<>(StatusCode.OK, true, "登录成功", data);
        } else
        {
            return new Result<>(StatusCode.LOGIN_ERROR, false, "用户名或密码错误", null);
        }
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll()
    {
        return new Result<>(StatusCode.OK, true, "查询成功", adminService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", adminService.findById(id));
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
        Page<Admin> pageList = adminService.findSearch(searchMap, page, size);
        return new Result<>(StatusCode.OK, true, "查询成功", new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", adminService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param admin
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Admin admin)
    {
        boolean flag = adminService.add(admin);
        if (flag)
        {
            return new Result(StatusCode.OK, true, "增加成功");
        }else {
            return new Result(StatusCode.ERROR, false, "管理员已存在");
        }
    }

    /**
     * 修改
     *
     * @param admin
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Admin admin, @PathVariable String id)
    {
        admin.setId(id);
        adminService.update(admin);
        return new Result(StatusCode.OK, true, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id)
    {
        adminService.deleteById(id);
        return new Result(StatusCode.OK, true, "删除成功");
    }

}
