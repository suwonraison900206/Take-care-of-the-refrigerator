create database ssafy_sk;

use ssafy_sk;

# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF exists `tutorals_tbl`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `users` (
  `user_id`     int8 NOT NULL auto_increment comment 'user id',
  `email`       varchar(255) not null comment 'user email, 로그인시 ID 로 사용',
  `password`    varchar(255) not null comment 'user password',
  `nickname`    varchar(255) not null comment 'user nick name',
  `introduce`   text null comment '자기소개',
  `image`       varchar(255) null comment '프로필 사진',
  `create_date` datetime DEFAULT current_timestamp() comment '가입한 날짜',
  `authority`   varchar(255) default 'user' null comment '관리자 여부 (root/user)',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_idx_unique_email` (`email`),
  unique key (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table `boards` (
  `board_id`     int8 not null auto_increment comment 'board id',
  `user_id`      int8 not null,
  `title`        varchar(255) not null,
  `content`      varchar(255) not null,
  `create_at`    datetime default current_timestamp(),
  `update_at`    datetime,
  `grade`        int8 not null,
  `cooking_time` int8 not null,
  primary key(`board_id`),
  foreign key(`user_id`) references users(`user_id`)
);

select * from users;
select * from boards;
desc users;
desc boards;

create table `processes` (
  `process_id`   int8 not null,
  `board_id`     int8 not null,
  `step_number`  int8 not null,
  primary key(`process_id`),
  foreign key(`board_id`) references boards(`board_id`)
);

-- create table `
