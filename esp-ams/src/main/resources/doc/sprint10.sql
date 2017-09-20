CREATE TABLE `t_esp_message_listener` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` varchar(10) NOT NULL DEFAULT '' COMMENT '推送类型:1 拆单消息 ;2 商品价格变更消息;4 商品上下架消息;5 订单妥投消息;6 商品添加删除消息；100 京东售后状态 ',
  `skuId` varchar(32) NOT NULL DEFAULT '' COMMENT '京东商品编号',
  `orderId` varchar(128) NOT NULL DEFAULT '' COMMENT '京东订单编号',
  `status` varchar(10) NOT NULL DEFAULT '' COMMENT '接口调用成功或失败：1 success；2 error',
  `error_massage` varchar(255) NOT NULL DEFAULT '' COMMENT '错误原因',
  `result` varchar(255) NOT NULL DEFAULT '' COMMENT '返回结果',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '消息log表';
