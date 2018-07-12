ALTER TABLE `t_esp_pro_coupon`
ADD COLUMN `grant_node`  varchar(10) NOT NULL DEFAULT '' COMMENT '发放节点' AFTER `sku_id`;