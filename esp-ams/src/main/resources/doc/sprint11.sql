use esp;
alter table t_esp_goods_base_info add sift_sort int not null  default 0 comment '精选商品排序';

ALTER TABLE `t_esp_pro_activity_cfg`
ADD COLUMN `coupon`  varchar(2) NOT NULL DEFAULT 'N' COMMENT '是否使用优惠券；Y：是；N：否';


CREATE TABLE `t_esp_pro_coupon` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT comment '主键id',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '优惠券名称',
  `extend_type` varchar(16) NOT NULL DEFAULT '' COMMENT '推广方式:用户领取:YHLQ；平台发放:PTFF；新用户专享:XYH',
  `type` varchar(16) NOT NULL DEFAULT '' COMMENT '优惠券类型:全品类:QPL;指定品类:ZDPL；指定商品:ZDSP;活动商品：HDSP',
  `effective_time` int(11) NOT NULL COMMENT '有效时间',
  `sill_type` varchar(2) NOT NULL DEFAULT '' COMMENT '有无门槛：Y：有门槛；N：无门槛',
  `coupon_sill` decimal(15,2) NOT NULL DEFAULT '0' COMMENT '优惠门槛',
  `discount_amonut` decimal(15,2) NOT NULL DEFAULT '0' COMMENT '优惠金额',
  `category_id1` varchar(20) DEFAULT '' NOT NULL  COMMENT '一级类目id',
  `category_id2` varchar(20) DEFAULT '' NOT NULL COMMENT '二级类目id',
  `goods_code` varchar(10) DEFAULT '' NOT NULL COMMENT '商品编码',
  `similar_goods_code` varchar(255) DEFAULT '' NOT NULL COMMENT '相似的商品编码，英文逗号分隔',
  `memo` varchar(255) DEFAULT '' NOT NULL COMMENT '备注',
  `create_user` varchar(50) NOT NULL COMMENT '创建人',
  `update_user` varchar(50) NOT NULL COMMENT '修改人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '修改时间',
  `is_delete` varchar(2) NOT NULL DEFAULT 'N' COMMENT '是否删除，Y：是；N：否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='优惠券信息表';


CREATE TABLE `t_esp_pro_mycoupon` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT comment '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `coupon_rel_id`   bigint(20) NOT NULL DEFAULT 0 COMMENT '优惠券与活动关联主键id',
  `status` varchar(8) NOT NULL COMMENT '状态：未使用：N;已使用：Y',
  `coupon_id` bigint(20) NOT NULL COMMENT '优惠券id',
  `telephone` varchar(15) NOT NULL DEFAULT''  COMMENT '用户手机号',
  `start_date` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '开始时间',
  `end_date` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '结束时间',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户优惠券关系表';


CREATE TABLE `t_esp_pro_coupon_rel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT comment '主键id',
  `pro_activity_id` bigint(20) NOT NULL COMMENT '活动id',
  `coupon_id` bigint(20) NOT NULL COMMENT '优惠券id',
  `total_num` int(10) NOT NULL COMMENT '优惠券发放数量',
  `remain_num` int(10) NOT NULL COMMENT '优惠券剩余数量',
  `limit_num` int(10) NOT NULL COMMENT '每人限领多少张',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券与满减活动关系表';

ALTER TABLE `t_esp_order_detail_info`
ADD COLUMN `coupon_money`  decimal(15,4) NOT NULL DEFAULT 0 comment '优惠券优惠金额 ';


CREATE TABLE `t_esp_goods_attr` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT comment '商品属性表主键',
 `name` varchar(64) NOT NULL COMMENT '商品属性名称',
 `created_user` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
 `updated_user` varchar(32) NOT NULL DEFAULT '' COMMENT '修改人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品属性表';

CREATE TABLE `t_esp_category_attr_rel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT comment '商品属性表主键',
   `category_id1` bigint(20) NOT NULL COMMENT '商品一级类目Id',
   `goods_attr_id` bigint(20) NOT NULL COMMENT '商品属性Id',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品类目属性关联表';


CREATE TABLE `t_esp_goods_attr_val` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT comment '商品属性表主键',
 `goods_id`  bigint(20) NOT NULL COMMENT '商品id' ,
 `attr_id` bigint(20) NOT NULL COMMENT 't_esp_goods_attr 表主键',
  `attr_val` varchar(64) NOT NULL DEFAULT '' COMMENT '属性值，比如：红色，白色',
  `sort` int NOT NULL comment '排序字段',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品不同规格下对应值表';



alter table t_esp_goods_stock_info  ADD COLUMN `sku_id` varchar(20) not null default '' COMMENT 'skuid',
ADD COLUMN attr_val_ids varchar(32) not null default '' comment '多规格属性组合,-分隔(100-103)';


ALTER TABLE `t_esp_order_info`
ADD COLUMN `coupon_id`  bigint(20) NOT NULL DEFAULT '-1' COMMENT '优惠券Id';

alter table t_esp_banner_info add column `attr` varchar(32) not null default '' comment '活动地址：activity;商品编号/skuid:good',
 add column `attr_val` varchar(32) not null default '' comment '属性值';

