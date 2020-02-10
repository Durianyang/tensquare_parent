package com.tensquare.spit.controller;

import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: Durian
 * Date: 2020/2/10 17:43
 * Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController
{
    private final SpitService spitService;

    @Autowired
    public SpitController(SpitService spitService)
    {
        this.spitService = spitService;
    }

    @GetMapping
    public Result<List<Spit>> findAll()
    {
        return new Result<>(StatusCode.OK, true, "查询成功", spitService.findAll());
    }

    @GetMapping("/{id}")
    public Result<Spit> findById(@PathVariable String id)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", spitService.findById(id));
    }

    @PostMapping()
    public Result add(@RequestBody Spit spit)
    {
        spitService.save(spit);
        return new Result(StatusCode.OK, true, "增加成功");
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody Spit spit, @PathVariable String id)
    {
        spit.set_id(id);
        spitService.update(spit);
        return new Result(StatusCode.OK, true, "修改成功");
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable String id)
    {
        spitService.deleteById(id);
        return new Result(StatusCode.OK, true, "删除成功");
    }

    @PutMapping("/thumbup/{id}")
    public Result thumbUp(@PathVariable String id)
    {
        spitService.thumbUP(id);
        return new Result(StatusCode.OK, true, "点赞成功");
    }

    @PostMapping("/comment/{parentId}/{page}/{size}")
    public Result<PageResult<Spit>> findByParentIdPage(@PathVariable String parentId,
                                                       @PathVariable int page,
                                                       @PathVariable int size)
    {
        Page<Spit> pageData = spitService.findByParentIdPage(parentId, page, size);
        return new Result<>(StatusCode.OK, true, "查询成功",
                new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }

}
