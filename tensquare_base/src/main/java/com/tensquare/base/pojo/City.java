package com.tensquare.base.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author: Durian
 * Date: 2020/2/8 21:32
 * Description:
 */
@Data
@Entity
@Table(name = "tb_city")
public class City implements Serializable
{
    @Id
    private String id;
    private String name;
    private String ishot;
}
