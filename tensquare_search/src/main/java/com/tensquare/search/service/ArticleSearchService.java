package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Author: Durian
 * Date: 2020/2/11 17:27
 * Description:
 */
@Service
public class ArticleSearchService
{
    private final ArticleDao articleDao;
    private final IdWorker idWorker;

    @Autowired
    public ArticleSearchService(ArticleDao articleDao, IdWorker idWorker)
    {
        this.articleDao = articleDao;
        this.idWorker = idWorker;
    }

    public void save(Article article)
    {
        article.setId(String.valueOf(idWorker.nextId()));
        articleDao.save(article);
    }

    public Page<Article> findByTitleOrContentLike(int page, int size, String keywords)
    {
        Pageable pageable = PageRequest.of(page - 1, size);
        return articleDao.findByTitleOrContentLike(keywords, keywords, pageable);
    }

}
