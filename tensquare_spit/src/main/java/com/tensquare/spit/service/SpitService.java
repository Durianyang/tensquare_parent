package com.tensquare.spit.service;

import cn.hutool.core.util.StrUtil;
import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.dao.SpitDao;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Author: Durian
 * Date: 2020/2/10 17:42
 * Description:
 */
@Service
@Transactional
public class SpitService
{
    private final IdWorker idWorker;
    private final SpitDao spitDao;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public SpitService(IdWorker idWorker, SpitDao spitDao, MongoTemplate mongoTemplate)
    {
        this.idWorker = idWorker;
        this.spitDao = spitDao;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Spit> findAll()
    {
        return spitDao.findAll();
    }

    public Spit findById(String id)
    {
        return spitDao.findById(id).orElse(null);
    }

    public void save(Spit spit)
    {
        spit.set_id(String.valueOf(idWorker.nextId()));
        spit.setPublishtime(new Date());
        spit.setShare(0);
        spit.setThumbup(0);
        spit.setComment(0);
        spit.setVisits(0);
        spit.setState("1");
        // 如果该吐槽有父节点，则父节点回复数+1
        if (!StrUtil.isBlank(spit.getParentid()))
        {
            addComment(spit.getParentid());
        }
        spitDao.save(spit);
    }

    public void update(Spit spit)
    {
        spitDao.save(spit);
    }

    public void deleteById(String id)
    {
        spitDao.deleteById(id);
    }

    /**
     * 点赞，使用原生方式避免效率问题  db.spit.update({"_id", "id"}, {$inc:{thumbup:NumberInt(1)}})
     * @param id _id
     */
    public void thumbUP(String id)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }

    public Page<Spit> findByParentIdPage(String parentId, int page, int size)
    {
        Pageable pageable = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentId, pageable);
    }

    public void addComment(String parentId)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(parentId));
        Update update = new Update();
        update.inc("comment", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }
}
