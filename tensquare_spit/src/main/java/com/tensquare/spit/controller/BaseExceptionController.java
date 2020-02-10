package com.tensquare.spit.controller;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Author: Durian
 * Date: 2020/2/10 18:18
 * Description:
 */
@RestControllerAdvice
public class BaseExceptionController
{
    @ExceptionHandler(value = Exception.class)
    public Result error(Exception e)
    {
        e.printStackTrace();
        return new Result(StatusCode.OK, true, "执行出错");
    }
}
