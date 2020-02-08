package com.tensquare.base.controller;

import com.tensquare.base.pojo.City;
import com.tensquare.base.service.CityService;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: Durian
 * Date: 2020/2/8 21:10
 * Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/city")
public class CityController
{
    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService)
    {
        this.cityService = cityService;
    }

    @GetMapping()
    public Result<List<City>> findAll()
    {
        return new Result<>(StatusCode.OK, true, "查询成功", cityService.findAll());
    }

    @GetMapping("/{cityId}")
    public Result<City> findById(@PathVariable("cityId") String cityId)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", cityService.findById(cityId));
    }

    @PostMapping()
    public Result save(@RequestBody City city)
    {
        cityService.save(city);
        return new Result(StatusCode.OK, true, "保存成功");
    }

    @PutMapping("/{cityId}")
    public Result<City> update(@PathVariable("cityId") String cityId, @RequestBody City city)
    {
        city.setId(cityId);
        cityService.update(city);
        return new Result<>(StatusCode.OK, true, "更新成功");
    }

    @DeleteMapping("/{cityId}")
    public Result<City> deleteById(@PathVariable("cityId") String cityId)
    {
        cityService.delete(cityId);
        return new Result<>(StatusCode.OK, true, "删除成功");
    }
}
