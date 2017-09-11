CREATE TABLE `t_esp_log_attr_info` (
`ID`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键标识ID' ,
`content`  varchar(250) NOT NULL DEFAULT '' COMMENT '信息内容' ,
`ext_id`  varchar(20) NOT NULL DEFAULT '' COMMENT '外部ID' ,
`sort_type`  varchar(50) NOT NULL DEFAULT '' COMMENT '信息类型（log:日志）' ,
`create_date`  datetime NOT NULL COMMENT '创建时间' ,
`update_date`  datetime NOT NULL COMMENT '修改时间' ,
PRIMARY KEY (`ID`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='log内容';

ALTER TABLE `t_esp_jd_category`
ADD UNIQUE INDEX `idx_cat_id` (`cat_id`);
