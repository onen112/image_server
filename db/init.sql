drop database if exists image_system;
create database image_system character set utf8mb4;
use image_system;

drop table if exists `image_table`;
create table `image_table`(
    image_id int not null primary key auto_increment,
    user_id int not null,
    image_name varchar(50),
    size bigint,
    upload_time varchar(50),
    md5 varchar(128),
    content_type varchar(50) comment '图片类型',
    path varchar(1024) comment '图片所在路径');

drop table if exists `user_table`;
create table `user_table`(
    user_id int not null primary key  auto_increment,
    user_name varchar(30) not null,
    user_password varchar(30) not null,
    user_email varchar(30) not null,
    rig_date varchar(30) not null
)

