package com.tensquare.recruit.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import cn.hutool.core.util.StrUtil;
import com.tensquare.entity.Result;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import com.tensquare.recruit.dao.RecruitDao;
import com.tensquare.recruit.pojo.Recruit;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class RecruitService
{

    private final RecruitDao recruitDao;
    private final IdWorker idWorker;

    @Autowired
    public RecruitService(IdWorker idWorker, RecruitDao recruitDao)
    {
        this.idWorker = idWorker;
        this.recruitDao = recruitDao;
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Recruit> findAll()
    {
        return recruitDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Recruit> findSearch(Map whereMap, int page, int size)
    {
        Specification<Recruit> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return recruitDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Recruit> findSearch(Map whereMap)
    {
        Specification<Recruit> specification = createSpecification(whereMap);
        return recruitDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Recruit findById(String id)
    {
        return recruitDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param recruit
     */
    public void add(Recruit recruit)
    {
        System.out.println(recruit);
        recruit.setId(idWorker.nextId() + "");
        recruit.setCreatetime(new Date());
        recruitDao.save(recruit);
    }

    /**
     * 修改
     *
     * @param recruit
     */
    @Transactional
    public void update(Recruit recruit)
    {
        recruitDao.save(recruit);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Transactional
    public void deleteById(String id)
    {
        recruitDao.deleteById(id);
    }

    /**
     * 最新职位
     */
    public List<Recruit> findRecommend(String state)
    {
        return recruitDao.findTop6ByStateOrderByCreatetimeDesc(state);
    }

    /**
     * 推荐职位
     */
    public List<Recruit> findNew(String state)
    {
        return recruitDao.findTop6ByStateOrderByCreatetimeDesc(state);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Recruit> createSpecification(Map searchMap)
    {

        return (Specification<Recruit>) (root, query, cb) ->
        {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (!StrUtil.isBlank((CharSequence) searchMap.get("id")))
            {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 职位名称
            if (!StrUtil.isBlank((CharSequence) searchMap.get("jobname")))
            {
                predicateList.add(cb.like(root.get("jobname").as(String.class), "%" + searchMap.get("jobname") + "%"));
            }
            // 薪资范围
            if (!StrUtil.isBlank((CharSequence) searchMap.get("salary")))
            {
                predicateList.add(cb.like(root.get("salary").as(String.class), "%" + searchMap.get("salary") + "%"));
            }
            // 经验要求
            if (!StrUtil.isBlank((CharSequence) searchMap.get("condition")))
            {
                predicateList.add(cb.like(root.get("condition").as(String.class), "%" + searchMap.get("condition") + "%"));
            }
            // 学历要求
            if (!StrUtil.isBlank((CharSequence) searchMap.get("education")))
            {
                predicateList.add(cb.like(root.get("education").as(String.class), "%" + searchMap.get("education") + "%"));
            }
            // 任职方式
            if (!StrUtil.isBlank((CharSequence) searchMap.get("type")))
            {
                predicateList.add(cb.like(root.get("type").as(String.class), "%" + searchMap.get("type") + "%"));
            }
            // 办公地址
            if (!StrUtil.isBlank((CharSequence) searchMap.get("address")))
            {
                predicateList.add(cb.like(root.get("address").as(String.class), "%" + searchMap.get("address") + "%"));
            }
            // 企业ID
            if (!StrUtil.isBlank((CharSequence) searchMap.get("eid")))
            {
                predicateList.add(cb.like(root.get("eid").as(String.class), "%" + searchMap.get("eid") + "%"));
            }
            // 状态
            if (!StrUtil.isBlank((CharSequence) searchMap.get("state")))
            {
                predicateList.add(cb.equal(root.get("state").as(String.class), searchMap.get("state")));
            }
            // 网址
            if (!StrUtil.isBlank((CharSequence) searchMap.get("url")))
            {
                predicateList.add(cb.equal(root.get("url").as(String.class), searchMap.get("url")));
            }
            // 标签
            if (!StrUtil.isBlank((CharSequence) searchMap.get("label")))
            {
                predicateList.add(cb.equal(root.get("label").as(String.class), searchMap.get("label")));
            }
            // 职位描述
            if (!StrUtil.isBlank((CharSequence) searchMap.get("content1")))
            {
                predicateList.add(cb.like(root.get("content1").as(String.class), "%" + searchMap.get("content1") + "%"));
            }
            // 职位要求
            if (!StrUtil.isBlank((CharSequence) searchMap.get("content2")))
            {
                predicateList.add(cb.like(root.get("content2").as(String.class), "%" + searchMap.get("content2") + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[0]));

        };

    }

}
