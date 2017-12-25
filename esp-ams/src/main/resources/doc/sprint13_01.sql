use esp;
CREATE TABLE `t_esp_goods_brand` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键标识ID',
`name` varchar(30) NOT NULL DEFAULT '' COMMENT '品牌名',
`is_delete` varchar(2) NOT NULL DEFAULT '00' COMMENT '是否删除(00 默认值,  01 删除)',
`created_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`updated_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='商品品牌表';

alter table t_esp_goods_base_info add COLUMN brand_id bigint(20) not null default 0 comment '品牌id';

ALTER TABLE `t_esp_pro_coupon`
ADD COLUMN `brand_id`  bigint(20) NOT NULL DEFAULT -1 COMMENT '品牌ID' ,
ADD COLUMN `offer_range`  bigint(20) NOT NULL DEFAULT -1 COMMENT '优惠范围（1.品牌 2.品类 3.指定商品）',
ADD COLUMN `category_id3`  varchar(20) NOT NULL DEFAULT '' COMMENT '三级类目id' ,
ADD COLUMN `sku_id`  varchar(30) NOT NULL DEFAULT '' COMMENT '商品的sku_id' ;