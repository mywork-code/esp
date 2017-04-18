/*==============================================================*/
/* Table: LS_CRM_USERS                                          */
/*==============================================================*/
create table LS_RBAC_USERS
(
   ID                   VARCHAR(50) not null comment 'ID标识',
   USER_NAME            VARCHAR(50) not null comment '用户名',
   PASSWORD             VARCHAR(100) not null comment '密码',
   REAL_NAME            VARCHAR(50) not null comment '真实姓名',
   MOBILE               VARCHAR(20) not null comment '手机',
   EMAIL                VARCHAR(50) comment '电子邮件地址',
   STATUS               VARCHAR(5) not null comment '有效状态 1-有效 0-无效',
   DEPARTMENT           VARCHAR(20) comment '部门',
   CREATED_BY           VARCHAR(50) not null comment '创建人',
   CREATED_DATE         datetime not null comment '创建日期',
   UPDATED_BY           VARCHAR(50) not null comment '更新人',
   UPDATED_DATE         datetime not null comment '更新日期',
   primary key (ID)
);

alter table LS_RBAC_USERS comment '客户信息表';

/*==============================================================*/
/* Index: UNIQUE_USER_NAME                                      */
/*==============================================================*/
create unique index UNIQUE_USER_NAME on LS_RBAC_USERS
(
   USER_NAME
);


/*==============================================================*/
/* Table: LS_CRM_ROLES                                          */
/*==============================================================*/
create table LS_RBAC_ROLES
(
   ID                   VARCHAR(50) not null comment '主键标识',
   ROLE_CODE            VARCHAR(50) not null comment '角色编码',
   ROLE_NAME            VARCHAR(100) not null comment '角色名称',
   DESCRIPTION          varchar(500) comment '描述',
   CREATED_BY           VARCHAR(50) not null comment '创建人',
   CREATED_DATE         datetime not null comment '创建日期',
   UPDATED_BY           VARCHAR(50) not null comment '更新人',
   UPDATED_DATE         datetime not null comment '更新日期',
   primary key (ID)
);
alter table LS_RBAC_ROLES comment '角色表';

/*==============================================================*/
/* Index: UNIQUE_ROLE_CODE                                      */
/*==============================================================*/
create unique index UNIQUE_ROLE_CODE on LS_RBAC_ROLES
(
   ROLE_CODE
);


/*==============================================================*/
/* Table: LS_CRM_MENUS                                          */
/*==============================================================*/
create table LS_RBAC_MENUS
(
   ID                   VARCHAR(50) not null comment '主键标识ID',
   TEXT                 VARCHAR(50) comment '文本标题',
   ICON_CLS             VARCHAR(50) comment '图标样式',
   URL                  VARCHAR(100) comment '打开地址',
   PARENT_ID            VARCHAR(50) comment '父节点',
   DISPLAY              INTEGER comment '显示顺序',
   CREATED_BY           VARCHAR(50) not null comment '创建人',
   CREATED_DATE         datetime not null comment '创建日期',
   UPDATED_BY           VARCHAR(50) not null comment '更新人',
   UPDATED_DATE         datetime not null comment '更新日期',
   primary key (ID)
);

alter table LS_RBAC_MENUS comment '菜单表';


/*==============================================================*/
/* Table: LS_CRM_USER_ROLE                                      */
/*==============================================================*/
create table LS_RBAC_USER_ROLE
(
   ID                   VARCHAR(50) not null comment 'ID',
   USER_ID              VARCHAR(50) not null comment '用户编码',
   ROLE_ID              VARCHAR(50) not null comment '角色ID',
   CREATED_BY           VARCHAR(50) not null comment '创建人',
   CREATED_DATE         datetime not null    comment '创建日期',
   UPDATED_BY           VARCHAR(50) not null comment '更新人',
   UPDATED_DATE         datetime not null    comment '更新日期',
   primary key (ID)
);

alter table LS_RBAC_USER_ROLE comment '用户角色表';

/*==============================================================*/
/* Index: UNIQUE_USER_ROLE_ID                                   */
/*==============================================================*/
create unique index UNIQUE_USER_ROLE_ID on LS_RBAC_USER_ROLE
(
   USER_ID,
   ROLE_ID
);

alter table LS_RBAC_USER_ROLE add constraint FK_USER_ROLE_USERID foreign key (USER_ID) 
	references LS_RBAC_USERS (ID) on delete restrict on update restrict;
alter table LS_RBAC_USER_ROLE add constraint FK_USER_ROLE_ROLEID foreign key (ROLE_ID) 
	references LS_RBAC_ROLES (ID) on delete restrict on update restrict;
	
/*==============================================================*/
/* Table: LS_CRM_ROLE_MENU                                      */
/*==============================================================*/
create table LS_RBAC_ROLE_MENU
(
   ID                   VARCHAR(50) not null comment 'ID',
   ROLE_ID              VARCHAR(50) not null comment '角色ID',
   MENU_ID              VARCHAR(50) not null comment '菜单ID',
   CREATED_BY           VARCHAR(50) not null comment '创建人',
   CREATED_DATE         datetime not null    comment '创建日期',
   UPDATED_BY           VARCHAR(50) not null comment '更新人',
   UPDATED_DATE         datetime not null    comment '更新日期',
   primary key (ID)
);

alter table LS_RBAC_ROLE_MENU comment '角色菜单表';

/*==============================================================*/
/* Index: UNIQUE_ROLE_MENU_ID                                   */
/*==============================================================*/
create unique index UNIQUE_ROLE_MENU_ID on LS_RBAC_ROLE_MENU
(
   ROLE_ID,
   MENU_ID
);

alter table LS_RBAC_ROLE_MENU add constraint FK_ROLE_MENU_MENUID foreign key (MENU_ID) 
	references LS_RBAC_MENUS (ID) on delete restrict on update restrict;
alter table LS_RBAC_ROLE_MENU add constraint FK_ROLE_MENU_ROLEID foreign key (ROLE_ID) 
	references LS_RBAC_ROLES (ID) on delete restrict on update restrict;
	
/*==============================================================*/
/* Table: LS_RBAC_PERMISSION                                    */
/*==============================================================*/
create table LS_RBAC_PERMISSIONS
(
   ID                   VARCHAR(50)  not null comment 'ID',
   PERMISSION_CODE      VARCHAR(100) not null comment '权限编码',
   PERMISSION_NAME      VARCHAR(100) not null comment '权限名称',
   DESCRIPTION          VARCHAR(200) not null comment '权限描述',
   CREATED_BY           VARCHAR(50)  not null comment '创建人',
   CREATED_DATE         datetime     not null comment '创建日期',
   UPDATED_BY           VARCHAR(50)  not null comment '更新人',
   UPDATED_DATE         datetime     not null comment '更新日期',
   primary key (ID)
);

alter table LS_RBAC_PERMISSIONS comment '权限表';

/*==============================================================*/
/* Index: UNIQUE_PERMISSION_CODE                                */
/*==============================================================*/
create unique index UNIQUE_PERMISSION_CODE on LS_RBAC_PERMISSIONS
(
   PERMISSION_CODE
);

/*==============================================================*/
/* Table: LS_RBAC_ROLE_PERMISSION                               */
/*==============================================================*/
create table LS_RBAC_ROLE_PERMISSION
(
   ID                   VARCHAR(50) not null comment 'ID',
   ROLE_ID              VARCHAR(50) not null comment '角色ID',
   PERMISSION_ID        VARCHAR(50) not null comment '权限ID',
   CREATED_BY           VARCHAR(50) not null comment '创建人',
   CREATED_DATE         datetime    not null comment '创建日期',
   UPDATED_BY           VARCHAR(50) not null comment '更新人',
   UPDATED_DATE         datetime    not null comment '更新日期',
   primary key (ID)
);

alter table LS_RBAC_ROLE_PERMISSION comment '角色权限表';

/*==============================================================*/
/* Index: UNIQUE_ROLE_PERMISSION_ID                             */
/*==============================================================*/
create unique index UNIQUE_ROLE_PERMISSION_ID on LS_RBAC_ROLE_PERMISSION
(
   ROLE_ID,
   PERMISSION_ID
);

alter table LS_RBAC_ROLE_PERMISSION add constraint FK_ROLE_PERMISSION_PERMISSIONID 
	foreign key (PERMISSION_ID) references LS_RBAC_PERMISSIONS (ID) on delete restrict on update restrict;

alter table LS_RBAC_ROLE_PERMISSION add constraint FK_ROLE_PERMISSION_ROLEID 
	foreign key (ROLE_ID) references LS_RBAC_ROLES (ID) on delete restrict on update restrict;