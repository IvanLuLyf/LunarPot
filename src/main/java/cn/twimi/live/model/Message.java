package cn.twimi.live.model;

import lombok.Data;

@Data
public class Message {
    private long id;
    private int type;
    private String content;
}
