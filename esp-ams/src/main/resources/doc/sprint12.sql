use esp;
ALTER TABLE `t_esp_order_info` ADD COLUMN `parent_order_id`  varchar(32) NOT NULL DEFAULT '' COMMENT '父订单号';
