package com.tensquare.recruit.controller;

import java.util.List;
import java.util.Map;

import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.tensquare.recruit.pojo.Enterprise;
import com.tensquare.recruit.service.EnterpriseService;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/enterprise")
public class EnterpriseController
{

    private final EnterpriseService enterpriseService;
	@Autowired
	public EnterpriseController(EnterpriseService enterpriseService)
	{
		this.enterpriseService = enterpriseService;
	}


	/**
     * 查询全部数据
     *
     * @return
     */
	@GetMapping
    public Result<List<Enterprise>> findAll()
    {
        return new Result<>(StatusCode.OK, true, "查询成功", enterpriseService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public Result<Enterprise> findById(@PathVariable String id)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", enterpriseService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageResult<Enterprise>> findSearch(@RequestBody Map searchMap,
                                                     @PathVariable int page,
                                                     @PathVariable int size)
    {
        Page<Enterprise> pageList = enterpriseService.findSearch(searchMap, page, size);
        return new Result<>(StatusCode.OK, true, "查询成功", new PageResult<Enterprise>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result<List<Enterprise>> findSearch(@RequestBody Map searchMap)
    {
        return new Result<>(StatusCode.OK, true, "查询成功", enterpriseService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param enterprise
     */
    @PostMapping
    public Result add(@RequestBody Enterprise enterprise)
    {
        enterpriseService.add(enterprise);
        return new Result(StatusCode.OK, true, "增加成功");
    }

    /**
     * 修改
     *
     * @param enterprise
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Enterprise enterprise, @PathVariable String id)
    {
        enterprise.setId(id);
        enterpriseService.update(enterprise);
        return new Result(StatusCode.OK, true, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id)
    {
        enterpriseService.deleteById(id);
        return new Result(StatusCode.OK, true, "删除成功");
    }

    /**
     * 查询热门企业
     *
     */
    @GetMapping("/search/hotlist")
    public Result<List<Enterprise>> hotlist()
    {
        return new Result<>(StatusCode.OK, true, "查询成功", enterpriseService.findByIshot("1"));
    }


}
