package cn.twimi.live.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private long id;
    @JsonIgnore
    private long liveId;
    private int type;
    private String content;
    private String extra;
    private Date timestamp;

    public static final int TYPE_SYSTEM = 0;
    public static final int TYPE_TEXT = 1;
    public static final int TYPE_IMAGE = 2;

    public Message() {

    }

    public Message(long liveId, String content) {
        this.liveId = liveId;
        this.content = content;
        this.timestamp = new Date();
    }
}
