package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * Author: Durian
 * Date: 2020/2/11 17:26
 * Description:
 */
public interface ArticleDao extends ElasticsearchCrudRepository<Article, String>
{
    Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
