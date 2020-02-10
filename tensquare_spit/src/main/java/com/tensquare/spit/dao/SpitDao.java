package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * Author: Durian
 * Date: 2020/2/10 18:12
 * Description:
 */
public interface SpitDao extends MongoRepository<Spit, String>
{
    Page<Spit> findByParentid(String parentId, Pageable pageable);
}
