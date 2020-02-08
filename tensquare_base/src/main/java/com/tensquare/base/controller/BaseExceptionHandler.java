package com.tensquare.base.controller;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Author: Durian
 * Date: 2020/2/8 22:59
 * Description: 统一异常处理
 */
@RestControllerAdvice
public class BaseExceptionHandler
{
    @ExceptionHandler(value = Exception.class)
    public Result error(Exception e)
    {
        e.printStackTrace();
        return new Result(StatusCode.ERROR, false, e.getMessage());
    }
}
