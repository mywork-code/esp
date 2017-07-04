ALTER TABLE `t_esp_goods_base_info`
ADD COLUMN `source`  varchar(12) DEFAULT '' COMMENT '商品来源标识(如：jd)',
ADD COLUMN `external_id`  varchar(32) DEFAULT '' COMMENT '外部商品id,唯一标识(如：对应t_esp_jd_goods表中jd_id)',
ADD COLUMN `external_status`  tinyint(1) DEFAULT '' COMMENT '外部商品是否关联标识(0：未关联，1：已关联)';

DROP TABLE  IF EXISTS esp.`t_esp_work_city_jd`;
CREATE TABLE esp.`t_esp_work_city_jd` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(40) DEFAULT '' COMMENT '编码',
  `PROVINCE` varchar(200) DEFAULT '' COMMENT '省',
  `CITY` varchar(200) DEFAULT '' COMMENT '市',
  `DISTRICT` varchar(200) DEFAULT '' COMMENT '县',
  `TOWNS` varchar(200) DEFAULT '' COMMENT '乡镇',
  `PARENT` bigint(20) DEFAULT 0 COMMENT '父节点',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='京东工作城市表';
