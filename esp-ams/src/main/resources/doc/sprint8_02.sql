ALTER TABLE `t_esp_feedback`
ADD COLUMN `type`  varchar(20) NOT NULL DEFAULT '' COMMENT '类型（ajp：安家派 ；ajqh：安家趣花）',
ADD COLUMN `module`  varchar(20) NOT NULL DEFAULT '' COMMENT '安家趣花关联模块（shop：购物；credit：额度）',
ADD COLUMN `picture`  varchar(250) NOT NULL DEFAULT '' COMMENT '上传图片最多三张';
