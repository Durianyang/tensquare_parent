package com.tensquare.article.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.hutool.core.util.StrUtil;
import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController
{

    private final ArticleService articleService;
    private final HttpServletRequest request;

    @Autowired
    public ArticleController(ArticleService articleService, HttpServletRequest request)
    {
        this.articleService = articleService;
        this.request = request;
    }


    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result<List<Article>> findAll()
    {
        return new Result<>(StatusCode.OK, true, "查询成功", articleService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Article> findById(@PathVariable String id)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", articleService.findById(id));
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
    public Result<PageResult<Article>> findSearch(@RequestBody Map searchMap,
                                                  @PathVariable int page,
                                                  @PathVariable int size)
    {
        Page<Article> pageList = articleService.findSearch(searchMap, page, size);
        return new Result<>(StatusCode.OK, true, "查询成功",
                new PageResult<Article>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result<List<Article>> findSearch(@RequestBody Map searchMap)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", articleService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param article
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article)
    {
        String roles = (String) request.getAttribute("roles");
        if (!StrUtil.isBlank(roles) || Objects.equals("admin", roles) || Objects.equals("user", roles))
        {
            articleService.add(article);
            return new Result(StatusCode.OK, true, "增加成功");
        }
        return new Result(StatusCode.ACCESS_ERROR, false, "未登录");
    }

    /**
     * 修改
     *
     * @param article
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Article article, @PathVariable String id)
    {
        article.setId(id);
        article.setUpdatetime(new Date());
        articleService.update(article);
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
        articleService.deleteById(id);
        return new Result(StatusCode.OK, true, "删除成功");
    }

    @PutMapping("/examine/{articleId}")
    public Result updateState(@PathVariable String articleId)
    {
        articleService.updateState(articleId);
        return new Result(StatusCode.OK, true, "审核成功");
    }

    @PutMapping("/thumbup/{articleId}")
    public Result updateThumbUp(@PathVariable String articleId)
    {
        articleService.updateThumbUp(articleId);
        return new Result(StatusCode.OK, true, "点赞成功");
    }


}
