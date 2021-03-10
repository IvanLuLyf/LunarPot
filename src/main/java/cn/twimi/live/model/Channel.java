package cn.twimi.live.model;

import lombok.Data;

import java.util.Date;

@Data
public class Channel {
    private long id;
    private String title;
    private long userId;
    private Date createTime;
    private int state;

    public Channel() {

    }

    public Channel(long userId, String title) {
        this.userId = userId;
        this.title = title;
        this.createTime = new Date();
        this.state = 0;
    }
}
