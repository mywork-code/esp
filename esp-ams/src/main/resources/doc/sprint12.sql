use esp;
ALTER TABLE `t_esp_order_info` ADD COLUMN `parent_order_id`  bigint(20) NOT NULL DEFAULT 0 COMMENT '父订单号';



CREATE TABLE `t_esp_bsdiff_info` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键标识ID',
`bsdiff_ver`  varchar(8)  NOT NULL DEFAULT '' COMMENT '存放zip版本号（只能是1，2，3......等正整数）' ,
`patch_name`  varchar(255)  NOT NULL DEFAULT '' COMMENT '存放每个版本对应的patch包（如：3对应3_1,3_2,4对应4_1,4_2,4_3等）' ,
`created_time`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`updated_time`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
`create_user`  varchar(20)  NOT NULL DEFAULT '' COMMENT '创建人' ,
`update_user`  varchar(20)  NOT NULL DEFAULT '' COMMENT '修改人' ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='bsdiff增量更新表';


create table `t_esp_invoice` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键标识ID',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '修改时间',
  `user_id`  bigint(20) NOT NULL COMMENT '用户id',
  `order_id` varchar(128) NOT NULL COMMENT '订单编号',
  `order_amt` decimal(15,4) NOT NULL COMMENT '订单金额',
  `tax` decimal(15,4) NOT NULL COMMENT '税金额',
  `no_tax_amt` decimal(15,4) NOT NULL DEFAULT 0 COMMENT '不含税金额',
  `head_type` tinyint not null DEFAULT 1 COMMENT '抬头类型 1：个人；2：单位',
  `telphone` varchar(32) not null DEFAULT '' COMMENT '收票人手机号',
  `company_name` varchar(255) not null DEFAULT '' COMMENT '单位名称',
  `taxpayer_num` varchar(255) not null DEFAULT '' COMMENT '纳税人识别号',
  `content` varchar(128) not null DEFAULT '' COMMENT '发票内容',
  `status` tinyint not null COMMENT '发票状态（1：申请中；2：申请成功；3：失败；4：取消）',
  `invoice_num` varchar(128) not null DEFAULT '' COMMENT '发票号',
  `seller` varchar(255) NOT NULL DEFAULT '' COMMENT '销售方',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='电子发票表';

