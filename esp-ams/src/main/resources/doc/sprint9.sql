DROP TABLE  IF EXISTS esp.`t_esp_log_attr_info`;
CREATE TABLE `t_esp_log_attr_info` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键标识ID' ,
`content`  varchar(250) NOT NULL DEFAULT '' COMMENT '信息内容' ,
`ext_id`  varchar(20) NOT NULL DEFAULT '' COMMENT '外部ID' ,
`sort_type`  varchar(50) NOT NULL DEFAULT '' COMMENT '信息类型（log:日志）' ,
`create_date`  datetime NOT NULL COMMENT '创建时间' ,
`update_date`  datetime NOT NULL COMMENT '修改时间' ,
PRIMARY KEY (`ID`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='log内容';

ALTER TABLE `t_esp_jd_category`
ADD UNIQUE INDEX `idx_cat_id` (`cat_id`);

DROP TABLE  IF EXISTS esp.`t_esp_weex_info`;
CREATE TABLE `t_esp_weex_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `weex_path` varchar(100) NOT NULL DEFAULT '' COMMENT 'weex路径',
  `weex_ver` varchar(12) NOT NULL DEFAULT '' COMMENT '版本号',
  `weex_eve` varchar(12) NOT NULL DEFAULT '' COMMENT '对应环境',
  `create_data` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `update_user` varchar(12) NOT NULL DEFAULT '' COMMENT '修改人',
  `create_user` varchar(12) NOT NULL DEFAULT '' COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='相关weex JS信息管理表';

ALTER TABLE `t_esp_weex_info`
ADD COLUMN `weex_type`  varchar(12) NOT NULL DEFAULT '' COMMENT 'weex的类型(commission或wallet)';


DROP TABLE  IF EXISTS esp.`t_esp_home_config_info`;
CREATE TABLE `t_esp_home_config_info` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键标识ID' ,
`home_name`  varchar(50) NOT NULL DEFAULT ''  COMMENT '窗口名称' ,
`start_time`  datetime NOT NULL COMMENT '开始时间' ,
`end_time`   datetime NOT NULL COMMENT '结束时间' ,
`home_status`  varchar(2) NOT NULL DEFAULT 'Y' COMMENT '结束时间（Y:有效 N:无效）' ,
`logo_url`  varchar(255) NOT NULL DEFAULT '' COMMENT '图片地址' ,
`active_link`  varchar(255) NOT NULL DEFAULT '' COMMENT '活动地址' ,
`create_date`  datetime NOT NULL COMMENT '创建时间' ,
`update_date`  datetime NOT NULL COMMENT '修改时间' ,
PRIMARY KEY (`ID`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页活动配置';


ALTER TABLE `t_esp_award_activity_info` ADD COLUMN `award_amont` decimal(10,2) NOT NULL default 0 COMMENT '信贷奖励金额';

ALTER TABLE `t_esp_order_detail_info`
ADD COLUMN `goods_cost_price`  decimal(15,4) NOT NULL default 0 COMMENT '商品成本价格';
