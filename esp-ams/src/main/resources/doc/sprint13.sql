CREATE TABLE `t_esp_limit_buy_act` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键标识ID',
`start_date` datetime NOT NULL DEFAULT '2017-01-01 10:00:00' COMMENT '活动开始时间' ,
`end_date` datetime NOT NULL DEFAULT '2017-01-02 10:00:00' COMMENT '活动结束时间' ,
`status` varchar(20) NOT NULL DEFAULT 'start' COMMENT '活动状态.未开始:start,进行中:proceed,已结束:over' ,
`create_user`  varchar(20)  NOT NULL DEFAULT '' COMMENT '创建人' ,
`update_user`  varchar(20)  NOT NULL DEFAULT '' COMMENT '修改人' ,
`created_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`updated_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='限时购活动表';


CREATE TABLE `t_esp_limit_goods_sku` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键标识ID',
`limit_buy_act_id` bigint(20) NOT NULL COMMENT '限时购活动表ID外键',
`goods_id` bigint(20) NOT NULL COMMENT '该商品goodsid',
`sku_id` varchar(20) NOT NULL DEFAULT '' COMMENT '该商品skuid',
  `market_price` decimal(15,3) NOT NULL DEFAULT '0.000' COMMENT '市场价',
  `activity_price` decimal(15,3) NOT NULL DEFAULT '0.000' COMMENT '活动价',
`limit_num_total` bigint(20) NOT NULL COMMENT '该商品限购总数量',
`limit_num` bigint(20) NOT NULL COMMENT '该商品每人限购数量',
`sort_no` bigint(20) NOT NULL COMMENT '该商品限时购排序',
`url` varchar(20) NOT NULL DEFAULT '' COMMENT '限时购缩略图URL',
`create_user`  varchar(20)  NOT NULL DEFAULT '' COMMENT '创建人' ,
`update_user`  varchar(20)  NOT NULL DEFAULT '' COMMENT '修改人' ,
`created_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`updated_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='限时购活动商品表';


CREATE TABLE `t_esp_limit_buydetail` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键标识ID',
`limit_buy_act_id` bigint(20) NOT NULL COMMENT '限时购活动表ID外键',
`limit_goods_sku_id` bigint(20) NOT NULL COMMENT '限时购活动商品表ID外键',
`user_id` bigint(20) NOT NULL COMMENT '用户ID',
`buy_no` int NOT NULL DEFAULT 0 COMMENT '该用户该商品购买数量，需要小于该商品单人限购',
`created_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`updated_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='限时购活动用户购买商品数量表';