package com.tensquare.article.controller;

import java.util.List;
import java.util.Map;

import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.article.pojo.Column;
import com.tensquare.article.service.ColumnService;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/column")
public class ColumnController
{

    private final ColumnService columnService;

    @Autowired
    public ColumnController(ColumnService columnService)
    {
        this.columnService = columnService;
    }


    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result<List<Column>> findAll()
    {
        return new Result<>(StatusCode.OK, true, "查询成功", columnService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Column> findById(@PathVariable String id)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", columnService.findById(id));
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
    public Result<PageResult<Column>> findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size)
    {
        Page<Column> pageList = columnService.findSearch(searchMap, page, size);
        return new Result<>(StatusCode.OK, true, "查询成功",
                new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result<List<Column>> findSearch(@RequestBody Map searchMap)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", columnService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param column
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Column column)
    {
        columnService.add(column);
        return new Result(StatusCode.OK, true, "增加成功");
    }

    /**
     * 修改
     *
     * @param column
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Column column, @PathVariable String id)
    {
        column.setId(id);
        columnService.update(column);
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
        columnService.deleteById(id);
        return new Result(StatusCode.OK, true, "删除成功");
    }

}
