DROP DATABASE if exists trending_ms_activity_db;
CREATE DATABASE trending_ms_activity_db;
USE  trending_ms_activity_db;

CREATE TABLE tags(
    tid INT auto_increment,
    tagname VARCHAR(50) NOT NULL,
    post_id INT NOT NULL,
constraint tid_pk primary key (tid)
);

ALTER TABLE tags ADD CONSTRAINT tags_post_uniqueness UNIQUE (tagname, post_id);

commit;

select * from tags;