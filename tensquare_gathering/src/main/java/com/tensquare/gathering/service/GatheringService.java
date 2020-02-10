package com.tensquare.gathering.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import com.tensquare.gathering.dao.GatheringDao;
import com.tensquare.gathering.pojo.Gathering;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
public class GatheringService
{

    private final GatheringDao gatheringDao;

    private final IdWorker idWorker;
	@Autowired
	public GatheringService(GatheringDao gatheringDao, IdWorker idWorker)
	{
		this.gatheringDao = gatheringDao;
		this.idWorker = idWorker;
	}

	/**
     * 查询全部列表
     *
     * @return
     */
    public List<Gathering> findAll()
    {
        return gatheringDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Gathering> findSearch(Map whereMap, int page, int size)
    {
        Specification<Gathering> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return gatheringDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Gathering> findSearch(Map whereMap)
    {
        Specification<Gathering> specification = createSpecification(whereMap);
        return gatheringDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    @Cacheable(value = "gathering", key = "#id")
    public Gathering findById(String id)
    {
        return gatheringDao.findById(id).orElse(null);
    }

    /**
     * 增加
     *
     * @param gathering
     */
    @CacheEvict(value = "gathering", key = "#gathering.id")
    public void add(Gathering gathering)
    {
        gathering.setState("0");
        gathering.setId(String.valueOf(idWorker.nextId()));
        gatheringDao.save(gathering);
    }

    /**
     * 修改
     *
     * @param gathering
     */
    @CacheEvict(value = "gathering", key = "#gathering.id")
    public void update(Gathering gathering)
    {
        gatheringDao.save(gathering);
    }

    /**
     * 删除
     *
     * @param id
     */
    @CacheEvict(value = "gathering", key = "#id")
    public void deleteById(String id)
    {
        gatheringDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Gathering> createSpecification(Map searchMap)
    {

        return (Specification<Gathering>) (root, query, cb) ->
        {
            List<Predicate> predicateList = new ArrayList<>();
            // 编号
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id")))
            {
                predicateList.add(cb.equal(root.get("id").as(String.class), searchMap.get("id")));
            }
            // 活动名称
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name")))
            {
                predicateList.add(cb.like(root.get("name").as(String.class), "%" + searchMap.get("name") + "%"));
            }
            // 大会简介
            if (searchMap.get("summary") != null && !"".equals(searchMap.get("summary")))
            {
                predicateList.add(cb.like(root.get("summary").as(String.class), "%" + searchMap.get("summary") + "%"));
            }
            // 详细说明
            if (searchMap.get("detail") != null && !"".equals(searchMap.get("detail")))
            {
                predicateList.add(cb.like(root.get("detail").as(String.class), "%" + searchMap.get("detail") + "%"));
            }
            // 主办方
            if (searchMap.get("sponsor") != null && !"".equals(searchMap.get("sponsor")))
            {
                predicateList.add(cb.like(root.get("sponsor").as(String.class), "%" + searchMap.get("sponsor") + "%"));
            }
            // 活动图片
            if (searchMap.get("image") != null && !"".equals(searchMap.get("image")))
            {
                predicateList.add(cb.like(root.get("image").as(String.class), "%" + searchMap.get("image") + "%"));
            }
            // 举办地点
            if (searchMap.get("address") != null && !"".equals(searchMap.get("address")))
            {
                predicateList.add(cb.like(root.get("address").as(String.class), "%" + searchMap.get("address") + "%"));
            }
            // 是否可见
            if (searchMap.get("state") != null && !"".equals(searchMap.get("state")))
            {
                predicateList.add(cb.equal(root.get("state").as(String.class), searchMap.get("state")));
            }
            // 城市
            if (searchMap.get("city") != null && !"".equals(searchMap.get("city")))
            {
                predicateList.add(cb.like(root.get("city").as(String.class), "%" + searchMap.get("city") + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[0]));

        };

    }

}
