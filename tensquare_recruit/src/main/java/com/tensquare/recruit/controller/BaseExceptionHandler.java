package com.tensquare.recruit.controller;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理类
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
