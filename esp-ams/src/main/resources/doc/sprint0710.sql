ALTER TABLE `t_esp_pro_coupon`
ADD COLUMN `grant_node`  varchar(10) NOT NULL DEFAULT '' COMMENT '发放节点' AFTER `sku_id`;

alter table t_esp_pro_activity_cfg
add column activity_cate tinyint(4) not null DEFAULT 0 comment '活动类目：0-普通活动；1-专属活动',
add column fyd_act_per decimal(15,0) not null DEFAULT 0 comment '商品活动价格系数设置',
add column fyd_down_per decimal(15,0) not null DEFAULT 0 comment '商品下架系数设置';
