ALTER TABLE esp.`t_esp_award_detail`
ADD COLUMN `real_name`  varchar(20)  COMMENT '真实姓名' ,
ADD COLUMN `mobile`  varchar(11)  COMMENT '手机号码' ,
ADD COLUMN `release_date`  datetime  COMMENT '放款时间';
