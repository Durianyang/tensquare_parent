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
@Table(name = "tb_label")
public class Label implements Serializable
{
    @Id
    private String id;
    private String labelname; // 标签名
    private String state; // 状态
    private Long count; // 使用数
    private Long fans; // 关注数
    private String recommend; // 是否推荐
}
