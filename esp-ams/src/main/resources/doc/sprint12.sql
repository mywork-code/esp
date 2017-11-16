use esp;
ALTER TABLE `t_esp_order_info` ADD COLUMN `parent_order_id`  bigint(20) NOT NULL DEFAULT 0 COMMENT '父订单号';



CREATE TABLE `t_esp_bsdiff_info2` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键标识ID',
`bsdiff_ver`  varchar(8)  NOT NULL DEFAULT '' COMMENT '存放zip版本号（只能是1，2，3......等正整数）' ,
`patch_name`  varchar(255)  NOT NULL DEFAULT '' COMMENT '存放每个版本对应的patch包（如：3对应3_1,3_2,4对应4_1,4_2,4_3等）' ,
`create_data`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`update_date`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
`create_user`  varchar(20)  NOT NULL DEFAULT '' COMMENT '创建人' ,
`update_user`  varchar(20)  NOT NULL DEFAULT '' COMMENT '修改人' ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='bsdiff增量更新表';
