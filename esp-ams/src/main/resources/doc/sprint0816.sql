use esp;
CREATE TABLE `t_esp_pa_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '用户id',
  `username` varchar(32) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `birthday` date NOT NULL DEFAULT '1900-01-01' COMMENT '生日',
  `telephone` varchar(12) NOT NULL DEFAULT '' COMMENT '手机号码',
  `sex` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别：1-男，0-女',
  `created_time` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '修改时间',
  `is_delete` varchar(2) NOT NULL DEFAULT 'N' COMMENT '是否删除，Y：是；N：否',
  `from_ip` varchar(32) NOT NULL DEFAULT '' COMMENT '客户来源ip,请求的request中获取',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户领取平安保险记录表';