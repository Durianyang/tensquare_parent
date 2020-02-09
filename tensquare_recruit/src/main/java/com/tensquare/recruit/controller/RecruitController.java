package com.tensquare.recruit.controller;

import java.util.List;
import java.util.Map;

import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.tensquare.recruit.pojo.Recruit;
import com.tensquare.recruit.service.RecruitService;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/recruit")
public class RecruitController
{

    private final RecruitService recruitService;

    @Autowired
    public RecruitController(RecruitService recruitService)
    {
        this.recruitService = recruitService;
    }


    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<Recruit>> findAll()
    {
        return new Result<>(StatusCode.OK, true, "查询成功", recruitService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public Result<Recruit> findById(@PathVariable String id)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", recruitService.findById(id));
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
    public Result<PageResult<Recruit>> findSearchPage(@RequestBody Map<String, Object> searchMap,
                                                      @PathVariable int page,
                                                      @PathVariable int size)
    {
        Page<Recruit> pageList = recruitService.findSearch(searchMap, page, size);
        return new Result<>(StatusCode.OK, true, "查询成功", new PageResult<Recruit>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result<List<Recruit>> findSearch(@RequestBody Map searchMap)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", recruitService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param recruit
     */
    @PostMapping
    public Result add(@RequestBody Recruit recruit)
    {
        recruitService.add(recruit);
        return new Result(StatusCode.OK, true, "增加成功");
    }

    /**
     * 修改
     *
     * @param recruit
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Recruit recruit, @PathVariable String id)
    {
        recruit.setId(id);
        recruitService.update(recruit);
        return new Result(StatusCode.OK, true, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id)
    {
        recruitService.deleteById(id);
        return new Result(StatusCode.OK, true, "删除成功");
    }

    @GetMapping("/search/recommend")
    public Result<List<Recruit>> recommend()
    {
        return new Result<>(StatusCode.OK, true, "查询成功", recruitService.findRecommend("2"));
    }

    @GetMapping("/search/newlist")
    public Result<List<Recruit>> newList()
    {
        return new Result<>(StatusCode.OK, true, "查询成功", recruitService.findNew("1"));
    }

}
