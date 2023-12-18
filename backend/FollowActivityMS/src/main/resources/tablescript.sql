DROP DATABASE if exists follow_ms_activity_db;
CREATE DATABASE follow_ms_activity_db;
USE  follow_ms_activity_db;

CREATE TABLE follows(
    fid INT auto_increment,
    followee_id INT NOT NULL,
    follower_id INT NOT NULL,
constraint fid_pk primary key (fid)
);

commit;

select * from follows;