ALTER TABLE esp.`t_esp_order_info`
ADD COLUMN `pre_delivery`  varchar(1)  COMMENT '是否为预发货（系统自动更改：Y(是)，商户填写物流单号后更改：N(否)）';

/** 退款详情表*/
DROP TABLE  IF EXISTS esp.`t_esp_cash_refund`;
CREATE TABLE esp.`t_esp_cash_refund` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
`create_date` datetime ,
`update_date` datetime ,
`amt` decimal(15,4)  COMMENT '退款总金额',
`order_id` varchar(255)  COMMENT '订单id',
`status` int  COMMENT '退款状态（1:退款提交，等待商家审核；2：同意退款；3：拒绝退款；4：取消退款；5:退款处理中；6：退款成功；7：退款失败；）',
`status_d` datetime COMMENT '状态更新时间',
`reject_num` int  COMMENT '拒绝退款的次数',
`user_id` bigint ,
`main_order_id` varchar(255) ,
`reason` varchar(255)  COMMENT '退款原因',
`memo` varchar(255) COMMENT '退款说明',
agree_d datetime COMMENT '同意退款时间',
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/** 退款流水表*/
DROP TABLE  IF EXISTS esp.`t_esp_refund_txn`;
CREATE TABLE esp.`t_esp_refund_txn` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`create_date` datetime ,
`update_date` datetime ,
`channel` varchar(16)  COMMENT '退款渠道(0:信用额度；1:银联；2：支付宝；3：微信)',
`cash_refund_id` bigint(20)  COMMENT '退款详情id',
`ori_txn_code` varchar(255)  COMMENT '原始消费交易的queryId',
`txn_code` varchar(255)  COMMENT '退款返回的交易流水号',
`status` varchar(16)  COMMENT '状态:1:处理中；2：成功；3：失败',
`resp_msg` varchar(255)  COMMENT '第三方接口返回的message',
`amt` decimal(15,4) ,
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/** key value 表*/
DROP TABLE  IF EXISTS esp.`t_esp_kvattr`;
CREATE TABLE esp.`t_esp_kvattr` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`create_date` datetime ,
`update_date` datetime ,
`key` varchar(255),
`value` varchar(255),
`source` varchar(255) COMMENT '可以是不同类型也可以是类的名字',
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

