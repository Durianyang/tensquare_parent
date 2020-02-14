package com.tensquare.qa.controller;

import java.util.Map;
import java.util.Objects;

import cn.hutool.core.util.StrUtil;
import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.qa.client.BaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController
{
    private final ProblemService problemService;
    private final HttpServletRequest request;
    private final BaseClient baseClient;

    @Autowired
    public ProblemController(ProblemService problemService, HttpServletRequest request, BaseClient baseClient)
    {
        this.problemService = problemService;
        this.request = request;
        this.baseClient = baseClient;
    }

    @GetMapping("/label/{labelId}")
    public Result findLabelById(@PathVariable String labelId)
    {
        return baseClient.findById(labelId);
    }

    /**
     * 查询全部数据
     *
     * @return 查询结果
     */
    @GetMapping
    public Result findAll()
    {
        return new Result<>(StatusCode.OK, true, "查询成功", problemService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", problemService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageResult<Problem>> findSearch(@RequestBody Map searchMap,
                                                  @PathVariable int page,
                                                  @PathVariable int size)
    {
        Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
        return new Result<>(StatusCode.OK, true, "查询成功",
                new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap 查询条件
     * @return 查询结果
     */
    @PostMapping("/search")
    public Result findSearch(@RequestBody Map searchMap)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", problemService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param problem 问题内容
     */
    @PostMapping
    public Result add(@RequestBody Problem problem)
    {
        String roles = (String) request.getAttribute("roles");
        if (!StrUtil.isBlank(roles) || Objects.equals("admin", roles) || Objects.equals("user", roles))
        {
            problemService.add(problem);
            return new Result<>(StatusCode.OK, true, "增加成功");
        }
        return new Result<>(StatusCode.ACCESS_ERROR, false, "未登录");
    }

    /**
     * 修改
     *
     * @param problem 问题info
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Problem problem, @PathVariable String id)
    {
        String roles = (String) request.getAttribute("roles");
        if (!StrUtil.isBlank(roles) || Objects.equals("admin", roles) || Objects.equals("user", roles))
        {
            problem.setId(id);
            problemService.update(problem);
            return new Result<>(StatusCode.OK, true, "修改成功");
        }
        return new Result<>(StatusCode.ACCESS_ERROR, false, "未登录");
    }

    /**
     * 删除
     *
     * @param id 问题ID
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id)
    {
        String roles = (String) request.getAttribute("roles");
        if (!StrUtil.isBlank(roles) || Objects.equals("admin", roles) || Objects.equals("user", roles))
        {
            problemService.deleteById(id);
            return new Result(StatusCode.OK, true, "删除成功");
        }
        return new Result<>(StatusCode.ACCESS_ERROR, false, "未登录");
    }

    /**
     * 最新回答
     */
    @GetMapping("/newlist/{labelId}/{page}/{size}")
    public Result<PageResult<Problem>> newList(@PathVariable String labelId,
                                               @PathVariable int page,
                                               @PathVariable int size)
    {
        Page<Problem> problems = problemService.newList(labelId, page, size);
        return new Result<>(StatusCode.OK, true, "查询成功",
                new PageResult<>(problems.getTotalElements(), problems.getContent()));
    }

    /**
     * 最热回答
     */
    @GetMapping("/hotlist/{labelId}/{page}/{size}")
    public Result<PageResult<Problem>> hotList(@PathVariable String labelId,
                                               @PathVariable int page,
                                               @PathVariable int size)
    {
        Page<Problem> problems = problemService.hotList(labelId, page, size);
        return new Result<>(StatusCode.OK, true, "查询成功",
                new PageResult<>(problems.getTotalElements(), problems.getContent()));
    }

    /**
     * 等待回答
     */
    @GetMapping("/waitlist/{labelId}/{page}/{size}")
    public Result<PageResult<Problem>> waitList(@PathVariable String labelId,
                                                @PathVariable int page,
                                                @PathVariable int size)
    {
        Page<Problem> problems = problemService.waitList(labelId, page, size);
        return new Result<>(StatusCode.OK, true, "查询成功",
                new PageResult<>(problems.getTotalElements(), problems.getContent()));
    }


}
