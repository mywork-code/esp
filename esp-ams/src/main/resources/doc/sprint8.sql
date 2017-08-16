DROP TABLE  IF EXISTS esp.`t_esp_search_keys`;
CREATE TABLE `t_esp_search_keys` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `key_type` varchar(1) NOT NULL DEFAULT '0' COMMENT '类型(0:最近搜索 1:热门搜索)',
  `key_value` varchar(200) NOT NULL DEFAULT '' COMMENT '内容',
  `user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '用户ID',
 `key_status` varchar(1) NOT NULL DEFAULT '0' COMMENT '类型(0:可用 1:删除)',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `device_id`  varchar(64)  NOT NULL DEFAULT '' COMMENT '设备号';
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品搜素记录表';
