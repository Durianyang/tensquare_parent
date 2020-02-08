package com.tensquare.entity;

import lombok.Data;

import java.util.List;

/**
 * Author: Durian
 * Date: 2020/2/8 20:12
 * Description:
 */
@Data
public class PageResult<T>
{
    private long total;
    private List<T> rows;

}
