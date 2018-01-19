use esp;
ALTER TABLE `t_esp_bsdiff_info`
ADD COLUMN `if_compel_update`  tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否强制更新1：是，0：否'