package com.tensquare.qa.client.impl;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.qa.client.BaseClient;
import org.springframework.stereotype.Component;

/**
 * Author: Durian
 * Date: 2020/2/14 13:39
 * Description:
 */
@Component
public class BaseClientHystrix implements BaseClient
{
    @Override
    public Result findById(String labelId)
    {
        return new Result(StatusCode.REMOTE_ERROR, false, "服务暂不可用");
    }
}
