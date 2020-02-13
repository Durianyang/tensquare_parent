package com.tensquare.friend.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author: Durian
 * Date: 2020/2/13 20:45
 * Description:
 */
@Data
@Entity
@Table(name = "tb_friend")
@IdClass(Friend.class) // 联合主键
public class Friend implements Serializable
{
    @Id
    private String userid;
    @Id
    private String friendid;

    private String islike; // 1：相互喜欢 0：未相互喜欢
}
