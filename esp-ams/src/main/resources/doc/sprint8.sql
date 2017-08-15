DROP TABLE  IF EXISTS esp.`t_esp_search_keys`;
CREATE TABLE `t_esp_search_keys` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `KEY_TYPE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '类型(0:最近搜索 1:热门搜索)',
  `KEY_VALUE` varchar(200) NOT NULL DEFAULT '' COMMENT '内容',
  `USER_ID` varchar(200) NOT NULL DEFAULT '' COMMENT '用户ID',
 `KEY_STATUS` tinyint(1) NOT NULL DEFAULT '0' COMMENT '类型(0:可用 1:删除)',
  `CREATE_DATE` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_DATE` datetime NOT NULL COMMENT '更新时间',
  `DEVICE_ID`  varchar(64)  NOT NULL DEFAULT '' COMMENT '设备号';
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品搜素记录表';
