
DROP DATABASE if exists authentication_ms_user_db;
CREATE DATABASE authentication_ms_user_db;
USE  authentication_ms_user_db;

CREATE TABLE users(
    user_id INT auto_increment,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    email_id VARCHAR(50) UNIQUE NOT NULL,
    full_name VARCHAR(50) NOT NULL,
    profile_img_id VARCHAR(200),
    bio VARCHAR(500),
    created_at timestamp default CURRENT_TIMESTAMP,
constraint user_id_pk primary key (user_id)
);

commit;


select * from users;

USE  authentication_ms_user_db;

CREATE TABLE images(
    img_id INT auto_increment,
    img_url VARCHAR(250) NOT NULL,
    constraint img_id_pk primary key (img_id)
);
commit;
