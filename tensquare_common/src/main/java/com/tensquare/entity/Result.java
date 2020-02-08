package com.tensquare.entity;

import lombok.Data;

/**
 * Author: Durian
 * Date: 2020/2/8 20:05
 * Description: 返回封装
 */
@Data
public class Result<T>
{
    private Integer code;
    private boolean flag;
    private String message;
    private T data;

    public Result()
    {
    }

    public Result(Integer code, boolean flag, String message)
    {
        this.code = code;
        this.flag = flag;
        this.message = message;
    }

    public Result(Integer code, boolean flag, String message, T data)
    {
        this.code = code;
        this.flag = flag;
        this.message = message;
        this.data = data;
    }
}
