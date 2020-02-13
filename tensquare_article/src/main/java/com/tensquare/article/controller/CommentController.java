package com.tensquare.article.controller;

import cn.hutool.core.util.StrUtil;
import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * Author: Durian
 * Date: 2020/2/10 20:02
 * Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController
{
    private final CommentService commentService;
    private final HttpServletRequest request;

    @Autowired
    public CommentController(CommentService commentService, HttpServletRequest request)
    {
        this.commentService = commentService;
        this.request = request;
    }

    @GetMapping("/article/{articleId}")
    public Result<List<Comment>> findByArticleId(@PathVariable String articleId)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", commentService.findByArticleId(articleId));
    }

    @GetMapping("/child/{parentId}")
    public Result<List<Comment>> findByParentId(@PathVariable String parentId)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", commentService.findByParentId(parentId));
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable String id)
    {
        commentService.deleteByID(id);
        return new Result<>(StatusCode.OK, true, "删除成功");
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody Comment comment, @PathVariable String id)
    {
        comment.set_id(id);
        commentService.update(comment);
        return new Result<>(StatusCode.OK, true, "修改成功");
    }

    @PostMapping
    public Result add(@RequestBody Comment comment)
    {
        String roles = (String) request.getAttribute("roles");
        if (!StrUtil.isBlank(roles) || Objects.equals("admin", roles) || Objects.equals("user", roles))
        {
            commentService.add(comment);
            return new Result<>(StatusCode.OK, true, "增加成功");
        }
        return new Result<>(StatusCode.ACCESS_ERROR, false, "未登录");
    }

}
