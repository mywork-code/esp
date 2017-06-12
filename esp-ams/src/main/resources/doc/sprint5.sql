ALTER TABLE esp.`t_esp_order_info`
ADD COLUMN `pre_delivery`  varchar(1)  COMMENT '是否为预发货（系统自动更改：Y(是)，商户填写物流单号后更改：N(否)）';
