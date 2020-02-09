package com.tensquare.qa.controller;

import java.util.List;
import java.util.Map;

import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.tensquare.qa.pojo.Reply;
import com.tensquare.qa.service.ReplyService;


/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/reply")
public class ReplyController
{

    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService)
    {
        this.replyService = replyService;
    }


    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result<List<Reply>> findAll()
    {
        return new Result<>(StatusCode.OK, true, "查询成功", replyService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public Result<Reply> findById(@PathVariable String id)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", replyService.findById(id));
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
    public Result<PageResult<Reply>> findSearch(@RequestBody Map<String, Object> searchMap,
                                                @PathVariable int page,
                                                @PathVariable int size)
    {
        Page<Reply> pageList = replyService.findSearch(searchMap, page, size);
        return new Result<>(StatusCode.OK, true, "查询成功", new PageResult<Reply>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result<List<Reply>> findSearch(@RequestBody Map searchMap)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", replyService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param reply
     */
    @PostMapping
    public Result add(@RequestBody Reply reply)
    {
        replyService.add(reply);
        return new Result(StatusCode.OK, true, "增加成功");
    }

    /**
     * 修改
     *
     * @param reply
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Reply reply, @PathVariable String id)
    {
        reply.setId(id);
        replyService.update(reply);
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
        replyService.deleteById(id);
        return new Result(StatusCode.OK, true, "删除成功");
    }

}
