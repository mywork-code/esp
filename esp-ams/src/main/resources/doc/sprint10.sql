CREATE TABLE `t_esp_message_listener` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` varchar(10) NOT NULL DEFAULT '' COMMENT '推送类型:1 拆单消息 ;2 商品价格变更消息;4 商品上下架消息;5 订单妥投消息;6 商品添加删除消息；100 京东售后状态 ',
  `skuId` varchar(32) NOT NULL DEFAULT '' COMMENT '京东商品编号',
  `orderId` varchar(128) NOT NULL DEFAULT '' COMMENT '京东订单编号',
  `status` varchar(10) NOT NULL DEFAULT '' COMMENT '接口调用成功或失败：1 success；2 error',
  `error_massage` varchar(255) NOT NULL DEFAULT '' COMMENT '错误原因',
  `result` varchar(255) NOT NULL DEFAULT '' COMMENT '返回结果',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '消息log表';


---在t_esp_order_detail_info 中新增字段discount_amount字段，用来存储商品的优惠金额
ALTER TABLE `t_esp_order_detail_info` ADD COLUMN `discount_amount` decimal(15,4) NOT NULL default 0  COMMENT '优惠金额',
ADD COLUMN pro_activity_id varchar(20) not null default '' comment '促销活动id';

--新建活动配置表
CREATE TABLE `t_esp_pro_activity_cfg` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '活动编号' ,
`activity_name`  varchar(20) NOT NULL DEFAULT '' COMMENT '活动名称' ,
`activity_type`  varchar(2) NOT NULL DEFAULT '' COMMENT '活动类型' ,
`start_time`  datetime NOT NULL COMMENT '活动开始时间' ,
`end_time`  datetime NOT NULL COMMENT '活动结束时间' ,
`offer_sill1`  decimal(15,0) NOT NULL DEFAULT 0 COMMENT '优惠门槛1' ,
`discount_amonut1`  decimal(15,0) NOT NULL DEFAULT 0 COMMENT '优惠金额1' ,
`offer_sill2`  decimal(15,0) NOT NULL DEFAULT 0 COMMENT '优惠门槛2' ,
`discount_amount2`  decimal(15,0) NOT NULL DEFAULT 0 COMMENT '优惠金额2' ,
`create_user` varchar(50) NOT NULL COMMENT '创建人',
`update_user` varchar(50) NOT NULL COMMENT '修改人',
`create_date` datetime NOT NULL COMMENT '创建时间',
`update_date` datetime NOT NULL COMMENT '修改时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销活动配置表';
--新创建分组表
CREATE TABLE `t_esp_pro_group_manager` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分组id' ,
`group_name`  varchar(20) NOT NULL DEFAULT '' COMMENT '分组名称' ,
`goods_sum`  bigint(20) NOT NULL DEFAULT 0 COMMENT '分组下商品的数量' ,
`activity_id`  bigint(20) NOT NULL DEFAULT 0 COMMENT '活动配置的id' ,
`order_sort`  bigint(20) NOT NULL DEFAULT 1 COMMENT '排序先后' ,
`create_user` varchar(50) NOT NULL COMMENT '创建人',
`update_user` varchar(50) NOT NULL COMMENT '修改人',
`create_date` datetime NOT NULL COMMENT '创建时间',
`update_date` datetime NOT NULL COMMENT '修改时间',
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销活动分组表';

--创建商品和分组关联表
DROP TABLE  IF EXISTS esp.`t_esp_pro_group_goods`;
CREATE TABLE `t_esp_pro_group_goods` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
`goods_id`  bigint(20) NOT NULL COMMENT '商品id' ,
`sku_id`  varchar(30) NOT NULL DEFAULT '' COMMENT '京东商品的skuId' ,
`group_id`  bigint(20) NOT NULL DEFAULT -1 COMMENT '分组id' ,
`market_price`  decimal(15,3) NOT NULL DEFAULT 0 COMMENT '市场价' ,
`activity_price`  decimal(15,3) NOT NULL DEFAULT 0 COMMENT '活动价' ,
`order_sort`  bigint(20) NOT NULL DEFAULT 1 COMMENT '排序' ,
`goods_code`  varchar(10) NOT NULL DEFAULT '' COMMENT '商品编号' ,
`detail_desc`  varchar(30) NOT NULL DEFAULT '' COMMENT '备注信息' ,
`create_user` varchar(50) NOT NULL COMMENT '创建人',
`update_user` varchar(50) NOT NULL COMMENT '修改人',
`create_date` datetime NOT NULL COMMENT '创建时间',
`update_date` datetime NOT NULL COMMENT '修改时间',
`activity_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '活动配置的id',
`status`  varchar(2) NOT NULL DEFAULT '' COMMENT 'S:成功；F：失败',
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分组商品关联表';


alter table t_esp_goods_base_info add column support_7d_refund varchar(2) not null default '' comment '是否支持7天无理由退货,Y、N';
update t_esp_goods_base_info set support_7d_refund = 'Y' where source is null;


alter table t_esp_banner_info  modify column banner_type varchar(20);
