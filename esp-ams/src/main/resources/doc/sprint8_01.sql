DROP TABLE  IF EXISTS esp.`t_apass_txn_attr`;
CREATE TABLE `t_apass_txn_attr` (
`id` bigint(20) NOT NULL AUTO_INCREMENT ,
`transaction_id`  varchar(64) NOT NULL  DEFAULT '' COMMENT '支付宝交易号' ,
`pass_trade_no`  varchar(64) NOT NULL  DEFAULT '' COMMENT '通道的统一订单号',
`time_end`  varchar(14) NOT NULL  DEFAULT '' COMMENT '支付完成时间',
`buyer_logon_id`  varchar(100) NOT NULL  DEFAULT '' COMMENT '买家支付宝账号',
`out_trade_no`  varchar(128) NOT NULL  DEFAULT '' COMMENT '商户订单号',
`txn_id`  varchar(128) NOT NULL  DEFAULT '' COMMENT '交易表ID',
`create_date` datetime NOT NULL COMMENT '创建时间',
`update_date` datetime NOT NULL COMMENT '修改时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易流水属性表';
