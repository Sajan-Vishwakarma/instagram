DROP DATABASE if exists posts_ms_activity_db;
CREATE DATABASE posts_ms_activity_db;
USE  posts_ms_activity_db;

CREATE TABLE likes(
    lid INT auto_increment,
    user_id INT NOT NULL,
    post_id INT NOT NULL,
constraint lid_pk primary key (lid)
);

CREATE TABLE comments(
    cid INT auto_increment,
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    comment VARCHAR(2000) NOT NULL,
    created_at timestamp default CURRENT_TIMESTAMP,
constraint cid_pk primary key (cid)
);

CREATE TABLE posts(
    post_id INT auto_increment,
    user_id INT NOT NULL,
    caption VARCHAR(2000) NOT NULL,
    privacy INT DEFAULT 0,
    created_at timestamp default CURRENT_TIMESTAMP,
constraint post_id_pk primary key (post_id)
);

CREATE TABLE media(
    mid INT auto_increment,
    post_id INT NOT NULL,
    img_id INT NOT NULL,
    place INT NOT NULL,
constraint mid_pk primary key (mid)
);

CREATE TABLE post_activity(
    post_activity_id INT auto_increment,
    post_id INT NOT NULL,
    comment_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
constraint post_activity_pk primary key (post_activity_id)
);

commit;