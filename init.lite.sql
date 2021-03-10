create table tp_user
(
    id          integer
        constraint tp_user_pk
            primary key autoincrement,
    username    varchar(50) not null,
    password    varchar(50) not null,
    name        varchar(20),
    email       varchar(50),
    phone       varchar(20),
    role_id     integer,
    create_time datetime,
    update_time datetime,
    state       integer,
    token       varchar(40),
    expire      datetime
);

insert into tp_user (id, username, password, email, phone, role_id, state)
values (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin@twimi.cn', '13312345678', 1, 1);

create table tp_channel
(
    id          integer
        constraint tp_channel_pk
            primary key autoincrement,
    title       varchar(50) not null,
    create_time datetime,
    user_id     integer,
    state       integer
);