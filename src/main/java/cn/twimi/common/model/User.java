package cn.twimi.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    public static final int LOGIN = 0;
    public static final int ADMIN = 1;
    public static final int HOST = 2;
    public static final int GROUP = 3;

    private long id;
    private String username;
    @JsonIgnore
    private String password;
    private String name;
    private String email;
    private String avatar;
    private String phone;
    private int roleId;
    private Date createTime;
    private Date updateTime;
    private int state;
    private String token;
    private Date expire;

    public User() {

    }

    public User(String username, String password, String name, String email, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createTime = new Date();
        this.roleId = 0;
        this.state = 0;
    }
}
