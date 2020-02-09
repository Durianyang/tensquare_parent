package com.tensquare.base.service;

import cn.hutool.core.util.StrUtil;
import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Durian
 * Date: 2020/2/8 22:24
 * Description:
 */
@Service
public class LabelService
{
    private final LabelDao labelDao;
    private final IdWorker idWorker;

    @Autowired
    public LabelService(LabelDao labelDao, IdWorker idWorker)
    {
        this.labelDao = labelDao;
        this.idWorker = idWorker;
    }

    public List<Label> findAll()
    {
        return labelDao.findAll();
    }

    public Label findById(String labelId)
    {
        return labelDao.findById(labelId).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Label label)
    {
        label.setId(String.valueOf(idWorker.nextId()));
        labelDao.save(label);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Label label)
    {
        labelDao.save(label);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(String labelId)
    {
        labelDao.deleteById(labelId);
    }


    /**
     * 按条件查询label
     * @param label 查询条件
     * @return 查询结果
     */
    public List<Label> findSearch(Label label)
    {
        /*
         *
         * @param root 根对象，也就是当前对象。where 类名 = label.id
         * @param criteriaQuery 封装的查询关键字 group by，order by
         * @param criteriaBuilder 封装的查询条件
         * @return 查询条件
         */
        return labelDao.findAll((Specification<Label>) (root, criteriaQuery, criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();
            if (!StrUtil.isBlank(label.getLabelname()))
            {
                predicates.add(criteriaBuilder.like(root.get("labelname").as(String.class),
                        "%" + label.getLabelname() + "%")); // where labelname like "%?%"
            }
            if (!StrUtil.isBlank(label.getState()))
            {
                predicates.add(criteriaBuilder.equal(root.get("state").as(String.class),
                         label.getState()));
            }
            if (!StrUtil.isBlank(label.getRecommend()))
            {
                predicates.add(criteriaBuilder.equal(root.get("recommend").as(String.class),
                        label.getRecommend()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    public Page<Label> findSearchPage(Label label, int page, int size)
    {
        Pageable pageable = PageRequest.of(page - 1, size);
        return labelDao.findAll((Specification<Label>) (root, criteriaQuery, criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();
            if (!StrUtil.isBlank(label.getLabelname()))
            {
                predicates.add(criteriaBuilder.like(root.get("labelname").as(String.class),
                        "%" + label.getLabelname() + "%")); // where labelname like "%?%"
            }
            if (!StrUtil.isBlank(label.getState()))
            {
                predicates.add(criteriaBuilder.equal(root.get("state").as(String.class),
                        label.getState()));
            }
            if (!StrUtil.isBlank(label.getRecommend()))
            {
                predicates.add(criteriaBuilder.equal(root.get("recommend").as(String.class),
                        label.getRecommend()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);

    }
}
