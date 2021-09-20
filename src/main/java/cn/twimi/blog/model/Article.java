package cn.twimi.blog.model;

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

    public static final int STATE_NORMAL = 0;
    public static final int STATE_HIDDEN = 1;

    public Article() {

    }

    public Article(long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createTime = new Date();
        this.state = STATE_NORMAL;
    }
}
