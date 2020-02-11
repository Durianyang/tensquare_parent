package com.tensquare.search.controller;

import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Durian
 * Date: 2020/2/11 17:29
 * Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController
{
    private final ArticleSearchService articleService;

    @Autowired
    public ArticleController(ArticleSearchService articleService)
    {
        this.articleService = articleService;
    }

    @PostMapping()
    public Result save(@RequestBody Article article)
    {
        articleService.save(article);
        return new Result(StatusCode.OK, true, "增加成功");
    }

    @GetMapping("/search/{keywords}/{page}/{size}")
    public Result<PageResult<Article>> search(@PathVariable String keywords,
                                              @PathVariable int page,
                                              @PathVariable int size)
    {
        Page<Article> pageData = articleService.findByTitleOrContentLike(page, size, keywords);
        return new Result<>(StatusCode.OK, true, "查询成功",
                new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }

}
