use esp;
ALTER TABLE `t_esp_bsdiff_info`
ADD COLUMN `if_compel_update`  tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否强制更新1：是，0：否';

DROP TABLE t_data_appuser_analysis;
CREATE TABLE `t_data_appuser_analysis` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键标识ID',
`created_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`updated_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
`is_delete` varchar(2) NOT NULL DEFAULT '00' COMMENT '是否删除(00 默认值,  01 删除)',
txn_id varchar(64) not null  default '' comment '格式:yyyyMMddHH;如2018012302',
type TINYINT(4) not null comment '统计单位：1-hour;2-daily',
platformids TINYINT(4) not null comment '0代表查询全平台,1代表Android，2代表iOS',
newuser varchar(32) not null default '' comment '查询新增用户数',
activeuser varchar(32) not null default '' comment '查询活跃用户数',
registeruser varchar(32) not null default '' comment 'APP端新增的注册用户数',
versionupuser varchar(32) not null default '' comment '全版本上升级用户数，必须传一个版本参数',
wau varchar(32) not null default '' comment '某日的近7日活跃用户数，只支持groupby为daily的查询',
mau varchar(32) not null default '' comment '某日的近30日活跃用户数，只支持groupby为daily的查询',
totaluser varchar(32) not null default '' comment '查询截至某日的累计用户数, 只支持groupby为daily的查询',
bounceuser varchar(32) not null default '' comment '一次性用户数',
session  varchar(32) not null default '' comment '启动次数',
sessionlength  varchar(64) not null default '' comment '汇总的使用时长',
avgsessionlength varchar(64) not null default '' comment '平均每次启动使用时长',
PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='设备用户talkingdata分析';


DROP TABLE t_data_appuser_retention;
CREATE TABLE `t_data_appuser_retention` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键标识ID',
`created_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`updated_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
`is_delete` varchar(2) NOT NULL DEFAULT '00' COMMENT '是否删除(00 默认值,  01 删除)',
txn_id varchar(64) not null  default '' comment '格式:yyyyMMdd;如20180123',
platformids TINYINT(4) not null comment '0代表查询全平台,1代表Android，2代表iOS',
day1retention varchar(32) not null default '' comment '新增用户次日留存率',
day3retention varchar(32) not null default '' comment '新增用户3日留存率',
day7retention varchar(32) not null default '' comment '新增用户7日留存率',
day14retention varchar(32) not null default '' comment '新增用户14日留存率',
day30retention varchar(32) not null default '' comment '新增用户30日留存率',
dauday1retention varchar(32) not null default '' comment '活跃用户次日留存率',
dauday3retention varchar(32) not null default '' comment '活跃用户3日留存率',
dauday7retention varchar(32) not null default '' comment '活跃用户7日留存率',
dauday14retention varchar(32) not null default '' comment '活跃用户14日留存率',
dauday30retention varchar(32) not null default '' comment '活跃用户30日留存率',
day7churnuser varchar(32) not null default '' comment '某日的7日不使用流失用户数',
day14churnuser varchar(32) not null default '' comment '某日的14日不使用流失用户数',
day7backuser varchar(32) not null default '' comment '7日以上流失用户中的回流用户',
day14backuser varchar(32) not null default '' comment '14日以上流失用户中的回流用户',
PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户talkingdata留存分析';


DROP TABLE t_data_esporder_analysis;
CREATE TABLE `t_data_esporder_analysis` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键标识ID',
`created_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`updated_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
`is_delete` varchar(2) NOT NULL DEFAULT '00' COMMENT '是否删除(00 默认值,  01 删除)',
txn_id varchar(64) not null  default '' comment '格式:yyyyMMdd;如20180123',
confirm_num int not null  default 0 comment '下单人数',
confirm_goods_num int not null  default 0 comment '下单商品件数',
confirm_amt  decimal(15,4) not null  default 0 comment '下单总金额',
pay_num int not null  default 0 comment '支付人数',
pay_goods_num int not null  default 0 comment '支付商品件数',
pay_amt  decimal(15,4) not null  default 0 comment '支付总金额',
percent_conv decimal(15,8) not null  default 0 comment '下单支付转化率',

PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='商城订单统计';


DROP TABLE t_data_esporderdetail;
CREATE TABLE `t_data_esporderdetail` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键标识ID',
`created_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`updated_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
`is_delete` varchar(2) NOT NULL DEFAULT '00' COMMENT '是否删除(00 默认值,  01 删除)',
order_analysis_id bigint not null  COMMENT '商城订单统计主键id',
goods_id bigint NOT NULL COMMENT '商品主键',
confirm_goods_num int(11) NOT NULL DEFAULT '0' COMMENT '下单商品件数',
confirm_amt  decimal(15,4) NOT NULL DEFAULT '0' COMMENT '下单商品金额',
pay_goods_num int(11) NOT NULL DEFAULT '0' COMMENT '支付商品件数',
pay_amt  decimal(15,4) NOT NULL DEFAULT '0' COMMENT '支付商品金额',
percent_conv decimal(15,8) NOT NULL DEFAULT '0' COMMENT '下单支付转化率',
PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='商城订单明细统计';