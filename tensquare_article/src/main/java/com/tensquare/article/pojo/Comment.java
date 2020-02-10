package com.tensquare.article.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Durian
 * Date: 2020/2/10 19:49
 * Description:
 */
@Data
public class Comment implements Serializable
{
    @Id
    private String _id;
    private String articleid;
    private String content;
    private String userid;
    private String parentid;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private Date updateTime;
}
