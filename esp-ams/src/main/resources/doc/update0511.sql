/** */
CREATE TABLE IF NO EXISTS `t_esp_log_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键标识ID',
  `operation_type` varchar(30) DEFAULT NULL COMMENT '操作类型',
  `content` varchar(2500) DEFAULT NULL,
  `create_user` varchar(50) NOT NULL COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;