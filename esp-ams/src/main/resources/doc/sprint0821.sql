use esp;
CREATE TABLE `t_esp_zy_prize_collec` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `created_time` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '修改时间',
  `is_delete` varchar(2) NOT NULL DEFAULT 'N' COMMENT '是否删除，Y：是；N：否',
  `company_name` varchar(32) NOT NULL DEFAULT '' COMMENT '中原分公司名',
  `goods_name` varchar(32) NOT NULL DEFAULT '' COMMENT '商品名',
  `emp_name` varchar(32) NOT NULL DEFAULT '' COMMENT '员工姓名',
  `emp_tel` varchar(32) NOT NULL DEFAULT '' COMMENT '员工手机号',
  `consignee_name` varchar(32) NOT NULL DEFAULT '' COMMENT '收货人姓名',
  `consignee_tel` varchar(32) NOT NULL DEFAULT '' COMMENT '收货人手机号',
  `consignee_addr` varchar(256) NOT NULL DEFAULT '' COMMENT '收货人地址',
  `user_id` varchar(16) NOT NULL DEFAULT '' COMMENT '登入用户的id',
  qh_reward_type varchar(16) not null default '' comment '几等奖',
  activity_id varchar(16) not null default '' comment '活动id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='中原领奖记录表';

