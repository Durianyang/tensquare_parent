package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: Durian
 * Date: 2020/2/8 21:04
 * Description: 标签控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController
{
    private final LabelService labelService;

    @Autowired
    public LabelController(LabelService labelService)
    {
        this.labelService = labelService;
    }

    @GetMapping()
    public Result<List<Label>> findAll()
    {
        return new Result<>(StatusCode.OK, true, "查询成功", labelService.findAll());
    }

    @GetMapping("/{labelId}")
    public Result<Label> findById(@PathVariable("labelId") String labelId)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", labelService.findById(labelId));
    }

    @PostMapping()
    public Result save(@RequestBody Label label)
    {
        labelService.save(label);
        return new Result(StatusCode.OK, true, "保存成功");
    }

    @PutMapping("/{labelId}")
    public Result<Label> update(@PathVariable("labelId") String labelId, @RequestBody Label label)
    {
        label.setId(labelId);
        labelService.update(label);
        return new Result<>(StatusCode.OK, true, "更新成功");
    }

    @DeleteMapping("/{labelId}")
    public Result<Label> deleteById(@PathVariable("labelId") String labelId)
    {
        labelService.delete(labelId);
        return new Result<>(StatusCode.OK, true, "删除成功");
    }

    @PostMapping("/search")
    public Result<List<Label>> findSearch(@RequestBody Label label)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", labelService.findSearch(label));
    }

    @PostMapping("/search/{page}/{size}")
    public Result<PageResult<Label>> findSearchPage(@RequestBody Label label,
                                                    @PathVariable("page") int page,
                                                    @PathVariable("size") int size)
    {
        Page<Label> pageData = labelService.findSearchPage(label, page, size);
        return new Result<>(StatusCode.OK, true, "查询成功",
                new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }

}
