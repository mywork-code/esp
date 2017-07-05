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

DROP TABLE  IF EXISTS esp.`t_esp_jd_category`;
CREATE TABLE `t_esp_jd_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cat_id` bigint(20) NOT NULL COMMENT '京东类目id',
  `name` varchar(20) NOT NULL COMMENT '类目名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级id',
  `cat_class` int NOT NULL COMMENT '级别',
  `category_id1` bigint(20) NOT NULL COMMENT 'apass类目1',
  `category_id2` bigint(20) NOT NULL COMMENT 'apass类目2',
  `category_id3` bigint(20) NOT NULL COMMENT 'apass类目3',
 `flag` tinyint(1) DEFAULT 0 COMMENT '是否关联安家趣花类目，1：已关联；0:未关联',
 `status` tinyint(1) DEFAULT 0 COMMENT '京东类目是否有效 1：有效，0 无效',
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
  `image_path` varchar(255) DEFAULT NULL COMMENT '主图地址',
  `weight` decimal(10,0) DEFAULT '0' COMMENT '重量',
  `upc` varchar(20) DEFAULT '' COMMENT '条形码',
  `product_area` varchar(20) DEFAULT '' COMMENT '产地',
  `sale_unit` varchar(10) DEFAULT '' COMMENT '销售单位',
  `ware_qd` varchar(255) DEFAULT '' COMMENT '包装清单',
  `state` tinyint(4) NOT NULL COMMENT '上下架状态',
  `first_category` int(10) NOT NULL COMMENT '一级分类',
  `second_category` int(10) NOT NULL COMMENT '二级分类',
  `third_category` int(10) NOT NULL COMMENT '三级分类',
  `similar_skus` varchar(255) DEFAULT '' COMMENT '同类skuids',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='京东商品表';


ALTER TABLE `t_esp_system_param_info`
ADD COLUMN `price_cost_date` decimal(10,4) DEFAULT 0 COMMENT '保本率(售价/成本价*100%=保本率)';
