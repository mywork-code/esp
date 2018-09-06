use esp;
alter table t_esp_pa_user add column agree_flag TINYINT(4) not null default 0 comment '0-未同意，1-已同意',
add column `age` int(11) NOT NULL DEFAULT '0' COMMENT '年龄',
add column `user_agent` varchar(255) NOT NULL DEFAULT '' COMMENT 'userAgent';