/*==============================================================*/
/* Table: LS_RBAC_USERS                                         */
/*==============================================================*/
insert into LS_RBAC_USERS (ID, USER_NAME, PASSWORD, REAL_NAME, 
MOBILE, EMAIL, STATUS, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE) 
VALUES ('admin', 'admin','$2a$10$4AIZHNj6cuYDhUvIH/UPAe9hm/D.VOTNyRP1f5Rar1sHHcjgADFyu', 
'admin', '', '', '1', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
