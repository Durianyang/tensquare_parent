package com.tensquare.base.dao;

import com.tensquare.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Author: Durian
 * Date: 2020/2/8 22:13
 * Description:
 */
public interface LabelDao extends JpaRepository<Label, String>, JpaSpecificationExecutor<Label>
{

}
