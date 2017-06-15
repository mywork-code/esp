ALTER TABLE esp.`t_esp_award_detail`
ADD COLUMN `real_name`  varchar(20)  COMMENT '真实姓名' ,
ADD COLUMN `mobile`  varchar(11)  COMMENT '手机号码' ,
ADD COLUMN `release_date`  datetime  COMMENT '放款时间';

ALTER TABLE esp.`t_esp_award_bind_rel`
ADD COLUMN `name`  varchar(20)  COMMENT '真实姓名';
ALTER TABLE esp.`t_esp_award_bind_rel`
ADD COLUMN `invite_name`  varchar(20)  COMMENT '邀请人真实姓名';

ALTER TABLE esp.`t_esp_goods_base_info`
ADD COLUMN `un_support_province`  varchar(255) NULL COMMENT '不配送区域';
