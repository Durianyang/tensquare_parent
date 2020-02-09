package com.tensquare.article.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.Predicate;

import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
public class ArticleService
{
    private final RedisTemplate redisTemplate;
    private final ArticleDao articleDao;
    private final IdWorker idWorker;

    @Autowired
    public ArticleService(ArticleDao articleDao, IdWorker idWorker, RedisTemplate redisTemplate)
    {
        this.articleDao = articleDao;
        this.idWorker = idWorker;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Article> findAll()
    {
        return articleDao.findAll();
    }

    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findSearch(Map whereMap, int page, int size)
    {
        Specification<Article> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return articleDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Article> findSearch(Map whereMap)
    {
        Specification<Article> specification = createSpecification(whereMap);
        return articleDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Article findById(String id)
    {
        // 先从缓存中查询当前对象
        Article article = (Article) redisTemplate.opsForValue().get("article_" + id);
        // 如果没有取到
        if (null == article)
        {
            // 从数据库中查询
            article = articleDao.findById(id).get();
            // 存入缓存
            redisTemplate.opsForValue().set("article_" + id, article, 10, TimeUnit.MINUTES);
        }
        return article;
    }

    /**
     * 增加
     *
     * @param article
     */
    public void add(Article article)
    {
        article.setCreatetime(new Date());
        article.setVisits(0);
        article.setThumbup(0);
        article.setComment(0);
        article.setState("0");
        article.setId(idWorker.nextId() + "");
        articleDao.save(article);
    }

    /**
     * 修改
     *
     * @param article
     */
    public void update(Article article)
    {
        redisTemplate.delete("article_" + article.getId());
        articleDao.save(article);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id)
    {
        redisTemplate.delete("article_" + id);
        articleDao.deleteById(id);
    }

    /**
     * 审核
     */
    public void updateState(String articleId)
    {
        articleDao.updateState(articleId);
    }

    /**
     * 点赞
     */
    public void updateThumbUp(String articleId)
    {
        articleDao.updateThumbUp(articleId);
    }


    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Article> createSpecification(Map searchMap)
    {

        return (Specification<Article>) (root, query, cb) ->
        {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id")))
            {
                predicateList.add(cb.equal(root.get("id").as(String.class), searchMap.get("id")));
            }
            // 专栏ID
            if (searchMap.get("columnid") != null && !"".equals(searchMap.get("columnid")))
            {
                predicateList.add(cb.equal(root.get("columnid").as(String.class), searchMap.get("columnid")));
            }
            // 用户ID
            if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid")))
            {
                predicateList.add(cb.equal(root.get("userid").as(String.class), searchMap.get("userid")));
            }
            // 标题
            if (searchMap.get("title") != null && !"".equals(searchMap.get("title")))
            {
                predicateList.add(cb.like(root.get("title").as(String.class), "%" + searchMap.get("title") + "%"));
            }
            // 文章正文
            if (searchMap.get("content") != null && !"".equals(searchMap.get("content")))
            {
                predicateList.add(cb.like(root.get("content").as(String.class), "%" + searchMap.get("content") + "%"));
            }
            // 文章封面
            if (searchMap.get("image") != null && !"".equals(searchMap.get("image")))
            {
                predicateList.add(cb.like(root.get("image").as(String.class), "%" + searchMap.get("image") + "%"));
            }
            // 是否公开
            if (searchMap.get("ispublic") != null && !"".equals(searchMap.get("ispublic")))
            {
                predicateList.add(cb.equal(root.get("ispublic").as(String.class), searchMap.get("ispublic")));
            }
            // 是否置顶
            if (searchMap.get("istop") != null && !"".equals(searchMap.get("istop")))
            {
                predicateList.add(cb.equal(root.get("istop").as(String.class), searchMap.get("istop")));
            }
            // 审核状态
            if (searchMap.get("state") != null && !"".equals(searchMap.get("state")))
            {
                predicateList.add(cb.equal(root.get("state").as(String.class), searchMap.get("state")));
            }
            // 所属频道
            if (searchMap.get("channelid") != null && !"".equals(searchMap.get("channelid")))
            {
                predicateList.add(cb.equal(root.get("channelid").as(String.class), searchMap.get("channelid")));
            }
            // URL
            if (searchMap.get("url") != null && !"".equals(searchMap.get("url")))
            {
                predicateList.add(cb.like(root.get("url").as(String.class), "%" + searchMap.get("url") + "%"));
            }
            // 类型
            if (searchMap.get("type") != null && !"".equals(searchMap.get("type")))
            {
                predicateList.add(cb.equal(root.get("type").as(String.class), searchMap.get("type")));
            }

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }

}
