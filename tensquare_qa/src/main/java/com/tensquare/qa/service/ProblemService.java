package com.tensquare.qa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import com.tensquare.qa.dao.ProblemDao;
import com.tensquare.qa.pojo.Problem;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class ProblemService
{

    private final ProblemDao problemDao;

    private final IdWorker idWorker;

    @Autowired
    public ProblemService(ProblemDao problemDao, IdWorker idWorker)
    {
        this.problemDao = problemDao;
        this.idWorker = idWorker;
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Problem> findAll()
    {
        return problemDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> findSearch(Map whereMap, int page, int size)
    {
        Specification<Problem> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Problem> findSearch(Map whereMap)
    {
        Specification<Problem> specification = createSpecification(whereMap);
        return problemDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Problem findById(String id)
    {
        return problemDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param problem
     */
    public void add(Problem problem)
    {
        problem.setId(idWorker.nextId() + "");
        problem.setCreatetime(new Date());
        problem.setReply(0L);
        problem.setVisits(0L);
        problem.setThumbup(0L);
        problem.setSolve("0");
        problemDao.save(problem);
    }

    /**
     * 修改
     *
     * @param problem
     */
    public void update(Problem problem)
    {
        problem.setUpdatetime(new Date());
        problemDao.save(problem);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id)
    {
        problemDao.deleteById(id);
    }

    /**
     * 最新回答
     *
     * @param labelId 问题所含标签ID
     * @param page    页码
     * @param size    页大小
     * @return 分页结果
     */
    public Page<Problem> newList(String labelId, int page, int size)
    {
        return problemDao.newList(labelId, PageRequest.of(page - 1, size));
    }

    /**
     * 最热回答
     *
     * @param labelId 问题所含标签ID
     * @param page    页码
     * @param size    页大小
     * @return 分页结果
     */
    public Page<Problem> hotList(String labelId, int page, int size)
    {
        return problemDao.hotList(labelId, PageRequest.of(page - 1, size));
    }

    /**
     * 等待回答
     *
     * @param labelId 问题所含标签ID
     * @param page    页码
     * @param size    页大小
     * @return 分页结果
     */
    public Page<Problem> waitList(String labelId, int page, int size)
    {
        return problemDao.waitList(labelId, PageRequest.of(page - 1, size));
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Problem> createSpecification(Map searchMap)
    {

        return (Specification<Problem>) (root, query, cb) ->
        {
            List<Predicate> predicateList = new ArrayList<Predicate>();
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id")))
            {
                predicateList.add(cb.equal(root.get("id").as(String.class), searchMap.get("id")));
            }
            // 标题
            if (searchMap.get("title") != null && !"".equals(searchMap.get("title")))
            {
                predicateList.add(cb.like(root.get("title").as(String.class), "%" + searchMap.get("title") + "%"));
            }
            // 内容
            if (searchMap.get("content") != null && !"".equals(searchMap.get("content")))
            {
                predicateList.add(cb.like(root.get("content").as(String.class), "%" + searchMap.get("content") + "%"));
            }
            // 用户ID
            if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid")))
            {
                predicateList.add(cb.equal(root.get("userid").as(String.class), searchMap.get("userid")));
            }
            // 昵称
            if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname")))
            {
                predicateList.add(cb.equal(root.get("nickname").as(String.class), searchMap.get("nickname")));
            }
            // 是否解决
            if (searchMap.get("solve") != null && !"".equals(searchMap.get("solve")))
            {
                predicateList.add(cb.equal(root.get("solve").as(String.class), searchMap.get("solve")));
            }
            // 回复人昵称
            if (searchMap.get("replyname") != null && !"".equals(searchMap.get("replyname")))
            {
                predicateList.add(cb.equal(root.get("replyname").as(String.class), searchMap.get("replyname")));
            }

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }

}
