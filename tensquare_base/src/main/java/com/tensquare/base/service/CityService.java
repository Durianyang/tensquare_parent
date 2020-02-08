package com.tensquare.base.service;

import com.tensquare.base.dao.CityDao;
import com.tensquare.base.pojo.City;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: Durian
 * Date: 2020/2/8 23:03
 * Description:
 */
@Service
@Transactional
public class CityService
{
    private final CityDao cityDao;
    private final IdWorker idWorker;
    @Autowired
    public CityService(CityDao cityDao, IdWorker idWorker)
    {
        this.cityDao = cityDao;
        this.idWorker = idWorker;
    }
    public List<City> findAll()
    {
        return cityDao.findAll();
    }

    public City findById(String cityId)
    {
        return cityDao.findById(cityId).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(City city)
    {
        city.setId(idWorker.nextId() + "");
        cityDao.save(city);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update(City city)
    {
        cityDao.save(city);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(String cityId)
    {
        cityDao.deleteById(cityId);
    }

}
