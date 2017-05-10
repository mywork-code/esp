/** 日志信息表*/
CREATE TABLE IF NO EXISTS `t_esp_log_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键标识ID',
  `operation_type` varchar(30) DEFAULT NULL COMMENT '操作类型',
  `content` varchar(2500) DEFAULT NULL,
  `create_user` varchar(50) NOT NULL COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/** 商户信息表  */
ALTER TABLE `t_esp_merchant_info` ADD COLUMN `merchant_return_address` VARCHAR (100) CHARACTER
SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户退货地址' AFTER `merchant_address`,
 ADD COLUMN `merchant_return_name` VARCHAR (100) CHARACTER
SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户收货人名称' AFTER `merchant_return_address`,
 ADD COLUMN `merchant_return_phone` VARCHAR (20) CHARACTER
SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户收货人手机号' AFTER `merchant_return_name`,
 ADD COLUMN `merchant_return_Postcode` VARCHAR (10) CHARACTER
SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户的收货邮政编码' AFTER `merchant_return_phone`;

/** 商户临时信息表  */
ALTER TABLE `t_esp_merchant_temp_info` ADD COLUMN `merchant_return_address` VARCHAR (100) CHARACTER
SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户退货地址' AFTER `merchant_address`,
 ADD COLUMN `merchant_return_name` VARCHAR (100) CHARACTER
SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户收货人名称' AFTER `merchant_return_address`,
 ADD COLUMN `merchant_return_phone` VARCHAR (20) CHARACTER
SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户收货人手机号' AFTER `merchant_return_name`,
 ADD COLUMN `merchant_return_Postcode` VARCHAR (10) CHARACTER
SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户的收货邮政编码' AFTER `merchant_return_phone`;

/** 卡片绑定信息表*/
ALTER TABLE `t_gfb_bind_cards` ADD COLUMN `is_from_esp` int (10) CHARACTER
SET utf8 COLLATE utf8_general_ci NULL DEFAULT 0 COMMENT '是否来自esp' AFTER `status`;

/**转介绍活动表*/
CREATE TABLE `t_esp_award_activity_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键标识ID',
  `activity_name` varchar(100) NOT NULL COMMENT '活动名称',
  `a_start_date` datetime NOT NULL COMMENT '活动开始时间',
  `a_end_date` datetime DEFAULT NULL COMMENT '活动结束时间',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '(有效：1，无效：0)  默认有效',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '个人0，组织1',
  `rebate` decimal(10,6) DEFAULT NULL COMMENT '返点配置',
  `create_by` varchar(20) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='转介绍活动表';
/**邀请人绑定关系表*/
CREATE TABLE `t_esp_award_bind_rel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
  `user_id` bigint(20) NOT NULL COMMENT '邀请人',
  `mobile` varchar(11) NOT NULL COMMENT '手机号',
  `is_new` tinyint(2) DEFAULT NULL COMMENT '是否是新用户',
  `invite_user_id` bigint(20) DEFAULT NULL COMMENT '被邀请人user_id',
  `invite_mobile` varchar(11) DEFAULT NULL COMMENT '被邀请人手机号',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='邀请人绑定关系表';
/**奖励明细表*/
CREATE TABLE `t_esp_award_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT 'user_id',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
  `tax_amount` decimal(10,2) DEFAULT NULL COMMENT '提现扣税金额',
  `amount` decimal(10,2) NOT NULL COMMENT '金额(提现或者获得)',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0，获得，1提现',
  `status` tinyint(2) NOT NULL DEFAULT '2' COMMENT '0成功，1失败，2处理中',
  `order_id` varchar(128) DEFAULT NULL COMMENT '获得的来源订单号order_id',
  `arrived_date` datetime DEFAULT NULL COMMENT '提现到账时间',
  `card_no` varchar(32) DEFAULT NULL COMMENT '提现银行卡号',
  `card_bank` varchar(100) DEFAULT NULL COMMENT '提现 卡银行',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='奖励明细表';