
DROP TABLE  IF EXISTS esp.`t_esp_work_city_jd`;
CREATE TABLE `t_esp_work_city_jd` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(40) NOT NULL DEFAULT '' COMMENT '编码',
  `PROVINCE` varchar(200) NOT NULL DEFAULT '' COMMENT '省',
  `CITY` varchar(200) NOT NULL DEFAULT '' COMMENT '市',
  `DISTRICT` varchar(200) NOT NULL DEFAULT '' COMMENT '县',
  `TOWNS` varchar(200) NOT NULL DEFAULT '' COMMENT '乡镇',
  `PARENT` bigint(20) NOT NULL DEFAULT '0' COMMENT '父节点',
  `LEVEL` varchar(2) NOT NULL DEFAULT '' COMMENT '第几级目录',
  `PREFIX` varchar(10) NOT NULL DEFAULT '' COMMENT '首字母',
  `SPELL` varchar(50) NOT NULL DEFAULT '' COMMENT '城市中文拼音',
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
 `flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否关联安家趣花类目，1：已关联；0:未关联',
 `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '京东类目是否有效 1：有效，0 无效',
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
  `image_path` varchar(255) NOT NULL DEFAULT '' COMMENT '主图地址',
  `weight` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '重量',
  `upc` varchar(255) NOT NULL DEFAULT '' COMMENT '条形码',
  `product_area` varchar(20) NOT NULL DEFAULT '' COMMENT '产地',
  `sale_unit` varchar(10) NOT NULL DEFAULT '' COMMENT '销售单位',
  `ware_qd` varchar(255) NOT NULL DEFAULT '' COMMENT '包装清单',
  `state` tinyint(4) NOT NULL COMMENT '上下架状态',
  `first_category` int(10) NOT NULL COMMENT '一级分类',
  `second_category` int(10) NOT NULL COMMENT '二级分类',
  `third_category` int(10) NOT NULL COMMENT '三级分类',
  `similar_skus` varchar(255) NOT NULL DEFAULT '' COMMENT '同类skuids',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='京东商品表';

DROP TABLE  IF EXISTS esp.`t_esp_goods_sales_volume`;
CREATE TABLE `t_esp_goods_sales_volume` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(11) NOT NULL COMMENT '商品ID',
  `sales_num` int(11) NOT NULL DEFAULT '0' COMMENT '销量',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品销量表';

DROP TABLE  IF EXISTS esp.`t_esp_service_error`;
CREATE TABLE `t_esp_service_error` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
 `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
   `order_id` varchar(128) NOT NULL DEFAULT '' comment '订单id',
   `type` varchar(64) NOT NULL DEFAULT '' comment '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务异常表';
