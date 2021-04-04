package cn.twimi.live.model;

import lombok.Data;

import java.util.Date;

@Data
public class Live {
    private long id;
    private String title;
    private long userId;
    private Date createTime;
    private int state;

    public static final int STATE_CREATED = 0;
    public static final int STATE_STARTED = 1;
    public static final int STATE_ENDED = 2;

    public Live() {

    }

    public Live(long userId, String title) {
        this.userId = userId;
        this.title = title;
        this.createTime = new Date();
        this.state = 0;
    }
}
