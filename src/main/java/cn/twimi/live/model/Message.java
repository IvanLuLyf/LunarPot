package cn.twimi.live.model;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private long id;
    private int type;
    private String content;
    private Date timestamp;
}
