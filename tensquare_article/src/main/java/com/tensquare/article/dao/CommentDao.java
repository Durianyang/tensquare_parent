package com.tensquare.article.dao;

import com.tensquare.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Author: Durian
 * Date: 2020/2/10 19:50
 * Description:
 */
public interface CommentDao extends MongoRepository<Comment, String>
{
    List<Comment> findByArticleidOrderByCreateTimeDesc(String articleId);

    List<Comment> findByParentidOrderByCreateTimeDesc(String parentId);
}
