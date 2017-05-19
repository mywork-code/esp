CREATE TABLE `t_apass_monitor` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `host` varchar(255) NOT NULL COMMENT '主机',
  `env` varchar(255) DEFAULT NULL COMMENT 'sit,uat,prod',
  `application` varchar(255) NOT NULL COMMENT '应用名称',
  `method_name` varchar(255) NOT NULL COMMENT '方法名称',
  `method_desciption` varchar(255) DEFAULT NULL COMMENT '方法描述',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '调用耗时(ms)否调用成功0，失败 1，成功',
  `time` varchar(255) DEFAULT NULL COMMENT '调用耗时(ms)',
  `message` varchar(255) DEFAULT NULL COMMENT '失败堆栈信息',
  `invoke_date` datetime NOT NULL COMMENT '调用时间',
  `error_message` varchar(255) DEFAULT NULL COMMENT '调用失败信息描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8
