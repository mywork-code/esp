ALTER TABLE `t_esp_goods_base_info`
ADD COLUMN `source`  varchar(12) DEFAULT '' COMMENT '商品来源标识(如：jd)',
ADD COLUMN `external_id`  varchar(32) DEFAULT '' COMMENT '外部商品id,唯一标识(如：对应t_esp_jd_goods表中jd_id)';


ALTER TABLE `t_esp_system_param_info`
ADD COLUMN `price_cost_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '保本率(售价/成本价*100%=保本率)';

ALTER TABLE `t_esp_order_info`
ADD COLUMN `source`  varchar(12) DEFAULT '' COMMENT '商品来源标识(如：jd)',
ADD COLUMN `ext_order_id` varchar(32) DEFAULT '' COMMENT '外部订单id(例如京东订单id)';


ALTER TABLE esp.`t_esp_order_info` ADD COLUMN `pre_stock_status` varchar(1) DEFAULT '' COMMENT '预占库存状态(1.预占 2.确认)';

ALTER TABLE esp.`t_esp_order_info` ADD COLUMN `ext_parent_id`  varchar(32) DEFAULT '' COMMENT '0：京东父订单';

ALTER TABLE `t_esp_order_detail_info`
ADD COLUMN `source`  varchar(12)  DEFAULT '' COMMENT '商品来源标识(如：jd)',
ADD COLUMN `sku_id`  varchar(32)  DEFAULT '' COMMENT 'sku_id';


ALTER TABLE `t_esp_address_info`
ADD COLUMN `province_code`  varchar(20) DEFAULT '' COMMENT '省份编码',
ADD COLUMN `city_code`  varchar(20) DEFAULT '' COMMENT '城市编码',
ADD COLUMN `district_code`  varchar(20) DEFAULT '' COMMENT '县城编码',
ADD COLUMN `towns_code`  varchar(20) DEFAULT '' COMMENT '乡镇编码',
ADD COLUMN `towns`  varchar(20)  DEFAULT '' COMMENT '乡镇';

ALTER TABLE `t_esp_order_info`
MODIFY COLUMN `pre_delivery`  varchar(1) DEFAULT '' COMMENT '是否为预发货（系统自动更改：N(否)，商户填写物流单号后更改：Y(是)）';


ALTER TABLE `t_esp_refund_detail_info`
ADD COLUMN `source`  varchar(255) DEFAULT '' COMMENT '商品来源（如：jd）',
ADD COLUMN `status`  varchar(255)  DEFAULT '' COMMENT '京东商品售后状态';

ALTER TABLE `t_esp_refund_detail_info`
ADD COLUMN `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id';

ALTER TABLE `t_esp_service_process_info`
ADD COLUMN `node_message`  varchar(255) DEFAULT '' COMMENT '备注信息';

ALTER TABLE `t_esp_refund_info`
ADD COLUMN `jd_return_type`  varchar(12) DEFAULT '' COMMENT '京东退换货返回方式';


ALTER TABLE `t_esp_goods_base_info`
ADD COLUMN `newCreat_date`  datetime  DEFAULT '1900-01-01 00:00:00' comment '新品创建时间';

ALTER TABLE `t_esp_merchant_info` ADD INDEX idx_merchantCode ( `merchant_code` );
