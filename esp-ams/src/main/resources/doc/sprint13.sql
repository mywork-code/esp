CREATE TABLE `t_esp_limit_buy_act` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键标识ID',
`start_date` datetime NOT NULL DEFAULT '2017-01-01 10:00:00' COMMENT '活动开始时间' ,
`end_date` datetime NOT NULL DEFAULT '2017-01-02 10:00:00' COMMENT '活动结束时间' ,
 `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '活动状态.未开始:1,进行中:2,已结束:3' ,
  `start_time` tinyint(4) NOT NULL COMMENT '选择的时间类型',
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
 `limit_curr_total`  bigint(20) NOT NULL COMMENT '该商品的限购剩余量',
`sort_no` bigint(20) NOT NULL COMMENT '该商品限时购排序',
`url` varchar(128) NOT NULL DEFAULT '' COMMENT '限时购缩略图URL',
 `up_load_status` tinyint not null DEFAULT 1 COMMENT '商品上传成功标志 1：成功；0：失败    默认为1',
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
`order_id`  varchar(128) NOT NULL COMMENT '订单编号',
`created_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`updated_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='限时购活动用户购买商品数量表';


ALTER TABLE `t_esp_order_detail_info`
ADD COLUMN `limit_activity_id`  varchar(20)  NOT NULL DEFAULT '' COMMENT '限时购活动ID' ;

CREATE TABLE `t_esp_limit_user_message` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键标识ID',
`limit_buy_act_id` bigint(20) NOT NULL COMMENT '限时购活动表ID外键',
`limit_goods_sku_id` bigint(20) NOT NULL COMMENT '限时购活动商品表ID外键',
`user_id` bigint(20) NOT NULL COMMENT '用户ID',
`telephone` bigint(20) NOT NULL COMMENT '用户提醒手机号，可以修改为非用户注册手机号',
`created_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`updated_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='限时购活动用户抢购提醒记录表';

alter table t_esp_goods_base_info add column main_goods_code varchar(30) not null default '' comment '主商品编号';

ALTER TABLE `t_esp_pro_group_goods`
MODIFY COLUMN `goods_code`  varchar(30)  NOT NULL DEFAULT '' COMMENT '商品编号';



