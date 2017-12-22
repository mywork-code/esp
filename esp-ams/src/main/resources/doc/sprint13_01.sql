CREATE TABLE `t_esp_goods_brand` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键标识ID',
`name` varchar(30) NOT NULL DEFAULT '' COMMENT '品牌名',
`is_delete` varchar(2) NOT NULL DEFAULT '00' COMMENT '是否删除(00 默认值,  01 删除)',
`created_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`updated_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='商品品牌表';

alter table t_esp_goods_base_info add COLUMN brand_id bigint(20) not null default 0 comment '品牌id';