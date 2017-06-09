/**新增加商品类别表*/
DROP TABLE IF EXISTS esp.t_esp_category;
CREATE TABLE esp.`t_esp_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `category_name` varchar(20) NOT NULL COMMENT '类目名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级id',
  `sort_order` bigint(20) NOT NULL COMMENT '排序',
  `level` bigint(20) NOT NULL COMMENT '级别',
  `picture_url` varchar(100) DEFAULT NULL COMMENT '图片的路径',
  `create_user` varchar(50) NOT NULL COMMENT '创建人',
  `update_user` varchar(50) NOT NULL COMMENT '修改人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  `status` varchar(1) NOT NULL DEFAULT '1' COMMENT '状态（0 可见  1 不可见 2 删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品分类信息表';

alter table esp.`t_esp_category` add index cname_idx(`category_name`);
alter table esp.`t_esp_category` add index parentId_idx(`parent_id`);

DROP TABLE IF EXISTS esp.t_esp_feedback;
CREATE TABLE esp.`t_esp_feedback` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
 `feedback_type` varchar(20) NOT NULL COMMENT '反馈类型(page_error:页面异常,fun_error:功能异常,other:其他)',
 `comments` varchar(300) NOT NULL COMMENT '反馈内容',
 `mobile` varchar(50) NOT NULL COMMENT '反馈人手机号',
 `create_date` datetime NOT NULL COMMENT '反馈时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='意见反馈信息表';

alter table esp.`t_esp_feedback` add index mobile_idx(`mobile`);


/**在商品的基础表中添加三列*/
ALTER TABLE esp.`t_esp_goods_base_info` ADD COLUMN `category_id1`  bigint(20) NULL COMMENT '商品分类一级Id' AFTER `id`;
ALTER TABLE esp.`t_esp_goods_base_info` ADD COLUMN `category_id2`  bigint(20) NULL COMMENT '商品分类二级Id' AFTER `category_id1`;
ALTER TABLE esp.`t_esp_goods_base_info` ADD COLUMN `category_id3`  bigint(20) NULL COMMENT '商品分类三级Id' AFTER `category_id2`;

/**在商户表和商户临时表中添加二列*/
ALTER TABLE esp.`t_esp_merchant_info` ADD COLUMN `channel`  int(11) NULL COMMENT '商户渠道' AFTER `merchant_name`;
ALTER TABLE esp.`t_esp_merchant_info` ADD COLUMN `merchant_area`  varchar(8) NULL COMMENT '商户所在区' AFTER `merchant_city`;
ALTER TABLE esp.`t_esp_merchant_temp_info` ADD COLUMN `channel`  int(11) NULL COMMENT '商户渠道' AFTER `merchant_name`;
ALTER TABLE esp.`t_esp_merchant_temp_info` ADD COLUMN `merchant_area`  varchar(8) NULL COMMENT '商户所在区' AFTER `merchant_city`;

/**在monitor表中添加一列  flag 用来表示监控了哪些方法*/
ALTER TABLE esp.`t_apass_monitor` ADD COLUMN `flag`  varchar(1) NULL DEFAULT 0 COMMENT '用于区分配置数据和非配置(0:非配置数据，1:配置数据)' AFTER `error_message`;
/**修改monitor表中的status列可以为空*/
ALTER TABLE esp.`t_apass_monitor` MODIFY COLUMN `status`  int(11) NULL DEFAULT 1 COMMENT '调用耗时(ms)否调用成功0，失败 1，成功' AFTER `method_desciption`;
/**在t_esp_order_info表中增加一列，订单下单渠道**/
ALTER TABLE esp.`t_esp_order_info` ADD COLUMN `device_type`  varchar(10) NULL DEFAULT 'android' COMMENT '下单渠道（ios,android）' AFTER `down_payment_amount`;
/**在monitor表中添加一列  notice 用来表示是否已发邮件通知**/
ALTER TABLE esp.`t_apass_monitor` ADD COLUMN `notice`  int(11) NULL DEFAULT 0 COMMENT '是否已发邮件通知' AFTER `flag`;
/**修改monitor表中的host列可以为空*/
ALTER TABLE esp.`t_apass_monitor` MODIFY COLUMN `host`  varchar(255) NULL COMMENT '主机' AFTER `id`;

alter table esp.`t_apass_monitor` add index env_method_idx(`env`,`method_name`);
DELETE FROM esp.`t_apass_monitor`;

