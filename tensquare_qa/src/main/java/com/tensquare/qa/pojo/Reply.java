package com.tensquare.qa.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实体类
 *
 * @author Administrator
 */
@Data
@Entity
@Table(name = "tb_reply")
public class Reply implements Serializable
{

    @Id
    private String id;//编号
    private String problemid;//问题ID
    private String content;//回答内容
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date createtime;//创建日期
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date updatetime;//更新日期
    private String userid;//回答人ID
    private String nickname;//回答人昵称

}
