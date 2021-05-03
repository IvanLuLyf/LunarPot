package cn.twimi.live.model;

import lombok.Data;

import java.util.Date;

@Data
public class Article {
    private long id;
    private String title;
    private String content;
    private Date createTime;
    private long userId;
    private int state;
}
