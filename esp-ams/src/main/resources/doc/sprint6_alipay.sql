INSERT INTO `esp`.`t_esp_kvattr` (`create_date`, `update_date`, `key`, `value`, `source`) VALUES (now(), now(), 'ratio', '0.1', 'com.apass.esp.domain.kvattr.DownPayRatio');


ALTER TABLE `t_esp_cash_refund`
ADD COLUMN `refund_type`  varchar(10)  DEFAULT 'online' COMMENT '退款方式（online: 线上  offline:线下）' ;
ALTER TABLE `t_esp_cash_refund`
ADD COLUMN `auditor_name`  varchar(20)  DEFAULT '' COMMENT '审核人' ,
ADD COLUMN `auditor_date`  datetime  DEFAULT NULL COMMENT '审核时间' ;
