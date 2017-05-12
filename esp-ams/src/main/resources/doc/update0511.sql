/** 日志信息表*/
CREATE TABLE IF NOT EXISTS `t_esp_log_info` (
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