package com.tensquare.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * Author: Durian
 * Date: 2020/2/11 15:36
 * Description:
 */
@Data
@Document(indexName = "tensquare_article", type = "article")
public class Article implements Serializable
{
    @Id
    private String id;
    /**
     * 是否索引，是否能被搜索
     * 是否分词，
     * 是否存储，是否显示出来
     */
    @Field(analyzer = "ik_max_word", type = FieldType.text, searchAnalyzer = "ik_max_word")
    private String title;
    @Field(analyzer = "ik_max_word", type = FieldType.text, searchAnalyzer = "ik_max_word")
    private String content;
    @Field(index = false, type = FieldType.text)
    private String state;
}
