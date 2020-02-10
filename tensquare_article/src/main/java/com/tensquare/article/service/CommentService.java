package com.tensquare.article.service;

import com.tensquare.article.dao.CommentDao;
import com.tensquare.article.pojo.Comment;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Author: Durian
 * Date: 2020/2/10 19:51
 * Description:
 */
@Service
@Transactional
public class CommentService
{
    private final CommentDao commentDao;
    private final IdWorker idWorker;

    @Autowired
    public CommentService(CommentDao commentDao, IdWorker idWorker)
    {
        this.commentDao = commentDao;
        this.idWorker = idWorker;
    }

    /**
     * 根据文章ID查询comment列表
     */
    public List<Comment> findByArticleId(String articleId)
    {
        return commentDao.findByArticleidOrderByCreateTimeDesc(articleId);
    }

    /**
     * 查询某个评论的子评论
     */
    public List<Comment> findByParentId(String parentId)
    {
        return commentDao.findByParentidOrderByCreateTimeDesc(parentId);
    }

    public void update(Comment comment)
    {
        comment.setUpdateTime(new Date());
        commentDao.save(comment);
    }

    public void add(Comment comment)
    {
        comment.set_id(String.valueOf(idWorker.nextId()));
        comment.setCreateTime(new Date());
        commentDao.save(comment);
    }

    public void deleteByID(String id)
    {
        commentDao.deleteById(id);
    }


}
