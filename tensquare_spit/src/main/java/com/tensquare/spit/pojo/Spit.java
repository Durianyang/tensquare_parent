package com.tensquare.spit.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Durian
 * Date: 2020/2/10 17:34
 * Description:
 */
@Data
public class Spit implements Serializable
{
    @Id
    private String _id;
    private String content; // 内容
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishtime; // 发布时间
    private String userid; // 用户ID
    private String nickname; // 用户昵称
    private Integer visits; // 浏览量
    private Integer thumbup; // 点赞数
    private Integer share; // 分享数
    private Integer comment; // 评论数
    private String state; // 状态
    private String parentid; // 父ID
}
