create database storehouse;

use storehouse;

create table user
(

    id                                          int auto_increment comment 'ID'
                                                primary key,
    account  varchar(20)                        null comment '账号',
    name     varchar(256)                       null comment '昵称',
    password varchar(20)                        null comment '密码',
    age      int                                null comment '年龄',
    gender   int                                null comment '性别',
    phone    varchar(30)                        null comment '电话',
    role_id  int                                null comment '权限 0超级管理员 1管理员 2普通用户',
    isValid  varchar(4) default 'Y'             null comment '是否有效 Y有效其他无效'
)
    charset = utf8;

insert into user (account, name, password, age, gender, phone, role_id) values ('sa', '超管', '123456', 18, 0, '121', 0);

create table menu
(

    id              int                               comment 'ID' primary key,
    menuCode        varchar(8)                        null comment '菜单编码',
    menuName        varchar(16)                       null comment '菜单名字',
    menuLevel       varchar(2)                        null comment '菜单级别',
    menuParentCode  varchar(8)                        null comment '菜单父编码',
    menuClick       varchar(16)                       null comment '点击触发函数',
    menuRight       varchar(8)                        null comment '权限 0超级管理员 1管理员 2普通用户',
    menuComponent   varchar(200)                      null,
    menuIcon        varchar(100)                      null
)
    charset = utf8;

insert into menu values (1, '001', '管理员管理', '1', NULL, 'Admin', '0', 'manage/AdminManage.vue', 'el-icon-user-solid');

insert into menu values (2, '002', '用户管理', '1', NULL, 'User', '0,1', 'manage/UserManage.vue', 'el-icon-user');

create table storage
(
    id     int(11)       not null auto_increment comment '主键' primary key,
    name   varchar(100)  not null comment '仓库名',
    remark varchar(1000) default null comment '备注'
)
    engine = InnoDB default charset = utf8;

create table goodstype
(
    id     int(11)       not null auto_increment comment '主键' primary key,
    name   varchar(100)  not null comment '分类名',
    remark varchar(1000) default null comment '备注'
)
    engine = InnoDB default charset = utf8;

create table goods
(
    id          int(11)       not null auto_increment comment '主键' primary key,
    name        varchar(100)  not null comment '分类名',
    storage     int(11)       not null comment '仓库',
    goodstype   int(11)       not null comment '物品类型',
    count       int(11)       default null comment '数量',
    remark      varchar(1000) default null comment '备注'
)
    engine = InnoDB default charset = utf8;

create table record
(
    id          int(11)       not null auto_increment comment '主键' primary key,
    goods       int(11)       not null comment '物品id',
    userId      int(11)       default null comment '取货人/补货人',
    admin_id    int(11)       default null comment '操作人id',
    count       int(11)       default null comment '数量',
    createtime  datetime      default null comment '操作时间',
    remark      varchar(1000) default null comment '备注'
)
    engine = InnoDB default charset = utf8;
