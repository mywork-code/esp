ALTER TABLE `t_esp_goods_base_info`
ADD COLUMN `source`  varchar(12) DEFAULT '' COMMENT '商品来源标识(如：jd)',
ADD COLUMN `external_id`  varchar(32) DEFAULT '' COMMENT '外部商品id,唯一标识(如：对应t_esp_jd_goods表中jd_id)';

DROP TABLE  IF EXISTS esp.`t_esp_work_city_jd`;
CREATE TABLE `t_esp_work_city_jd` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(40) DEFAULT '' COMMENT '编码',
  `PROVINCE` varchar(200) DEFAULT '' COMMENT '省',
  `CITY` varchar(200) DEFAULT '' COMMENT '市',
  `DISTRICT` varchar(200) DEFAULT '' COMMENT '县',
  `TOWNS` varchar(200) DEFAULT '' COMMENT '乡镇',
  `PARENT` bigint(20) DEFAULT '0' COMMENT '父节点',
  `LEVEL` varchar(2) DEFAULT '' COMMENT '第几级目录',
  `PREFIX` varchar(10) DEFAULT '' COMMENT '首字母',
  `SPELL` varchar(50) DEFAULT '' COMMENT '城市中文拼音',
  `CREATE_DATE` datetime NOT NULL,
  `UPDATE_DATE` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='京东工作城市表';

DROP TABLE  IF EXISTS esp.`t_esp_jd_category`;
CREATE TABLE `t_esp_jd_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cat_id` bigint(20) NOT NULL COMMENT '京东类目id',
  `name` varchar(20) NOT NULL COMMENT '类目名称',
  `parent_id` bigint(20) NOT NULL COMMENT '父级id',
  `cat_class` int NOT NULL COMMENT '级别',
  `category_id1` bigint(20) NOT NULL COMMENT 'apass类目1',
  `category_id2` bigint(20) NOT NULL COMMENT 'apass类目2',
  `category_id3` bigint(20) NOT NULL COMMENT 'apass类目3',
 `flag` tinyint(1) DEFAULT 0 COMMENT '是否关联安家趣花类目，1：已关联；0:未关联',
 `status` tinyint(1) DEFAULT 0 COMMENT '京东类目是否有效 1：有效，0 无效',
 `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='京东类目表';

DROP TABLE  IF EXISTS esp.`t_esp_jd_goods`;
CREATE TABLE `t_esp_jd_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sku_id` bigint(20) NOT NULL COMMENT 'skuId',
  `price` decimal(10,2) NOT NULL COMMENT '协议价',
  `jd_price` decimal(10,2) NOT NULL COMMENT '京东价',
  `brand_name` varchar(255) NOT NULL COMMENT '品牌',
  `name` varchar(255) NOT NULL COMMENT '商品名称',
  `image_path` varchar(255) DEFAULT '' COMMENT '主图地址',
  `weight` decimal(10,2) DEFAULT '0.00' COMMENT '重量',
  `upc` varchar(20) DEFAULT '' COMMENT '条形码',
  `product_area` varchar(20) DEFAULT '' COMMENT '产地',
  `sale_unit` varchar(10) DEFAULT '' COMMENT '销售单位',
  `ware_qd` varchar(255) DEFAULT '' COMMENT '包装清单',
  `state` tinyint(4) NOT NULL COMMENT '上下架状态',
  `first_category` int(10) NOT NULL COMMENT '一级分类',
  `second_category` int(10) NOT NULL COMMENT '二级分类',
  `third_category` int(10) NOT NULL COMMENT '三级分类',
  `similar_skus` varchar(255) DEFAULT '' COMMENT '同类skuids',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='京东商品表';


ALTER TABLE `t_esp_system_param_info`
ADD COLUMN `price_cost_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '保本率(售价/成本价*100%=保本率)';

ALTER TABLE `t_esp_order_info`
ADD COLUMN `source`  varchar(12) DEFAULT '' COMMENT '商品来源标识(如：jd)',
ADD COLUMN `ext_order_id` varchar(32) DEFAULT '' COMMENT '外部订单id(例如京东订单id)';

DROP TABLE  IF EXISTS esp.`t_esp_goods_sales_volume`;
CREATE TABLE `t_esp_goods_sales_volume` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(11) NOT NULL COMMENT '商品ID',
  `sales_num` int(11) NOT NULL DEFAULT '0' COMMENT '销量',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品销量表';


ALTER TABLE esp.`t_esp_order_info` ADD COLUMN `pre_stock_status` varchar(1) DEFAULT '' COMMENT '预占库存状态(1.预占 2.确认)';

ALTER TABLE esp.`t_esp_order_info` ADD COLUMN `ext_parent_id`  varchar(32) DEFAULT '' COMMENT '0：京东父订单';

ALTER TABLE `t_esp_order_detail_info`
ADD COLUMN `source`  varchar(12)  DEFAULT '' COMMENT '商品来源标识(如：jd)',
ADD COLUMN `sku_id`  varchar(32)  DEFAULT '' COMMENT 'sku_id';


ALTER TABLE `t_esp_address_info`
ADD COLUMN `province_code`  varchar(20) DEFAULT '' COMMENT '省份编码',
ADD COLUMN `city_code`  varchar(20) DEFAULT '' COMMENT '城市编码',
ADD COLUMN `district_code`  varchar(20) DEFAULT '' COMMENT '县城编码',
ADD COLUMN `towns_code`  varchar(20) DEFAULT '' COMMENT '乡镇编码',
ADD COLUMN `towns`  varchar(20)  DEFAULT '' COMMENT '乡镇';

ALTER TABLE `t_esp_order_info`
MODIFY COLUMN `pre_delivery`  varchar(1) DEFAULT '' COMMENT '是否为预发货（系统自动更改：N(否)，商户填写物流单号后更改：Y(是)）';

DROP TABLE  IF EXISTS esp.`t_esp_service_error`;
CREATE TABLE `t_esp_service_error` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
 `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
   `order_id` varchar(128) DEFAULT '' comment '订单id',
   `type` varchar(64) DEFAULT '' comment '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务异常表';


ALTER TABLE `t_esp_refund_detail_info`
ADD COLUMN `source`  varchar(255) DEFAULT '' COMMENT '商品来源（如：jd）',
ADD COLUMN `status`  varchar(255)  DEFAULT '' COMMENT '京东商品售后状态';

ALTER TABLE `t_esp_refund_detail_info`
ADD COLUMN `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id';

ALTER TABLE `t_esp_service_process_info`
ADD COLUMN `node_message`  varchar(255) DEFAULT '' COMMENT '备注信息';

ALTER TABLE `t_esp_refund_info`
ADD COLUMN `jd_return_type`  varchar(12) DEFAULT '' COMMENT '京东退换货返回方式';


ALTER TABLE `t_esp_goods_base_info`
ADD COLUMN `newCreat_date`  datetime  DEFAULT '1900-01-01 00:00:00' comment '新品创建时间';
