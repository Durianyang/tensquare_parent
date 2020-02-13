package com.tensquare.friend.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author: Durian
 * Date: 2020/2/13 21:17
 * Description:
 */
@Data
@Entity
@Table(name = "tb_nofriend")
@IdClass(NoFriend.class)
public class NoFriend implements Serializable
{
    @Id
    private String userid;
    @Id
    private String friendid;
}
