package com.tensquare.base.dao;

import com.tensquare.base.pojo.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Author: Durian
 * Date: 2020/2/8 23:03
 * Description:
 */
public interface CityDao extends JpaRepository<City, String>, JpaSpecificationExecutor<City>
{
}
