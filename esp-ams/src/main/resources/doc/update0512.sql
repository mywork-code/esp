--新增加商品类别表
DROP TABLE IF EXISTS esp.t_esp_category;
CREATE TABLE esp.`t_esp_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `category_name` varchar(20) NOT NULL COMMENT '类目名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级id',
  `sort_order` bigint(20) NOT NULL COMMENT '排序',
  `level` bigint(20) NOT NULL COMMENT '级别',
  `picture_url` varchar(100) DEFAULT NULL COMMENT '图片的路径',
  `create_user` varchar(50) NOT NULL COMMENT '创建人',
  `update_user` varchar(50) NOT NULL COMMENT '修改人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品分类信息表';

DROP TABLE IF EXISTS esp.t_esp_feedback;
CREATE TABLE esp.`t_esp_feedback` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
 `feedback_type` varchar(20) NOT NULL COMMENT '反馈类型(page_error:页面异常,fun_error:功能异常,other:其他)',
 `comments` varchar(300) NOT NULL COMMENT '反馈内容',
 `mobile` varchar(50) NOT NULL COMMENT '反馈人手机号',
 `create_date` datetime NOT NULL COMMENT '反馈时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='意见反馈信息表';

--在商品的基础表中添加三列
ALTER TABLE esp.`t_esp_goods_base_info` ADD COLUMN `category_id1`  bigint(20) NULL COMMENT '商品分类一级Id' AFTER `id`;
ALTER TABLE esp.`t_esp_goods_base_info` ADD COLUMN `category_id2`  bigint(20) NULL COMMENT '商品分类二级Id' AFTER `category1_id`;
ALTER TABLE esp.`t_esp_goods_base_info` ADD COLUMN `category_id3`  bigint(20) NULL COMMENT '商品分类三级Id' AFTER `category2_id`;