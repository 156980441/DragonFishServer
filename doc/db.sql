drop database if exists MAYAOPING;

create database MAYAOPING default character set utf8;

use MAYAOPING;

create table if not exists TB_USER (
SEQ int auto_increment primary key,
USER_NAME varchar(32) not null,
PASSWORD varchar(32)  not null,
CREATE_DATE datetime,
UPDATE_DATE datetime,
UPDATE_BY varchar(32),
ACTIVITY bool,
IS_ADMIN bool
);

create table if not exists TB_ADVERTISE(
SEQ int auto_increment primary key,
TITLE varchar(32),
PIC_URL1 varchar(512),
PIC_URL2 varchar(512),
PIC_URL3 varchar(512),
ADV_URL varchar(512),
ACTIVITY bool,
CREATE_DATE datetime,
CREATED_BY varchar(32),
UPDATE_DATE datetime,
UPDATED_BY varchar(32),
LAYOUT varchar(32),
CITY varchar(32),
IS_ADMIN bool
);

create table TB_USER_MACHINE(
SEQ int auto_increment primary key,
USER_NO int,
MACHINE_ID varchar(32),
MACHINE_TITLE varchar(32),
CREATE_DATE datetime,
ACTIVITY bool
);

create table if not exists TB_LOGIN_INFO(
USER_NO int,
IP varchar(32),
LOGIN_DATE datetime
);

create table if not exists TB_MACHINE(
SEQ int auto_increment primary key,
ID int,
TITLE varchar(32),
ACTIVITY bool, # 对应服务器状态选项，有服务器设置是否开启。并不是APP传来的开关选项
UPDATE_DATE datetime,
TEMPERATURE float,
TDS float,
PH float,
STATE int  # 对应APP传来的开关选项
);

create table if not exists TB_START_PAGE(
SEQ int,
PIC_URL varchar(512),
ACTIVITY bool,
CREATE_DATE datetime,
UPDATE_DATE datetime,
CITY varchar(32),
PIC_NAME varchar(32)
);

alter table TB_USER add MAIL varchar(128) not null after PASSWORD;

insert into tb_user (user_name,password,mail,activity,is_admin,create_date) values ('admin','123456','156980441@qq.com',1,1,now());